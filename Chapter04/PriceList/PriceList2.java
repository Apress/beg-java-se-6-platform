// PriceList2.java

import javax.swing.*;
import javax.swing.table.*;

public class PriceList2 extends JFrame
{
   public PriceList2 (String title)
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

      TableModel model = new DefaultTableModel (rows, columns)
                         {
                             public Class getColumnClass (int column)
                             {
                                if (column >= 0 &&
                                    column <= getColumnCount ())
                                    return getValueAt (0, column).getClass ();
                                else
                                    return Object.class;
                             }
                         };
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
                          new PriceList2 ("Price List #2");
                       }
                   };
      java.awt.EventQueue.invokeLater (r);
   }
}
