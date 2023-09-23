package org.example.belonging;

/**
 * @author Rong Sun
 * An abstract class for all kinds of items include weapon
 */
public abstract class Item {
    // Name of the item
    String name;

    // Price of the item
    int price;

    // Weight of the item. Defaults to 1
    int weight = 1;

    /**
     * @author Yucheng Zhu
     * Get the name of the item
     * @return The name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * @author Yucheng Zhu
     * Set the name of the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @author Yucheng Zhu
     * Get the price of the item. Used in shopping.
     * @return The price of the item
     */
    public int getPrice() {
        return price;
    }

    /**
     * @author Yucheng Zhu
     * Set the price of the item. Used in shopping.
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * @author Yucheng Zhu
     * Get the weight of the item. Most items have the weight of 1.
     * Used to calculate whether an inventory reaches the max capacity.
     * @return The weight of the item
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @author Yucheng Zhu
     * Get the weight of the item. Most items have the weight of 1.
     * Used to calculate whether an inventory reaches the max capacity.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }


    public Item(String name, int price, int weight) {
        this.name = name;
        this.price = price;
        this.weight = weight;
    }
}
