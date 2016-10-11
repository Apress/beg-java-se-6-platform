// CompileFiles1.java

import javax.tools.*;

public class CompileFiles1
{
   public static void main (String [] args)
   {
      if (args.length == 0)
      {
          System.err.println ("usage: java CompileFiles1 srcFile [srcFile]+");
          return;
      }

      JavaCompiler compiler = ToolProvider.getSystemJavaCompiler ();
      if (compiler == null)
      {
          System.err.println ("compiler not available");
          return;
      }

      compiler.run (null, null, null, args);
   }
}
