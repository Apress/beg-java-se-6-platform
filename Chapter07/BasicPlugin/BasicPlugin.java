// BasicPlugin.java

// Unix compile   : javac -cp $JAVA_HOME/lib/jconsole.jar BasicPlugin.java
//
// Windows compile: javac -cp %JAVA_HOME%/lib/jconsole.jar BasicPlugin.java

import java.util.*;

import javax.swing.*;

import com.sun.tools.jconsole.*;

public class BasicPlugin extends JConsolePlugin
{
   private Map<String, JPanel> tabs = null;

   private BasicTab basicTab = null;

   public Map<String, JPanel> getTabs ()
   {
      System.out.println ("getTabs() called");

      if (tabs == null)
      {
          tabs = new LinkedHashMap<String, JPanel> ();

          basicTab = new BasicTab ();
          tabs.put ("Basic", basicTab);
      }
      return tabs;
   }

   public SwingWorker<?, ?> newSwingWorker ()
   {
      System.out.println ("newSwingWorker() called");

      return new BasicTask (basicTab);
   }
}

class BasicTab extends JPanel
{
   private JLabel label = new JLabel ();

   BasicTab ()
   {
      add (label);
   }

   void refreshTab ()
   {
      label.setText (new Date ().toString ());
   }
}

class BasicTask extends SwingWorker<Void, Void>
{
   private BasicTab basicTab;

   BasicTask (BasicTab basicTab)
   {
      this.basicTab = basicTab;
   }

   @Override
   public Void doInBackground ()
   {
      System.out.println ("doInBackground() called");

      // Nothing needs to be done, but this method needs to be present.
      return null;
   }

   @Override
   public void done ()
   {
      System.out.println ("done() called");

      basicTab.refreshTab ();   
   }
}
