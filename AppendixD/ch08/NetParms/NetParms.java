// NetParms.java

import java.net.*;

import java.util.*;

public class NetParms
{
   public static void main (String [] args) throws SocketException
   {
      Enumeration<NetworkInterface> eni;
      eni = NetworkInterface.getNetworkInterfaces ();
      for (NetworkInterface ni: Collections.list (eni))
      {
           System.out.println ("Name = "+ni.getName ());
           System.out.println ("Display Name = "+ni.getDisplayName ());
           System.out.println ("Loopback = "+ni.isLoopback ());
           System.out.println ("Up and running = "+ni.isUp ());
           System.out.println ("MTU = "+ni.getMTU ());
           System.out.println ("Supports multicast = "+
                               ni.supportsMulticast ());
           System.out.println ("Sub-interfaces");
           Enumeration<NetworkInterface> eni2;
           eni2 = ni.getSubInterfaces ();
           for (NetworkInterface ni2: Collections.list (eni2))
                System.out.println ("   "+ni2);
           List<InterfaceAddress> ias = ni.getInterfaceAddresses ();
           for (InterfaceAddress ia: ias)
           {
                // Because it is possible for getInterfaceAddresses() to
                // return a list consisting of a single null element -- I
                // found this to be the case for a WAN (PPP/SLIP) Interface,
                // an if statement test is needed to prevent a
                // NullPointerException.

                if (ia == null)
                    break;

                System.out.println ("Interface Address");
                System.out.println ("  Address: "+ia.getAddress ());
                System.out.println ("  Broadcast: "+ia.getBroadcast ());
                System.out.println ("  Prefix length/Subnet mask: "+
                                    ia.getNetworkPrefixLength ());
           }
           System.out.println ();
      }
   }
}
