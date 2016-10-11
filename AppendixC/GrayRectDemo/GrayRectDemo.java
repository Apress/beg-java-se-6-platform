// GrayRectDemo.java

import java.awt.*;

import javax.swing.*;

public class GrayRectDemo extends JFrame
{
   public GrayRectDemo ()
   {
      super ("Gray Rect Demo");
      setDefaultCloseOperation (EXIT_ON_CLOSE);

      // Cover the main window with a component that delays after painting its
      // contents.

      getContentPane ().add (new SlowPaintComponent ());

      setSize (300, 300);
      setVisible (true);
   }

   public static void main (String [] args)
   {
      Runnable r = new Runnable ()
                   {
                       public void run ()
                       {
                          new GrayRectDemo ();
                       }
                   };
      EventQueue.invokeLater (r);
   }
}

class SlowPaintComponent extends JLabel
{
   final static int DELAY = 1000;

   SlowPaintComponent ()
   {
      // This component will always paint its entire display area -- there are
      // no transparent areas.

      setOpaque (true);
   }

   public void paintComponent (Graphics g)
   {
      // Paint background.

      g.setColor (Color.white);
      g.fillRect (0, 0, getWidth (), getHeight ());

      // Paint foreground shape.

      g.setColor (Color.black);
      g.fillOval (0, 0, getWidth (), getHeight ());

      try
      {
          // Sleep for DELAY milliseconds so that the gray rect problem can be
          // demonstrated.

          Thread.sleep (DELAY);
      }
      catch (InterruptedException e)
      {
      }
   }
}
