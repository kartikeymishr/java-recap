package com.kartikey.oops.challenge;

public class Main {
    public static void main(String[] args) {
        Hamburger hamburger = new Hamburger("Basic", "Sausage", 3.56, "White");
        double price = hamburger.itemizeHamburger();

        hamburger.addHamburgerAddition1("Tomato", 0.27);
        hamburger.addHamburgerAddition2("Lettuce", 0.75);
        hamburger.addHamburgerAddition3("Cheese", 1.12);

        System.out.println("Total burger price is " + hamburger.itemizeHamburger());

        HealthyBurger healthyBurger = new HealthyBurger("Bacon", 5.67);

        healthyBurger.addHamburgerAddition1("Egg", 5.43);
        healthyBurger.addHealthyExtra1("Lentils", 3.41);

        System.out.println("Total healthy burger price is " + healthyBurger.itemizeHamburger());

        DeluxeBurger deluxeBurger = new DeluxeBurger();
        System.out.println("Total deluxe burger price is " + deluxeBurger.itemizeHamburger());
    }
}
