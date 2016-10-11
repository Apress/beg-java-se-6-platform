// UnitsConverter.java

import java.awt.*;
import java.awt.event.*;

import java.io.*;

import javax.swing.*;

public class UnitsConverter extends JFrame
{
   Converter [] converters =
   {
      new Converter ("Acres", "Square meters", "Area", 4046.8564224),
      new Converter ("Square meters", "Acres", "Area", 1.0/4046.8564224),
      new Converter ("Pounds", "Kilograms", "Mass or Weight", 0.45359237),
      new Converter ("Kilograms", "Pounds", "Mass or Weight", 1.0/0.45359237),
      new Converter ("Miles/gallon (US)", "Miles/liter", "Fuel Consumption",
                     0.2642),
      new Converter ("Miles/liter", "Miles/gallon (US)", "Fuel Consumption",
                     1.0/0.2642),
      new Converter ("Inches/second", "Meters/second", "Speed", 0.0254),
      new Converter ("Meters/second", "Inches/second", "Speed", 1.0/0.0254),
      new Converter ("Grains", "Ounces", "Mass (Avoirdupois)/UK", 1.0/437.5),
      new Converter ("Ounces", "Grains", "Mass (Avoirdupois)/UK", 437.5)
   };

   public UnitsConverter (String title)
   {
      super (title);
      setDefaultCloseOperation (EXIT_ON_CLOSE);
      getRootPane ().setBorder (BorderFactory.createEmptyBorder (10, 10, 10,
                                                                 10));

      JPanel pnlLeft = new JPanel ();
      pnlLeft.setLayout (new BorderLayout ());

      pnlLeft.add (new JLabel ("Converters"), BorderLayout.CENTER);

      final JList lstConverters = new JList (converters);
      lstConverters.setSelectionMode (ListSelectionModel.SINGLE_SELECTION);
      lstConverters.setSelectedIndex (0);
      pnlLeft.add (new JScrollPane (lstConverters), BorderLayout.SOUTH);

      JPanel pnlRight = new JPanel ();
      pnlRight.setLayout (new BorderLayout ());

      JPanel pnlTemp = new JPanel ();
      pnlTemp.add (new JLabel ("Units:"));
      final JTextField txtUnits = new JTextField (20);
      pnlTemp.add (txtUnits);
      pnlRight.add (pnlTemp, BorderLayout.NORTH);

      pnlTemp = new JPanel ();                                                    
      JButton btnConvert = new JButton ("Convert");
      ActionListener al;
      al = new ActionListener ()
           {
               public void actionPerformed (ActionEvent ae)
               {
                  try
                  {
                      double value = Double.parseDouble (txtUnits.getText ());
                      int index = lstConverters.getSelectedIndex ();
                      txtUnits.setText (""+converters [index].convert (value));
                   }
                   catch (NumberFormatException e)
                   {
                      JOptionPane.showMessageDialog (null, "Invalid input "+
                                                     "-- please re-enter");
                   }
               }

           };
      btnConvert.addActionListener (al);
      pnlTemp.add (btnConvert);
      JButton btnClear = new JButton ("Clear");
      al = new ActionListener ()
           {
               public void actionPerformed (ActionEvent ae)
               {
                  txtUnits.setText (""); 
               }
           };
      btnClear.addActionListener (al);
      pnlTemp.add (btnClear);
      JButton btnHelp = new JButton ("Help");
      al = new ActionListener ()
           {
               public void actionPerformed (ActionEvent ae)
               {
                  new Help (UnitsConverter.this, "Units Converter Help");
               }
           };
      btnHelp.addActionListener (al);
      pnlTemp.add (btnHelp);
      JButton btnAbout = new JButton ("About");
      al = new ActionListener ()
           {
               public void actionPerformed (ActionEvent ae)
               {
                  new About (UnitsConverter.this, "Units Converter");
               }
           };
      btnAbout.addActionListener (al);
      pnlTemp.add (btnAbout);

      pnlRight.add (pnlTemp, BorderLayout.CENTER);

      getContentPane ().add (pnlLeft, BorderLayout.WEST);
      getContentPane ().add (pnlRight, BorderLayout.EAST);

      pack ();
      setResizable (false);
      setVisible (true);
   }

   public static void main (String [] args)
   {
      Runnable r = new Runnable ()
                   {
                       public void run ()
                       {
                          new UnitsConverter ("Units Converter 1.0");
                       }
                   };
      EventQueue.invokeLater (r);
   }
}

class About extends JDialog
{
   About (Frame frame, String title)
   {
      super (frame, "About", true);

      JLabel lbl = new JLabel ("Units Converter 1.0");
      getContentPane ().add (lbl, BorderLayout.NORTH);

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
      setVisible (true);
   }
}

class Converter
{
   private double multiplier;

   private String srcUnits, dstUnits, cat;

   Converter (String srcUnits, String dstUnits, String cat, double multiplier)
   {
      this.srcUnits = srcUnits;
      this.dstUnits = dstUnits;
      this.cat = cat;
      this.multiplier = multiplier;
   }

   double convert (double value)
   {
      return value*multiplier;
   }

   public String toString ()
   {
      return srcUnits+" to "+dstUnits+" -- "+cat;
   }
}

class Help extends JDialog
{
   Help (Frame frame, String title)
   {
      super (frame, title);
      setModalExclusionType (Dialog.ModalExclusionType.APPLICATION_EXCLUDE);

      try
      {
         JEditorPane ep = new JEditorPane ("file:///"+new File ("").
                                           getAbsolutePath ()+"/uchelp.html");
         ep.setEnabled (false);
         getContentPane ().add (ep);
      }
      catch (IOException ioe)
      {
         JOptionPane.showMessageDialog (frame,
                                        "Unable to install editor pane");
         return;
      }

      setSize (200, 200);
      setLocationRelativeTo (frame);
      setVisible (true);
   }
}
