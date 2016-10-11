// CityNavigator.java

import java.util.*;

public class CityNavigator
{
   static NavigableSet<String> citiesSet;

   public static void main (String [] args)
   {
      String [] cities =
      {
         "Beijing",
         "Berlin",
         "Baghdad",
         "Buenos Aires",
         "Bangkok",
         "Belgrade"
      };

      // Create and populate a navigable set of cities.

      citiesSet = new TreeSet<String> ();
      for (String city: cities)
           citiesSet.add (city);

      // Dump the city names in ascending order. Behind the scenes, the
      // following code is implemented in terms of
      //
      // Iterator iter = citiesSet.iterator ();
      // while (iter.hasNext ())
      //    System.out.println (iter.next ());

      System.out.println ("CITIES IN ASCENDING ORDER");
      for (String city: citiesSet)
           System.out.println ("  "+city);
      System.out.println ();

      // Dump the city names in descending order. Behind the scenes, the
      // following code is implemented in terms of
      //
      // Iterator iter = citiesSet.descendingSet.iterator ();
      // while (iter.hasNext ())
      //    System.out.println (iter.next ());

      System.out.println ("CITIES IN DESCENDING ORDER");
      for (String city: citiesSet.descendingSet ())
           System.out.println ("  "+city);
      System.out.println ();

      // Demonstrate the closest-match methods in ascending order set.

      System.out.println ("CLOSEST-MATCH METHODS/ASCENDING ORDER DEMO");
      outputMatches ("Berlin");
      System.out.println ();

      outputMatches ("C");
      System.out.println ();

      outputMatches ("A");
      System.out.println ();

      // Demonstrate closest-match methods in descending order set.

      citiesSet = citiesSet.descendingSet ();
      System.out.println ("CLOSEST-MATCH METHODS/DESCENDING ORDER DEMO");
      outputMatches ("Berlin");
      System.out.println ();

      outputMatches ("C");
      System.out.println ();

      outputMatches ("A");
      System.out.println ();
   }

   static void outputMatches (String city)
   {
      // ceiling() returns the least element in the set greater than or equal
      // to the given element (or null if the element does not exist).
      
      System.out.println ("  ceiling('"+city+"'): "+citiesSet.ceiling (city));

      // floor() returns the greatest element in the set less than or equal to
      // the given element (or null if the element does not exist).

      System.out.println ("  floor('"+city+"'): "+citiesSet.floor (city));

      // higher() returns the least element in the set strictly greater than
      // the given element (or null if the element does not exist).

      System.out.println ("  higher('"+city+"'): "+citiesSet.higher (city));

      // lower() returns the greatest element in the set strictly less than 
      // the given element (or null if the element does not exist).

      System.out.println ("  lower('"+city+"'): "+citiesSet.lower (city));
   }
}
