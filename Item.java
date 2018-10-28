/*
 * TCSS 305 Autumn 2018
 * Assignment 2
 */

package model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

/**
 * This class represents a single Item.
 * 
 * @author Trevor Nichols
 * @version 1.0
 */
public final class Item {
    
    /**A default bulk quantity to use when one is not provided - 0 quantity. */
    private static final int DEFAULT_BULK_QUANTITY = 0;
    
    /**The name of this Item. */
    private String myItemName;
    
    /**The price of this Item. */
    private BigDecimal myPrice;
    
    /**The bulk quantity of this Item. */
    private int myBulkQuantity; //Note that some items may not have a bulk quantity.
    
    /**The bulk price of this Item. */
    private BigDecimal myBulkPrice; //Note that some items may not have a bulk price.
   
    /**
     * Constructs a new Item with a given name and price.
     * 
     * @param theName the name assigned to this Item
     * @param thePrice the price assigned to this Item
     */
    public Item(final String theName, final BigDecimal thePrice) {
        
        this(theName, thePrice, DEFAULT_BULK_QUANTITY, BigDecimal.ZERO);
    }

    /**
     * Constructs a new Item with a given a name, a price, the bulk quantity, 
     * and a bulk price.
     * 
     * @param theName the name assigned to this Item.
     * @param thePrice the price assigned to this Item.
     * @param theBulkQuantity the bulk quantity assigned to this Item.
     * @param theBulkPrice the bulk price assigned to this Item.
     */
    public Item(final String theName, final BigDecimal thePrice, final int theBulkQuantity,
                final BigDecimal theBulkPrice) {
        
        myItemName = theName;
        myBulkQuantity = theBulkQuantity;
        
        //Note that we do not need to make a defensive copy of BigDecimal objects because
        //they are immutable.
        myPrice = thePrice; 
        myBulkPrice = theBulkPrice;
    }

    /**
     * Returns the name of this Item.
     * 
     * @return the name of this Item.
     */
    public String getName() {
        
        return myItemName;
    }
    
    /**
     * Returns the price of this Item.
     * 
     * @return the price of this Item.
     */
    public BigDecimal getPrice() {
        
        return myPrice;
    }
    
    /**
     * Returns the bulk quantity of this Item.
     * 
     * @return the bulk quantity of this Item.
     */
    public int getBulkQuantity() {
        
        return myBulkQuantity;
    }
    
    /**
     * Returns the bulk price of this Item.
     * 
     * @return the bulk price of this Item.
     */
    public BigDecimal getBulkPrice() {
        
        return myBulkPrice;
    }

    /**
     * Returns true if the item has bulk pricing and false otherwise.
     * 
     * @return true if the item has bulk pricing and false otherwise
     */
    public boolean isBulk() {
        
        return myBulkQuantity != DEFAULT_BULK_QUANTITY 
                && myBulkPrice.compareTo(BigDecimal.ZERO) != 0;
    }
    
    /**
     * {@inheritDoc}
     * 
     * The string representation for this Item will be formatted one of two ways.
     * 
     * without bulk pricing it will be formated as follows
     * <br>[Item Name], [Price of Item]
     * 
     * with bulk pricing the formating will be the following
     * <br>[Item Name], [Price of Item] ([Bulk Quantity] for [Bulk Price of Item])
     */
    @Override
    public String toString() {
    
        final StringBuilder builder = new StringBuilder(128); // default initial size = 16
        final NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        
        builder.append(myItemName);
        builder.append(", ");
        builder.append(nf.format(myPrice));
        
        if (this.isBulk()) {
            builder.append(" (");
            builder.append(myBulkQuantity);
            builder.append(" for ");
            builder.append(nf.format(myBulkPrice));
            builder.append(")");
        }
    
        return builder.toString();
    }

    /**
     * {@inheritDoc}
     * 
     * This method compares the name, price, bulk quantity, and bulk price to determine
     * the quality of 2 Item objects. All four fields must be equal in order for two Items
     * to be consider equal.
     */
    @Override
    public boolean equals(final Object theOther) {
    
        boolean result = false;
    
        if ((theOther != null) && (theOther.getClass() == this.getClass())) {
    
            final Item otherItem = (Item) theOther;
    
            result = myItemName.equals(otherItem.myItemName) 
                     && myPrice.compareTo(otherItem.myPrice) == 0
                     && myBulkQuantity == otherItem.myBulkQuantity 
                     && myBulkPrice.compareTo(otherItem.myBulkPrice) == 0;
        }
        
        return result;
    }


    @Override
    public int hashCode() {
        
        return Objects.hash(myItemName, myPrice, myBulkQuantity, myBulkPrice);
    }

}
