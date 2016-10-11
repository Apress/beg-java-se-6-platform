// FuncSupported.java

import java.sql.*;

public class FuncSupported
{
   public static void main (String [] args) throws SQLException
   {
      if (args.length != 2)
      {
          System.err.println ("usage: java FuncSupported jdbcURL funcname");
          return;
      }

      Connection con = DriverManager.getConnection (args [0]);

      System.out.println ("Function "+args [1]+
                          (isSupported (con, args[1]) ? " is supported" :
                          " is not supported"));

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

   static boolean isSupported (Connection con, String func) throws SQLException
   {
      DatabaseMetaData dbmd = con.getMetaData ();

      if (func.equalsIgnoreCase ("CONVERT"))
          return dbmd.supportsConvert ();

      func = func.toUpperCase ();

      if (dbmd.getNumericFunctions ().toUpperCase ().indexOf (func) != -1)
          return true;

      if (dbmd.getStringFunctions ().toUpperCase ().indexOf (func) != -1)
          return true;

      if (dbmd.getSystemFunctions ().toUpperCase ().indexOf (func) != -1)
          return true;

      if (dbmd.getTimeDateFunctions ().toUpperCase ().indexOf (func) != -1)
          return true;

      return false;
   }
}
