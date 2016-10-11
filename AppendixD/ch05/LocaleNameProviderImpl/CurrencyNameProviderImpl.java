// CurrencyNameProviderImpl.java

import java.util.*;
import java.util.spi.*;

public class CurrencyNameProviderImpl extends CurrencyNameProvider
{
   final static Locale [] locales = new Locale [] { new Locale ("ti", "ER") };

   public Locale [] getAvailableLocales ()
   {
      return locales;
   }

   public String getSymbol (String currencyCode, Locale locale)
   {
      if (currencyCode == null || locale == null)
          throw new NullPointerException ();

      if (currencyCode.length () != 3)
          throw new IllegalArgumentException ("currency code length not 3");

      for (int i = 0; i < 3; i++)
           if (!Character.isUpperCase (currencyCode.charAt (i)))
               throw new IllegalArgumentException ("bad currency code");

      if (!locale.equals (locales [0]))
          throw new IllegalArgumentException ("unsupported locale");

      if (currencyCode.equals ("ERN"))
          return "Nfk";
      else
          return null;
   }
}
