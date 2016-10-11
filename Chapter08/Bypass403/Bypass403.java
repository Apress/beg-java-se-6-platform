// Bypass403.java

import java.io.*;

import java.net.*;

import java.util.*;

class Bypass403
{
   public static void main (String [] args) throws Exception
   {
      URL url = new URL ("http://www.xn--80a0addceeeh.com");
      URLConnection urlc = url.openConnection ();
      urlc.setRequestProperty ("User-Agent", "Mozilla 5.0 (Windows; U; "+
                               "Windows NT 5.1; en-US; rv:1.8.0.11) "+
                               "Gecko/20070312 Firefox/1.5.0.11");

      InputStream is = urlc.getInputStream ();
      int c;
      while ((c = is.read ()) != -1)
         System.out.print ((char) c);
   }
}
