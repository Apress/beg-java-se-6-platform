// PartitionSpace.java

import java.io.*;

public class PartitionSpace
{
   public static void main (String [] args)
   {
      File [] roots = File.listRoots ();
      for (int i = 0; i < roots.length; i++)
      {
           System.out.println ("Partition: "+roots [i]);
           System.out.println ("Free space on this partition = "+
                               roots [i].getFreeSpace ());
           System.out.println ("Usable space on this partition = "+
                               roots [i].getUsableSpace ());
           System.out.println ("Total space on this partition = "+
                               roots [i].getTotalSpace ());
           System.out.println ("***");
      }
   }
}
