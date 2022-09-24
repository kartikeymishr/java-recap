package com.kartikey.oops;

public class Main {

    public static void main(String[] args) {
        // Composition ~ PC has Case, Monitor, Motherboard
        /*
        Dimensions dimensions = new Dimensions(20, 20, 5);
        Case _case = new Case("220B", "Dell", "240", dimensions);
        Monitor monitor = new Monitor("27inch Beast", "Acer", 27, new Resolution(2450, 1440));
        Motherboard motherboard = new Motherboard("450 Tomahawk", "MSI", 4, 4, "v2.44");
        PC pc = new PC(_case, monitor, motherboard);

        // pc.getMonitor().drawPixelAt(1500, 1200, "purple");
        pc.powerUp();
        // pc.getMotherboard().loadProgram("Windows 10");
        // pc.getCase().pressPowerButton();
        */


        // Encapsulation
        /*
        // No encapsulation; Bad design
        Player player = new Player();
        // player.name = "Kartikey"; // Cannot find 'name' as field renamed to 'fullName'
        player.health = 50;
        player.weapon = "Sword";

        int damage = 10;
        player.loseHealth(damage);

        // Like a cheat code!
        player.health = 200;

        System.out.println("Remaining health = " + player.health);

        // Using encapsulation; Good design
        EnhancedPlayer enPlayer = new EnhancedPlayer("Kartikey", 80, "Sword");
        // Internally health attribute is renamed to hitPoints but major code changes reuqired for it
        System.out.println("Initial health is " + enPlayer.getHealth());
        */

    }
}
