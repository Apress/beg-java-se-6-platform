// Copy.java

import java.util.*;

public class Copy
{
   public static void main (String [] args)
   {
      String [] sa = { "First", "Second", "Third" };
      CharSequence [] csa;
      csa = Arrays.copyOf (sa, sa.length, CharSequence[].class);
      for (int i = 0; i < csa.length; i++)
           System.out.println (csa [i].length ());
   }
}
