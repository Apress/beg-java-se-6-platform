// SkyView.java

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import java.io.*;

import javax.imageio.*;

import javax.swing.*;

import org.sdss.skyserver.*;

public class SkyView extends JFrame
{
   final static int IMAGE_WIDTH = 300;
   final static int IMAGE_HEIGHT = 300;

   static ImgCutoutSoap imgcutoutsoap;

   public SkyView ()
   {
      super ("SkyView");
      setDefaultCloseOperation (EXIT_ON_CLOSE);

      setContentPane (createContentPane ());

      pack ();
      setResizable (false);
      setVisible (true);
   }

   JPanel createContentPane ()
   {
      JPanel pane = new JPanel (new BorderLayout (10, 10));
      pane.setBorder (BorderFactory.createEmptyBorder (10, 10, 10, 10));

      final JLabel lblImage = new JLabel ("", JLabel.CENTER);
      lblImage.setPreferredSize (new Dimension (IMAGE_WIDTH+9,
                                                IMAGE_HEIGHT+9));
      lblImage.setBorder (BorderFactory.createEtchedBorder ());

      pane.add (new JPanel () {{ add (lblImage); }}, BorderLayout.NORTH);

      JPanel form = new JPanel (new GridLayout (4, 1));

      final JLabel lblRA = new JLabel ("Right ascension:");
      int width = lblRA.getPreferredSize ().width+20;
      int height = lblRA.getPreferredSize ().height;
      lblRA.setPreferredSize (new Dimension (width, height));
      lblRA.setDisplayedMnemonic ('R');
      final JTextField txtRA = new JTextField (25);
      lblRA.setLabelFor (txtRA);

      form.add (new JPanel ()
                {{ add (lblRA); add (txtRA); 
                   setLayout (new FlowLayout (FlowLayout.CENTER, 0, 5)); }});

      final JLabel lblDec = new JLabel ("Declination:");
      lblDec.setPreferredSize (new Dimension (width, height));
      lblDec.setDisplayedMnemonic ('D');
      final JTextField txtDec = new JTextField (25);
      lblDec.setLabelFor (txtDec);

      form.add (new JPanel ()
                {{ add (lblDec); add (txtDec);
                   setLayout (new FlowLayout (FlowLayout.CENTER, 0, 5));}});

      final JLabel lblScale = new JLabel ("Scale:");
      lblScale.setPreferredSize (new Dimension (width, height));
      lblScale.setDisplayedMnemonic ('S');
      final JTextField txtScale = new JTextField (25);
      lblScale.setLabelFor (txtScale);

      form.add (new JPanel ()
                {{ add (lblScale); add (txtScale);
                   setLayout (new FlowLayout (FlowLayout.CENTER, 0, 5));}});

      final JLabel lblDO = new JLabel ("Drawing options:");
      lblDO.setPreferredSize (new Dimension (width, height));
      lblDO.setDisplayedMnemonic ('o');
      final JTextField txtDO = new JTextField (25);
      lblDO.setLabelFor (txtDO);

      form.add (new JPanel ()
                {{ add (lblDO); add (txtDO);
                   setLayout (new FlowLayout (FlowLayout.CENTER, 0, 5));}});

      pane.add (form, BorderLayout.CENTER);

      final JButton btnGP = new JButton ("Get Picture");
      ActionListener al;
      al = new ActionListener ()
           {
               public void actionPerformed (ActionEvent e)
               {
                  try
                  {
                      double ra = Double.parseDouble (txtRA.getText ());
                      double dec = Double.parseDouble (txtDec.getText ());
                      double scale = Double.parseDouble (txtScale.getText ());
                      String dopt = txtDO.getText ().trim ();

                      byte [] image = imgcutoutsoap.getJpeg (ra, dec, scale,
                                                             IMAGE_WIDTH,
                                                             IMAGE_HEIGHT,
                                                             dopt);
                      lblImage.setIcon (new ImageIcon (image));
                  }
                  catch (Exception exc)
                  {
                      JOptionPane.showMessageDialog (SkyView.this,
                                                     exc.getMessage ());
                  }
               }
           };
      btnGP.addActionListener (al);
      pane.add (new JPanel () {{ add (btnGP); }}, BorderLayout.SOUTH);

      return pane;
   }

   public static void main (String [] args) throws IOException
   {
      ImgCutout imgcutout = new ImgCutout ();
      imgcutoutsoap = imgcutout.getImgCutoutSoap ();

      Runnable r = new Runnable ()
                   {
                       public void run ()
                       {
                          try
                          {
                              String lnf;
                              lnf = UIManager.
                                      getSystemLookAndFeelClassName ();
                              UIManager.setLookAndFeel (lnf);
                          }
                          catch (Exception e)
                          {
                          }
                          new SkyView ();
                       }
                   };
      EventQueue.invokeLater (r);
   }
}
