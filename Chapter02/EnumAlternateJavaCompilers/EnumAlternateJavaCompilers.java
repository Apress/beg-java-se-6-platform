// EnumAlternateJavaCompilers.java

import java.util.*;

import javax.tools.*;

public class EnumAlternateJavaCompilers
{
   public static void main (String [] args)
   {
      ServiceLoader<JavaCompiler> compilers;
      compilers = ServiceLoader.load (JavaCompiler.class);
      System.out.println (compilers.toString ());

      for (JavaCompiler compiler: compilers)
           System.out.println (compiler);
   }
}
