// ShowCurrencies.java

import java.awt.*;

import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

public class ShowCurrencies extends JFrame
{
   public ShowCurrencies ()
   {
      super ("Show Currencies");
      setDefaultCloseOperation (EXIT_ON_CLOSE);

      final Locale [] locales = Locale.getAvailableLocales ();

      TableModel model = new AbstractTableModel ()
      {
         public int getColumnCount ()
         {
            return 3;
         }

         public String getColumnName (int column)
         {
            if (column == 0)
                return "Locale";
            else
            if (column == 1)
                return "Currency Code";
            else
                return "Currency Symbol";
         }

         public int getRowCount ()
         {
            return locales.length;
         }

         public Object getValueAt (int row, int col)
         {
            if (col == 0)
                return locales [row];
            else
                try
                {
                    if (col == 1)
                        return Currency.getInstance (locales [row])
                                       .getCurrencyCode ();
                    else
                        return Currency.getInstance (locales [row])
                                       .getSymbol (locales [row]);
                }
                catch (IllegalArgumentException iae)
                {
                    return null;
                }
         }
      };

      JTable table = new JTable (model);
      JScrollPane sp = new JScrollPane (table);

      // Make sure that the table displays exactly 10 rows.

      Dimension size = sp.getViewport ().getPreferredSize ();
      size.height = 10*table.getRowHeight ();
      table.setPreferredScrollableViewportSize (size);

      getContentPane ().add (sp);

      pack ();
      setVisible (true);
   }

   public static void main (String [] args)
   {
      Runnable r = new Runnable ()
                   {
                       public void run ()
                       {
                          new ShowCurrencies ();
                       }
                   };
      EventQueue.invokeLater (r);
   }
}
