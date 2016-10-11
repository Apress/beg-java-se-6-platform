// CompilerInfo.java

import java.util.*;

import javax.lang.model.*;

import javax.tools.*;

public class CompilerInfo
{
   public static void main (String [] args)
   {
      if (args.length != 1)
      {
          System.err.println ("usage: java CompilerInfo option");
          return;
      }

      JavaCompiler compiler = ToolProvider.getSystemJavaCompiler ();
      if (compiler == null)
      {
          System.err.println ("compiler not available");
          return;
      }

      System.out.println ("Supported source versions:");
      Set<SourceVersion> srcVer = compiler.getSourceVersions ();
      for (SourceVersion sv: srcVer)
           System.out.println ("  " + sv.name ());

      int nargs = compiler.isSupportedOption (args [0]);
      if (nargs == -1)
          System.out.println ("Option "+args [0]+" is not supported");
      else
          System.out.println ("Option "+args [0]+" takes "+nargs+
                              " arguments");
   }
}
