//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.12.14 at 05:27:24 PM GMT 
//


package uk.ac.soton.ecs.forge.restws.representations;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Receipt complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Receipt">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="receiptID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="coffees" type="{}Coffee" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="beverages" type="{}Beverage" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="totalPrice" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Receipt", propOrder = {
    "receiptID",
    "coffees",
    "beverages",
    "totalPrice"
})
public class Receipt {

    protected int receiptID;
    protected List<Coffee> coffees;
    protected List<Beverage> beverages;
    protected double totalPrice;

    /**
     * Gets the value of the receiptID property.
     * 
     */
    public int getReceiptID() {
        return receiptID;
    }

    /**
     * Sets the value of the receiptID property.
     * 
     */
    public void setReceiptID(int value) {
        this.receiptID = value;
    }

    /**
     * Gets the value of the coffees property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the coffees property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCoffees().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Coffee }
     * 
     * 
     */
    public List<Coffee> getCoffees() {
        if (coffees == null) {
            coffees = new ArrayList<Coffee>();
        }
        return this.coffees;
    }

    /**
     * Gets the value of the beverages property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the beverages property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBeverages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Beverage }
     * 
     * 
     */
    public List<Beverage> getBeverages() {
        if (beverages == null) {
            beverages = new ArrayList<Beverage>();
        }
        return this.beverages;
    }

    /**
     * Gets the value of the totalPrice property.
     * 
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the value of the totalPrice property.
     * 
     */
    public void setTotalPrice(double value) {
        this.totalPrice = value;
    }

}
