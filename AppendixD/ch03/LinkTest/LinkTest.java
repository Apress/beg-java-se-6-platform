// LinkTest.java

import java.awt.*;
import java.awt.event.*;

import java.io.*;

import java.net.*;

import javax.swing.*;

public class LinkTest extends JFrame
{
   public LinkTest ()
   {
      super ("Link Test");
      setDefaultCloseOperation (EXIT_ON_CLOSE);

      JButton btnAbout = new JButton ("About");
      ActionListener al;
      al = new ActionListener ()
               {
                  public void actionPerformed (ActionEvent e)
                  {
                     new About (LinkTest.this, "About LinkTest");
                  }
               };
      btnAbout.addActionListener (al);

      getContentPane ().add (btnAbout);

      setSize (175, 75);
      setVisible (true);
   }

   public static void main (String [] args)
   {
      Runnable r = new Runnable ()
                   {
                       public void run ()
                       {
                          new LinkTest ();
                       }
                   };
      EventQueue.invokeLater (r);
   }
}

class About extends JDialog
{
   About (JFrame frame, String title)
   {
      super (frame, "About", true);

      getContentPane ().add (new Link ("Visit java.sun.com",
                                       "http://java.sun.com", Color.blue,
                                       Color.red), BorderLayout.NORTH);

      JPanel pnl = new JPanel ();
      JButton btnOk = new JButton ("Ok");
      btnOk.addActionListener (new ActionListener ()
                               {
                                   public void actionPerformed (ActionEvent e)
                                   {
                                      dispose ();
                                   }
                               });
      pnl.add (btnOk);
      getContentPane ().add (pnl, BorderLayout.SOUTH);

      pack ();
      setResizable (false);
      setLocationRelativeTo (frame);
      setVisible (true); // This is a self-visible dialog; you don't need to
                         // make it visible.
   }
}

class Link extends JLabel
{
   private Desktop desktop;
   private String link;
   private Color textColor, activeColor;

   Link (String text, String link, Color textColor, Color activeColor)
   {
      super (text, JLabel.CENTER);

      this.link = link;                          
      this.textColor = textColor;
      this.activeColor = activeColor; // Link color when mouse button pressed.

      setForeground (textColor);

      if (Desktop.isDesktopSupported ())
          desktop = Desktop.getDesktop ();

      addMouseListener (new LinkListener ());
   }

   class LinkListener extends MouseAdapter
   {
      private URI uri;

      public void mousePressed (MouseEvent e)
      {
         setForeground (activeColor);
      }

      public void mouseReleased (MouseEvent e)
      {
         setForeground (textColor);

         if (Link.this.contains (e.getX (), e.getY ()))
         {
             if (desktop != null &&
                 desktop.isSupported (Desktop.Action.BROWSE))
                 try
                 {
                     if (uri == null)
                         uri = new URI (link);

                     // Although browse() is being invoked on the
                     // event-dispatch thread, this should not prove to be
                     // disruptive to the GUI because the call to launch the
                     // browser is not time-consuming.

                     desktop.browse (uri);
                 }
                 catch (Exception ex)
                 {
                     JOptionPane.showMessageDialog (null, ex.getMessage ());
                 }
         }
      }
   }
}
