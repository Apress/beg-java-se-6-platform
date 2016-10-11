// BuggyThreads.java

public class BuggyThreads
{
   public static void main (String [] args)
   {
      System.out.println ("Starting Thread A");
      new ThreadA ("A").start ();
      System.out.println ("Starting Thread B");
      new ThreadB ("B").start ();

      System.out.println ("Entering infinite loop");
      while (true);
   }
}

class ThreadA extends Thread
{
   ThreadA (String name)
   {
      setName (name);
   }

   public void run ()
   {
      while (true)
      {
         synchronized ("A")
         {
            System.out.println ("Thread A acquiring Lock A");
            synchronized ("B")
            {
               System.out.println ("Thread A acquiring Lock B");
               try
               {
                  Thread.sleep ((int) Math.random ()*100);
               }
               catch (InterruptedException e)
               {
               }
               System.out.println ("Thread A releasing Lock B");
            }
            System.out.println ("Thread A releasing Lock A");
         }
      }
   }
}

class ThreadB extends Thread
{
   ThreadB (String name)
   {
      setName (name);
   }

   public void run ()
   {
      while (true)
      {
         synchronized ("B")
         {
            System.out.println ("Thread B acquiring Lock B");
            synchronized ("A")
            {
               System.out.println ("Thread B acquiring Lock A");
               try
               {
                  Thread.sleep ((int) Math.random ()*100);
               }
               catch (InterruptedException e)
               {
               }
               System.out.println ("Thread B releasing Lock A");
            }
            System.out.println ("Thread B releasing Lock B");
         }
      }
   }
}

