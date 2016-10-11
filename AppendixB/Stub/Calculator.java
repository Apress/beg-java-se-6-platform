// Calculator.java

import javax.swing.*;

public class Calculator extends JFrame
{
   @Stub
   public Calculator ()
   {
      super ("Calculator");
      setDefaultCloseOperation (EXIT_ON_CLOSE);

      // ... To do.

      pack ();
      setVisible (true);     
   }

   @Stub
   double doCalc (String expr)
   {
      return 0.0;
   }

   public static void main (String [] args)
   {
      Runnable r = new Runnable ()
                   {
                       public void run ()
                       {
                          new Calculator ();
                       }
                   };
      java.awt.EventQueue.invokeLater (r);
   }
}
