import java.util.*;

public class TagManager
{
    private PlayerNode gameFront;
    private PlayerNode historyFront;

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

    public boolean gameRingContains(String name)
    {
        PlayerNode current = gameFront;
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

    public boolean isGameOver()
    {
        if (gameFront.next == null)
        {
            return true;
        }
        return false;
    }

    public String winner()
    {
        if (isGameOver())
        {
            return gameFront.name;
        }
        return null;
    }

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
                //remove front
                gameFront.tagger = currentGame.name;
                currentHistory = gameFront;
                gameFront = gameFront.next;
                currentHistory.next = historyFront;
                historyFront = currentHistory;
                break;
            }
            else if (currentGame.next.name.equalsIgnoreCase(name))
            {
                //remove next
                currentGame.next.tagger = currentGame.name;
                currentHistory = currentGame.next;
                currentGame.next = currentGame.next.next;
                currentHistory.next = historyFront;
                historyFront = currentHistory;
                break;
            }
            currentGame = currentGame.next;
        }
    }
}