/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esof322.a4;

import esof322.a4.Room;

/*I added this abstract class to implement the Abstract Factory Design Pattern.*/

/**
 *
 * @author Ashley Bertrand
 */
public abstract class AdventureGameFactory {
    //abstract method implemented differently in Level0Factory and Level1Factory
    abstract public Room createGame(AdventureGameFactory factory);
}
