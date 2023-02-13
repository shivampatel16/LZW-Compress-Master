/**
 * @Author: Shivam Patel
 * @Andrew_ID: shpatel
 * @Course: 95-771 Data Structures and Algorithms for Information Processing
 * @Assignment_Number: Project 5
 */

public class LinkedListNode
{
    // Stores the key part in the LinkedListNode
    private String key;

    // Stores the value part in the LinkedListNode
    private int value;

    // Stores the reference to the next LinkedListNode
    private LinkedListNode link;



    // Constructor to initialize the instance variables of the class
    public LinkedListNode(String initialKey, int initialValue, LinkedListNode initialLink)
    {
        key = initialKey;
        value = initialValue;
        link = initialLink;
    }


    /**
     * Function to get the value in the LinkedListNode
     * @return value in the LinkedListNode
     */
    public int getValue() {
        return value;
    }

    /**
     * Function to get the key in the LinkedListNode
     * @return key in the LinkedListNode
     */
    public String getKey() {
        return key;
    }

    /**
     * Function to get the reference of the next LinkedListNode
     * @return reference of the next LinkedListNode
     */
    public LinkedListNode getLink( )
    {
        return link;
    }

    /**
     * Function to set key of the LinkedListNode
     * @param key key of the LinkedListNode
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Function to set the value of the LinkedListNode
     * @param value value of the LinkedListNode
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Function to set the link (reference to the next LinkedListNode)
     * @param newLink reference to the next LinkedListNode)
     */
    public void setLink(LinkedListNode newLink)
    {
        link = newLink;
    }
}
