//Ashley Bertrand and Megan Weller
package esof322.a3;

/*Two methods were added to this class for accessibility purposes of 
obtaining the key's description.
*/

/**
 * Adventure Game Program Code Copyright (c) 1999 James M. Bieman
 *
 * To compile: javac AdventureGame.java To run: java AdventureGame
 *
 * The main routine is AdventureGame.main
 *
 * Update August 2010: refactored Vector contents into ArrayList<Item> contents.
 * This gets rid of the use of obsolete Vector and makes it type safe.
 *
 * 
 * 
 * We added a String key and a get and set method for the key description when
 * you are trying to enter a locked door while you are in room 10 and room 11. 
 *
 */
// class Room
import java.util.ArrayList;
import java.util.ListIterator;

public class Room implements CaveSite {

    private String description;
    private String key;
    public CaveSite[] side = new CaveSite[6];

    private ArrayList<Item> contents = new ArrayList<Item>();

    Room() {
        side[0] = new Wall();
        side[1] = new Wall();
        side[2] = new Wall();
        side[3] = new Wall();
        side[4] = new Wall();
        side[5] = new Wall();
    }

    public void setDesc(String d) {
        description = d;
    }
    
    public void setSide(int direction, CaveSite m) {
        side[direction] = m;
    }

    public void addItem(Item theItem) {
        contents.add(theItem);
    }

    public void removeItem(Item theItem) {
        contents.remove(theItem);
    }

    public boolean roomEmpty() {
        return contents.isEmpty();
    }

    public Item[] getRoomContents() {
        Item[] contentsArray = new Item[contents.size()];
        contentsArray = contents.toArray(contentsArray);
        return contentsArray;
    }

    public void enter(Player p) {
        p.setLoc(this);
    }

    public void exit(int direction, Player p) {
        side[direction].enter(p);
    }

    public String getDesc() {
        ListIterator<Item> roomContents = contents.listIterator();
        String contentString = "";
        while (roomContents.hasNext()) {
            contentString
                    = contentString + (roomContents.next()).getDesc() + " ";
        }

        return description + '\n' + '\n'
                + "Room Contents: " + contentString + '\n';
    }
    
    /*I had to add two new methods to set the currentRoom key description.
    I tried many different ways to grab the key description, but the easiest
    was to create it's own set and get field. -MW
    */
    public void setKeyDesc(String k){
        key = k;
    }
   
    public String getKeyDesc(){
        return key;
    }
}
