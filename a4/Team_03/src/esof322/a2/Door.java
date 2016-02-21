//Ashley Bertrand and Megan Weller
package esof322.a2;

/*We used this class for much of the key implementation code.  
We changed many of the items' descriptions to get the necessary 
display messages.
*/

/**
 * Adventure Game Program Code Copyright (c) 1999 James M. Bieman
 *
 * To compile: javac AdventureGame.java To run: java AdventureGame
 *
 * The main routine is AdventureGame.main
 *
 *
 * We added a relationship to Room for the key description text. In the 
 * enter() method, there will be an added setKeyDesc() for the room
 * you are entering or trying to enter through a locked door.
 * 

 */
// class Door
public class Door implements CaveSite {


  private Key myKey;
  private Room currentRm;       //Room variable to hold current Room Location
  
  /** The door's location. */
  private CaveSite outSite;
  private CaveSite inSite;

    /**
     * In this implementation doors are always locked. A player must have the
     * correct key to get through a door. Doors automatically lock after a
     * player passes through.
     */
  
     /** We can construct a door at the site. */
  Door(CaveSite out, CaveSite in, Key k){
    outSite = out;
    inSite = in;
    myKey = k;
  }
  

 /** A player will need the correct key to enter. */
  
  /** Assumptions. -MW
   *I mapped out how the original main driver would get to this method and this was the
   * best place for me to set the key text. Since it is of type void, I first struggled
   * with trying to return the key text with the description of the room.
   */
  
 public void enter(Player p){ 
     
 if (p.haveItem(myKey)) {
    // Old System.out.println for old interface
    System.out.println("Your key works! The door creaks open,");
    System.out.println("and slams behind you after you pass through.");
   
    //Ashley changed for display purposes in carryingArea  
    myKey.setDesc("A shiny gold key.");
    
    if (p.getLoc() == outSite){
        inSite.enter(p);
        //Enter R11
        //Grab the current location and set the setKeyDesc() to the fact that we can open the door
         currentRm = p.getLoc();
         currentRm.setKeyDesc("Wicked! Your keys works! The door creaks open,\n and slams behind you after you pass through.\n");
           
    }
    else if (p.getLoc() == inSite){
        outSite.enter(p);
        //Enter R10
        //Grab the current location and set the setKeyDesc() to the fact that we can open the door
        currentRm = p.getLoc();
        currentRm.setKeyDesc("Wicked! Your keys works! The door creaks open,\n and slams behind you after you pass through.\n");
    } 
  }
  else {System.out.println("You don't have the key for this door!");
       System.out.println("Sorry.");   
       
       //Grab room location
       //set currentRom setKeyDesc(), we don't have a key to the door
       currentRm = p.getLoc();
       currentRm.setKeyDesc("You don't have the key for this door!\n Sorry.");
       
       
    }
 
 }
 
}
