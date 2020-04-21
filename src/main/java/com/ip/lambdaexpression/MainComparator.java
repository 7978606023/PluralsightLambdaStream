package com.ip.lambdaexpression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import com.ip.model.Person;
import com.ip.utils.Comparator;

public class MainComparator {

    public static void main(String... args) {
        List <Person> list = new ArrayList<Person>();
        list.add(new Person("anubhav", "sahoo", 26));
        list.add(new Person("pratap", "sahu", 27));
        list.add(new Person("anu", "jena", 22));
        list.add(new Person("dipa", "rasmi", 23));
        
        Comparator<Person> cmpAge = (p1, p2) -> p2.getAge() - p1.getAge() ;
        Comparator<Person> cmpFirstName = (p1, p2) -> p1.getFirstName().compareTo(p2.getFirstName()) ;
        Comparator<Person> cmpLastName = (p1, p2) -> p1.getLastName().compareTo(p2.getLastName()) ;
        
        Function<Person, Integer> f1 = p -> p.getAge();
        Function<Person, String> f2 = p -> p.getLastName();
        Function<Person, String> f3 = p -> p.getFirstName();

        Comparator<Person> cmpPersonAge = Comparator.comparing(Person::getAge);
        Comparator<Person> cmpPersonLastName = Comparator.comparing(Person::getLastName);
        
        
        Comparator<Person> cmp = Comparator.comparing(Person::getLastName)
                                           .thenComparing(Person::getFirstName)
                                           .thenComparing(Person::getAge);
       // Collections.sort(list, cmpAge);
    }
}
