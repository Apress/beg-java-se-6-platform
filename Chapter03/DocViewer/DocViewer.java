// DocViewer.java

import java.awt.*;

public class DocViewer
{
   public static void main (String [] args)
   {
      SplashScreen splashScreen = SplashScreen.getSplashScreen ();
      if (splashScreen != null)
      {
          // Surround the image with a border that occupies 5% of the smaller
          // of the width and height.

          Dimension size = splashScreen.getSize ();
          int borderDim;
          if (size.width < size.height)
              borderDim = (int) (size.width * 0.05);
          else
              borderDim = (int) (size.height * 0.05);

          Graphics g = splashScreen.createGraphics ();
          g.setColor (Color.blue);
          for (int i = 0; i < borderDim; i++)
               g.drawRect (i, i, size.width-1-i*2, size.height-1-i*2);

          // Make sure the text fits the splash window before drawing -- the
          // text is centered in the lower part of the splash window.

          FontMetrics fm = g.getFontMetrics ();
          int sWidth = fm.stringWidth ("Initializing...");
          int sHeight = fm.getHeight ();
          if (sWidth < size.width && 2*sHeight < size.height)
          {
              g.setColor (Color.blue);
              g.drawString ("Initializing...",
                            (size.width-sWidth)/2,
                            size.height-2*sHeight);
          }

          // Update the splash window with the overlay image.

          splashScreen.update ();

          // Pause for 5 seconds to simulate a lengthy initialization task,
          // and to view the image.
          try
          {
              Thread.sleep (5000);
          }
          catch (InterruptedException e)
          {
          }
      }

      // Continue with the DocViewer application.
   }
}
