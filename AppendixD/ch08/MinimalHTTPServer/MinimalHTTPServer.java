// MinimalHTTPServer.java

import java.io.*;

import java.net.*;

import java.util.*;

import com.sun.net.httpserver.*;

public class MinimalHTTPServer
{
   public static void main (String [] args) throws IOException
   {
      HttpServer server = HttpServer.create (new InetSocketAddress (8000), 0);
      server.createContext ("/echo", new Handler ());
      server.createContext ("/date", new DateHandler ());
      server.start ();
   }
}

class DateHandler implements HttpHandler
{
   public void handle (HttpExchange xchg) throws IOException
   {
      xchg.sendResponseHeaders (200, 0);
      OutputStream os = xchg.getResponseBody ();
      DataOutputStream dos = new DataOutputStream (os);
      dos.writeBytes ("<html><head></head><body><center><b>"+
                      new Date ().toString ()+"</b></center></body></html>");  
      dos.close ();
   }
}

class Handler implements HttpHandler
{
   public void handle (HttpExchange xchg) throws IOException
   {
      Headers headers = xchg.getRequestHeaders ();
      Set<Map.Entry<String, List<String>>> entries = headers.entrySet ();

      StringBuffer response = new StringBuffer ();
      for (Map.Entry<String, List<String>> entry: entries)
           response.append (entry.toString ()+"\n");

      xchg.sendResponseHeaders (200, response.length ());
      OutputStream os = xchg.getResponseBody ();
      os.write (response.toString ().getBytes ());
      os.close ();
   }
}
