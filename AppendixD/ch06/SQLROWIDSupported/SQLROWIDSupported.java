// SQLROWIDSupported.java

import java.sql.*;

public class SQLROWIDSupported
{
   public static void main (String [] args)
   {
      if (args.length != 1)
      {
          System.err.println ("usage: java SQLROWIDSupported jdbcURL");
          return;
      }

      try
      {
          Connection con;
          con = DriverManager.getConnection (args [0]);

          DatabaseMetaData dbmd = con.getMetaData ();
          if (dbmd.getRowIdLifetime () != RowIdLifetime.ROWID_UNSUPPORTED)
              System.out.println ("SQL ROWID Data Type is supported");
          else
              System.out.println ("SQL ROWID Data Type is not supported");

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
