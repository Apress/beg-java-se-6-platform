// DynamicLayout.java

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class DynamicLayout extends JFrame
{
   public DynamicLayout (String title)
   {
      super (title);
      setDefaultCloseOperation (EXIT_ON_CLOSE);

      getContentPane ().setLayout (new GridLayout (3, 1));

      final Toolkit tk = Toolkit.getDefaultToolkit ();
      Object prop = tk.getDesktopProperty ("awt.dynamicLayoutSupported");

      JPanel pnl = new JPanel ();
      pnl.add (new JLabel ("awt.DynamicLayoutSupported:"));
      JLabel lblSetting1;
      lblSetting1 = new JLabel (prop.toString ());
      pnl.add (lblSetting1);

      getContentPane ().add (pnl);

      pnl = new JPanel ();
      pnl.add (new JLabel ("Dynamic layout active:"));
      final JLabel lblSetting2;
      lblSetting2 = new JLabel (tk.isDynamicLayoutActive () ? "yes" : "no");
      pnl.add (lblSetting2);

      getContentPane ().add (pnl);
      
      pnl = new JPanel ();
      pnl.add (new JLabel ("Toggle dynamic layout"));
      JCheckBox ckbSet = new JCheckBox ();
      ckbSet.addItemListener (new ItemListener ()
                              {
                                  public void itemStateChanged (ItemEvent ie)
                                  {
                                     if (tk.isDynamicLayoutActive ())
                                         tk.setDynamicLayout (false);
                                     else
                                         tk.setDynamicLayout (true);

                                     boolean active;
                                     active = tk.isDynamicLayoutActive ();
                                     lblSetting2.setText (active ? "yes"
                                                                 : "no");
                                  }
                              });
      pnl.add (ckbSet);

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
                          new DynamicLayout ("Dynamic Layout");
                       }
                   };
      EventQueue.invokeLater (r);
   }
}
