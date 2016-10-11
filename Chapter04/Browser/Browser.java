// Browser.java

import java.awt.*;
import java.awt.event.*;

import java.io.*;

import javax.swing.*;
import javax.swing.event.*;

public class Browser extends JFrame implements HyperlinkListener
{
   private JTextField txtURL;

   private JTabbedPane tp;

   private JLabel lblStatus;

   private ImageIcon ii = new ImageIcon ("close.gif");

   private Dimension iiSize = new Dimension (ii.getIconWidth (),
                                             ii.getIconHeight ());

   private int tabCounter = 0;

   public Browser ()
   {
      super ("Browser");
      setDefaultCloseOperation (EXIT_ON_CLOSE);

      JMenuBar mb = new JMenuBar ();
      JMenu mFile = new JMenu ("File");
      JMenuItem mi = new JMenuItem ("Add Tab");
      ActionListener addTabl = new ActionListener ()
                               {
                                   public void actionPerformed (ActionEvent e)
                                   {
                                      addTab ();        
                                   }
                               };
      mi.addActionListener (addTabl);
      mFile.add (mi);
      mb.add (mFile);
      setJMenuBar (mb);

      JPanel pnlURL = new JPanel ();
      pnlURL.setLayout (new BorderLayout ());
      pnlURL.add (new JLabel ("URL: "), BorderLayout.WEST);
      txtURL = new JTextField ("");
      pnlURL.add (txtURL, BorderLayout.CENTER);
      getContentPane ().add (pnlURL, BorderLayout.NORTH);

      tp = new JTabbedPane ();
      addTab ();
      getContentPane ().add (tp, BorderLayout.CENTER);  

      lblStatus = new JLabel (" ");
      getContentPane ().add (lblStatus, BorderLayout.SOUTH);

      ActionListener al;
      al = new ActionListener ()
           {
               public void actionPerformed (ActionEvent ae)
               {
                  try
                  {
                      Component c = tp.getSelectedComponent ();
                      JScrollPane sp = (JScrollPane) c;
                      c = sp.getViewport ().getView ();
                      JEditorPane ep = (JEditorPane) c;
                      ep.setPage (ae.getActionCommand ());
                  }
                  catch (Exception e)
                  {
                      lblStatus.setText ("Browser problem: "+e.getMessage ());
                  }
               }
           };
      txtURL.addActionListener (al);

      setSize (300, 300);
      setVisible (true);
   }

   void addTab ()
   {
      JEditorPane ep = new JEditorPane ();
      ep.setEditable (false);
      ep.addHyperlinkListener (this);
      tp.addTab (null, new JScrollPane (ep));

      JButton tabCloseButton = new JButton (ii);
      tabCloseButton.setActionCommand (""+tabCounter);
      tabCloseButton.setPreferredSize (iiSize);

      ActionListener al;
      al = new ActionListener ()
           {
               public void actionPerformed (ActionEvent ae)
               {
                  JButton btn = (JButton) ae.getSource ();
                  String s1 = btn.getActionCommand ();
                  for (int i = 1; i < tp.getTabCount (); i++)
                  {
                       JPanel pnl = (JPanel) tp.getTabComponentAt (i);
                       btn = (JButton) pnl.getComponent (0);
                       String s2 = btn.getActionCommand ();
                       if (s1.equals (s2))
                       {
                           tp.removeTabAt (i);
                           break;
                       }
                  }
               }
           };
      tabCloseButton.addActionListener (al);

      if (tabCounter != 0)
      {
          JPanel pnl = new JPanel ();
          pnl.setOpaque (false);
          pnl.add (tabCloseButton);
          tp.setTabComponentAt (tp.getTabCount ()-1, pnl);
          tp.setSelectedIndex (tp.getTabCount ()-1);
      }

      tabCounter++;
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
                          new Browser ();
                       }
                   };
      EventQueue.invokeLater (r);
   }
}
