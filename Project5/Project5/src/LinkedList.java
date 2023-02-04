/**
 * @Author: Shivam Patel
 * @Andrew_ID: shpatel
 * @Course: 95-771 Data Structures and Algorithms for Information Processing
 * @Assignment_Number: Project 5
 */

public class LinkedList {

    // Stores the head of the linked list
    private LinkedListNode head;

    // Stores the tail of the linked list
    private LinkedListNode tail;

    // Stores the current node in traversal of the linked list
    private LinkedListNode currentNode;

    // Constructor to initialise the instance variables of the class
    public LinkedList()
    {
        head = null;
        tail = null;
        currentNode = null;
    }


    /**
     * Function to add a node at the end of the linked list
     * @param key Stores the key of the LinkedListNode
     * @param value Stores the value of the LinkedListNode
     */
    public void addAtEndNode(String key, int value)
    {
        // Create a new LinkedListNode to be added to the linked list
        LinkedListNode newNode = new LinkedListNode(key, value, null);

        // If the linked list is empty
        if (head == null && tail == null)
        {
            // Point head to the newNode
            head = newNode;
        }
        // If there are some elements in the LinkedList
        else
        {
            // Add the newNode to the end of the linked list
            tail.setLink(newNode);
        }
        // Update tail to point to the newNode
        tail = newNode;
    }

    /**
     * Function to get the currentNode in the LinkedList during its traversal
     * @return currentNode in the LinkedList during its traversal
     */
    public LinkedListNode getCurrentNode() {
        return currentNode;
    }

    /**
     * Function to get the head of the LinkedList
     * @return head of the LinkedList
     */
    public LinkedListNode getHead() {
        return head;
    }

    /**
     * Function to get the tail of the LinkedList
     * @return tail of the LinkedList
     */
    public LinkedListNode getTail() {
        return tail;
    }

    /**
     * Function to set the currentNode in the LinkedList during its traversal
     * @param currentNode currentNode in the LinkedList during its traversal
     */
    public void setCurrentNode(LinkedListNode currentNode) {
        this.currentNode = currentNode;
    }

    /**
     * Function to set the head of the LinkedList
     * @param head Head of the LinkedList
     */
    public void setHead(LinkedListNode head) {
        this.head = head;
    }

    /**
     * Function to set the tail of the LinkedList
     * @param tail Tail of the LinkedList
     */
    public void setTail(LinkedListNode tail) {
        this.tail = tail;
    }
}

