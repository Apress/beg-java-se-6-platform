// BasicAttach.java

// Unix compile   : javac -cp $JAVA_HOME/lib/tools.jar BasicAttach.java
//
// Windows compile: javac -cp %JAVA_HOME%/lib/tools.jar BasicAttach.java

import java.io.*;

import java.util.*;

import com.sun.tools.attach.*;

public class BasicAttach
{
   public static void main (String [] args) throws Exception
   {
      if (args.length != 1)
      {
          System.err.println ("Unix usage   : "+
                              "java -cp $JAVA_HOME/lib/tools.jar:. "+
                              "BasicAttach appmainclassname");
          System.err.println ();
          System.err.println ("Windows usage: "+
                              "java -cp %JAVA_HOME%/lib/tools.jar;. "+
                              "BasicAttach appmainclassname");
          return;
      }                                                             

      // Return a list of running virtual machines to which we can potentially
      // attach.

      List<VirtualMachineDescriptor> vmds = VirtualMachine.list ();

      // Search this list for the virtual machine whose display name matches
      // the name passed to this application as a command-line argument.

      for (VirtualMachineDescriptor vmd: vmds)
           if (vmd.displayName ().equals (args [0]))
           {
               // Attempt to attach.

               VirtualMachine vm = VirtualMachine.attach (vmd.id ());

               // Identify the location and name of the agent JAR file to
               // load. The location is relative to the target virtual machine
               // -- not the virtual machine running BasicAttach. The location
               // and JAR name are passed to the target virtual machine, which
               // (in this case) is responsible for loading the basicAgent.jar
               // file from the location.

               String agent = vm.getSystemProperties ()
                                .getProperty ("java.home")+File.separator+
                                "lib"+File.separator+"basicAgent.jar";

               // Attempt to load the agent into the target virtual machine.

               vm.loadAgent (agent);

               // Detach.

               vm.detach ();

               // Attempt to attach.

               vm = VirtualMachine.attach (vm.id ());

               // Attempt to load the agent into the target virtual machine,
               // specifying a comma-separated list of options.

               vm.loadAgent (agent, "a=b,c=d,x=y");
               return;
           }

      System.out.println ("Unable to find target virtual machine");
   }
}
