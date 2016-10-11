// SQLXMLSupported.java

import java.sql.*;

public class SQLXMLSupported
{
   public static void main (String [] args)
   {
      if (args.length != 1)
      {
          System.err.println ("usage: java SQLXMLSupported jdbcURL");
          return;
      }

      try
      {
          Connection con;
          con = DriverManager.getConnection (args [0]);

          DatabaseMetaData dbmd = con.getMetaData ();
          ResultSet rs = dbmd.getTypeInfo ();
          boolean found = false;
          while (rs.next ())
          {
             if (rs.getInt ("DATA_TYPE") == Types.SQLXML)
             {
                 found = true;
                 break;
             }
          }

          if (found)
                 System.out.println ("SQL XML Data Type is supported");
             else
                 System.out.println ("SQL XML Data Type is not supported");

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
      }
      catch (SQLException sqlex)
      {
          System.out.println (sqlex);
      }
   }
}
