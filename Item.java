/**
 * Item class implements the category and buff amount of items found in the game
 */
import java.util.HashMap;

public class Item {
    
    public String name;
    private int buffAmt;
    private String buffCategory;
    public static HashMap<String, Item> itemList = new HashMap<>(); // all Items in the game
    
    /**
     * Constructor creates an Item with the given name, buff category, and buff amount
     * @param name
     * @param buffCategory
     * @param buffAmt
     */
    public Item(String name, String buffCategory, int buffAmt) {
        itemList.put(name, this);
        this.name = name;
        this.buffCategory = buffCategory;
        this.buffAmt = buffAmt;
    }

    /**
     * Returns info about an item
     */
    public String toString() {
        return name + ": Adds " + buffAmt + " to " + buffCategory;
    }

    /**
     * Getter for buff category of an item
     * @return
     */
    public String getBuffCategory() {
        return buffCategory;
    }

    /**
     * Getter for buff amount of an item
     * @return
     */
    public int getBuff() {
        return buffAmt;
    }

}
