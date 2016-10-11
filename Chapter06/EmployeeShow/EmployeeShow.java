// EmployeeShow.java

import java.io.*;

import java.sql.*;

import javax.swing.*;

public class EmployeeShow extends JFrame
{
   static ImageIcon image;

   public EmployeeShow ()
   {
      super ();

      setDefaultCloseOperation (EXIT_ON_CLOSE);

      ImageArea ia = new ImageArea ();
      ia.setImage (image.getImage ());

      getContentPane ().add (ia);

      pack ();

      setVisible (true);
   }

   public static void main (String [] args)
   {
      try
      {
          Connection con;
          con = DriverManager.getConnection ("jdbc:derby://localhost:1527/"+
                                             "c:\\db\\employee");

          Statement s = con.createStatement ();
          ResultSet rs = s.executeQuery ("select photo from employee where "+
                                         "name = 'Duke'");
          if (rs.next ())
          {
              Blob photo = rs.getBlob (1);

              ObjectInputStream ois = null;
              try
              {
                  ois = new ObjectInputStream (photo.getBinaryStream ());
                  image = (ImageIcon) ois.readObject ();
              }
              catch (Exception ex)
              {
                  System.out.println (ex);
              }
              finally
              {
                  try
                  {
                      ois.close ();
                  }
                  catch (IOException ioex)
                  {
                  }
              }
          }
          else
              JOptionPane.showMessageDialog (null, "No Duke employee");
          s.close ();

          if (con.getMetaData ().getDriverName ().equals ("Apache Derby "+
              "Embedded JDBC Driver"))
              try
              {
                  DriverManager.getConnection ("jdbc:derby:;shutdown=true");
              }
              catch (SQLException sqlex)
              {
                  System.out.println ("Database shut down normally");
              }

          if (image != null)
          {
              Runnable r = new Runnable ()
                           {
                               public void run ()
                               {
                                  new EmployeeShow ();
                               }
                           };
              java.awt.EventQueue.invokeLater (r);
          }
      }
      catch (SQLException sqlex)
      {
          System.out.println (sqlex);
      }
   }
}
