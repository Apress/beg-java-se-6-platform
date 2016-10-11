// Terminals.java

import java.util.*;

import javax.smartcardio.*;

public class Terminals
{
   public static void main (String [] args) throws Exception
   {
      TerminalFactory factory = TerminalFactory.getDefault ();
      System.out.println ("Default factory: "+factory);
      dumpTerminals (factory);

      factory = TerminalFactory.getInstance ("PC/SC", null);
      System.out.println ("PC/SC factory: "+factory);
      dumpTerminals (factory);
   }

   static void dumpTerminals (TerminalFactory factory) throws Exception
   {
      List<CardTerminal> terminals = factory.terminals ().list ();
      for (CardTerminal terminal: terminals)
         System.out.println (terminal);
   }
}
