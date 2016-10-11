// DumpSchemas.java

import java.sql.*;

public class DumpSchemas
{
   public static void main (String [] args)
   {
      if (args.length != 1)
      {
          System.err.println ("usage: java DumpSchemas jdbcURL");
          return;
      }

      try
      {
          Connection con;
          con = DriverManager.getConnection (args [0]);

          DatabaseMetaData dbmd = con.getMetaData ();
          ResultSet rs = dbmd.getSchemas ();
          while (rs.next ())
             System.out.println (rs.getString (1));

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
