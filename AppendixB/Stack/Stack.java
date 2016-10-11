// Stack.java

public class Stack<T>
{
   private T [] items;
   private int top;

   @SuppressWarnings("unchecked")
   public Stack (int size)
   {
      items = (T []) new Object [size];
      top = -1;
   }

   public void push (T item) throws Exception
   {
      if (top == items.length-1)
          throw new Exception ("Stack Full");
      items [++top] = item;
   }

   public T pop () throws Exception
   {
      if (top == -1)
          throw new Exception ("Stack Empty");
      return items [top--];
   }
}
