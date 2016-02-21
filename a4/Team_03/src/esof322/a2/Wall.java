//Ashley Bertrand and Megan Weller
package esof322.a2;

/**
 * Adventure Game Program Code Copyright (c) 1999 James M. Bieman
 *
 * To compile: javac AdventureGame.java To run: java AdventureGame
 *
 * The main routine is AdventureGame.main
 *
 *
 */
// class Wall
public class Wall implements CaveSite {

    public void enter(Player p) {
        System.out.println("Ouch! That hurts.");
    }

}
