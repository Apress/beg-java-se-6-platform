// ShowCalPage.java

import java.applet.Applet;

import java.awt.*;
import java.awt.event.*;

import java.text.*;

import java.util.*;

import javax.swing.*;

public class ShowCalPage extends JApplet
{
   public void init ()
   {
      try
      {
         EventQueue.invokeAndWait (new Runnable ()
                                   {
                                       public void run ()
                                       {
                                          createGUI ();
                                       }
                                   });
      }
      catch (Exception exc)
      {
         System.err.println (exc);
      }
   }

   private void createGUI ()
   {
      String [] localeDescriptions =
      {
         "English",
         "Japanese Gregorian",
         "Japanese Imperial Era"
      };

      final Locale [] locales =
      {
         Locale.ENGLISH,
         Locale.JAPANESE,
         CalPage.JAPAN_IMP_ERA
      };

      final CalPage cp = new CalPage (getWidth ()-50, getHeight ()-50,
                                      locales [0]);
      cp.setBorder (BorderFactory.createEtchedBorder ());
      JPanel pnl = new JPanel ();
      pnl.add (cp);
      getContentPane ().add (pnl, BorderLayout.NORTH);

      pnl = new JPanel ();
      pnl.add (new JLabel ("Locale:"));
      JComboBox cbLocales = new JComboBox (localeDescriptions);
      ItemListener il;
      il = new ItemListener ()
           {
               public void itemStateChanged (ItemEvent e)
               {
                  if (e.getStateChange () == ItemEvent.SELECTED)
                  {
                      JComboBox cb = (JComboBox) e.getSource ();
                      cp.setNewLocale (locales [cb.getSelectedIndex ()]);
                  }
               }
           };
      cbLocales.addItemListener (il);
      pnl.add (cbLocales);
      getContentPane ().add (pnl, BorderLayout.CENTER);
   }
}

class CalPage extends JPanel
{
   final static Locale JAPAN_IMP_ERA = new Locale ("ja", "JP", "JP");

   private Locale locale;

   CalPage (int width, int height, Locale initLocale)
   {
      setPreferredSize (new Dimension (width, height));

      locale = initLocale;
   }

   public void paintComponent (Graphics g)
   {
      int width = getWidth ();
      int height = getHeight ();

      g.setColor (Color.white);
      g.fillRect (0, 0, width, height);

      Calendar cal = Calendar.getInstance (locale);
      Date now = new Date ();
      cal.setTime (now);

      String header = cal.getDisplayName (Calendar.MONTH, Calendar.LONG,
                                          locale);
      if (locale.equals (JAPAN_IMP_ERA))
          header = cal.getDisplayName (Calendar.ERA, Calendar.LONG, locale)+
                   " "+cal.get (Calendar.YEAR)+" -- "+header;
      else
          header += " "+cal.get (Calendar.YEAR);

      FontMetrics fm = g.getFontMetrics ();
      Insets insets = getInsets ();
      g.setColor (Color.black);
      g.drawString (header, (width-fm.stringWidth (header))/2,
                    insets.top+fm.getHeight ());

      DateFormatSymbols dfs = new DateFormatSymbols (locale);
      String [] weekdayNames = dfs.getShortWeekdays ();
      int fieldWidth = (width-insets.left-insets.right)/7;
      g.drawString (weekdayNames [Calendar.SUNDAY], insets.left+
                    (fieldWidth-
                    fm.stringWidth (weekdayNames [Calendar.SUNDAY]))/2,
                    insets.top+3*fm.getHeight ());
      g.drawString (weekdayNames [Calendar.MONDAY], insets.left+fieldWidth+
                    (fieldWidth-
                    fm.stringWidth (weekdayNames [Calendar.MONDAY]))/2,
                    insets.top+3*fm.getHeight ());
      g.drawString (weekdayNames [Calendar.TUESDAY], insets.left+2*fieldWidth+
                    (fieldWidth-
                    fm.stringWidth (weekdayNames [Calendar.TUESDAY]))/2,
                    insets.top+3*fm.getHeight ());
      g.drawString (weekdayNames [Calendar.WEDNESDAY], insets.left+3*
                    fieldWidth+(fieldWidth-
                    fm.stringWidth (weekdayNames [Calendar.WEDNESDAY]))/2,
                    insets.top+3*fm.getHeight ());
      g.drawString (weekdayNames [Calendar.THURSDAY], insets.left+4*
                    fieldWidth+(fieldWidth-
                    fm.stringWidth (weekdayNames [Calendar.THURSDAY]))/2,
                    insets.top+3*fm.getHeight ());
      g.drawString (weekdayNames [Calendar.FRIDAY], insets.left+5*fieldWidth+
                    (fieldWidth-
                    fm.stringWidth (weekdayNames [Calendar.FRIDAY]))/2,
                    insets.top+3*fm.getHeight ());
      g.drawString (weekdayNames [Calendar.SATURDAY], insets.left+6*
                    fieldWidth+(fieldWidth-
                    fm.stringWidth (weekdayNames [Calendar.SATURDAY]))/2,
                    insets.top+3*fm.getHeight ());

      int dom = cal.get (Calendar.DAY_OF_MONTH);
      cal.set (Calendar.DAY_OF_MONTH, 1);
      int col = 0;
      switch (cal.get (Calendar.DAY_OF_WEEK))
      {
         case Calendar.MONDAY: col = 1; break;

         case Calendar.TUESDAY: col = 2; break;

         case Calendar.WEDNESDAY: col = 3; break;

         case Calendar.THURSDAY: col = 4; break;

         case Calendar.FRIDAY: col = 5; break;

         case Calendar.SATURDAY: col = 6;
      }
      cal.set (Calendar.DAY_OF_MONTH, dom);

      int row = 5*fm.getHeight ();
      for (int i = 1; i <= cal.getActualMaximum (Calendar.DAY_OF_MONTH); i++)
      {
           g.drawString (""+i, insets.left+fieldWidth*col+
                         (fieldWidth-fm.stringWidth (""+i))/2, row);
           if (++col > 6)
           {
               col = 0;
               row += fm.getHeight ();
           }
      }

      row += 2*fm.getHeight ();
      DateFormat df = DateFormat.getDateInstance (DateFormat.FULL, locale);
      g.drawString (df.format (now),
                    (width-fm.stringWidth (df.format (now)))/2, row);
   }

   void setNewLocale (Locale locale)
   {
      this.locale = locale;
      repaint ();
   }
}
