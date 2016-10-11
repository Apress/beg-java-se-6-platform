// BugLog.java

import java.awt.*;
import java.awt.event.*;

import java.text.*;

import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

public class BugLog extends JFrame
{
   private static DateFormat df;

   public BugLog (String title) throws ParseException
   {
      super (title);
      setDefaultCloseOperation (EXIT_ON_CLOSE);

      String [] columns = { "Bug ID", "Description", "Date Filed",
                            "Date Fixed" };

      df = DateFormat.getDateTimeInstance (DateFormat.SHORT, DateFormat.SHORT,
                                           Locale.US);

      Object [][] rows =
      {
         { 1000, "Crash during file read", df.parse ("2/10/07 10:12 am"),
           df.parse ("2/11/07 11:15 pm") },
         { 1020, "GUI not repainted", df.parse ("3/5/07 6:00 pm"),
           df.parse ("3/8/07 3:00 am") },
         { 1025, "File not found exception", df.parse ("1/18/07 9:30 am"),
           df.parse ("1/22/07 4:13 pm") }
      };

      TableModel model = new DefaultTableModel (rows, columns);
      JTable table = new JTable (model);
      final TableRowSorter<TableModel> sorter;
      sorter = new TableRowSorter<TableModel> (model);
      table.setRowSorter (sorter);
      getContentPane ().add (new JScrollPane (table));

      JPanel pnl = new JPanel ();
      pnl.add (new JLabel ("Filter expression:"));
      final JTextField txtFE = new JTextField (25);
      pnl.add (txtFE);
      JButton btnSetFE = new JButton ("Set Filter Expression");
      ActionListener al;
      al = new ActionListener ()
           {
               public void actionPerformed (ActionEvent e)
               {
                  String expr = txtFE.getText ();
                  sorter.setRowFilter (RowFilter.regexFilter (expr));
                  sorter.setSortKeys (null);
               }
           };
      btnSetFE.addActionListener (al);
      pnl.add (btnSetFE);
      getContentPane ().add (pnl, BorderLayout.SOUTH);

      setSize (750, 150);
      setVisible (true);
   }

   public static void main (String [] args)
   {
      Runnable r = new Runnable ()
                   {
                       public void run ()
                       {
                          try
                          {
                              new BugLog ("Bug Log");
                          }
                          catch (ParseException pe)
                          {
                              JOptionPane.showMessageDialog (null,
                                                             pe.getMessage ());
                              System.exit (1);
                          }
                       }
                   };
      EventQueue.invokeLater (r);
   }
}
