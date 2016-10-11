// Converter.java

package wsdemo;

import javax.jws.WebService;

@WebService
public class Converter
{
   public double acresToSqMeters (double value)
   {
      return value*4046.8564224; // acres to square meters
   }

   public double sqMetersToAcres (double value)
   {
      return value/4046.8564224; // square meters to acres
   }

   public double lbsToKilos (double value)
   {
      return value*0.45359237; // pounds to kilograms
   }

   public double kilosToLbs (double value)
   {
      return value/0.45359237; // kilograms to pounds
   }
}
