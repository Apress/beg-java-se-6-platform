// PriceList1.java

import javax.swing.*;
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
      JTable table = new JTable (model);
      RowSorter<TableModel> sorter = new TableRowSorter<TableModel> (model);
      table.setRowSorter (sorter);
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
