package com.ip.lambdaexpression;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ip.model.City;
import com.ip.model.CityPerson;
import com.ip.model.Person;

/**
 *
 * @author Pratap
 */
public class MainAPICollectionMap {

    public static void main(String[] args) {

        CityPerson p1 = new CityPerson("Alice", 23);
        CityPerson p2 = new CityPerson("Brian", 56);
        CityPerson p3 = new CityPerson("Chelsea", 46);
        CityPerson p4 = new CityPerson("David", 28);
        CityPerson p5 = new CityPerson("Erica", 37);
        CityPerson p6 = new CityPerson("Francisco", 18);
        
        City newYork = new City("New York");
        City shanghai = new City("Shanghai");
        City paris = new City("Paris");
        
        Map<City, List<Person>> map = new HashMap<>();
        
        map.putIfAbsent(paris, new ArrayList<>());
        map.get(paris).add(p1);
        
        map.computeIfAbsent(newYork, city -> new ArrayList<>()).add(p2);
        map.computeIfAbsent(newYork, city -> new ArrayList<>()).add(p3);
    
        System.out.println("People from Paris : " + map.getOrDefault(paris, Collections.EMPTY_LIST));
        System.out.println("People from New York : " + map.getOrDefault(newYork, Collections.EMPTY_LIST));
        
        Map<City, List<Person>> map1 = new HashMap<>();
        map1.computeIfAbsent(newYork, city -> new ArrayList<>()).add(p1);
        map1.computeIfAbsent(shanghai, city -> new ArrayList<>()).add(p2);
        map1.computeIfAbsent(shanghai, city -> new ArrayList<>()).add(p3);
        
        System.out.println("Map 1");
        map1.forEach((city, people) -> System.out.println(city + " : " + people));

        
        Map<City, List<Person>> map2 = new HashMap<>();
        map2.computeIfAbsent(shanghai, city -> new ArrayList<>()).add(p4);
        map2.computeIfAbsent(paris, city -> new ArrayList<>()).add(p5);
        map2.computeIfAbsent(paris, city -> new ArrayList<>()).add(p6);
        
        System.out.println("Map 2");
        map2.forEach((city, people) -> System.out.println(city + " : " + people));

        map2.forEach(
                (city, people) -> {
                    map1.merge(
                            city, people, 
                            (peopleFromMap1, peopleFromMap2) -> {
                                peopleFromMap1.addAll(peopleFromMap2);
                                return peopleFromMap1;
                            });
                }
        );
        
        System.out.println("Merged map1 ");
        map1.forEach(
                (city, people) -> System.out.println(city + " : " + people)
        );
    }
}
