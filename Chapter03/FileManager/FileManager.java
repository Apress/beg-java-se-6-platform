// FileManager.java

import java.awt.*;
import java.awt.event.*;

import java.io.*;

import java.net.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

public class FileManager extends JFrame
{
   private Desktop desktop;

   private int x, y;

   public FileManager (String title, final File rootDir)
   {
      super (title);
      setDefaultCloseOperation (EXIT_ON_CLOSE);

      if (Desktop.isDesktopSupported ())
          desktop = Desktop.getDesktop ();

      DefaultMutableTreeNode rootNode;
      rootNode = new DefaultMutableTreeNode (rootDir);
      createNodes (rootDir, rootNode);
      final JTree tree = new JTree (rootNode);

      final JPopupMenu popup = new JPopupMenu ();
      PopupMenuListener pml;
      pml = new PopupMenuListener ()
            {
                public void popupMenuCanceled (PopupMenuEvent pme)
                {
                }

                public void popupMenuWillBecomeInvisible (PopupMenuEvent pme)
                {
                   int nc = popup.getComponentCount ();
                   for (int i = 0; i < nc; i++)
                        popup.remove (0);
                }

                public void popupMenuWillBecomeVisible (PopupMenuEvent pme)
                {
                   final Desktop.Action [] actions =
                   {
                      Desktop.Action.OPEN,
                      Desktop.Action.EDIT,
                      Desktop.Action.PRINT
                   };

                   ActionListener al;
                   al = new ActionListener ()
                        {
                            public void actionPerformed (ActionEvent ae)
                            {
                               try
                               {
                                   TreePath tp;
                                   tp = tree.getPathForLocation (x, y);
                                   if (tp != null)
                                   {        
                                       int pc = tp.getPathCount ();
                                       Object o = tp.getPathComponent (pc-1);

                                       DefaultMutableTreeNode n;
                                       n = (DefaultMutableTreeNode) o;

                                       File file = (File) n.getUserObject ();

                                       JMenuItem mi;
                                       mi = (JMenuItem) ae.getSource ();
                                       String s = mi.getText ();

                                       if (s.equals (actions [0].name ()))
                                           desktop.open (file);
                                       else
                                       if (s.equals (actions [1].name ()))
                                           desktop.edit (file);
                                       else
                                       if (s.equals (actions [2].name ()))
                                           desktop.print (file);
                                   }
                               }
                               catch (Exception e)
                               {
                               }
                            }
                        };

                   for (Desktop.Action action: actions)
                        if (desktop.isSupported (action))
                        {
                            TreePath tp = tree.getPathForLocation (x, y);
                            if (tp != null)
                            {
                                int pc = tp.getPathCount ();
                                Object o = tp.getPathComponent (pc-1);

                                DefaultMutableTreeNode n;
                                n = (DefaultMutableTreeNode) o;

                                File file = (File) n.getUserObject ();
                                if (!file.isDirectory () ||
                                    file.isDirectory () &&
                                    action == Desktop.Action.OPEN)
                                {
                                    JMenuItem mi;
                                    mi = new JMenuItem (action.name ());
                                    mi.addActionListener (al);
                                    popup.add (mi);
                                }
                            }
                        }
                }
            };
      if (desktop != null)
          popup.addPopupMenuListener (pml);

      tree.addMouseListener (new MouseAdapter ()
                             {
                                 public void mousePressed (MouseEvent e)
                                 {
                                    probablyShowPopup (e);
                                 }

                                 public void mouseReleased (MouseEvent e)
                                 {
                                    probablyShowPopup (e);
                                 }

                                 void probablyShowPopup (MouseEvent e)
                                 {
                                    if (e.isPopupTrigger ())
                                    {
                                        x = e.getX ();
                                        y = e.getY ();
                                        popup.show (e.getComponent (),
                                                    e.getX (),
                                                    e.getY ());}
                                 }
                             });

      getContentPane ().add (new JScrollPane (tree));

      setSize (400, 300);
      setVisible (true);
   }

   private void createNodes (File rootDir, DefaultMutableTreeNode rootNode)
   {
      File [] files = rootDir.listFiles ();
      for (int i = 0; i < files.length; i++)
      {
           DefaultMutableTreeNode node;
           node = new DefaultMutableTreeNode (files [i]);
           rootNode.add (node);

           if (files [i].isDirectory ())
               createNodes (files [i], node);
      }
   }

   public static void main (String [] args)
   {
      String rootDir = ".";
      if (args.length > 0)
      {
          rootDir = args [0];
          if (!rootDir.endsWith ("\\"))
              rootDir += "\\";
      }

      final String _rootDir = rootDir;
      Runnable r = new Runnable ()
                   {
                       public void run ()
                       {
                          new FileManager ("File Manager",
                                           new File (_rootDir));
                       }
                   };
      EventQueue.invokeLater (r);
   }
}
