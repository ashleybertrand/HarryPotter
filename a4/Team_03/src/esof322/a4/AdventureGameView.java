//Ashley Bertrand
package esof322.a4;

/*We used the view to display information in textAreas by calling 
 methods on an instance of the AdventureGameModelFacade class.

 A new feature for level1 is an additional field for displaying hints.
 */
import javax.swing.*;

import BreezySwing.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

//This here is the GUI code - the window objects
public class AdventureGameView extends GBFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //Window objects -----------------------------------------------------------
    JLabel welcomeLabel
            = addLabel("Welcome to the Adventure Game "
                    + "(inspired by an old game called the Colossal Cave Adventure)."
                    + " Java implementation Copyright (c) 1999-2012 by James M. Bieman",
                    1, 1, 5, 1);

    JLabel viewLable = addLabel("Your View: ", 2, 1, 1, 1);
    JTextArea viewArea = addTextArea("Start", 3, 1, 4, 3);

    JLabel carryingLable = addLabel("You are carrying: ", 6, 1, 1, 1);
    JTextArea carryingArea = addTextArea("Nothing.", 7, 1, 4, 2);

    JTextArea hintsArea = addTextArea("Hints: ", 10, 1, 4, 2);

    JLabel choiceLabel = addLabel("Choose a direction, pick-up, or drop an item", 11, 1, 5, 1);

    JButton level0Button = addButton("Level 0", 3, 5, 1, 1);
    JButton level1Button = addButton("Level 1", 4, 5, 1, 1);

    JButton loadButton = addButton("Load game", 5, 5, 1, 1);
    JButton saveButton = addButton("Save game", 6, 5, 1, 1);

    JButton grabButton = addButton("Grab an item", 12, 5, 1, 1);
    JButton dropButton = addButton("Drop an item", 13, 5, 1, 1);

    JButton northButton = addButton("North", 12, 2, 1, 1);
    JButton southButton = addButton("South", 14, 2, 1, 1);
    JButton eastButton = addButton("East", 13, 3, 1, 1);
    JButton westButton = addButton("West", 13, 1, 1, 1);
    JButton upButton = addButton("Up", 12, 3, 1, 1);
    JButton downButton = addButton("Down", 14, 3, 1, 1);

    //ties AdventureGameView to AdventureGameModelFacade
    AdventureGameModelFacade model;

    //Constructor---------------------------------------------------------------
    public AdventureGameView() throws IOException {
        setTitle("Adventure Game");

        //Connecting the GUI to the Game
        model = new AdventureGameModelFacade();

        viewArea.setEditable(false);
        carryingArea.setEditable(false);
        displayCurrentInfo();
    }

    //buttonClicked method------------------------------------------------------
    public void buttonClicked(JButton buttonObj) {
        //directional buttons checkForKey() before entering locked doors--------
        //in level 1, room 9 checkForHints() displays hints
        if (buttonObj == upButton) {
            model.checkForKey();
            model.goUp();
            try {
                hintsArea.setText(model.checkForHints());
            } catch (IOException ex) {
                Logger.getLogger(AdventureGameView.class.getName()).log(Level.SEVERE, null, ex);
            }
            displayCurrentInfo();
        } else if (buttonObj == downButton) {
            model.checkForKey();
            model.goDown();
            try {
                hintsArea.setText(model.checkForHints());
            } catch (IOException ex) {
                Logger.getLogger(AdventureGameView.class.getName()).log(Level.SEVERE, null, ex);
            }
            displayCurrentInfo();
        } else if (buttonObj == northButton) {
            model.goNorth();
            try {
                hintsArea.setText(model.checkForHints());
            } catch (IOException ex) {
                Logger.getLogger(AdventureGameView.class.getName()).log(Level.SEVERE, null, ex);
            }
            displayCurrentInfo();
        } else if (buttonObj == southButton) {
            model.goSouth();
            try {
                hintsArea.setText(model.checkForHints());
            } catch (IOException ex) {
                Logger.getLogger(AdventureGameView.class.getName()).log(Level.SEVERE, null, ex);
            }
            displayCurrentInfo();
        } else if (buttonObj == eastButton) {
            model.goEast();
            try {
                hintsArea.setText(model.checkForHints());
            } catch (IOException ex) {
                Logger.getLogger(AdventureGameView.class.getName()).log(Level.SEVERE, null, ex);
            }
            displayCurrentInfo();
        } else if (buttonObj == westButton) {
            model.goWest();
            try {
                hintsArea.setText(model.checkForHints());
            } catch (IOException ex) {
                Logger.getLogger(AdventureGameView.class.getName()).log(Level.SEVERE, null, ex);
            }
            displayCurrentInfo();
            
            //grabbing an item--------------------------------------------------
        } else if (buttonObj == grabButton) {
            grab();
            displayCurrentInfo();
            
            //dropping an item--------------------------------------------------
        } else if (buttonObj == dropButton) {
            drop();
            displayCurrentInfo();
            
            //saving a game state-----------------------------------------------
        } else if (buttonObj == saveButton) {
            try {
                model.saveGame();
            } catch (IOException ex) {
                Logger.getLogger(AdventureGameView.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //loading a game state----------------------------------------------
        } else if (buttonObj == loadButton) {
            try {
                model.loadPlayer();
                model.loadCave();
                loadPlayerItems();
                loadRoomContents();
            } catch (IOException ex) {
                Logger.getLogger(AdventureGameView.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                hintsArea.setText(model.checkForHints());
            } catch (IOException ex) {
                Logger.getLogger(AdventureGameView.class.getName()).log(Level.SEVERE, null, ex);
            }            

            //player starts at 'outside' when switching levels------------------
            //player carries no items
            //room contents get re-initiated
        } else if (buttonObj == level0Button) {
            viewArea.setText(model.level0());
            carryingArea.setText("Nothing.");
            try {
                hintsArea.setText(model.checkForHints());
            } catch (IOException ex) {
                Logger.getLogger(AdventureGameView.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (buttonObj == level1Button) {
            viewArea.setText(model.level1());
            carryingArea.setText("Nothing.");
            try {
                hintsArea.setText(model.checkForHints());
            } catch (IOException ex) {
                Logger.getLogger(AdventureGameView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Private methods-----------------------------------------------------------
    private void loadRoomContents() throws IOException {
        //update viewArea when loading game
        viewArea.setText(model.getRoomDesc());
    }

    private void loadPlayerItems() throws IOException {
        //update carryingArea when loading game
        carryingArea.setText(model.getPlayerItems());
    }

    private void displayCurrentInfo() {
        viewArea.setText(model.getView());
    }

    private void grab() {
        carryingArea.setText(model.grab());
    }

    private void drop() {
        carryingArea.setText(model.drop());
    }

    public static void main(String[] args) throws IOException {
        JFrame view = new AdventureGameView();
        view.setSize(700, 700);
        view.setVisible(true);
    }
}
