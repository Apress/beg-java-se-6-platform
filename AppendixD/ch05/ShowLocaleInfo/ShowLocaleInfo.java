// ShowLocaleInfo.java

import java.util.*;

public class ShowLocaleInfo
{
   public static void main (String [] args)
   {
      Locale ti_ER = new Locale ("ti", "ER");

      String displayCountry = ti_ER.getDisplayCountry (Locale.ENGLISH);
      System.out.println (displayCountry);

      displayCountry = ti_ER.getDisplayCountry (ti_ER);
      for (int i = 0; i < displayCountry.length (); i++)
           System.out.print (Integer.toHexString (displayCountry.charAt (i))+
                             " ");
      System.out.println ();

      String displayLanguage = ti_ER.getDisplayLanguage (Locale.ENGLISH);
      System.out.println (displayLanguage);

      displayLanguage = ti_ER.getDisplayLanguage (ti_ER);
      for (int i = 0; i < displayLanguage.length (); i++)
           System.out.print (Integer.toHexString (displayLanguage.charAt (i))+
                             " ");
      System.out.println ();

      System.out.println (ti_ER.getDisplayVariant ());
   }
}
