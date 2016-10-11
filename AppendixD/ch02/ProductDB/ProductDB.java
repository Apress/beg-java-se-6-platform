//ProductDB.java

import java.util.*;
import java.util.Map;

public class ProductDB
{
   public static void build (Map<Integer, Product> map)
   {
      map.put (1000, new Product ("DVD player", 350));
      map.put (1011, new Product ("10 kilo bag of potatoes", 15.75));
      map.put (1102, new Product ("Magazine", 8.50));
      map.put (2023, new Product ("Automobile", 18500));
      map.put (2034, new Product ("Towel", 9.99));
   }
	
   public static void main(String[] args)
   {
      TreeMap<Integer, Product> db = new TreeMap<Integer, Product> ();
      build (db);

      System.out.println ("Database view of products ranging from 1000-1999");
      System.out.println (db.subMap (1000, 1999)+"\n");

      System.out.println ("Database view of products >= 1011");
      System.out.println (db.tailMap (1011)+"\n");

      System.out.println ("Database view of products < 2023");
      System.out.println (db.headMap (2023));

      System.out.println ();                                                            
      System.out.println ("First key higher than 2034: "+db.higherKey (2034));
      System.out.println ("First key lower than 2034: "+db.lowerKey (2034));
   }
}

class Product
{
   String desc;
   double price;

   Product (String desc, double price)
   {
      this.desc = desc;
      this.price = price;
   }

   public String toString ()
   {
      return "Description="+desc+", Price="+price;
   }
}
