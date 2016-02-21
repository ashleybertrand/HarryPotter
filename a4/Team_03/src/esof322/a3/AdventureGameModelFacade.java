//Ashley Bertrand and Megan Weller
package esof322.a3;

import java.io.IOException;

/* Most of our implementation of code is in this class.
We connected the Facade in the GUI with the directional buttons,
getView(), grab(), and drop().

We took the Player thePlayer, and Adventure theCave from the original
user interface and created the game in the Constructor 

*/


public class AdventureGameModelFacade {

    // some private fields to reference current location,
    // its description, what I'm carrying, etc.
    //
    // These methods and fields are left as exercises.
    //initialize variables
    Player thePlayer;
    Adventure theCave;
    Room startRm;
    Room currentRm;
    Room previousRoom;

    boolean compareRoom; //used for change of room comparison

    //variables to hold the directional value -MW
    int up = 4;
    int down = 5;
    int north = 0;
    int south = 1;
    int east = 2;
    int west = 3;


    AdventureGameModelFacade() throws IOException { // we initialize
        thePlayer = new Player();
        theCave = new Adventure();
        startRm = theCave.createAdventure();
        thePlayer.setRoom(startRm);
    }

    //Assumptions -MW
    /* I am assuming that each direction holds a certain value (0-5) that will
    correlate with a direction that will later come in use for Adventure when
    creating the map. 
    */
    
    public void goUp() {
        thePlayer.go(up);
        enterRoom();
    }

    public void goDown() {
        thePlayer.go(down);
        enterRoom();
    }

    public void goNorth() {
        thePlayer.go(north);
        enterRoom();
    }

    public void goSouth() {
        thePlayer.go(south);
        enterRoom();
    }

    public void goEast() {
        thePlayer.go(east);
        enterRoom();
    }

    public void goWest() {
        thePlayer.go(west);
        enterRoom();
    }
    
    /*Assumptions: -MW
    Since each button is doing the same action, one method is provided to enter 
    each room.
    */

    public void enterRoom() {
        previousRoom = currentRm;           // this will hold the before value 
        currentRm = thePlayer.getLoc();      //get location of current room
        compareRoom = (previousRoom == currentRm);  //compares the previous room to current to see if you
                                                    //are running into a wall. 
    }

    
    /*Assumptions -MW
    Instead of making a separate view for the Ouch! and Key, we added it to
    the getView(). 
    */
   
    // You need to finish these getView and getItems methods.
    public String getView() {
        if (!compareRoom) {                    //if previousRoom != currentRm, aka you moved into a different room
            currentRm = thePlayer.getLoc();   //get location of room
            
            if(currentRm.getKeyDesc() == null){     //if there isn't a key description set
                return currentRm.getDesc();         //return only the room description
                
            }else{
            //this else catches when you enter R11 and the door slams behind you.    
            //Otherwise, it would skip the description until the player moved a different direction    
            return (currentRm.getKeyDesc() + "\n\n" + currentRm.getDesc()); //return the key description and the
            }                                                               //and the current room description
        }
        if(currentRm.getKeyDesc() !=null){              //if the key description isn't null
            return (currentRm.getKeyDesc() + "\n\n" + currentRm.getDesc()); //return key and room description
          
        }
        return ("Ouch! That hurts.\n\n" + currentRm.getDesc()); //return Ouch! and the current room description
    }

    /*Assumptions -Ashley
     -If there is more than one item to grab, player grabs first item listed
     -After 'Grab' is clicked, carryingArea text will not change again until 'Grab' or 'Drop' is next clicked 
     */
    public String grab() {
        //Cannot grab anything because Player is already holding two items
        if (thePlayer.handsFull()) {
            return thePlayer.getFirstItem() + " " +  thePlayer.getSecondItem() + " Your hands are full.";
        
        //Player has nothing to grab because the room is empty
        } else if (thePlayer.getLoc().roomEmpty()) {
            //Player has two items
            if (thePlayer.numItemsCarried() == 2) {
                return thePlayer.getFirstItem() + " " +  thePlayer.getSecondItem() + " The room is empty.";
            //Player has one item
            } else if (thePlayer.numItemsCarried() == 1) {
                return thePlayer.getFirstItem() + " The room is empty.";
            //Player has not items
            } else {
                return "Nothing. The room is empty.";
            }
        
        //Player grabs an item
        } else {
            Item[] possibleItems = thePlayer.getLoc().getRoomContents();
            Item itemToGrab = possibleItems[0];
            //Picking up the first item
            thePlayer.pickUp(itemToGrab);
            thePlayer.getLoc().removeItem(itemToGrab);
            //Player has one item
            if (thePlayer.numItemsCarried() == 1) {
                return itemToGrab.getDesc();
            //Player has two items
            } else {
                return thePlayer.getFirstItem() + " " +  itemToGrab.getDesc();
            }
        }
    }

    /*Assumptions -Ashley
     -If there is more than one item to drop, player drops first item listed
     -After 'Drop' is clicked, carryingArea text will not change again until 'Drop' or 'Grab' is next clicked
     */
    public String drop() {
        //Player had 0 items before 'Drop' was clicked
        if (thePlayer.handsEmpty()) {
            return "Nothing. You have nothing to drop.";
        }

        //Dropping the first item
        String itemToDrop = thePlayer.getFirstItem();
        Item addItemToRoom = new Item();
        addItemToRoom.setDesc(itemToDrop);

        //Player had 2 items before 'Drop' was clicked
        if (thePlayer.numItemsCarried() == 2) {
            //need to check numItemsCarried before calling drop() because of different return options
            thePlayer.drop(1);
            return thePlayer.getFirstItem();
        }

        //Player had 1 item before 'Drop' was clicked
        thePlayer.drop(1);
        return "Nothing.";
    }
    
}
