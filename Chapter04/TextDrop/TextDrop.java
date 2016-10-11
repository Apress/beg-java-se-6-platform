// TextDrop.java

import java.awt.*;
import java.awt.event.*;

import java.io.*;

import javax.swing.*;

public class TextDrop extends JFrame
{
    private JTextField txtField1, txtField2;

    public TextDrop (String title)
    {
       super (title);
       setDefaultCloseOperation (EXIT_ON_CLOSE);

       getContentPane ().setLayout (new GridLayout (3, 1));

       JPanel pnl = new JPanel ();
       pnl.add (new JLabel ("Text field 1"));
       txtField1 = new JTextField ("Text1", 25);
       txtField1.setDragEnabled (true);
       pnl.add (txtField1);
       getContentPane ().add (pnl);

       pnl = new JPanel ();
       pnl.add (new JLabel ("Text field 2"));
       txtField2 = new JTextField ("Text2", 25);
       txtField2.setDragEnabled (true);
       pnl.add (txtField2);
       getContentPane ().add (pnl);

       pnl = new JPanel ();
       pnl.add (new JLabel ("Drop mode"));
       JComboBox cb = new JComboBox (new String [] { "USE_SELECTION",
                                                     "INSERT" });
       cb.setSelectedIndex (0);
       ActionListener al;
       al = new ActionListener ()
            {
                public void actionPerformed (ActionEvent e)
                {
                   JComboBox cb = (JComboBox) e.getSource ();
                   int index = cb.getSelectedIndex ();
                   if (index == 0)
                   {
                       txtField1.setDropMode (DropMode.USE_SELECTION);
                       txtField2.setDropMode (DropMode.USE_SELECTION);
                   }
                   else
                   {
                       txtField1.setDropMode (DropMode.INSERT);
                       txtField2.setDropMode (DropMode.INSERT);
                   }
                }
            };
       cb.addActionListener (al);
       pnl.add (cb);
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
                           new TextDrop ("Text Drop");
                        }
                    };
       EventQueue.invokeLater (r);
    }
}
