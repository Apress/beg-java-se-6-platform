// PostfixCalc.java

import java.io.*;

import java.util.*;

public class PostfixCalc
{
   public static void main (String [] args) throws IOError
   {
      Console console = System.console ();
      if (console == null)
      {
          System.err.println ("unable to obtain console");
          return;
      }

      console.printf ("Postfix expression Calculator\n\n");
      console.printf ("Valid operators: + - * /\n");
      console.printf ("Valid commands: c/C (clear stack), "+
                      "t/t (view stack top)\n\n");

      Deque<Double> stack = new ArrayDeque<Double> ();

      loop:
      while (true)
      {
         String line = console.readLine (">").trim ();

         switch (line.charAt (0))
         {
            case 'Q':
            case 'q': break loop;

            case 'C':
            case 'c': while (stack.peekFirst () != null)
                         stack.removeFirst ();
                      break;

            case 'T':
            case 't': console.printf ("%f\n", stack.peekFirst ());
                      break;

            case '+': if (stack.size () < 2)
                      {
                          console.printf ("missing operand\n");
                          break;
                      }

                      double op2 = stack.removeFirst ();
                      double op1 = stack.removeFirst ();
                      double res = op1+op2;
                      console.printf ("%f+%f=%f\n", op1, op2, res);
                      stack.addFirst (res);
                      break;

            case '-': if (stack.size () < 2)
                      {
                          console.printf ("missing operand\n");
                          break;
                      }

                      op2 = stack.removeFirst ();
                      op1 = stack.removeFirst ();
                      res = op1-op2;
                      console.printf ("%f-%f=%f\n", op1, op2, res);
                      stack.addFirst (res);
                      break;

            case '*': if (stack.size () < 2)
                      {
                          console.printf ("missing operand\n");
                          break;
                      }

                      op2 = stack.removeFirst ();
                      op1 = stack.removeFirst ();
                      res = op1*op2;
                      console.printf ("%f*%f=%f\n", op1, op2, res);
                      stack.addFirst (res);
                      break;

            case '/': if (stack.size () < 2)
                      {
                          console.printf ("missing operand\n");
                          break;
                      }

                      op2 = stack.removeFirst ();
                      op1 = stack.removeFirst ();
                      res = op1/op2;
                      console.printf ("%f/%f=%f\n", op1, op2, res);
                      stack.addFirst (res);
                      break;

            default : try
                      {
                          stack.addFirst (Double.parseDouble (line));
                      }
                      catch (NumberFormatException nfe)
                      {
                         console.printf ("double value expected\n");
                      }
         }
      }
   }
}
