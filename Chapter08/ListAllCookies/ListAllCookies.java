// ListAllCookies.java

import java.net.*;

import java.util.*;

public class ListAllCookies
{
   public static void main (String [] args) throws Exception
   {
      if (args.length != 1)
      {
          System.err.println ("usage: java ListAllCookies url");
          return;
      }

      CookieManager cm = new CookieManager ();
      cm.setCookiePolicy (CookiePolicy.ACCEPT_ALL);
      CookieHandler.setDefault (cm);
        
      new URL (args [0]).openConnection ().getContent ();

      List<HttpCookie> cookies = cm.getCookieStore ().getCookies ();
      for (HttpCookie cookie: cookies)
      {
           System.out.println ("Name = "+cookie.getName ());
           System.out.println ("Value = "+cookie.getValue ());
           System.out.println ("Lifetime (seconds) = "+cookie.getMaxAge ());
           System.out.println ("Path = "+cookie.getPath ());
           System.out.println ();
      }
   }
}
