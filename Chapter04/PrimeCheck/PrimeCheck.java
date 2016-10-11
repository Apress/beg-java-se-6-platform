// PrimeCheck.java

import java.awt.*;
import java.awt.event.*;

import java.math.*;

import java.util.concurrent.*;

import javax.swing.*;

public class PrimeCheck extends JFrame
{
   public PrimeCheck ()
   {
      super ("Prime Check");
      setDefaultCloseOperation (EXIT_ON_CLOSE);

      final JLabel lblResult = new JLabel (" ");

      JPanel pnl = new JPanel ();
      pnl.add (new JLabel ("Enter integer:"));
      final JTextField txtNumber = new JTextField (10);
      pnl.add (txtNumber);
      JButton btnCheck = new JButton ("Check");
      ActionListener al;
      al = new ActionListener ()
           {
               public void actionPerformed (ActionEvent ae)
               {
                  try
                  {
                      BigInteger bi = new BigInteger (txtNumber.getText ());
                      lblResult.setText ("One moment...");
                      new PrimeCheckTask (bi, lblResult).execute ();
                  }
                  catch (NumberFormatException nfe)
                  {
                      lblResult.setText ("Invalid input");
                  }
               }
           };
      btnCheck.addActionListener (al);
      pnl.add (btnCheck);
      getContentPane ().add (pnl, BorderLayout.NORTH);

      pnl = new JPanel ();
      pnl.add (lblResult);
      getContentPane ().add (pnl, BorderLayout.SOUTH);

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
                          new PrimeCheck ();
                       }
                   };
      EventQueue.invokeLater (r);
   }
}

class PrimeCheckTask extends SwingWorker<Boolean, Void>
{
   private BigInteger bi;
   private JLabel lblResult;

   PrimeCheckTask (BigInteger bi, JLabel lblResult)
   {
      this.bi = bi;
      this.lblResult = lblResult;
   }

   @Override
   public Boolean doInBackground ()
   {
      return bi.isProbablePrime (1000);
   }

   @Override
   public void done ()
   {
      try
      {
         try
         {
             boolean isPrime = get ();
             if (isPrime)
                 lblResult.setText ("Integer is prime");
             else
                 lblResult.setText ("Integer is not prime");
         }
         catch (InterruptedException ie)
         {
             lblResult.setText ("Interrupted");
         }
      }
      catch (ExecutionException ee)
      {
         String reason = null;
         Throwable cause = ee.getCause ();
         if (cause == null)
             reason = ee.getMessage ();
         else
             reason = cause.getMessage ();

         lblResult.setText ("Unable to determine primeness");
      }
   }
}
