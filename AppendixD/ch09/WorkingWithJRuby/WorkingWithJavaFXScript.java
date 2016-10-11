// WorkingWithJavaFXScript.java

import java.awt.*;

import java.io.*;

import javax.script.*;

public class WorkingWithJavaFXScript
{
   public static void main (String [] args)
   {
      ScriptEngineManager manager = new ScriptEngineManager ();

      // The JavaFX Script script engine is accessed via the FX short name.

      final ScriptEngine engine = manager.getEngineByName ("FX");

      engine.put ("msg:java.lang.String", "JavaFX Script");

      Runnable r = new Runnable ()
      {
         public void run ()
         {
            try
            {
                System.out.println ("EDT running: "+
                                    EventQueue.isDispatchThread ());
                engine.eval (new BufferedReader (new FileReader ("demo.fx")));
            }
            catch (Exception e)
            {
                e.printStackTrace ();
            }
         }
      };
      EventQueue.invokeLater (r);
  }
}
