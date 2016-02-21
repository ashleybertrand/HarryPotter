//Ashley Bertrand
package esof322.a4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/* Here the Facade is connected with the GUI in the directional 
 buttons, grab, drop, save, load, and difficulty levels.

 We took the Player thePlayer, and Adventure theCave from the original
 user interface and created the game in the Constructor 

 The level buttons call methods in this class to setup the game 
 appropriately.  

 Save and load procedures are also implemented here, with some 
 additional methods necessary for correct game functionality.
 */
public class AdventureGameModelFacade {

    //private fields to reference location, description, what is being carried, etc.
    Player thePlayer;
    Adventure theCave;
    Room startRm;
    Room currentRm;
    Room previousRoom;
    String[] currentData;

    //used for change of room comparison
    boolean compareRoom;

    //variables to hold the directional value
    int up = 4;
    int down = 5;
    int north = 0;
    int south = 1;
    int east = 2;
    int west = 3;

    //variables to set level
    Level0Factory level0;
    Level1Factory level1;
    AdventureGameFactory factory;

    //initialize
    AdventureGameModelFacade() throws IOException {
        thePlayer = new Player();
        theCave = new Adventure();
        startRm = theCave.createAdventure();
        thePlayer.setLoc(startRm);
        currentData = new String[15];
        level0 = new Level0Factory();
        level1 = new Level1Factory();
    }

    /*Assumptions -Megan
     I am assuming that each direction holds a certain value (0-5) that will
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

    /*Assumptions: -Megan
     Since each button is doing the same action, one method is provided to enter 
     each room.
     */
    public void enterRoom() {
        previousRoom = currentRm;                   //this will hold the before value 
        currentRm = thePlayer.getLoc();             //get location of current room
        compareRoom = (previousRoom == currentRm);  //compares the previous room to current to see if you are running into a wall. 
    }

    /*Assumptions -MW
     Instead of making a separate view for the Ouch! and Key, we added it to
     the getView(). 
     */
    public String getView() {
        //if previousRoom != currentRm (you moved into a different room)
        if (!compareRoom) {
            currentRm = thePlayer.getLoc();
            //if there isn't a key description set
            if (currentRm.getKeyDesc() == null) {
                //return only the room description
                return currentRm.getDesc();
                //This else catches when you enter r11 and the door slams behind you.    
                //Otherwise, it would skip the description until the player moved a different direction.      
            } else {
                return (currentRm.getKeyDesc() + "\n\n" + currentRm.getDesc());
            }
        }

        //if the key description isn't null
        if (currentRm.getKeyDesc() != null) {
            return (currentRm.getKeyDesc() + "\n\n" + currentRm.getDesc());
        }

        return ("Ouch! That hurts.\n\n" + currentRm.getDesc());
    }

