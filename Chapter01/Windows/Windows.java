// Windows.java

import java.awt.*;

import javax.swing.*;

public class Windows
{
   public static void main (String [] args)
   {
      // Create a pseudo-ownerless Swing dialog (its owner is a hidden shared
      // frame window).

      JDialog d1 = new JDialog ((JFrame) null, "Dialog 1");
      d1.setName ("Dialog 1");

      // Create a true ownerless Swing dialog.

      JDialog d2 = new JDialog ((Window) null, "Dialog 2");
      d2.setName ("Dialog 2");

      // Create an ownerless frame.

      Frame f = new Frame ();
      f.setName ("Frame 1");

      // Create a window owned by the frame.

      Window w1 = new Window (f);
      w1.setName ("Window 1");

      // Create an ownerless window.

      Window w2 = new Window (null);
      w2.setName ("Window 2");

      // Output lists of all windows, ownerless windows, and frame windows.

      System.out.println ("ALL WINDOWS");
      Window [] windows = Window.getWindows ();
      for (Window window: windows)
           System.out.println (window.getName ()+": "+window.getClass ());
      System.out.println ();

      System.out.println ("OWNERLESS WINDOWS");
      Window [] ownerlessWindows = Window.getOwnerlessWindows ();
      for (Window window: ownerlessWindows)
           System.out.println (window.getName ()+": "+window.getClass ());
      System.out.println ();

      System.out.println ("FRAME WINDOWS");
      Frame [] frames = Frame.getFrames ();
      for (Frame frame: frames)
           System.out.println (frame.getName ()+": "+frame.getClass ());
   }
}
