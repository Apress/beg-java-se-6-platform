// RedirectScriptOutputToGUI.java

import java.awt.*;
import java.awt.event.*;

import java.io.*;

import javax.script.*;

import javax.swing.*;

public class RedirectScriptOutputToGUI extends JFrame
{
   static ScriptEngine engine;

   public RedirectScriptOutputToGUI ()
   {
      super ("Redirect Script Output to GUI");
      setDefaultCloseOperation (EXIT_ON_CLOSE);

      getContentPane ().add (createGUI ());

      pack ();
      setVisible (true);
   }

   JPanel createGUI ()
   {
      JPanel pnlGUI = new JPanel ();
      pnlGUI.setLayout (new BorderLayout ());

      JPanel pnl = new JPanel ();
      pnl.setLayout (new GridLayout (2, 1));

      final JTextArea txtScriptInput = new JTextArea (10, 60);
      pnl.add (new JScrollPane (txtScriptInput));

      final JTextArea txtScriptOutput = new JTextArea (10, 60);
      pnl.add (new JScrollPane (txtScriptOutput));

      pnlGUI.add (pnl, BorderLayout.NORTH);

      GUIWriter writer = new GUIWriter (txtScriptOutput);
      PrintWriter pw = new PrintWriter (writer, true);
      engine.getContext ().setWriter (pw);
      engine.getContext ().setErrorWriter (pw);

      pnl = new JPanel ();

      JButton btnEvaluate = new JButton ("Evaluate");
      ActionListener actionEvaluate;
      actionEvaluate = new ActionListener ()
                       {
                           public void actionPerformed (ActionEvent e)
                           {
                              try
                              {
                                  engine.eval (txtScriptInput.getText ());
                                  dumpBindings ();
                              }
                              catch (ScriptException se)
                              {
                                  JFrame parent;
                                  parent = RedirectScriptOutputToGUI.this;
                                  JOptionPane.
                                     showMessageDialog (parent,
                                                        se.getMessage ());
                              }
                           }
                       };
      btnEvaluate.addActionListener (actionEvaluate);
      pnl.add (btnEvaluate);

      JButton btnClear = new JButton ("Clear");
      ActionListener actionClear;
      actionClear = new ActionListener ()
                    {
                        public void actionPerformed (ActionEvent e)
                        {
                           txtScriptInput.setText ("");
                           txtScriptOutput.setText ("");
                        }
                    };
      btnClear.addActionListener (actionClear);
      pnl.add (btnClear);

      pnlGUI.add (pnl, BorderLayout.SOUTH);

      return pnlGUI;
   }

   static void dumpBindings ()
   {
      System.out.println ("ENGINE BINDINGS");
      Bindings bindings = engine.getBindings (ScriptContext.ENGINE_SCOPE);
      if (bindings == null)
          System.out.println ("  No bindings");
      else
          for (String key: bindings.keySet ())
               System.out.println ("  "+key+": "+bindings.get (key));
      System.out.println ();
   }

   public static void main (String [] args)
   {
      ScriptEngineManager manager = new ScriptEngineManager ();
      engine = manager.getEngineByName ("rhino");
      dumpBindings ();

      Runnable r = new Runnable ()
                   {
                       public void run ()
                       {
                          new RedirectScriptOutputToGUI ();
                       }
                   };
      EventQueue.invokeLater (r);
   }
}

class GUIWriter extends Writer
{
   private JTextArea txtOutput;

   GUIWriter (JTextArea txtOutput)
   {
      this.txtOutput = txtOutput;
   }

   public void close ()
   {
      System.out.println ("close");
   }

   public void flush ()
   {
      System.out.println ("flush");
   }

   public void write (char [] cbuf, int off, int len)
   {
      txtOutput.setText (txtOutput.getText ()+new String (cbuf, off, len));
   }
}
