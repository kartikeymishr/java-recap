package com.kartikey.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {
        Employee john = new Employee("John Doe", 30);
        Employee kartikey = new Employee("Kartikey Mishr", 25);
        Employee jack = new Employee("Jack Hill", 40);
        Employee snow = new Employee("Snow White", 22);
        Employee red = new Employee("Red Ridinghood", 35);
        Employee charming = new Employee("Prince Charming", 31);

        List<Employee> employees = new ArrayList<>();
        employees.add(john);
        employees.add(kartikey);
        employees.add(jack);
        employees.add(snow);
        employees.add(red);
        employees.add(charming);

        Function<Employee, String> getFirstName =
                employee -> employee.getName().substring(0, employee.getName().indexOf(' '));
        Function<Employee, String> getLastName =
                employee -> employee.getName().substring(employee.getName().indexOf(' ') + 1);

        /*
        printEmployeesByAge(employees, "Employees over 30", employee -> employee.getAge() > 30);
        printEmployeesByAge(employees, "Employees under 30", employee -> employee.getAge() < 30);
        printEmployeesByAge(employees, "Employees under 25", employee -> employee.getAge() < 25);

        IntPredicate greaterThan15 = i -> i > 15;
        IntPredicate lessThan100 = i -> i < 100;

        System.out.println(greaterThan15.test(10));
        int a = 20;
        System.out.println(greaterThan15.test(a + 5));

        System.out.println(greaterThan15.and(lessThan100).test(150));

        System.out.println();

        Random random = new Random();
        Supplier<Integer> randomSupplier = () -> random.nextInt(1000);
        for (int i = 0; i < 10; i++) {
            System.out.println(randomSupplier.get());
        }
        */
        /*employees.forEach(employee -> {
            System.out.println("First Name is == " + getFirstName.apply(employee));
        });
        employees.forEach(employee -> {
            System.out.println("Last Name is == " + getLastName.apply(employee));
        });*/

        Random random = new Random();
        for (Employee employee : employees) {
            if (random.nextBoolean()) {
                System.out.println(getAName(getFirstName, employee));
            } else {
                System.out.println(getAName(getLastName, employee));
            }
        }

        Function<Employee, String> upperCase = employee -> employee.getName().toUpperCase();
        Function<String, String> firstName = name -> name.substring(0, name.indexOf(' '));
        Function chainedFunction = upperCase.andThen(firstName);
        System.out.println(chainedFunction.apply(employees.get(0)));

        BiFunction<String, Employee, String> concatAge =
                (name, employee) -> name.concat(" " + employee.getAge());

        String upperName = upperCase.apply(employees.get(0));
        System.out.println(concatAge.apply(upperName, employees.get(0)));

        IntUnaryOperator incBy5 = i -> i + 5;
        System.out.println(incBy5.applyAsInt(10));
    }

    private static String getAName(Function<Employee, String> getName, Employee employee) {
        return getName.apply(employee);
    }

    private static void printEmployeesByAge(List<Employee> employees,
                                            String ageText,
                                            Predicate<Employee> ageCondition) {
        System.out.println(ageText);
        System.out.println("=== === === === ===");
        for (Employee employee : employees) {
            if (ageCondition.test(employee)) {
                System.out.println(employee.getName());
            }
        }
        System.out.println();
    }
}