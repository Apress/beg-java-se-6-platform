// CompileFiles2.java

import javax.tools.*;

public class CompileFiles2
{
   public static void main (String [] args)
   {
      if (args.length == 0)
      {
          System.err.println ("usage: java CompileFiles2 srcFile [srcFile]+");
          return;
      }

      JavaCompiler compiler = ToolProvider.getSystemJavaCompiler ();
      if (compiler == null)
      {
          System.err.println ("compiler not available");
          return;
      }

      DiagnosticCollector<JavaFileObject> dc;
      dc = new DiagnosticCollector<JavaFileObject>();

      StandardJavaFileManager sjfm;
      sjfm = compiler.getStandardFileManager (dc, null, null);

      Iterable<? extends JavaFileObject> fileObjects;
      fileObjects = sjfm.getJavaFileObjects (args);

      compiler.getTask (null, sjfm, dc, null, null, fileObjects).call ();

      for (Diagnostic d: dc.getDiagnostics ())
      {
           System.out.println (d.getMessage (null));
           System.out.printf ("Line number = %d\n", d.getLineNumber ());
           System.out.printf ("File = %s\n", d.getSource ());
      }
   }
}
