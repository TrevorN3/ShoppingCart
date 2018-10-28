/*
 * TCSS 305 Autumn 2018
 * Assignment 2
 */

package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A class that represents a ShoppingCart filled with ItemOrders.
 * 
 * @author Trevor Nichols
 * @version 1.0
 */
public class ShoppingCart {

    /**A constant for members that get a discount. */
    private static final BigDecimal MEMBERSHIP_DISCOUNT = new BigDecimal("0.15");
    
    /**A constant for the amount a member must spend before being able to receive
       their discount. */
    private static final BigDecimal MIN_PRICE_SPENT = new BigDecimal("20.00");
    
    /**The shopping cart for this ShoppingCart. */
    private Map<Item, ItemOrder> myShoppingCart;
    
    /**The membership status for this ShoppingCart. */
    private boolean myMembership;

    /**
     * Constructs a new empty ShoppingCart.
     */
    public ShoppingCart() {
        
        myShoppingCart = new HashMap<Item, ItemOrder>();
    }

    /**
     * Adds a new ItemOrder to this ShoppingCart.
     * 
     * @param theOrder an ItemOrder to be added to this ShoppingCart.
     */
    public void add(final ItemOrder theOrder) {
        
        if (myShoppingCart.containsKey(theOrder.getItem())) {
            
            myShoppingCart.remove(theOrder.getItem());
        }
        
        myShoppingCart.put(theOrder.getItem(), theOrder);
    }

    /**
     * Sets the membership status for this ShoppingCart.
     * 
     * @param theMembership the membership status to be assigned to this ShoppingCart.
     */
    public void setMembership(final boolean theMembership) {
        
        myMembership = theMembership;
    }

    /**
     * Returns the total price of this ShoppingCart.
     * 
     * @return the total price of this ShoppingCart.
     */
    public BigDecimal calculateTotal() {
        
        BigDecimal result = BigDecimal.ZERO;
        
        for (Item key : myShoppingCart.keySet()) {
            
            result = result.add(this.calculateTotalHelper(key));
        }
        
        if (myMembership && result.compareTo(MIN_PRICE_SPENT) > 0) {
            
            final BigDecimal temp = result.multiply(MEMBERSHIP_DISCOUNT);
            result = result.subtract(temp);
        }
        
        return result.setScale(2, RoundingMode.HALF_EVEN);
    }
    
    /**
     * A helper method that returns the price of a single ItemOrder.
     * 
     * @param theItem the item who's price will be calculated
     * @return the price of a given Item
     */
    private BigDecimal calculateTotalHelper(final Item theItem) {
        
        BigDecimal result = BigDecimal.ZERO;
        final int numBulkPurchase = myShoppingCart.get(theItem).getQuantity()
                                    / theItem.getBulkQuantity();
        
        if (theItem.isBulk() && numBulkPurchase > 0) {
            
            final BigDecimal numBulk = new BigDecimal(numBulkPurchase);
            
            result = numBulk.multiply(theItem.getBulkPrice());
            
            BigDecimal remainder = new BigDecimal(myShoppingCart.get(theItem).getQuantity()
                                                  % theItem.getBulkQuantity());
            
            remainder = remainder.multiply(theItem.getPrice());
            result = result.add(remainder);
            
        } else { 
            
            final BigDecimal temp = new BigDecimal(myShoppingCart.get(theItem).getQuantity());
            result = temp.multiply(theItem.getPrice());
        }
        
        return result;
    }
    
    /**
     * Clears this ShoppingCart.
     */
    public void clear() {
        
        myShoppingCart.clear();
    }
    
    /**
     * {@inheritDoc}
     * 
     * The string representation for this ShoppingCart which is formatted as follows:
     * <br>(quantity of item) (name of item) for (total price of item)
     * each item in the cart will be on a new line.
     */
    @Override
    public String toString() {
        
        final StringBuilder builder = new StringBuilder(128); // default initial size = 16
        final NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        
        for (Item key : myShoppingCart.keySet()) {
            builder.append(myShoppingCart.get(key).getQuantity());
            builder.append(" ");
            builder.append(key.getName());
            builder.append(" for ");
            
            //I did not know how to break this up properly over 2 lines.
            builder.append(nf.format(this.calculateTotalHelper(key).
                                     setScale(2, RoundingMode.HALF_EVEN)));
            builder.append("\n");
            
        }
        return builder.toString();
    }

}