    /*Assumptions -Ashley
     -If there is more than one item to grab, player grabs first item listed
     -After 'Grab' is clicked, carryingArea text will not change again until 'Grab' or 'Drop' is next clicked 
     */
    public String grab() {
        //Cannot grab anything because Player is already holding two items
        if (thePlayer.handsFull()) {
            return thePlayer.getFirstItem() + " " + thePlayer.getSecondItem() + " Your hands are full.";

            //Player has nothing to grab because the room is empty
        } else if (thePlayer.getLoc().roomEmpty()) {
            //Player has two items
            if (thePlayer.numItemsCarried() == 2) {
                return thePlayer.getFirstItem() + " " + thePlayer.getSecondItem() + " The room is empty.";
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
                return thePlayer.getFirstItem() + " " + itemToGrab.getDesc();
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

    public String level0() {
        Room roomWithTreasure = new Room();
        //player is carrying nothing
        if (thePlayer.numItemsCarried() == 2) {
            if (thePlayer.getFirstItem().equals("A bag filled with ten thousand Galleons.")) {
                roomWithTreasure = thePlayer.dropIntoRoom(1);
                thePlayer.getRidOfItem(1);
            } else if (thePlayer.getSecondItem().equals("A bag filled with ten thousand Galleons.")) {
                roomWithTreasure = thePlayer.dropIntoRoom(2);
                thePlayer.getRidOfItem(1);
            }
        } else if (thePlayer.numItemsCarried() == 1) {
            if (thePlayer.getFirstItem().equals("A bag filled with ten thousand Galleons.")) {
                roomWithTreasure = thePlayer.dropIntoRoom(1);
            } else if (thePlayer.getFirstItem().equals("A shiny gold key.")) {
                thePlayer.getRidOfItem(1);
            }
        }
        
        //player's location is outside
        startRm = level0.createGame(factory);
        thePlayer.setLoc(startRm);
        
        //System.out.println("treasure room: " + roomWithTreasure.getDesc());
        //System.out.println("r11:" + level0.getRoom("r11").getDesc());
        
        return startRm.getDesc();
    }
    
    public String level1() {
        Room roomWithTreasure = new Room();
        //player is carrying nothing
        if (thePlayer.numItemsCarried() == 2) {
            if (thePlayer.getFirstItem().equals("A bag filled with ten thousand Galleons.")) {
                roomWithTreasure = thePlayer.dropIntoRoom(1);
                thePlayer.getRidOfItem(1);
            } else if (thePlayer.getSecondItem().equals("A bag filled with ten thousand Galleons.")) {
                roomWithTreasure = thePlayer.dropIntoRoom(2);
                thePlayer.getRidOfItem(1);
            }
        } else if (thePlayer.numItemsCarried() == 1) {
            if (thePlayer.getFirstItem().equals("A bag filled with ten thousand Galleons.")) {
                roomWithTreasure = thePlayer.dropIntoRoom(1);
            } else if (thePlayer.getFirstItem().equals("A shiny gold key.")) {
                thePlayer.getRidOfItem(1);
            }
        }
        
        //player's location is outside
        startRm = level1.createGame(factory);
        thePlayer.setLoc(startRm);
        
        //System.out.println("treasure room: " + roomWithTreasure.getDesc());
        //System.out.println("r11:" + level0.getRoom("r11").getDesc());
        
        return startRm.getDesc();
    }

    public String checkForHints() throws IOException {
        //if player is in level 1, room 9, and key is not being carried or treasure has not been found
        if (thePlayer.getLoc().getDesc().equals("As you enter the room, you notice a piece of paper on the ground.\n"
                + "Oh wait!  It's not just ordinary paper; it's the Marauder's Map!\n"
                + "It can show you where important items are!  See hints below (r9).\n"
                + "\n"
                + "Room Contents: \n"
                + "")) {
            return level1.getHints();
        } 
            return "Hints: "; 
    }

    //reading game.txt to display what player is carring
    public String getPlayerItems() throws IOException {
        String[] gameData = loadGame();
        if (gameData[1].equals("")) {
            return "Nothing.";
        }
        return gameData[1] + " " + gameData[2];
    }

    //reading game.txt to display room description and contents
    public String getRoomDesc() throws IOException {
        String[] gameData = loadGame();
        int room = thePlayer.getLoc().getRoomNumber();

        //adding 3 to account for offset as set in textfile
        if (gameData[room + 3].equals("")) {
            return thePlayer.getLoc().setContentsToEmpty();
        } else {
            return thePlayer.getLoc().getDesc();
        }
    }

    //player can pass through a door when carrying the key
    public void checkForKey() {
        Door door = new Door();
        if (thePlayer.haveKey()) {
            door.unlocked(thePlayer, theCave);
        } else {
            door.locked(thePlayer, theCave);
        }
    }

    /*Notes to user:
     1. Change file path accordingly
     2. A game cannot be loaded unless a game has been saved
     */
    //returns contents of game.txt as an array of strings
    public String[] loadGame() throws FileNotFoundException, IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\Ashley Bertrand\\Desktop\\Team_03\\game.txt"));
        ArrayList<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }

    //loads player data
    public void loadPlayer() throws IOException {
        String[] gameData = loadGame();

        //setting player's location to first line in text file
        thePlayer.setLoc(theCave.getRoom(gameData[0]));
        currentRm = thePlayer.getLoc();

        //getRidOfItem removes an item from the game when a player has it when 'Load game' is clicked
        int numItemsCarried = thePlayer.numItemsCarried();
        while (numItemsCarried > 0) {
            thePlayer.getRidOfItem(1);
            numItemsCarried--;
        }

        //setting player's first item to second line in text file (if it exists)
        if (!gameData[1].equals("")) {
            thePlayer.carry(gameData[1]);
        }

        //setting player's second item to third line in text file (if it exists)
        if (!gameData[2].equals("")) {
            thePlayer.carry(gameData[2]);
        }
    }

    //loads cave data
    public void loadCave() throws IOException {
        String[] gameData = loadGame();

        //starting at fourth line in text file
        for (int i = 3; i < gameData.length; i++) {
            //clearing contents of all rooms
            removeContents(i);
            if (!gameData[i].equals("")) {
                //adding objects to rooms according to game.txt
                addContents(i, gameData[i]);
            }
        }
    }

    public void removeContents(int roomNumber) {
        //clearing contents of a room according to roomNumber
        switch (roomNumber) {
            //outside
            case 3:
                if (!theCave.getRoom("outside").roomEmpty()) {
                    theCave.getRoom("outside").removeAllItems();
                }
                break;
            //r1
            case 4:
                if (!theCave.getRoom("r1").roomEmpty()) {
                    theCave.getRoom("r1").removeAllItems();
                }
                break;
            case 5:
                if (!theCave.getRoom("r2").roomEmpty()) {
                    theCave.getRoom("r2").removeAllItems();
                }
                break;
            case 6:
                if (!theCave.getRoom("r3").roomEmpty()) {
                    theCave.getRoom("r3").removeAllItems();
                }
                break;
            case 7:
                if (!theCave.getRoom("r4").roomEmpty()) {
                    theCave.getRoom("r4").removeAllItems();
                }
                break;
            case 8:
                if (!theCave.getRoom("r5").roomEmpty()) {
                    theCave.getRoom("r5").removeAllItems();
                }
                break;
            case 9:
                if (!theCave.getRoom("r6").roomEmpty()) {
                    theCave.getRoom("r6").removeAllItems();
                }
                break;
            case 10:
                if (!theCave.getRoom("r7").roomEmpty()) {
                    theCave.getRoom("r7").removeAllItems();
                }
                break;
            case 11:
                if (!theCave.getRoom("r8").roomEmpty()) {
                    theCave.getRoom("r8").removeAllItems();
                }
                break;
            case 12:
                if (!theCave.getRoom("r9").roomEmpty()) {
                    theCave.getRoom("r9").removeAllItems();
                }
                break;
            case 13:
                if (!theCave.getRoom("r10").roomEmpty()) {
                    theCave.getRoom("r10").removeAllItems();
                }
                break;
            case 14:
                if (!theCave.getRoom("r11").roomEmpty()) {
                    theCave.getRoom("r11").removeAllItems();
                }
                break;
        }
    }

    public String addContents(int roomNumber, String contents) {
        //finding the number of items in a room
        int numOfItems = 1;
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) == ',') {
                numOfItems++;
            }
        }

