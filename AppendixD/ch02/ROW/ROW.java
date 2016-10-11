// ROW.java

// Invoke java ROW filespec to show filespec's read-only/writable status.
//
// Invoke java ROW RO filespec to set filespec's status to read-only.
//
// Invoke java ROW W filespec to set filespec's status to writable.

import java.io.File;

public class ROW
{
   public static void main (String [] args)
   {
      if (args.length != 1 && args.length != 2)
      {
          System.err.println ("usage: java ROW [RO | W] filespec");
          return;
      }

      String option = (args.length == 1) ? "" : args [0];
      File filespec = new File (args [(args.length == 1) ? 0 : 1]);

      if (option.equals ("RO"))
      {
          if (filespec.setWritable (false))
              System.out.println (filespec+" made read-only");
          else
              System.out.println ("Permission denied");
      }
      else
      if (option.equals ("W"))
      {
          if (filespec.setWritable (true))
              System.out.println (filespec+" made writable");
          else
              System.out.println ("Permission denied");
      }
      else
          System.out.println (filespec+" is currently "+ 
                              (filespec.canWrite ()
                              ? "writable" : "read-only"));
   }
}
