// WorkingWithJRuby.java

import java.io.*;

import javax.script.*;

public class WorkingWithJRuby
{
   public static void main (String [] args) throws Exception
   {
      ScriptEngineManager manager = new ScriptEngineManager ();

      // The JRuby script engine is accessed via the jruby short name.

      ScriptEngine engine = manager.getEngineByName ("jruby");

      // Evaluate TempConverter.rb to generate intermediate code.

      engine.eval (new BufferedReader (new FileReader ("TempConverter.rb")));

      Invocable invocable = (Invocable) engine;
      Object tempconverter = invocable.invokeFunction ("getTempConverter");

      double degreesCelsius;
      degreesCelsius = (Double) invocable.invokeMethod (tempconverter, "f2c",
                                                        98.6);
      System.out.println ("98.6 degrees Fahrenheit = "+degreesCelsius+
                          " degrees Celsius");

      double degreesFahrenheit;
      degreesFahrenheit = (Double) invocable.invokeMethod (tempconverter,
                                                           "c2f", 100.0);
      System.out.println ("100.0 degrees Celsius = "+degreesFahrenheit+
                          " degrees Fahrenheit");
  }
}