        //creating new items with corresponding descriptions
        Item[] listOfItems = new Item[numOfItems];
        String[] listOfItemNames = contents.split(",");
        for (int i = 0; i < listOfItemNames.length; i++) {
            Item item = new Item();
            item.setDesc(listOfItemNames[i]);
            listOfItems[i] = item;
        }

        //adding contents to a room according to roomNumber
        switch (roomNumber) {
            //outside
            case 3:
                for (int i = 0; i < listOfItems.length; i++) {
                    theCave.getRoom("outside").addItem(listOfItems[i]);
                }
                break;
            //r1
            case 4:
                for (int i = 0; i < listOfItems.length; i++) {
                    theCave.getRoom("r1").addItem(listOfItems[i]);
                }
                break;
            case 5:
                for (int i = 0; i < listOfItems.length; i++) {
                    theCave.getRoom("r2").addItem(listOfItems[i]);
                }
                break;
            case 6:
                for (int i = 0; i < listOfItems.length; i++) {
                    theCave.getRoom("r3").addItem(listOfItems[i]);
                }
                break;
            case 7:
                for (int i = 0; i < listOfItems.length; i++) {
                    theCave.getRoom("r4").addItem(listOfItems[i]);
                }
                break;
            case 8:
                for (int i = 0; i < listOfItems.length; i++) {
                    theCave.getRoom("r5").addItem(listOfItems[i]);
                }
                break;
            case 9:
                for (int i = 0; i < listOfItems.length; i++) {
                    theCave.getRoom("r6").addItem(listOfItems[i]);
                }
                break;
            case 10:
                for (int i = 0; i < listOfItems.length; i++) {
                    theCave.getRoom("r7").addItem(listOfItems[i]);
                }
                break;
            case 11:
                for (int i = 0; i < listOfItems.length; i++) {
                    theCave.getRoom("r8").addItem(listOfItems[i]);
                }
                break;
            case 12:
                for (int i = 0; i < listOfItems.length; i++) {
                    theCave.getRoom("r9").addItem(listOfItems[i]);
                }
                break;
            case 13:
                for (int i = 0; i < listOfItems.length; i++) {
                    theCave.getRoom("r10").addItem(listOfItems[i]);
                }
                break;
            case 14:
                for (int i = 0; i < listOfItems.length; i++) {
                    theCave.getRoom("r11").addItem(listOfItems[i]);
                }
                break;
        }
        return "";
    }

    /*Note: I could not break up this method into smaller functions because of Stream Closed Exception
     File contents by line:
     line 1: player's location
     line 2: player's first item
     line 3: player's second item
     line 4: contents of 'outside'
     line 5: contents of 'r1'
     ...
     line 15: contents of 'r11'
     */
    public void saveGame() throws IOException {
        FileWriter gameFile = new FileWriter("game.txt");

        //writing player's location to gameFile--------------------------------------------------------------------------
        gameFile.write(thePlayer.getLoc().getRoomName() + "\n");
        currentData[0] = thePlayer.getLoc().getRoomName();

        //writing player's items to gameFile-----------------------------------------------------------------------------
        //player has 0 items
        if (thePlayer.numItemsCarried() == 0) {
            gameFile.write("\n\n");
            currentData[1] = "";
            currentData[2] = "";
        }

        //player has 1 item
        if (thePlayer.numItemsCarried() == 1) {
            gameFile.write(thePlayer.getFirstItem() + "\n\n");
            currentData[1] = thePlayer.getFirstItem();
            currentData[2] = "";
        }

        //player has 2 items
        if (thePlayer.numItemsCarried() == 2) {
            gameFile.write(thePlayer.getFirstItem() + '\n');
            currentData[1] = thePlayer.getFirstItem();

            gameFile.write(thePlayer.getSecondItem() + '\n');
            currentData[2] = thePlayer.getSecondItem();
        }

        //writing each room's contents to gameFile-----------------------------------------------------------------------
        //outside
        if (theCave.getRoom("outside").roomEmpty() || level0.getRoom("outside").roomEmpty() || level1.getRoom("outside").roomEmpty()) {
            gameFile.write("\n");
            currentData[3] = "";
        } else {
            Item[] items = theCave.getRoom("outside").getRoomContents();
            for (int i = 0; i < items.length - 1; i++) {
                gameFile.write(items[i].getDesc() + ",");
            }
            //do not add a comma after last item
            gameFile.write(items[items.length - 1].getDesc());
            gameFile.write("\n");
            currentData[3] = items[items.length - 1].getDesc();
        }

        //r1
        if (theCave.getRoom("r1").roomEmpty() || level0.getRoom("r1").roomEmpty() || level1.getRoom("r1").roomEmpty()) {
            gameFile.write("\n");
            currentData[4] = "";
        } else {
            Item[] items = theCave.getRoom("r1").getRoomContents();
            for (int i = 0; i < items.length - 1; i++) {
                gameFile.write(items[i].getDesc() + ",");
            }
            gameFile.write(items[items.length - 1].getDesc());
            gameFile.write("\n");
            currentData[4] = items[items.length - 1].getDesc();
        }

        //r2
        if (theCave.getRoom("r2").roomEmpty() || level0.getRoom("r2").roomEmpty() || level1.getRoom("r2").roomEmpty()) {
            gameFile.write("\n");
            currentData[5] = "";
        } else {
            Item[] items = theCave.getRoom("r2").getRoomContents();
            for (int i = 0; i < items.length - 1; i++) {
                gameFile.write(items[i].getDesc() + ",");
            }
            gameFile.write(items[items.length - 1].getDesc());
            gameFile.write("\n");
            currentData[5] = items[items.length - 1].getDesc();
        }

        //r3
        if (theCave.getRoom("r3").roomEmpty() || level0.getRoom("r3").roomEmpty() || level1.getRoom("r3").roomEmpty()) {
            gameFile.write("\n");
            currentData[6] = "";
        } else {
            Item[] items = theCave.getRoom("r3").getRoomContents();
            for (int i = 0; i < items.length - 1; i++) {
                gameFile.write(items[i].getDesc() + ",");
            }
            gameFile.write(items[items.length - 1].getDesc());
            gameFile.write("\n");
            currentData[6] = items[items.length - 1].getDesc();
        }

        //r4
        if (theCave.getRoom("r4").roomEmpty() || level0.getRoom("r4").roomEmpty() || level1.getRoom("r4").roomEmpty()) {
            gameFile.write("\n");
            currentData[7] = "";
        } else {
            Item[] items = theCave.getRoom("r4").getRoomContents();
            for (int i = 0; i < items.length - 1; i++) {
                gameFile.write(items[i].getDesc() + ",");
            }
            gameFile.write(items[items.length - 1].getDesc());
            gameFile.write("\n");
            currentData[7] = items[items.length - 1].getDesc();
        }

        //r5
        if (theCave.getRoom("r5").roomEmpty() || level0.getRoom("r5").roomEmpty() || level1.getRoom("r5").roomEmpty()) {
            gameFile.write("\n");
            currentData[8] = "";
        } else {
            Item[] items = theCave.getRoom("r5").getRoomContents();
            for (int i = 0; i < items.length - 1; i++) {
                gameFile.write(items[i].getDesc() + ",");
            }
            gameFile.write(items[items.length - 1].getDesc());
            gameFile.write("\n");
            currentData[8] = items[items.length - 1].getDesc();
        }

        //r6
        if (theCave.getRoom("r6").roomEmpty() || level0.getRoom("r6").roomEmpty() || level1.getRoom("r6").roomEmpty()) {
            gameFile.write("\n");
            currentData[9] = "";
        } else {
            Item[] items = theCave.getRoom("r6").getRoomContents();
            for (int i = 0; i < items.length - 1; i++) {
                gameFile.write(items[i].getDesc() + ",");
            }
            gameFile.write(items[items.length - 1].getDesc());
            gameFile.write("\n");
            currentData[9] = items[items.length - 1].getDesc();
        }

        //r7
        if (theCave.getRoom("r7").roomEmpty() || level0.getRoom("r7").roomEmpty() || level1.getRoom("r7").roomEmpty()) {
            gameFile.write("\n");
            currentData[10] = "";
        } else {
            Item[] items = theCave.getRoom("r7").getRoomContents();
            for (int i = 0; i < items.length - 1; i++) {
                gameFile.write(items[i].getDesc() + ",");
            }
            gameFile.write(items[items.length - 1].getDesc());
            gameFile.write("\n");
            currentData[10] = items[items.length - 1].getDesc();
        }

        //r8
        if (theCave.getRoom("r8").roomEmpty() || level0.getRoom("r8").roomEmpty() || level1.getRoom("r8").roomEmpty()) {
            gameFile.write("\n");
            currentData[11] = "";
        } else {
            Item[] items = theCave.getRoom("r8").getRoomContents();
            for (int i = 0; i < items.length - 1; i++) {
                gameFile.write(items[i].getDesc() + ",");
            }
            gameFile.write(items[items.length - 1].getDesc());
            gameFile.write("\n");
            currentData[11] = items[items.length - 1].getDesc();
        }

        //r9
        if (theCave.getRoom("r9").roomEmpty() || level0.getRoom("r9").roomEmpty() || level1.getRoom("r9").roomEmpty()) {
            gameFile.write("\n");
            currentData[12] = "";
        } else {
            Item[] items = theCave.getRoom("r9").getRoomContents();
            for (int i = 0; i < items.length - 1; i++) {
                gameFile.write(items[i].getDesc() + ",");
            }
            gameFile.write(items[items.length - 1].getDesc());
            gameFile.write("\n");
            currentData[12] = items[items.length - 1].getDesc();
        }

        //r10
        if (theCave.getRoom("r10").roomEmpty() || level0.getRoom("r10").roomEmpty() || level1.getRoom("r10").roomEmpty()) {
            gameFile.write("\n");
            currentData[13] = "";
        } else {
            Item[] items = theCave.getRoom("r10").getRoomContents();
            for (int i = 0; i < items.length - 1; i++) {
                gameFile.write(items[i].getDesc() + ",");
            }
            gameFile.write(items[items.length - 1].getDesc());
            gameFile.write("\n");
            currentData[13] = items[items.length - 1].getDesc();
        }

        //r11
        if (theCave.getRoom("r11").roomEmpty() || level0.getRoom("r11").roomEmpty() || level1.getRoom("r11").roomEmpty()) {
            gameFile.write("\n");
            currentData[14] = "";
        } else {
            Item[] items = theCave.getRoom("r11").getRoomContents();
            for (int i = 0; i < items.length - 1; i++) {
                gameFile.write(items[i].getDesc() + ",");
            }
            gameFile.write(items[items.length - 1].getDesc());
            gameFile.write("\n");
            currentData[14] = items[items.length - 1].getDesc();
        }

        gameFile.close();
    }

}
