// PriceList1.java

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class PriceList1 extends JFrame
{
   public PriceList1 (String title)
   {
      super (title);
      setDefaultCloseOperation (EXIT_ON_CLOSE);

      String [] columns = { "Item", "Price" };

      Object [][] rows =
      {
         { "Bag of potatoes", 10.98 },
         { "Magazine", 7.99 },
         { "Can of soup", 0.89 },
         { "DVD movie", 39.99 }
      };

      TableModel model = new DefaultTableModel (rows, columns);
      final JTable table = new JTable (model);
      RowSorter<TableModel> sorter = new TableRowSorter<TableModel> (model);
      table.setRowSorter (sorter);
      table.setSelectionMode (ListSelectionModel.SINGLE_SELECTION);
      ListSelectionListener lsl;
      lsl = new ListSelectionListener ()
            {
                public void valueChanged (ListSelectionEvent lse)
                {
                   int index = table.getSelectedRow ();
                   if (index != -1)
                   {
                       JOptionPane.showMessageDialog (PriceList1.this,
                                                      "View index = "+index+
                                                      ", Model index = "+
                                        table.convertRowIndexToModel (index));
                   }
                }
            };
      table.getSelectionModel ().addListSelectionListener (lsl);
      getContentPane ().add (new JScrollPane (table));

      setSize (200, 150);
      setVisible (true);
   }

   public static void main (String [] args)
   {
      Runnable r = new Runnable ()
                   {
                       public void run ()
                       {
                          new PriceList1 ("Price List #1");
                       }
                   };
      java.awt.EventQueue.invokeLater (r);
   }
}
