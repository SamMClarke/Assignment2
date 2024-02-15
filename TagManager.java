/*
Authors: Sam Clarke
Date: 2/15/2024
Class: CS 145
Assignment: Assignment #2: Tag
File: TagManager.java
Source: Deitel / Deitel
Purpose: Manages a game of tag created by the user, keeping track of the current game and history.
*/

import java.util.*;

public class TagManager
{
    private PlayerNode gameFront;
    private PlayerNode historyFront;

    /**
     * Initializes a new Tag game manager.
     *
     * @param names The list of the names of players that are playing the game.
     * @throws IllegalArgumentException If the list of names is either empty or null.
     */

    public TagManager(List<String> names)
    {
        if (names.size() == 0 || names == null)
        {
            throw new IllegalArgumentException("List of names was either null or empty");
        }

        gameFront = new PlayerNode(names.get(0));
        PlayerNode current = gameFront;

        for (int i = 1; i < names.size(); i++)
        {
            current.next = new PlayerNode(names.get(i));
            current = current.next;
        }
    }

    /**
     * Prints out the current game ring.
     *
     */

    public void printGameRing()
    {
        PlayerNode current = gameFront;
        while (current.next != null)
        {
            System.out.println("  " + current.name + " is trying to tag " + current.next.name);
            current = current.next;
        }
        System.out.println("  " + current.name + "is trying to tag " + gameFront.name);
    }

    /**
     * Prints out the current history.
     *
     */

    public void printHistory()
    {
        if (historyFront != null)
        {
            PlayerNode current = historyFront;
            while (current != null)
            {
                System.out.println("  " + current.name + " was tagged by " + current.tagger);
                current = current.next;
            }
        }
    }

    /**
     * Checks if a given name is in the current game ring.
     *
     * @param name The name of the person to check for.
     * @return A boolean: true if the person does exist, false otherwise.
     */

    public boolean gameRingContains(String name)
    {
        PlayerNode current = gameFront;
        while (current != null)
        {
            if (current.name.equalsIgnoreCase(name)) //equalsIgnoreCase checks if string are equal ignoring any case differences
            {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Checks if a given name is in the current history.
     *
     * @param name The name of the person to check for.
     * @return A boolean: true if the person does exist, false otherwise.
     */

    public boolean historyContains(String name)
    {
        PlayerNode current = historyFront;
        while (current != null)
        {
            if (current.name.equalsIgnoreCase(name))
            {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Checks if the game is over (i.e only one person in the game ring).
     *
     * @return A boolean: true if only one person exists in the game ring, false otherwise.
     */

    public boolean isGameOver()
    {
        if (gameFront.next == null)
        {
            return true;
        }
        return false;
    }
    
    /**
     * Returns the name of the last person in the game ring (the winner).
     *
     * @return The name of the person who won.
     */

    public String winner()
    {
        if (isGameOver())
        {
            return gameFront.name;
        }
        return null;
    }

    /**
     * Tags a given player, removing them from the game ring and adding them to the history.
     *
     * @param name The name of the person to remove.
     * @throws IllegalStateException If the game is already over.
     * @throws IllegalArgumentException If the given person is not playing the game.
     */

    public void tag(String name)
    {
        if (isGameOver())
        {
            throw new IllegalStateException("Game is over");
        }
        if (!gameRingContains(name))
        {
            throw new IllegalArgumentException("The person " + name + " is not playing the game");
        }

        PlayerNode currentGame = gameFront;
        PlayerNode currentHistory = historyFront;
        
        while (currentGame != null)
        {
            if (gameFront.name.equalsIgnoreCase(name) && currentGame.next == null)
            {
                //Remove the person from the front of the game ring.
                gameFront.tagger = currentGame.name; //Sets the tagger field.
                currentHistory = gameFront; //Sets the current history to the person in the front
                gameFront = gameFront.next; //Sets the game front to the second person in the game.
                currentHistory.next = historyFront; //Points the current history to the rest of the history.
                historyFront = currentHistory; //Sets the history front to the new front of the current history.
                break;
            }
            else if (currentGame.next.name.equalsIgnoreCase(name))
            {
                //Removes the next person from the game ring.
                currentGame.next.tagger = currentGame.name; //Sets the tagger field.
                currentHistory = currentGame.next; //Sets the current hisotry to the next person in the game.
                currentGame.next = currentGame.next.next; //Skips (jumps over) the person to be tagged.
                currentHistory.next = historyFront; //Points the current history to the rest of the history.
                historyFront = currentHistory; //Sets the history front to the new front of the current history
                break;
            }
            currentGame = currentGame.next;
        }
    }
}