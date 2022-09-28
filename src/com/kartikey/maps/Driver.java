package com.kartikey.maps;

import java.util.HashMap;
import java.util.Map;

public class Driver {
    public static void main(String[] args) {
        // No guarantees of storing entries in the order they were added to HashMap
        Map<String, String> languages = new HashMap<>();
        languages.put("Java", "A compiled high level object oriented platform independent language");
        languages.put("Python", "An interpreted object oriented high level programming language with dynamic semantics");
        languages.put("Algol", "An algorithmic language");
        System.out.println(languages.put("BASIC", "Beginners All Purposes Symbolic Instruction Code"));
        System.out.println(languages.put("BASIC", "Beginners All Purposes Symbolic Instruction Code"));
        languages.put("Lisp", "Therein lies madness");

        System.out.println(languages.get("Java"));

        System.out.println("=== === === === === === === === === === === === === === === === ");

        for (String key : languages.keySet()) {
            System.out.println(key + ": " + languages.get(key));
        }


    }
}
