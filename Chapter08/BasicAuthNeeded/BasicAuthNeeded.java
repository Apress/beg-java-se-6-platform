// BasicAuthNeeded.java

import java.net.*;

import java.util.*;

public class BasicAuthNeeded
{
   public static void main (String [] args) throws Exception
   {
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
