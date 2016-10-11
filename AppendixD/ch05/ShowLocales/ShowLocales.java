// ShowLocales.java

import java.awt.*;

import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

public class ShowLocales extends JFrame
{
   public ShowLocales ()
   {
      super ("Show Locales");
      setDefaultCloseOperation (EXIT_ON_CLOSE);

      final Locale [] locales = Locale.getAvailableLocales ();

      TableModel model = new AbstractTableModel ()
      {
         public int getColumnCount ()
         {
            return 5;
         }

         public String getColumnName (int column)
         {
            if (column == 0)
                return "Locale";
            else
            if (column == 1)
                return "Country (Default Locale)";
            else
            if (column == 2)
                return "Language (Default Locale)";
            else
            if (column == 3)
                return "Country (Localized)";
            else
                return "Language (Localized)";
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
                        return locales [row].getDisplayCountry ();
                    else
                    if (col == 2)
                        return locales [row].getDisplayLanguage ();
                    else
                    if (col == 3)
                        return locales [row].getDisplayCountry (locales [row]);
                    else
                        return locales [row].getDisplayLanguage (locales [row]);
                }
                catch (IllegalArgumentException iae)
                {
                    return null;
                }
         }
      };

      JTable table = new JTable (model);
      table.setPreferredScrollableViewportSize (new Dimension (750, 300));
      Renderer r = new Renderer ();
      table.getColumnModel ().getColumn (3).setCellRenderer (r);
      table.getColumnModel ().getColumn (4).setCellRenderer (r);
      getContentPane ().add (new JScrollPane (table));

      pack ();
      setVisible (true);
   }

   public static void main (String [] args)
   {
      Runnable r = new Runnable ()
                   {
                       public void run ()
                       {
                          new ShowLocales ();
                       }
                   };
      EventQueue.invokeLater (r);
   }
}

class Renderer extends JLabel implements TableCellRenderer
{
   Renderer ()
   {
      // Deactivate JLabel's use of the bold style.

      setFont (getFont ().deriveFont (Font.PLAIN));
   }

   public Component getTableCellRendererComponent (JTable table, Object value,
                                                   boolean isSelected,
                                                   boolean isFocus, int row,
                                                   int column)
   {
      String s = (String) value;
      if (s.equals ("\u12a4\u122d\u1275\u122b") ||
          s.equals ("\u1275\u130d\u122d\u129b"))
          setFont (new Font ("GF Zemen Unicode", Font.PLAIN, 12));

      setText (s);
      return this;
   }
}
