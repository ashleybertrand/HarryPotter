//Ashley Bertrand
package esof322.a4;

/*Numerous methods were added for greater manipulation of player objects 
 and for simplicity of logic in AdventureGameModelFacade.  Descriptions 
 are given below.
 */
/**
 * Adventure Game Program Code Copyright (c) 1999 James M. Bieman
 *
 * To compile: javac AdventureGame.java To run: java AdventureGame
 *
 * The main routine is AdventureGame.main
 *
 *
 */
public class Player {

    private Room myLoc;
    private Item[] myThings = new Item[2];
    private int itemCount = 0;

    public void setLoc(Room r) {
        myLoc = r;
    }

    public Room getLoc() {
        return myLoc;
    }

    public String look() {
        return myLoc.getDesc();
    }

    public void go(int direction) {
        myLoc.exit(direction, this);
    }

    public void pickUp(Item i) {
        if (itemCount < 2) {
            myThings[itemCount] = i;
            itemCount++;
            myLoc.removeItem(i);
        }
    }
    
    //player grabs item (nothing gets removed from room)
    public void carry(String desc) {
        Item item = new Item();
        item.setDesc(desc);
        if (itemCount < 2) {
            myThings[itemCount]  = item;
            itemCount++;
        }
    }
    
    //searches player's items for an item
    public boolean haveItem(Item itemToFind) {
        for (int n = 0; n < itemCount; n++) {
            if (myThings[n] == itemToFind) {
                return true;
            }
        }
        return false;
    }
    
    //searches player's items for the key
    public boolean haveKey() {
        for (int n = 0; n < itemCount; n++) {
            if (myThings[n].getDesc().equals("A shiny gold key.")) {
                return true;
            }
        }
        return false;
    }
    
    public void drop(int itemNum) {
        if (itemNum > 0 & itemNum <= itemCount) {
            switch (itemNum) {
                case 1: {
                    myLoc.addItem(myThings[0]);
                    myThings[0] = myThings[1];
                    itemCount--;
                    break;
                }
                case 2: {
                    myLoc.addItem(myThings[1]);
                    itemCount--;
                    break;
                }
            }
        }
    }
    
    public Room dropIntoRoom(int itemNum) {
        if (itemNum > 0 & itemNum <= itemCount) {
            switch (itemNum) {
                case 1: {
                    myLoc.addItem(myThings[0]);
                    myThings[0] = myThings[1];
                    itemCount--;
                    break;
                }
                case 2: {
                    myLoc.addItem(myThings[1]);
                    itemCount--;
                    break;
                }
            }
        }
        return myLoc;
    }
    
    //player drops item (nothing gets added to room)
    public void getRidOfItem(int itemNum) {
        if (itemNum > 0 & itemNum <= itemCount) {
            switch (itemNum) {
                case 1: {
                    myThings[0] = myThings[1];
                    itemCount--;
                    break;
                }
                case 2: {
                    itemCount--;
                    break;
                }
            }
        }
    }

    //Ashley added the following two methods for formatting purposes
    //called in the model by grab() and drop()
    public String getFirstItem() {
        return myThings[0].getDesc();
    }

    //called in the model by grab()
    public String getSecondItem() {
        return myThings[1].getDesc();
    }
    
    public String showMyThings() {
        String outString = "";
        for (int n = 0; n < itemCount; n++) {
            outString = outString + Integer.toString(n + 1) + ": "
                    + myThings[n].getDesc() + " ";
        }
        return outString;
    }

    public boolean handsFull() {
        return itemCount == 2;
    }

    public boolean handsEmpty() {
        return itemCount == 0;
    }

    public int numItemsCarried() {
        return itemCount;
    }
}
