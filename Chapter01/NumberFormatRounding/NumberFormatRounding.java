// NumberFormatRounding.java

import java.math.*;

import java.text.*;

public class NumberFormatRounding
{
   public static void main (String [] args)
   {
      NumberFormat nf = NumberFormat.getNumberInstance ();
      nf.setMaximumFractionDigits (2);

      System.out.println ("Default rounding mode: "+nf.getRoundingMode ());
      System.out.println ("123.454 rounds to "+nf.format (123.454));
      System.out.println ("123.455 rounds to "+nf.format (123.455));
      System.out.println ("123.456 rounds to "+nf.format (123.456));
      System.out.println ();

      nf.setRoundingMode (RoundingMode.HALF_DOWN);
      System.out.println ("Rounding mode: "+nf.getRoundingMode ());
      System.out.println ("123.454 rounds to "+nf.format (123.454));
      System.out.println ("123.455 rounds to "+nf.format (123.455));
      System.out.println ("123.456 rounds to "+nf.format (123.456));
      System.out.println ();

      nf.setRoundingMode (RoundingMode.FLOOR);
      System.out.println ("Rounding mode: "+nf.getRoundingMode ());
      System.out.println ("123.454 rounds to "+nf.format (123.454));
      System.out.println ("123.455 rounds to "+nf.format (123.455));
      System.out.println ("123.456 rounds to "+nf.format (123.456));
      System.out.println ();

      nf.setRoundingMode (RoundingMode.CEILING);
      System.out.println ("Rounding mode: "+nf.getRoundingMode ());
      System.out.println ("123.454 rounds to "+nf.format (123.454));
      System.out.println ("123.455 rounds to "+nf.format (123.455));
      System.out.println ("123.456 rounds to "+nf.format (123.456));
   }
}
