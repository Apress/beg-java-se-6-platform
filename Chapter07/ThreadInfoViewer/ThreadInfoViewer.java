// ThreadInfoViewer.java;

// Unix compile   : javac -cp $JAVA_HOME/lib/tools.jar ThreadInfoViewer.java
//
// Windows compile: javac -cp %JAVA_HOME%/lib/tools.jar ThreadInfoViewer.java

import static java.lang.management.ManagementFactory.*;

import java.lang.management.*;

import java.io.*;

import java.util.*;

import javax.management.*;
import javax.management.remote.*;

import com.sun.tools.attach.*;

public class ThreadInfoViewer
{
    static final String CON_ADDR =
      "com.sun.management.jmxremote.localConnectorAddress";

    public static void main (String [] args) throws Exception
    {
       if (args.length != 1)
       {
          System.err.println ("Unix usage   : "+
                              "java -cp $JAVA_HOME/lib/tools.jar:. "+
                              "ThreadInfoViewer pid");
          System.err.println ();
          System.err.println ("Windows usage: "+
                              "java -cp %JAVA_HOME%/lib/tools.jar;. "+
                              "ThreadInfoViewer pid");
          return;
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
       // name of the thread MBean that is controlled by the JMX agent's MBean
       // server.

       ObjectName thdName = new ObjectName (THREAD_MXBEAN_NAME);
       Set<ObjectName> mbeans = mbsc.queryNames (thdName, null);

       // The for-each loop conveniently returns the name of the thread MBean.
       // There should only be one iteration because there is only one thread
       // MBean.

       for (ObjectName name: mbeans)
       {
            // Obtain a proxy for the ThreadMXBean interface that forwards its
            // method calls through the MBeanServerConnection identified by
            // mbsc.

            ThreadMXBean thdb;
            thdb = newPlatformMXBeanProxy (mbsc, name.toString (),
                                           ThreadMXBean.class);

            // Obtain and output thread information.

            System.out.println ("Threads presumably still alive...");

            long [] thdIDs = thdb.getAllThreadIds ();
            if (thdIDs != null) // safety check (possibly unnecessary)
                for (long thdID: thdIDs)
                {
                     ThreadInfo thdi = thdb.getThreadInfo (thdID);
                     System.out.println ("  Name: "+thdi.getThreadName ());
                     System.out.println ("  State: "+thdi.getThreadState ());
                }

            // The information identifies any deadlocked threads...

            System.out.println ("Deadlocked threads...");

            thdIDs = thdb.findDeadlockedThreads ();
            if (thdIDs == null)
                System.out.println ("  None");
            else
            {
                ThreadInfo [] thdsi = thdb.getThreadInfo (thdIDs);
                for (ThreadInfo thdi: thdsi)
                     System.out.println ("  Name: "+thdi.getThreadName ());
            }
       }
    }
}
