// WindowIcons.java

import java.awt.*;
import java.awt.image.*;

import java.util.*;

import javax.swing.*;

public class WindowIcons extends JFrame
{
   final static int BIG_ICON_WIDTH = 32;
   final static int BIG_ICON_HEIGHT = 32;
   final static int BIG_ICON_RENDER_WIDTH = 20;

   final static int SMALL_ICON_WIDTH = 16;
   final static int SMALL_ICON_HEIGHT = 16;
   final static int SMALL_ICON_RENDER_WIDTH = 10;

   public WindowIcons ()
   {
      super ("Window Icons");
      setDefaultCloseOperation (EXIT_ON_CLOSE);

      ArrayList<BufferedImage> images = new ArrayList<BufferedImage> ();

      BufferedImage bi;
      bi = new BufferedImage (SMALL_ICON_WIDTH, SMALL_ICON_HEIGHT,
                              BufferedImage.TYPE_INT_ARGB);
      Graphics g = bi.getGraphics ();
      g.setColor (Color.black);
      g.fillRect (0, 0, SMALL_ICON_RENDER_WIDTH, SMALL_ICON_HEIGHT);
      g.dispose ();
      images.add (bi);

      bi = new BufferedImage (BIG_ICON_WIDTH, BIG_ICON_HEIGHT,
                              BufferedImage.TYPE_INT_ARGB);
      g = bi.getGraphics ();
      for (int i = 0; i < BIG_ICON_HEIGHT; i++)
      {
           g.setColor (((i & 1) == 0) ? Color.black : Color.white);
           g.fillRect (0, i, BIG_ICON_RENDER_WIDTH, 1);
      }
      g.dispose ();
      images.add (bi);

      setIconImages (images);

      setSize (250, 100);
      setVisible (true);

      // Create and display a modeless Swing dialog via an anonymous inner
      // class.

      new JDialog (this, "Arbitrary Dialog")
          {
             {
                setSize (200, 100);
                setVisible (true);
             }
          };
   }

   public static void main (String [] args)
   {
      Runnable r = new Runnable ()
                   {
                       public void run ()
                       {
                          new WindowIcons ();
                       }
                   };
      EventQueue.invokeLater (r);
   }
}
