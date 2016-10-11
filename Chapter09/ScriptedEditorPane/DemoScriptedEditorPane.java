// DemoScriptedEditorPane.java

import java.awt.*;

import java.io.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class DemoScriptedEditorPane extends JFrame
   implements HyperlinkListener
{
   private JLabel lblStatus;

   DemoScriptedEditorPane ()
   {
      super ("Demo ScriptedEditorPane");
      setDefaultCloseOperation (EXIT_ON_CLOSE);

      ScriptedEditorPane pane = null;
      try
      {
          // Create a scripted editor pane component that loads the contents
          // of a test.html file, which is located in the current directory.

          pane = new ScriptedEditorPane ("file:///"+
                                         new File ("").getAbsolutePath ()+
                                         "/demo.html");
          pane.setEditable (false);
          pane.setBorder (BorderFactory.createEtchedBorder ());
          pane.addHyperlinkListener (this);
      }
      catch (Exception e)
      {
         System.out.println (e.getMessage ());
         return;
      }
      getContentPane ().add (pane, BorderLayout.CENTER);

      lblStatus = new JLabel (" ");
      lblStatus.setBorder (BorderFactory.createEtchedBorder ());
      getContentPane ().add (lblStatus, BorderLayout.SOUTH);

      setSize (350, 250);
      setVisible (true);
   }

   public void hyperlinkUpdate (HyperlinkEvent hle)
   {
      HyperlinkEvent.EventType evtype = hle.getEventType ();

      if (evtype == HyperlinkEvent.EventType.ENTERED)
          lblStatus.setText (hle.getURL ().toString ());
      else
      if (evtype == HyperlinkEvent.EventType.EXITED)
          lblStatus.setText (" ");
   }

   public static void main (String [] args)
   {
      Runnable r = new Runnable ()
                   {
                       public void run ()
                       {
                          new DemoScriptedEditorPane ();
                       }
                   };
      EventQueue.invokeLater (r);
   }
}
