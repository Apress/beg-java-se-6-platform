// RemoveAccents.java

import java.awt.*;
import java.awt.event.*;

import java.text.*;

import javax.swing.*;

public class RemoveAccents extends JFrame
{
   public RemoveAccents ()
   {
      super ("Remove Accents");
      setDefaultCloseOperation (EXIT_ON_CLOSE);

      JPanel pnl = new JPanel ();
      pnl.add (new JLabel ("Enter text"));

      final JTextField txtText;
      txtText = new JTextField (" façade touché "+
          "Rindfleischetikettierungsüberwachungsaufgabenübertragungsgesetz ");
      pnl.add (txtText);

      JButton btnRemove = new JButton ("Remove");
      ActionListener al;
      al = new ActionListener ()
           {
               public void actionPerformed (ActionEvent e)
               {
                  String text = txtText.getText ();
                  text = Normalizer.normalize (text, Normalizer.Form.NFD);
                  txtText.setText (text.replaceAll ("[^\\p{ASCII}]", ""));
               }
           };
      btnRemove.addActionListener (al);
      pnl.add (btnRemove);

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
                          new RemoveAccents ();
                       }
                   };
      EventQueue.invokeLater (r);
   }
}
