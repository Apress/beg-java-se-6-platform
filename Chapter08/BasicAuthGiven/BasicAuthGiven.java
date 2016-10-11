// BasicAuthGiven.java

import java.net.*;

import java.util.*;

public class BasicAuthGiven
{
   final static String USERNAME = "test";
   final static String PASSWORD = "this";

   static class BasicAuthenticator extends Authenticator
   {
      public PasswordAuthentication getPasswordAuthentication ()
      {
         System.out.println ("Password requested from "+
                             getRequestingHost ()+" for authentication "+
                             "scheme "+getRequestingScheme ());
         return new PasswordAuthentication (USERNAME, PASSWORD.toCharArray());
      }     
   }

   public static void main (String [] args) throws Exception
   {
      Authenticator.setDefault (new BasicAuthenticator ());

      String s;
      s = "http://prism.library.cornell.edu/control/authBasic/authTest";
      URL url = new URL (s);

      URLConnection urlc = url.openConnection ();

      Map<String,List<String>> hf = urlc.getHeaderFields ();
      for (String key: hf.keySet ())
           System.out.println (key+": "+urlc.getHeaderField (key));

      System.out.println (((HttpURLConnection) urlc).getResponseCode ());
   }
}
