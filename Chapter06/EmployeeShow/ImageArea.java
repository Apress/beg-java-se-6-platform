// ImageArea.java

import java.awt.*;

import javax.swing.*;

/**
 *  This class defines a specialized panel for displaying an acquired image.
 *
 *  @author Jeff Friesen
 */

public class ImageArea extends JPanel
{
   /**
    *  Displayed image's Image object.
    */

   private Image image;

   /**
    *  Construct an ImageArea by setting its default preferred size to
    *  (300, 300).
    */

   public ImageArea ()
   {
      // Set the ImageArea's default preferred size to 300 pixels by 300
      // pixels.

      setPreferredSize (new Dimension (300, 300));
   }

   /**
    *  Repaint the ImageArea with the current image's pixels.
    *
    *  @param g graphics context
    */

   public void paintComponent (Graphics g)
   {
      // Repaint the component's background.

      super.paintComponent (g);

      // If an image has been defined, draw that image using the Component
      // layer of this ImageArea object as the ImageObserver.

      if (image != null)
          g.drawImage (image, 0, 0, this);
   }

   /**
    *  Establish a new image and update the display.
    *
    *  @param image new image's Image reference
    */

   public void setImage (Image image)
   {
      // Save the image for later repaint.

      this.image = image;

      // Set this panel's preferred size to the image's size, to influence the
      // display of scrollbars.

      setPreferredSize (new Dimension (image.getWidth (this),
                                       image.getHeight (this)));

      // Invalidate this panel component, walk up the containment hierarchy to
      // the first validateRoot (the JScrollPane), and validate that root --
      // which also validates the ImageArea component. Validation results in
      // the JScrollPane displaying scrollbars or not -- and the image being
      // displayed.

      revalidate ();
   }
}
