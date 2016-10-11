// BasicAgent.java

import java.lang.instrument.*;

public class BasicAgent
{
   public static void agentmain (String agentArgs, Instrumentation inst)
   {
      System.out.println ("Basic agent invoked");
      System.out.println ();

      if (agentArgs == null)
      {
          System.out.println ("No options passed");
          return;
      }

      System.out.println ("Options...");
      String [] options = agentArgs.split (",");
      for (String option: options)
           System.out.println (option);
   }
}
