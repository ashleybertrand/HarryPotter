/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esof322.a3;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ashley Bertrand
 */
public class AllTest {

    public AllTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    //class Door: enter
    public void testEnterDoor() {
        //setting up cavesite
        Room outside = new Room();
        Room inside = new Room();
        Key key = new Key();
        Door door = new Door(outside, inside, key);
        Player player = new Player();
        player.setLoc(outside);

        //outside to inside without key
        door.enter(player);
        assertFalse(player.getLoc().equals(inside));
        assertTrue(player.getLoc().equals(outside));

        //player picks up key from outside
        outside.addItem(key);
        player.pickUp(key);

        //outside to inside with key
        door.enter(player);
        assertTrue(player.getLoc().equals(inside));

        //player drops key
        player.drop(1);

        //inside to outside without key
        door.enter(player);
        assertFalse(player.getLoc().equals(outside));
        assertTrue(player.getLoc().equals(inside));

        //player picks up key
        player.pickUp(key);

        //inside to outside with key
        door.enter(player);
        assertTrue(player.getLoc().equals(outside));
    }

    @Test
    //class Player: go
    public void testGo() {
        //setting up cavesite
        Player player = new Player();
        Room room1 = new Room();
        Room room2 = new Room();
        room1.setSide(0, room2);
        player.setRoom(room1);      //Note: setRoom() and setLoc() do the same thing, redundant
        
        //player 'goes' in direction 0
        player.go(0);
        assertTrue(player.getLoc().equals(room2));
        assertTrue(player.look().equals(room2.getDesc()));
    }

    @Test
    //class Player: pickUp
    public void testPickUp() {
        //setting up cavesite
        Player player = new Player();
        Item item1 = new Item();
        item1.setDesc("item1");
        String firstExpected = "1: item1 ";
        Item item2 = new Item();
        item2.setDesc("item2");
        String secondExpected = "1: item1 2: item2 ";
        Item item3 = new Item();
        item3.setDesc("item3");
        String thirdExpected = "1: item1 2: item2 3: item3 ";
        Room room = new Room();
        room.addItem(item1);
        room.addItem(item2);
        room.addItem(item3);
        player.setLoc(room);

        //player picks up item1
        player.pickUp(item1);

        assertEquals(player.getFirstItem(), item1.getDesc());
        assertFalse(player.handsEmpty());
        assertFalse(player.handsFull());
        assertEquals(1, player.numItemsCarried());
        assertTrue(player.haveItem(item1));
        assertTrue(player.showMyThings().equals(firstExpected));

        //player picks up item2 and is now carrying 2 items
        player.pickUp(item2);
        assertEquals(player.getSecondItem(), item2.getDesc());
        assertEquals(2, player.numItemsCarried());
        assertTrue(player.haveItem(item1));
        assertTrue(player.haveItem(item2));
        assertTrue(player.showMyThings().equals(secondExpected));
        assertTrue(player.handsFull());

        //player is called to pick up a third item but can only carry a maximum of 2 items
        player.pickUp(item3);
        assertEquals(2, player.numItemsCarried());
        assertFalse(player.haveItem(item3));
        assertFalse(player.showMyThings().equals(thirdExpected));
    }

    @Test
    //class Player: drop
    public void testDrop() {
        //setting up cavesite
        Player player = new Player();
        Item item1 = new Item();
        item1.setDesc("item1");
        Item item2 = new Item();
        item2.setDesc("item2");
        Room room = new Room();
        player.setLoc(room);
        
        //player picks up item1
        player.pickUp(item1);
        
        //player drops first item
        player.drop(1);
        assertEquals(0, player.numItemsCarried());
        assertFalse(player.haveItem(item1));
        assertTrue(player.handsEmpty());

        //player picks up item1 and item2
        player.pickUp(item1);
        player.pickUp(item2);
        
        //player drops second item
        player.drop(2);
        assertTrue(player.getFirstItem().equals(item1.getDesc()));
        assertEquals(1, player.numItemsCarried());
        assertTrue(player.haveItem(item1));
        assertFalse(player.haveItem(item2));
        assertFalse(player.handsEmpty());

        //player picks up item2, player is holding 2 items
        player.pickUp(item2);
        
        //player drops invalid item (is not a valid case in switch statement)
        player.drop(0);
        player.drop(3);
        //hands are not empty because player 'dropped' invalid items
        assertFalse(player.handsEmpty());
        //player is still carrying 2 items
        assertEquals(2, player.numItemsCarried());
        assertTrue(player.handsFull());
    }

    @Test
    //class Room: addItem
    public void testAddItem() {
        //setting up cavesite
        Room room = new Room();
        Item item = new Item();
        room.addItem(item);
        Item[] contents = room.getRoomContents();
        assertTrue(contents[0].equals(item));
        assertFalse(room.roomEmpty());
    }

    @Test
    //class Room: removeItem
    public void testRemoveItem() {
        //setting up cavesite
        Room room = new Room();
        Item item = new Item();
        room.addItem(item);
        room.removeItem(item);
        assertTrue(room.roomEmpty());
    }

    @Test
    //class Room: enter
    public void testEnterRoom() {
        //setting up cavesite
        Room room = new Room();
        Player player = new Player();
        room.enter(player);
        assertTrue(player.getLoc().equals(room));
        assertTrue(player.look().equals(room.getDesc()));
    }

    @Test
    //class Room: exit
    public void testExitRoom() {
        //setting up cavesite
        Room room1 = new Room();
        Room room2 = new Room();
        room1.setSide(0, room2);
        Player player = new Player();
        room1.exit(0, player);
        assertTrue(player.getLoc().equals(room2));
        assertTrue(player.look().equals(room2.getDesc()));
    }

}
