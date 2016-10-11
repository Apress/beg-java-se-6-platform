// RunConverter.java

package wsdemo;

import javax.xml.ws.Endpoint;

public class RunConverter
{
   public static void main (String [] args)
   {
      // Start the lightweight HTTP server and the Converter Web service.

      Endpoint.publish ("http://localhost:8080/WSDemo/Converter",
                        new Converter ());
   }
}
