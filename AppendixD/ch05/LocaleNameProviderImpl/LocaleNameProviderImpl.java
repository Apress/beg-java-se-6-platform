// LocaleNameProviderImpl.java

import java.util.*;
import java.util.spi.*;

public class LocaleNameProviderImpl extends LocaleNameProvider
{
   final static Locale [] locales = new Locale [] { new Locale ("ti", "ER") };

   public Locale [] getAvailableLocales ()
   {
      return locales;
   }

   public String getDisplayCountry (String countryCode, Locale locale)
   {
      if (countryCode.equals ("ER"))
      {
          if (locale.equals (locales [0]))
              return "\u12a4\u122d\u1275\u122b";
          else
          if (locale.equals (Locale.ENGLISH))
              return "Eritrea";
      }

      return null;
   }

   public String getDisplayLanguage (String languageCode, Locale locale)
   {
      if (languageCode.equals ("ti"))
      {
          if (locale.equals (locales [0]))
              return "\u1275\u130d\u122d\u129b";
          else
          if (locale.equals (Locale.ENGLISH))
              return "Tigrinya";
      }

      return null;
   }

   public String getDisplayVariant (String variantCode, Locale locale)
   {
      return null;
   }
}
