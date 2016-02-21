//Ashley Bertrand
package esof322.a4;

/*Numerous methods were added to this class for accessibility purposes of 
 obtaining the key's description and to gain more information about a Room.
 See descriptions below.
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

    //clears the contents of a room
    public void removeAllItems() {
        contents.clear();
    }
    
    public boolean roomEmpty() {
        return contents.isEmpty();
    }
    
    //returns a room's contents as an array
    public Item[] getRoomContents() {
        Item[] contentsArray = new Item[contents.size()];
        contentsArray = contents.toArray(contentsArray);
        return contentsArray;
    }
    
    //used to configure hints
    public boolean hasTreasure() {
        Item[] items = getRoomContents();
        for (int i = 0; i < items.length; i++) {
            if (items[i].getDesc().equals("A bag filled with ten thousand Galleons.")) {
                return true;
            }
        }
        return false;
    }
    
    //used to configure hints
    public boolean hasKey() {
        Item[] items = getRoomContents();
        for (int i = 0; i < items.length; i++) {
            if (items[i].getDesc().equals("A shiny gold key.")) {
                return true;
            }
        }
        return false;
    }
    
    public void enter(Player p) {
        p.setLoc(this);
    }

    public void exit(int direction, Player p) {
        side[direction].enter(p);
    }

    //used for display purposes in carryingArea
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
    
    //used for display purposes in setting room contents to empty
    public String setContentsToEmpty() {
        removeAllItems();
        return description + '\n' + '\n'
            + "Room Contents: " + '\n';
    }
    
    //used for formatting, readability, and usability purposes
    public String getRoomName() {
        switch (description) {
            case "You are standing outside Hogwarts School of Witchcraft and\n"
                + "Wizardry, near the Whomping Willow. You are searching\n"
                + "for the hidden Galleons that may perhaps be in the cave that\n"
                + "opens straight below you (outside).":
                return "outside";
                
            case "Too bad this cave is not on the Marauder's Map.  You shout\n"
                + "'Lumos' to ignite your wand, and you see there is a narrow,\n"
                + " dark passage to the east (r1).":
                return "r1";
                
            case "You are in a gloomy oval shaped room with grey walls.\n"
                + " \"Enemies of the heir, beware\" is written on the wall.\n"
                + "There is a dim light to the west, and a narrow\n"
                + "dark hole to the east only about 18 inches high (r2).":
                return "r2";
                
            case "You really need your wand here. \n"
                + "There is a wide passage that quickly narrows\n"
                + "to the west, a bright opening to the east,\n"
                + "and a deep hole that appears to have no bottom\n"
                + "in the middle of the room (r3).":
                return "r3";
                
            case "There is what looks like a giant snake skin\n"
                + "in the corner.  Perhaps from the Basilisk?  A passage leads to\n"
                + " the west, another one to the north, and a slippery route\n"
                + "goes down steeply. You can hear the shrieks of mandrakes (r4).":
                return "r4";
                
            case "There is a dim light from above and the shrieks\n"
                + "are clearly coming from a passageway to the east (r5).":
                return "r5";
                
            case "The ceiling is full of pixies.\n"
                + "Make sure to cover your head (r6)!":
                return "r6";
                
            case "This room is very damp. There are puddles on the floor\n"
                + "and a steady dripping from above. Let's hope Moaning\n"
                + "Myrtle didn't flood the girls' lavatory(r7).":
                return "r7";
                
            case "Ron's rat, Scabbers runs across your foot, and woah!  Here "
                + "comes Crookshanks chasing behind.  A narrow passage runs\n"
                + "to the east and an even narrower one runs to the west (r8).":
                return "r8";
                
            case "Water drips from the ceiling as you cover your head.\n "
                + "There is no exit from this room with only the option to turn back east.\n"
                + "Will you decide to enter the chamber again? (r9)":
                return "r9";
                
            case "It appears that someone has been here. The harp is\n"
                + "playing to put Fluffy, the three-headed dog asleep.\n"
                + "Oh wait!  It looks like Fluffy is gaurding something.\n"
                + "There's a trap door on the floor, but it is locked.\n"
                + "'Alohomora' won't help you here, you need a key (r10).":
                return "r10";
                
            case "This room is very dark. You can just barely see (r11).":
                return "r11";
            
            default:
                return "your hands";
        }  
    }
    
    //called by getRoomDesc() in Facade
    public int getRoomNumber() {
        switch (description) {
            case "You are standing outside Hogwarts School of Witchcraft and\n"
                + "Wizardry, near the Whomping Willow. You are searching\n"
                + "for the hidden Galleons that may perhaps be in the cave that\n"
                + "opens straight below you (outside).":
                return 0;
                
            case "Too bad this cave is not on the Marauder's Map.  You shout\n"
                + "'Lumos' to ignite your wand, and you see there is a narrow,\n"
                + " dark passage to the east (r1).":
                return 1;
                
            case "You are in a gloomy oval shaped room with grey walls.\n"
                + " \"Enemies of the heir, beware\" is written on the wall.\n"
                + "There is a dim light to the west, and a narrow\n"
                + "dark hole to the east only about 18 inches high (r2).":
                return 2;
                
            case "You really need your wand here. \n"
                + "There is a wide passage that quickly narrows\n"
                + "to the west, a bright opening to the east,\n"
                + "and a deep hole that appears to have no bottom\n"
                + "in the middle of the room (r3).":
                return 3;
                
            case "There is what looks like a giant snake skin\n"
                + "in the corner.  Perhaps from the Basilisk?  A passage leads to\n"
                + " the west, another one to the north, and a slippery route\n"
                + "goes down steeply. You can hear the shrieks of mandrakes (r4).":
                return 4;
                
            case "There is a dim light from above and the shrieks\n"
                + "are clearly coming from a passageway to the east (r5).":
                return 5;
                
            case "The ceiling is full of pixies.\n"
                + "Make sure to cover your head (r6)!":
                return 6;
                
            case "This room is very damp. There are puddles on the floor\n"
                + "and a steady dripping from above. Let's hope Moaning\n"
                + "Myrtle didn't flood the girls' lavatory(r7).":
                return 7;
                
            case "Ron's rat, Scabbers runs across your foot, and woah!  Here "
                + "comes Crookshanks chasing behind.  A narrow passage runs\n"
                + "to the east and an even narrower one runs to the west (r8).":
                return 8;
                
            case "Water drips from the ceiling as you cover your head.\n "
                + "There is no exit from this room with only the option to turn back east.\n"
                + "Will you decide to enter the chamber again? (r9)":
                return 9;
                
            case "It appears that someone has been here. The harp is\n"
                + "playing to put Fluffy, the three-headed dog asleep.\n"
                + "Oh wait!  It looks like Fluffy is gaurding something.\n"
                + "There's a trap door on the floor, but it is locked.\n"
                + "'Alohomora' won't help you here, you need a key (r10).":
                return 10;
                
            case "This room is very dark. You can just barely see (r11).":
                return 11;
            default:
                return -1;
        }  
    }
    
    public void setKeyDesc(String k){
        key = k;
    }
   
    public String getKeyDesc(){
        return key;
    }
}
