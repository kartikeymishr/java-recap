package com.kartikey.oops.challenge;

public class DeluxeBurger extends Hamburger {

    public static final String ADDITIONAL_ITEM_ERROR = "Cannot add additional items to a deluxe burger";

    public DeluxeBurger() {
        super("Deluxe", "Sausage & Bacon", 14.54, "White");
        super.addHamburgerAddition1("Chips", 2.75);
        super.addHamburgerAddition2("Drink", 1.01);
    }

    @Override
    public void addHamburgerAddition1(String name, double price) {
        System.out.println(ADDITIONAL_ITEM_ERROR);
    }

    @Override
    public void addHamburgerAddition2(String name, double price) {
        System.out.println(ADDITIONAL_ITEM_ERROR);
    }

    @Override
    public void addHamburgerAddition3(String name, double price) {
        System.out.println(ADDITIONAL_ITEM_ERROR);
    }

    @Override
    public void addHamburgerAddition4(String name, double price) {
        System.out.println(ADDITIONAL_ITEM_ERROR);
    }
}
