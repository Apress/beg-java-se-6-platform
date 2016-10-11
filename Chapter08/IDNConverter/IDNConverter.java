// IDNConverter.java

import java.awt.*;
import java.awt.event.*;

import java.net.*;

import javax.swing.*;

public class IDNConverter extends JFrame
{
   JTextField txtASCII, txtUnicode;

   public IDNConverter ()
   {
      super ("IDN Converter");
      setDefaultCloseOperation (EXIT_ON_CLOSE);

      getContentPane ().setLayout (new GridLayout (2, 1));

      JPanel pnl = new JPanel ();
      pnl.add (new JLabel ("Unicode name"));
      txtUnicode = new JTextField (30);
      pnl.add (txtUnicode);
      JButton btnToASCII = new JButton ("To ASCII");
      ActionListener al;
      al = new ActionListener ()
           {
               public void actionPerformed (ActionEvent e)
               {
                  txtASCII.setText (IDN.toASCII (txtUnicode.getText ()));
               }
           };
      btnToASCII.addActionListener (al);
      pnl.add (btnToASCII);

      getContentPane ().add (pnl);

      pnl = new JPanel ();
      pnl.add (new JLabel ("ACE equivalent"));
      txtASCII = new JTextField (30);
      pnl.add (txtASCII);
      JButton btnToUnicode = new JButton ("To Unicode");
      al = new ActionListener ()
           {
               public void actionPerformed (ActionEvent e)
               {
                  txtUnicode.setText (IDN.toUnicode (txtASCII.getText ()));
               }
           };
      btnToUnicode.addActionListener (al);
      pnl.add (btnToUnicode);

      getContentPane ().add (pnl);

      pack ();
      setVisible (true);
   }

   public static void main (String [] args)
   {
      Runnable r = new Runnable ()
                   {
                       public void run ()
                       {
                          new IDNConverter ();
                       }
                   };
      EventQueue.invokeLater (r);
   }
}
