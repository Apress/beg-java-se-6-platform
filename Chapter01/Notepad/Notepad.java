// Notepad.java

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

public class Notepad extends JFrame
{
   private JTextArea document = new JTextArea (10, 40);

   public Notepad ()
   {
      super ("Notepad 1.0");
      setDefaultCloseOperation (EXIT_ON_CLOSE);

      JMenuBar menuBar = new JMenuBar ();

      JToolBar toolBar = new JToolBar ();

      JMenu menu = new JMenu ("File");
      menu.setMnemonic (KeyEvent.VK_F);

      Action newAction = new NewAction (document);
      menu.add (new JMenuItem (newAction));
      toolBar.add (newAction);

      // Java SE 6 introduces a setHideActionText() method to determine
      // whether or not a button displays text originating from an action. To
      // demonstrate this method, the code below makes it possible for a
      // toolbar button to display the action's text -- a toolbar button does
      // not display this text in its default state.

      JButton button = (JButton) toolBar.getComponentAtIndex (0);
      button.setHideActionText (false);

      menuBar.add (menu);

      menu = new JMenu ("View");
      menu.setMnemonic (KeyEvent.VK_V);

      Action statAction = new StatAction (this);
      menu.add (new JCheckBoxMenuItem (statAction));

      menuBar.add (menu);

      setJMenuBar (menuBar);

      getContentPane ().add (toolBar, BorderLayout.NORTH);
      getContentPane ().add (document, BorderLayout.CENTER);

      pack ();
      setVisible (true);
   }

   public static void main (String [] args)
   {
      Runnable r = new Runnable ()
                   {
                       public void run ()
                       {
                          new Notepad ();
                       }
                   };
      EventQueue.invokeLater (r);
   }
}

class NewAction extends AbstractAction
{
   JTextArea document;

   NewAction (JTextArea document)
   {
      this.document = document;

      putValue (NAME, "New");
      putValue (MNEMONIC_KEY, KeyEvent.VK_N);
      putValue (SMALL_ICON, new ImageIcon ("newicon_16x16.gif"));

      // Before Java SE 6, an action's SMALL_ICON key was used to assign the 
      // same icon to a button and a menu item. Java SE 6 now makes it
      // possible to assign different icons to these components. If an icon
      // is added via LARGE_ICON_KEY, this icon appears on buttons, whereas
      // an icon added via SMALL_ICON appears on menu items. However, if there
      // is no LARGE_ICON_KEY-based icon, the SMALL_ICON-based icon is
      // assigned to a toolbar's button (for example), in addition to a menu
      // item.

      putValue (LARGE_ICON_KEY, new ImageIcon ("newicon_32x32.gif"));
   }

   public void actionPerformed (ActionEvent e)
   {
      document.setText ("");
   }
}

class StatAction extends AbstractAction
{
   private JFrame frame;
   private JLabel labelStatus = new JLabel ("Notepad 1.0");

   StatAction (JFrame frame)
   {
      this.frame = frame;

      putValue (NAME, "Status Bar");
      putValue (MNEMONIC_KEY, KeyEvent.VK_A);

      // By default, a mnemonic decoration is presented under the leftmost
      // character in a string having multiple occurrences of this character.
      // For example, the previous putValue (MNEMONIC_KEY, KeyEvent.VK_A);
      // results in the "a" in "Status" being decorated. If you prefer to
      // decorate a different occurrence of a letter (such as the "a" in
      // "Bar"), you can now do this thanks to Java SE 6's
      // displayedMnemonicIndex property and DISPLAYED_MNEMONIC_INDEX_KEY. In
      // the code below, the zero-based index (8) of the "a" appearing in
      // "Bar" is chosen as the occurrence of "a" to receive the decoration.

      putValue (DISPLAYED_MNEMONIC_INDEX_KEY, 8);

      // Java SE 6 now makes it possible to choose the initial selection state
      // of a toggling component. In this application, the component is a
      // JCheckBoxMenuItem that is responsible for determining whether or not
      // to display a status bar. Initially, the status bar will not be shown,
      // which is why false is assigned to the selected property in the method
      // call below.

      putValue (SELECTED_KEY, false);

      labelStatus = new JLabel ("Notepad 1.0");
      labelStatus.setBorder (new EtchedBorder ());
   }

   public void actionPerformed (ActionEvent e)
   {
      // Because a component updates the selected property, it is easy to find
      // out the current selection setting, and then use this setting to
      // either add or remove the status bar.

      Boolean selection = (Boolean) getValue (SELECTED_KEY);
      if (selection)
          frame.getContentPane ().add (labelStatus, BorderLayout.SOUTH);
      else
          frame.getContentPane ().remove (labelStatus);

      frame.getRootPane ().revalidate ();
   }
}
