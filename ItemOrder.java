/*
 * TCSS 305 Autumn 2018
 * Assignment 2
 */

package model;

/**
 * This class represents a single ItemOrder.
 * 
 * @author Trevor Nichols
 * @version 1.0
 */
public final class ItemOrder {
    
    /**The Item of this ItemOrder. */
    private Item myItem;
    
    /**The quantity for this ItemOrder. */
    private int myQuantity;
    
    /**
     * Constructs a new ItemOrder given an Item and a quantity.
     * 
     * @param theItem the Item assigned to this ItemOrder.
     * @param theQuantity the quantity assigned to this ItemOrder.
     */
    public ItemOrder(final Item theItem, final int theQuantity) {
        
        //We do not make a defensive copy of theItem because Item is an immutable object.
        myItem = theItem;
        myQuantity = theQuantity;
    }

    /**
     * Returns the Item of this ItemOrder.
     * 
     * @return the Item of this ItemOrder
     */
    public Item getItem() {
        
        return myItem;
    }
    
    /**
     * Returns the quantity of this ItemOrder.
     * 
     * @return the quantity assigned to this ItemOrder.
     */
    public int getQuantity() {
        
        return myQuantity;
    }

    /**
     * {@inheritDoc}
     * 
     * The string representation for this ItemOrder will be formatted as follows:
     * <br>Item Order[quantity=(current value), item=(current item name)]
     */
    @Override
    public String toString() {
        
        final StringBuilder builder = new StringBuilder(128); // default initial size = 16
       
        builder.append("Item Order [quantity=");
        builder.append(myQuantity);
        builder.append(", item=");
        builder.append(myItem.getName());
        builder.append("]");
        return builder.toString();
    }

}
