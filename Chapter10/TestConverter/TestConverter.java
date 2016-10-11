// TestConverter.java

import wsdemo.*;

public class TestConverter
{
   public static void main (String [] args)
   {
      ConverterService service = new ConverterService ();

      Converter proxy = service.getConverterPort ();

      System.out.println ("2.5 acres = "+proxy.acresToSqMeters (2.5)+
                          " square meters");
      System.out.println ("358 square meters = "+proxy.sqMetersToAcres (358)+
                          " acres");
      System.out.println ("6 pounds = "+proxy.lbsToKilos (6)+" kilograms");
      System.out.println ("2.7 kilograms = "+proxy.kilosToLbs (2.7)+
                          " pounds");
   }
}
