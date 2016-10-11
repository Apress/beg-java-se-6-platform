// EmployeeInit.java

import java.io.*;

import java.sql.*;

import javax.swing.*;

public class EmployeeInit
{
   public static void main (String [] args)
   {
      try
      {
          Connection con;
          con = DriverManager.getConnection ("jdbc:derby://localhost:1527/"+
                                             "c:\\db\\employee");

          PreparedStatement ps;
          ps = con.prepareStatement ("insert into employee(name,photo) "+
                                     "values(?,?)");
          ps.setString (1, "Duke");

          Blob blob = con.createBlob ();
          try
          {
              ImageIcon ii = new ImageIcon ("duke.png");

              ObjectOutputStream oos;
              oos = new ObjectOutputStream (blob.setBinaryStream (1));
              oos.writeObject (ii);
              oos.close ();
              ps.setBlob (2, blob);
              ps.execute ();
          }
          catch (Exception ex)
          {
              System.out.println (ex);
          }
          blob.free ();
          ps.close ();
      }
      catch (SQLException sqlex)
      {
          System.out.println (sqlex);
      }
   }
}
