// QuickLaunch.java

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;

import java.io.*;

import javax.swing.*;

public class QuickLaunch
{
   static AboutBox aboutBox;
   static ChooseApplication chooseApplication;

   public static void main (String [] args)
   {
      if (!SystemTray.isSupported ())
      {
          JOptionPane.showMessageDialog (null, "System tray is not supported");
          System.exit (0);
      }

      SystemTray systemTray = SystemTray.getSystemTray ();

      // Create the tray icon's image.

      Dimension size = systemTray.getTrayIconSize ();
      BufferedImage bi = new BufferedImage (size.width, size.height,
                                            BufferedImage.TYPE_INT_RGB);
      Graphics g = bi.getGraphics ();
      g.setColor (Color.black);
      g.fillRect (0, 0, size.width, size.height);
      g.setFont (new Font ("Arial", Font.BOLD, 10));
      g.setColor (Color.yellow);
      g.drawString ("QL", 1, 11);

      try
      {
          // Create and populate a popup menu with QuickLaunch menu items.
          // Attach an action listener to each menu item that does something
          // useful.

          PopupMenu popup = new PopupMenu ();

          MenuItem miAbout = new MenuItem ("About QuickLaunch");
          ActionListener al;
          al = new ActionListener ()
               {
                   public void actionPerformed (ActionEvent e)
                   {
                      if (aboutBox == null)
                      {
                          aboutBox = new AboutBox ();
                          aboutBox.setVisible (true);
                      }
                   }
               };
          miAbout.addActionListener (al);
          popup.add (miAbout);

          popup.addSeparator ();

          MenuItem miChoose = new MenuItem ("Choose Application");
          al = new ActionListener ()
               {
                   public void actionPerformed (ActionEvent e)
                   {
                      if (chooseApplication == null)
                          chooseApplication = new ChooseApplication ();
                   }
               };
          miChoose.addActionListener (al);
          popup.add (miChoose);

          MenuItem miLaunch = new MenuItem ("Launch Application")
                                  {
                                     public void addNotify ()
                                     {
                                        super.addNotify ();
                                        Font font = getFont ();
                                        font = font.deriveFont (Font.BOLD);
                                        setFont (font);
                                     }
                                  };
          ActionListener alLaunch;
          alLaunch = new ActionListener ()
                     {
                         public void actionPerformed (ActionEvent e)
                         {  
                            try
                            {
                                JTextField txt;
                                txt = ChooseApplication.txtApp;
                                String cmd = txt.getText ().trim ();
                                Runtime r = Runtime.getRuntime ();
                                if (!cmd.equals (""))
                                    r.exec (cmd);
                            }
                            catch (IOException ioe)
                            {
                                JOptionPane.showMessageDialog (null,
                                                               "Unable to "+
                                                               "launch");
                            }
                         }
                     };
          miLaunch.addActionListener (alLaunch);
          popup.add (miLaunch);

          popup.addSeparator ();

          MenuItem miExit = new MenuItem ("Exit");
          al = new ActionListener ()
               {
                   public void actionPerformed (ActionEvent e)
                   {
                      System.exit (0);
                   }
               };
          miExit.addActionListener (al);
          popup.add (miExit);

          // Create and add a tray icon to the system tray. Use the previously
          // created image and popup, along with a Quick Launch tooltip.

          TrayIcon ti = new TrayIcon (bi, "Quick Launch", popup);
          ti.addActionListener (alLaunch);
          systemTray.add (ti);
      }
      catch (AWTException e)
      {
          JOptionPane.showMessageDialog (null, "Unable to create and/or "+
                                         "install tray icon");
          System.exit (0);
      }
   }
}

class AboutBox extends JDialog
{
   AboutBox ()
   {
      // Create an ownerless modal dialog. The cast is needed to differentiate
      // between the JDialog(Dialog, boolean) and JDialog(Frame, boolean)
      // constructors.

      super ((java.awt.Dialog) null, true); 

      setDefaultCloseOperation (DO_NOTHING_ON_CLOSE);
      addWindowListener (new WindowAdapter ()
                         {
                             public void windowClosing (WindowEvent e)
                             {
                                dispose ();
                             }

                             public void windowClosed (WindowEvent e)
                             {          
                                QuickLaunch.aboutBox = null;
                             }
                         });

      JPanel pnl;
      pnl = new JPanel ()
            {
                {
                   setPreferredSize (new Dimension (250, 100));
                   setBorder (BorderFactory.createEtchedBorder ());
                }

                public void paintComponent (Graphics g)
                {
                   Insets insets = getInsets ();
                   g.setColor (Color.lightGray);
                   g.fillRect (0, 0, getWidth ()-insets.left-insets.right,
                               getHeight ()-insets.top-insets.bottom);

                   g.setFont (new Font ("Arial", Font.BOLD, 24));
                   FontMetrics fm = g.getFontMetrics ();
                   Rectangle2D r2d;
                   r2d = fm.getStringBounds ("Quick Launch 1.0", g);
                   int width = (int)((Rectangle2D.Float) r2d).width;

                   g.setColor (Color.black);
                   g.drawString ("Quick Launch 1.0", (getWidth()-width)/2,
                                 insets.top+(getHeight ()-insets.bottom-
                                 insets.top)/2);
                }
            };
      getContentPane ().add (pnl, BorderLayout.NORTH);

      final JButton btnOk = new JButton ("Ok");
      btnOk.addActionListener (new ActionListener ()
                               {
                                   public void actionPerformed (ActionEvent e)
                                   {
                                      dispose ();
                                   }
                               });
      getContentPane ().add (new JPanel () {{ add (btnOk); }},
                             BorderLayout.SOUTH);

      pack ();
      setResizable (false);
      setLocationRelativeTo (null);
   }
}

class ChooseApplication extends JFrame
{
   static JTextField txtApp = new JTextField ("", 30);

   ChooseApplication ()
   {
      setDefaultCloseOperation (DO_NOTHING_ON_CLOSE);
      addWindowListener (new WindowAdapter ()
                         {
                             public void windowClosing (WindowEvent e)
                             {
                                dispose ();
                             }

                             public void windowClosed (WindowEvent e)
                             {
                                QuickLaunch.chooseApplication = null;
                             }
                         });

      JPanel pnl = new JPanel ();
      pnl.add (new JLabel ("Enter application"));
      pnl.add (txtApp);
      getContentPane ().add (pnl);

      pack ();
      setResizable (false);
      setLocationRelativeTo (null);
      setVisible (true);
   }
}
