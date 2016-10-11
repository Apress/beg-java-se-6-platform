// ScriptedEditorPane.java

import java.awt.*;

import java.io.*;

import java.net.*;

import java.util.*;

import javax.script.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

public class ScriptedEditorPane extends JEditorPane
{
   // The anchor element associated with the most recent hyperlink event. It
   // probably should be located in the ScriptEnvironment, where it is used.

   private javax.swing.text.Element currentAnchor;

   // The Rhino script engine.

   private ScriptEngine engine;

   // The Java environment corresponding to the JavaScript document object.

   private ScriptEnvironment env;

   // An initialization script that connects a JavaScript document object with
   // linkcolor and defaultlinkcolor properties, to an adapter with __get__()
   // and __put()__ member functions, which access the script environment.

   private String initScript =
      "var document = new JSAdapter ({"+
      "    __get__ : function (name)"+
      "              {"+
      "                 if (name == 'defaultlinkcolor')"+
      "                     return env.getDefaultLinkColor ();"+
      "                 else"+
      "                 if (name == 'linkcolor')"+
      "                     return env.getLinkColor ();"+
      "              },"+
      "    __put__ : function (name, value)"+
      "              {"+
      "                 if (name == 'linkcolor')"+
      "                     env.setLinkColor (value);"+
      "              }"+
      "})";

   // The concatenated contents of all <script></script> sections in top-down
   // order.

   private String script;

   // Create a scripted editor pane without an HTML document. A document can
   // be subsequently added via a setPage() call.

   public ScriptedEditorPane () throws ScriptException
   {
      ScriptEngineManager manager = new ScriptEngineManager ();
      engine = manager.getEngineByName ("rhino");

      // For convenience, I throw a ScriptException instead of creating a new
      // exception class for this purpose. 

      if (engine == null)
          throw new ScriptException ("no Rhino script engine");

      // Set up environment for JSAdapter and evaluate initialization script.

      env = new ScriptEnvironment ();
      engine.put ("env", env);
      engine.eval (initScript);

      addHyperlinkListener (new ScriptedLinkListener ());
   }

   // Create a scripted editor pane with the specified HTML document.

   public ScriptedEditorPane (String pageUrl)
      throws IOException, ScriptException
   {
      this ();
      setPage (pageUrl);
   }

   // Associate an HTML document with the scripted editor pane. Prior to the
   // association, the document is parsed to extract the contents of all
   // <script></script sections.

   public void setPage (URL url) throws IOException
   {
      InputStreamReader isr = new InputStreamReader (url.openStream ());
      BufferedReader reader;
      reader = new BufferedReader (isr);
      Callback cb = new Callback ();
      new ParserDelegator ().parse (reader, cb, true);
      reader.close ();
      script = cb.getScript ();

      super.setPage (url);
   }

   // Extract the contents of all <script> sections via this callback. Because
   // the parser exposes these contents as if they were HTML comments, care is
   // needed to differentiate them from actual HTML comments. Learn more about
   // the parser from Jeff Heaton's "Parsing HTML with Swing" article
   // (http://www.samspublishing.com/articles/article.asp?p=31059&seqNum=1).

   private class Callback extends HTMLEditorKit.ParserCallback
   {
      // A <script></script> section is being processed when this variable is
      // true. It defaults to false.

      private boolean inScript;

      // The contents of all <script></script> sections are stored in a
      // StringBuffer instead of a String to minimize String object creation.

      private StringBuffer scriptBuffer = new StringBuffer ();

      // Return the script.

      String getScript ()
      {
         return scriptBuffer.toString ();
      }

      // Only append the data to the string buffer if the parser has already
      // detected a <script> tag.

      public void handleComment (char [] data, int pos)
      {
         if (inScript)
             scriptBuffer.append (data);
      }

      // Detect a <script> tag.

      public void handleStartTag (HTML.Tag t,
                                  MutableAttributeSet a, int pos)
      {
         if (t == HTML.Tag.SCRIPT)
             inScript = true;
      }

      // Detect a </script> tag.

