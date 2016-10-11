// SaveToGIF.java

import java.awt.*;
import java.awt.image.*;

import java.io.*;

import javax.imageio.*;

public class SaveToGIF
{
   final static int WIDTH = 50;
   final static int HEIGHT = 50;
   final static int NUM_ITER = 1500;

   public static void main (String [] args)
   {
      // Create a sample image consisting of randomly-colored pixels in
      // randomly-colored positions.

      BufferedImage bi;
      bi = new BufferedImage (WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
      Graphics g = bi.getGraphics ();
      for (int i = 0; i < NUM_ITER; i++)
      {
           int x = rnd (WIDTH);
           int y = rnd (HEIGHT);
           g.setColor (new Color (rnd (256), rnd (256), rnd (256)));
           g.drawLine (x, y, x, y);
      }
      g.dispose ();

      // Save the image to image.gif.

      try
      {
          ImageIO.write (bi, "gif", new File ("image.gif"));
      }
      catch (IOException ioe)
      {
          System.err.println ("Unable to save image to file");
      }
   }

   static int rnd (int limit)
   {
      return (int) (Math.random ()*limit);
   }
}
