/** 
 * VideoGameCharacter class manages a character's name, stats, inventory, size, and location with methods
 */
import java.util.ArrayList;
import java.util.HashMap;

public class VideoGameCharacter implements Contract 
{
    String playerName;
    public HashMap<String, Integer> stats;
    public ArrayList<String> inventory;
    private String selectedItem;
    private Coordinates location;
    private Coordinates size;
    private int maxHealth;
    
    /**
     * Constructs a character with the given name and maxHealth, and initializes its attributes
     * @param name
     * @param maxHealth
     */
    public VideoGameCharacter(String name, int maxHealth) {
        playerName = name;
        selectedItem = "";
        location = new Coordinates();
        size = new Coordinates(10,10);
        location.setAccessible(-100,100,-100,100,true);

        this.stats = new HashMap<>(6);
        this.stats.put("Strength", 100);
        this.stats.put("Endurance", 100);
        this.stats.put("Damage", 100);
        this.stats.put("Defense", 100);
        this.stats.put("Speed", 100);
        this.stats.put("Health", maxHealth);

        inventory = new ArrayList(5);
    }

    /**
     * Prints the current stats of the character
     */
    public void getStats() {
        System.out.println(this.stats);
    }

    /**
     * Prints the current inventory of the character
     */
    public void getInventory() {
        System.out.println(this.inventory);
    }
    
    /**
     * Grabs an item by adding it to the character's inventory (if there is enough space)
     * @param item
     */
    public void grab(String item) {
        if (inventory.size() < 5) {
            inventory.add(item);
            this.selectedItem = item;
        }
        else {
            System.out.println("You do not have any space left in your inventory.");
        }
    }

    /**
     * Drops an item by removing it from the character's inventory
     * @param item
     * @return item dropped
     */
    public String drop(String item) {
        if (item.equals("")) {
            return "No item was selected to be dropped.";
        }
        if (this.selectedItem.equals(item)) {
            selectedItem = "";
        }
        inventory.remove(item);
        Item chosenItem = Item.itemList.get(item);
        this.stats.replace(chosenItem.getBuffCategory(), this.stats.get(chosenItem.getBuffCategory()) - chosenItem.getBuff());

        return "You have dropped " + item;
    }

    /**
     * Prints info about the item using the Item class's toString() method
     * @param item
     */
    public void examine(String item) {
        System.out.println(Item.itemList.get(item));
    }

    /**
     * Uses item by adding its buff to the character's stats
     * @param item
     */
    public void use(String item) {
        if (inventory.isEmpty()) {
            System.out.println("You have no items to use.");
        }
        else {
            Item chosenItem = Item.itemList.get(item);
            this.stats.replace(chosenItem.getBuffCategory(), this.stats.get(chosenItem.getBuffCategory()) + chosenItem.getBuff());
        }
    }

    /**
     * Has the character "walk" 10 coordinate points in the given direction if it's accessible
     * @param direction
     * @return whether character has successfully walked there
     */
    public boolean walk(String direction) {
        if (direction.equals("east")) {
            this.location.x += 10;
            System.out.println("You have walked to " + this.location );
            return this.location.isAccessible(this.location.x+10, this.location.y);
        }
        else if (direction.equals("north")) {
            this.location.y+=10;
            System.out.println("You have walked to " + this.location);
            return this.location.isAccessible(this.location.x, this.location.y+10);
        }
        else if (direction.equals("south")) {
            this.location.y-=10;
            System.out.println("You have walked to " + this.location);
            return this.location.isAccessible(this.location.x-10, this.location.y);
        }
        else if (direction.equals("west")) {
            this.location.x-=10;
            System.out.println("You have walked to " + this.location);
            return this.location.isAccessible(this.location.x +=10, this.location.y-10);
        }
        System.out.println("You cannot walk to this location.");
        return false;
        
    }

    /**
     * Has the character "fly" to a given coordinate point if it's accessible; acts as fast travel
     * @param x
     * @param y
     * @return whether character has successfully flown there
     */
    public boolean fly(int x, int y) {
        if (this.location.isAccessible(x, y)) {
            this.location.x = x;
            this.location.y = y;
            System.out.println("You have flown to " + this.location);
            return true;
        }

        System.out.println("You cannot fly to this location.");
        return false;
    }

    /**
     * Shrinks the character's size by a factor of 5 if its size isn't already too small
     * @return area
     */
    public Number shrink() {
        if (this.size.x < 5 && this.size.y < 5) {
            System.out.println("You cannot shrink any smaller. Your area is: ");
            return this.size.x*this.size.y;
        }

        this.size.x /= 5.0;
        this.size.y /= 5.0;
        System.out.println("You have shrunk. Your new area is: ");
        return this.size.x * this.size.y;
    }

    /**
     * Grows the character's size by a factor of 5 if its size isn't already too large
     * @return area
     */
    public Number grow() {
        if (this.size.x > 100 && this.size.y > 100) {
            System.out.println("You cannot grow any larger. Your area is: ");
            return this.size.x*this.size.y;
        }

        this.size.x *= 5.0;
        this.size.y *= 5.0;
        System.out.println("You have grown. Your new area is: ");
        return this.size.x * this.size.y;
    }

    /**
     * Reduces the character's health by the damage amount
     * @param amt
     */
    public void takeDamage(int amt) {
        int currHealth = this.stats.get("Health");
        this.stats.replace("Health", currHealth - amt);
    }

    /**
     * Restores full stats of a character
     */
    public void rest() {
        for (String category : this.stats.keySet()) {
            this.stats.replace(category, 100);
        }
    }

    /**
     * Drops the character's current selected item
     */
    public void undo() {
        this.drop(this.selectedItem);
    }

    public static void main(String[] args) {
        Item coffee = new Item("coffee", "Speed", 10);
        VideoGameCharacter m = new VideoGameCharacter("mario", 100);

        /*m.getStats();
        m.grab("coffee");
        m.grab("flower");
        m.grab("cherry");
        m.grab("strengthBoost");
        m.grab("enduranceBoost");
        m.grab("shield");
        m.getInventory();
        
        m.use("coffee");
        m.getStats();
        m.examine("coffee");
        m.drop("coffee");
        m.location.setAccessible(-100,100,-100,100, true);
        System.out.println(m.fly(100, 100)); */

        m.size.x = 120;
        m.size.y = 120;
        System.out.println(m.grow());

        m.takeDamage(50);
        m.getStats();
        m.rest();
        m.getStats();
        m.grab("coffee");
        m.getInventory();
        m.undo();
        m.getInventory();
        
    }
}

