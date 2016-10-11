// WorkingWithJRuby.java

import javax.script.*;

public class WorkingWithJRuby
{
   public static void main (String [] args) throws Exception
   {
      ScriptEngineManager manager = new ScriptEngineManager ();

      // The JRuby script engine is accessed via the jruby short name.

      ScriptEngine engine = manager.getEngineByName ("jruby");

      engine.eval ("`java WorkingWithJavaFXScript`");
  }
}
