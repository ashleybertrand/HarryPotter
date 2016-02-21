/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esof322.a4;

/*This concrete class provides the user with hints to locations of
 different objects when playing in Level 1.
 */

/**
 *
 * @author Ashley Bertrand
 */
public class Level1Factory extends AdventureGameFactory {
    private Room entrance;
    private Room outside;
    private Room r1;
    private Room r2;
    private Room r3;
    private Room r4;
    private Room r5;
    private Room r6;
    private Room r7;
    private Room r8;
    private Room r9;
    private Room r10;
    private Room r11;

    public Level1Factory() {
        outside = new Room();
        r1 = new Room();
        r2 = new Room();
        r3 = new Room();
        r4 = new Room();
        r5 = new Room();
        r6 = new Room();
        r7 = new Room();
        r8 = new Room();
        r9 = new Room();
        r10 = new Room();
        r11 = new Room();
    }

    @Override
    public Room createGame(AdventureGameFactory factory) {
        // The outside: 
        outside.setDesc(
                "You are standing outside Hogwarts School of Witchcraft and\n"
                + "Wizardry, near the Whomping Willow. You are searching\n"
                + "for the hidden Galleons that may perhaps be in the cave that\n"
                + "opens straight below you (outside).");

        // Room 1:
        r1.setDesc(
                "Too bad this cave is not on the Marauder's Map.  You shout\n"
                + "'Lumos' to ignite your wand, and you see there is a narrow,\n"
                + " dark passage to the east (r1).");

        // Connect the outside to Room 1:
        outside.setSide(5, r1);
        r1.setSide(4, outside);
        entrance = outside;

        // Room 2:
        r2.setDesc(
                "You are in a gloomy oval shaped room with grey walls.\n"
                + " \"Enemies of the heir, beware\" is written on the wall.\n"
                + "There is a dim light to the west, and a narrow\n"
                + "dark hole to the east only about 18 inches high (r2).");

        // Room 3:
        r3.setDesc("You really need your wand here. \n"
                + "There is a wide passage that quickly narrows\n"
                + "to the west, a bright opening to the east,\n"
                + "and a deep hole that appears to have no bottom\n"
                + "in the middle of the room (r3).");

        // Connect Rooms 1, 2, & 3:
        r1.setSide(2, r2);
        r2.setSide(3, r1);
        r2.setSide(2, r3);
        r3.setSide(3, r2);

        // Room 4:
        r4.setDesc("There is what looks like a giant snake skin\n"
                + "in the corner.  Perhaps from the Basilisk?  A passage leads to\n"
                + " the west, another one to the north, and a slippery route\n"
                + "goes down steeply. You can hear the shrieks of mandrakes (r4).");

        // Room 5:
        r5.setDesc("There is a dim light from above and the shrieks\n"
                + "are clearly coming from a passageway to the east (r5).");

        // Room 6:
        r6.setDesc("The ceiling is full of pixies.\n"
                + "Make sure to cover your head (r6)!");

        // Room 7:
        r7.setDesc("This room is very damp. There are puddles on the floor\n"
                + "and a steady dripping from above. Let's hope Moaning\n"
                + "Myrtle didn't flood the girls' lavatory(r7).");

        // Connect rooms 3, 4, 5, 6, & 7.
        r3.setSide(2, r4);
        r3.setSide(5, r5);
        r4.setSide(3, r3);
        r4.setSide(5, r7);
        r5.setSide(4, r3);
        r5.setSide(2, r6);
        r6.setSide(3, r5);
        r7.setSide(4, r4);

        // Room 8:
        r8.setDesc("Ron's rat, Scabbers runs across your foot, and woah!  Here "
                + "comes Crookshanks chasing behind.  A narrow passage runs\n"
                + "to the east and an even narrower one runs to the west (r8).");

        // Room 9 (level 1 enhancement):
        r9.setDesc("As you enter the room, you notice a piece of paper on the ground.\n"
                + "Oh wait!  It's not just ordinary paper; it's the Marauder's Map!\n"
                + "It can show you where important items are!  See hints below (r9).");

        // Room 10:
        r10.setDesc("It appears that someone has been here. The harp is\n"
                + "playing to put Fluffy, the three-headed dog asleep.\n"
                + "Oh wait!  It looks like Fluffy is gaurding something.\n"
                + "There's a trap door on the floor, but it is locked.\n"
                + "'Alohomora' won't help you here, you need a key (r10).");

        // Room 11:
        r11.setDesc("This room is very dark. You can just barely see (r11).");
        Treasure theTreasure = new Treasure();
        theTreasure.setDesc("A bag filled with ten thousand Galleons.");
        r11.addItem(theTreasure);

        // Lets connect them:
        r4.setSide(0, r8);
        r8.setSide(1, r4);
        r8.setSide(3, r9);
        r8.setSide(2, r10);
        r9.setSide(2, r8);
        r10.setSide(3, r8);

        // Create a key and put it in r6:
        Key theKey = new Key();
        theKey.setDesc("A shiny gold key.");
        r6.addItem(theKey);

        // We add a door between r10 and r11: 
        Door theDoor = new Door(r10, r11, theKey);
        r10.setSide(5, theDoor);
        r11.setSide(4, theDoor);
        
        // Now return the entrance:
        entrance = outside;
        return entrance;
    }
    
    //takes a room name and returns the corresponding room
    public Room getRoom(String room) {
        switch (room) {
            case "outside":
                return outside;
            case "r1":
                return r1;
            case "r2":
                return r2;
            case "r3":
                return r3;
            case "r4":
                return r4;
            case "r5":
                return r5;
            case "r6":
                return r6;
            case "r7":
                return r7;
            case "r8":
                return r8;
            case "r9":
                return r9;
            case "r10":
                return r10;
            case "r11":
                return r11;
            default:
                return new Room();
        }
    }
    
    public String getHints() {
        Room[] rooms = new Room[12];
        rooms[0] = outside;
        rooms[1] = r1;
        rooms[2] = r2;
        rooms[3] = r3;
        rooms[4] = r4;
        rooms[5] = r5;
        rooms[6] = r6;
        rooms[7] = r7;
        rooms[8] = r8;
        rooms[9] = r9;
        rooms[10] = r10;
        rooms[11] = r11;
        
        String hints = "Hints: ";
        
        for (int i = 0; i < rooms.length; i++) {
            //if player has not yet found the treasure
            if (rooms[i].hasTreasure()) {
                hints += "The treasure is in " + rooms[i].getRoomName() + ". ";
            }
            
            //if the key is in a room (not in player's hands)
            if (rooms[i].hasKey()) {
                hints += "The key is in " + rooms[i].getRoomName() + ". ";
            }
        }
        return hints;
    }
}
