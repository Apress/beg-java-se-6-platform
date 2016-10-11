// ThreadingBehavior.java

import java.util.*;

import javax.script.*;

public class ThreadingBehavior
{
   public static void main (String [] args)
   {
      ScriptEngineManager manager = new ScriptEngineManager ();

      List<ScriptEngineFactory> factories = manager.getEngineFactories ();
      for (ScriptEngineFactory factory: factories)
           System.out.println ("Threading behavior: "+
                               factory.getParameter ("THREADING"));
   }
}
