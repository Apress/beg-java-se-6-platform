// LoadAverageViewer.java;

// Unix compile   : javac -cp $JAVA_HOME/lib/tools.jar LoadAverageViewer.java
//
// Windows compile: javac -cp %JAVA_HOME%/lib/tools.jar LoadAverageViewer.java

import static java.lang.management.ManagementFactory.*;

import java.lang.management.*;

import java.io.*;

import java.util.*;

import javax.management.*;
import javax.management.remote.*;

import com.sun.tools.attach.*;

public class LoadAverageViewer
{
    static final String CON_ADDR =
      "com.sun.management.jmxremote.localConnectorAddress";

    static final int MIN_MINUTES = 2;
    static final int MAX_MINUTES = 10;

    public static void main (String [] args) throws Exception
    {
       int minutes = MIN_MINUTES;

       if (args.length != 2)
       {
          System.err.println ("Unix usage   : "+
                              "java -cp $JAVA_HOME/lib/tools.jar:. "+
                              "LoadAverageViewer pid minutes");
          System.err.println ();
          System.err.println ("Windows usage: "+
                              "java -cp %JAVA_HOME%/lib/tools.jar;. "+
                              "LoadAverageViewer pid minutes");
          return;
       }

       try
       {
           int min = Integer.parseInt (args [1]);
           if (min < MIN_MINUTES || min > MAX_MINUTES)
           {
               System.err.println (min+" out of range ["+MIN_MINUTES+", "+
                                   MAX_MINUTES+"]");
               return;
           }
           minutes = min;
       }
       catch (NumberFormatException nfe)
       {
           System.err.println ("Unable to parse "+args [1]+" as an integer.");
           System.err.println ("LoadAverageViewer will repeatedly check load "+
                               " average (if available) every minute for "+
                               MIN_MINUTES+" minutes.");
       }

       // Attempt to attach to the target virtual machine whose identifier is
       // specified as a command-line argument.

       VirtualMachine vm = VirtualMachine.attach (args [0]);

       // Attempt to obtain the target virtual machine's connector address so
       // that this virtual machine can communicate with its connector server.

       String conAddr = vm.getAgentProperties ().getProperty (CON_ADDR);

       // If there is no connector address, a connector server and JMX agent
       // are not started in the target virtual machine. Therefore, load the
       // JMX agent into the target.

       if (conAddr == null)
       {
           // The JMX agent is stored in management-agent.jar. This JAR file
           // is located in the lib subdirectory of the JRE's home directory.

           String agent = vm.getSystemProperties ()
                            .getProperty ("java.home")+File.separator+
                            "lib"+File.separator+"management-agent.jar";

           // Attempt to load the JMX agent.

           vm.loadAgent (agent);

           // Once again, attempt to obtain the target virtual machine's
           // connector address.

           conAddr = vm.getAgentProperties ().getProperty (CON_ADDR);

           // Although the second attempt to obtain the connector address
           // should succeed, throw an exception if it does not.

           if (conAddr == null)
               throw new NullPointerException ("conAddr is null");
       }

       // Prior to connecting to the target virtual machine's connector
       // server, the String-based connector address must be converted into a
       // JMXServiceURL.

       JMXServiceURL servURL = new JMXServiceURL (conAddr);

       // Attempt to create a connector client that is connected to the
       // connector server located at the specified URL.

       JMXConnector con = JMXConnectorFactory.connect (servURL);

       // Attempt to obtain an MBeanServerConnection that represents the
       // remote JMX agent's MBean server.

       MBeanServerConnection mbsc = con.getMBeanServerConnection ();

       // Obtain object name for thread MBean, and use this name to obtain the
       // name of the OS MBean that is controlled by the JMX agent's MBean
       // server.

       ObjectName osName = new ObjectName (OPERATING_SYSTEM_MXBEAN_NAME);
       Set<ObjectName> mbeans = mbsc.queryNames (osName, null);

       // The for-each loop conveniently returns the name of the OS MBean.
       // There should only be one iteration because there is only one OS
       // MBean.

       for (ObjectName name: mbeans)
       {
            // Obtain a proxy for the OperatingSystemMXBean interface that
            // forwards its method calls through the MBeanServerConnection
            // identified by mbsc.

            OperatingSystemMXBean osb;
            osb = newPlatformMXBeanProxy (mbsc, name.toString (),
                                          OperatingSystemMXBean.class);

            double loadAverage = osb.getSystemLoadAverage ();
            if (loadAverage < 0)
            {      System.out.println (loadAverage);
                System.out.println ("Load average not supported on platform");
                return;
            }

            for (int i = 0; i < minutes; i++)
            {
                 System.out.printf ("Load average: %f", loadAverage);
                 System.out.println ();

                 try
                 {
                     Thread.sleep (60000); // Sleep for about one minute.
                 }
                 catch (InterruptedException ie)
                 {
                 }

                 loadAverage = osb.getSystemLoadAverage ();
            }

            break;
       }
    }
}
