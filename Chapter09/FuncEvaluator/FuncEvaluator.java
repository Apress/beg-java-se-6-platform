// FuncEvaluator.java

import java.io.*;

import javax.script.*;

public class FuncEvaluator
{
   public static void main (String [] args)
   {
      if (args.length != 2)
      {
          System.err.println ("usage: java FuncEvaluator scriptfile "+
                              "script-exp");
          return;
      }

      ScriptEngineManager manager = new ScriptEngineManager ();
      ScriptEngine engine = manager.getEngineByName ("rhino");

      try
      {
          System.out.println (engine.eval (new FileReader (args [0])));
          System.out.println (engine.eval (args [1]));
      }
      catch (ScriptException se)
      {
          System.err.println (se.getMessage ());
      }
      catch (IOException ioe)
      {
          System.err.println (ioe.getMessage ());
      }
   }
}
