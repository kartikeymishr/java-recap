package com.kartikey.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> bingoNumbers = Arrays.asList(
                "N40", "N36",
                "B12", "B6",
                "G53", "G49", "G60", "G50", "g64",
                "I26", "I17", "I29",
                "071"
        );

        /*List<String> gNumbers = new ArrayList<>();

        bingoNumbers.forEach(number -> {
            if (number.toUpperCase().startsWith("G")) {
                gNumbers.add(number);
            }
        });

        gNumbers.sort(String::compareTo);
        gNumbers.forEach(System.out::println);*/

        /*bingoNumbers.stream()
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("G"))
                .sorted()
                .forEach(System.out::println);

        List<String> iNumbers = bingoNumbers.stream()
                .map(String::toLowerCase)
                .filter(s -> s.startsWith("i"))
                .sorted().toList();

        System.out.println(Arrays.toString(iNumbers.toArray()));

        Stream<String> ioNumberStream = Stream.of("I26", "I17", "I29", "071");
        Stream<String> inNumberStream = Stream.of("N40", "N36", "I26", "I17", "I29", "071");

        Stream<String> concatStream = Stream.concat(ioNumberStream, inNumberStream);
        System.out.println("=== === === === === ===");
        System.out.println(concatStream.distinct()
                .peek(System.out::println) // peek() mainly exists to support debugging
                .count());*/

        Employee john = new Employee("John Doe", 30);
        Employee jane = new Employee("Jane Deer", 25);
        Employee jack = new Employee("Jack Hill", 40);
        Employee kartikey = new Employee("Kartikey Mishr", 22);

        Department hr = new Department("Human Resources");
        hr.addEmployee(jane);
        hr.addEmployee(jack);
        hr.addEmployee(kartikey);

        Department accounting = new Department("Accounting");
        accounting.addEmployee(john);

        List<Department> departments = new ArrayList<>();
        departments.add(hr);
        departments.add(accounting);

        List<Employee> allEmployees = departments.stream()
                .flatMap(department -> department.getEmployees().stream())
                .toList();

        allEmployees.forEach(System.out::println);

        List<String> newGNumbers = bingoNumbers.stream()
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("G"))
                .sorted()
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        newGNumbers.forEach(System.out::println);

        Map<String, List<Employee>> gEmployeeMap = allEmployees.stream()
                .filter(e -> e.getName().startsWith("J"))
                .collect(Collectors.groupingBy(Employee::getName));

        for (Map.Entry<String, List<Employee>> entry : gEmployeeMap.entrySet()) {
            System.out.println(Arrays.toString(entry.getValue().toArray()));
        }

        // Youngest Employee in the organization
        departments.stream()
                .flatMap(department -> department.getEmployees().stream())
                .reduce((e1, e2) -> e1.getAge() < e2.getAge() ? e1 : e2)
                .ifPresent(System.out::println);
    }
}