      public void handleEndTag (HTML.Tag t, int pos)
      {
         if (t == HTML.Tag.SCRIPT)
             inScript = false;
      }
   }

   // Provide the glue between document's properties and the Java environment
   // in which the script runs.

   private class ScriptEnvironment
   {
      // The default color of an anchor tag's link text as determined by the
      // current CSS style sheet.

      private Color defaultLinkColor;

      // Create a script environment. Extract the default link color via the
      // current CSS style sheet.

      ScriptEnvironment ()
      {
         HTMLEditorKit kit;
         kit = (HTMLEditorKit) getEditorKitForContentType ("text/html");
         StyleSheet ss = kit.getStyleSheet ();
         Style style = ss.getRule ("a"); // Get rule for anchor tag.
         if (style != null)
         {
             Object o = style.getAttribute (CSS.Attribute.COLOR);
             defaultLinkColor = ss.stringToColor (o.toString ());
         }
      }

      // Return the default link color.

      public Color getDefaultLinkColor ()
      {
         return defaultLinkColor;
      }

      // Return the link color of the current anchor element.

      public Color getLinkColor ()
      {
         AttributeSet as = currentAnchor.getAttributes ();
         return StyleConstants.getForeground (as);
      }

      // Set the link color for the current anchor element.

      public void setLinkColor (Color color)
      {
         StyleContext sc = StyleContext.getDefaultStyleContext ();
         AttributeSet as = sc.addAttribute (SimpleAttributeSet.EMPTY,
                                            StyleConstants.Foreground,
                                            color);
         ((HTMLDocument) currentAnchor.getDocument ()).
             setCharacterAttributes (currentAnchor.getStartOffset (),
                                     currentAnchor.getEndOffset ()-
                                     currentAnchor.getStartOffset(), as,
                                     false);
      }
   }

   // Provide a listener for identifying the current anchor element, detecting
   // an onmouseover attribute (for an entered event) or an onmouseout element
   // (for an exited event) that is associated with this element's <a> tag,
   // and evaluating this attribute's JavaScript code.

   private class ScriptedLinkListener implements HyperlinkListener
   {
      // For convenience, this listener's hyperlinkUpdate() method ignores
      // HTML frames.

      public void hyperlinkUpdate (HyperlinkEvent he)
      {                      
         HyperlinkEvent.EventType type = he.getEventType ();

         if (type == HyperlinkEvent.EventType.ENTERED)
         {
             currentAnchor = he.getSourceElement ();
             AttributeSet as = currentAnchor.getAttributes ();
             AttributeSet asa = (AttributeSet) as.getAttribute (HTML.Tag.A);
             if (asa != null)
             {
                 Enumeration<?> ean = asa.getAttributeNames ();
                 while (ean.hasMoreElements ())
                 {
                    Object o = ean.nextElement ();
                    if (o instanceof String)
                    {
                        String attr = o.toString ();
                        if (attr.equalsIgnoreCase ("onmouseover"))
                        {
                            String value = (String) asa.getAttribute (o);
                            try
                            {
                                engine.eval (script+value);
                            }
                            catch (ScriptException se)
                            {
                                System.out.println (se);
                            }
                            break;
                        }
                    }
                 }
             }
         }
         else
         if (type == HyperlinkEvent.EventType.EXITED)
         {
             currentAnchor = he.getSourceElement ();
             AttributeSet as = currentAnchor.getAttributes ();
             AttributeSet asa = (AttributeSet) as.getAttribute (HTML.Tag.A);
             if (asa != null)
             {
                 Enumeration<?> ean = asa.getAttributeNames ();
                 while (ean.hasMoreElements ())
                 {
                    Object o = ean.nextElement ();
                    if (o instanceof String)
                    {
                        String attr = o.toString ();
                        if (attr.equalsIgnoreCase ("onmouseout"))
                        {
                            String value = (String) asa.getAttribute (o);
                            try
                            {
                                engine.eval (script+value);
                            }
                            catch (ScriptException se)
                            {
                                System.out.println (se);
                            }
                            break;
                        }
                    }
                 }
             }
         }
      }
   }
}                                                  
