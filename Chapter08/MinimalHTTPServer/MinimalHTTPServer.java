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
      server.start ();
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

