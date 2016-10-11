// WhoIs.java

import application.*;

import java.io.*;

import java.net.*;

import javax.swing.*;

public class WhoIs extends SingleFrameApplication
{
   final static int WHOIS_PORT = 43;

   JButton btnGo;
   JTextArea txtInfo;
   JTextField txtDomain;

   String whoIsServer = "whois.geektools.com";

   @Override
   protected void initialize (String [] args)
   {
      if (args.length == 1)
          whoIsServer = args [0];
   }

   @Override
   protected void startup ()
   {
      show (makeContentPane ());
   }

   JPanel makeContentPane ()
   {
      JPanel pane = new JPanel ();
      GroupLayout layout = new GroupLayout (pane);
      pane.setLayout (layout);

      layout.setAutoCreateGaps (true);
      layout.setAutoCreateContainerGaps (true);

      JLabel lblDomain = new JLabel ();
      lblDomain.setName ("lblDomain");
      txtDomain = new JTextField (20);
      btnGo = new JButton ();
      txtInfo = new JTextArea (20, 50);
      JScrollPane spInfo = new JScrollPane (txtInfo);

      GroupLayout.Group group;
      group = layout.createParallelGroup (GroupLayout.Alignment.CENTER)
               .addGroup (layout.createSequentialGroup ()
                .addComponent (lblDomain)
                .addComponent (txtDomain)
                .addComponent (btnGo))
               .addComponent (spInfo);
      layout.setHorizontalGroup (group);

      group = layout.createSequentialGroup ()
               .addGroup (layout.
                          createParallelGroup (GroupLayout.Alignment.BASELINE)
                .addComponent (lblDomain)
                .addComponent (txtDomain)
                .addComponent (btnGo))
               .addComponent (spInfo);
      layout.setVerticalGroup (group);

      ActionMap map = ApplicationContext.getInstance ().getActionMap (this);
      javax.swing.Action action = map.get ("retrieveInfo");
      btnGo.setAction (action);
      txtDomain.setAction (action);

      return pane;
   }

   @application.Action
   public Task retrieveInfo ()
   {
      return new WhoIsRetriever ();
   }

   public static void main (String [] args)
   {
      Application.launch (WhoIs.class, args);
   }

   class WhoIsRetriever extends Task<String, Void>
   {
      @Override
      protected String doInBackground () throws Exception
      {
         StringBuffer sb = new StringBuffer (1000);

         Socket s = new Socket (whoIsServer, WHOIS_PORT);

         PrintStream pso = new PrintStream (s.getOutputStream ());

         InputStreamReader isr = new InputStreamReader (s.getInputStream ());
         BufferedReader bri = new BufferedReader (isr);

         pso.print (txtDomain.getText ()+"\r\n");
         pso.flush ();

         String replyLine;
         while ((replyLine = bri.readLine ()) != null)
         {
            sb.append (replyLine);
            sb.append ('\n');
         }

         return sb.toString ();
      }

      @Override
      protected void succeeded (String info)
      {
         txtInfo.setText (info);
         txtInfo.setCaretPosition (0);
      }    
   }
}
