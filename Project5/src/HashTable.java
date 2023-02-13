/**
 * @Author: Shivam Patel
 * @Andrew_ID: shpatel
 * @Course: 95-771 Data Structures and Algorithms for Information Processing
 * @Assignment_Number: Project 5
 */

import java.util.ArrayList;

public class HashTable {

    // Stores an array list of LinkedList (basic structure of the HashTable)
    public ArrayList<LinkedList> arrayList;

    // Stores the count of the total codeword in the hash table
    private int codeWordCount = -1;

    /**
     * Function to get the value of code word count
     * @return Value of code word count
     */
    public int getCodeWordCount() {
        return codeWordCount;
    }

    /**
     * Constructor to initialize the hash table with ASCII characters from 0 to 255
     */
    public HashTable() {

        // Initialise arraylist with the required size
        arrayList = new ArrayList<>(127);

        // For each element in the arraylist, store a new, empty linked list
        for (int i = 0; i < 127; i++) {
            arrayList.add(new LinkedList());
        }

        // Populate the hash table with ASCII characters from 0 to 255
        for (int i = 0; i < 256; i++) {

            // Update total codeword in the hash table
            codeWordCount = i;

            // Update values of key and value
            String key = "" + (char) i;
            int value = i;

            // Stores the index in the arraylist where the key:value pair will be stored
            int index = Math.abs(key.hashCode()) % 127;

            // Add key:value pair at the end of the linked list at the given index in the arraylist
            arrayList.get(index).addAtEndNode(key, value);
        }
    }

    /**
     * Function to know if String s is in the hash table
     * @param s String s
     * @return Boolean value stating if String s is present in the hash table or not
     */
    boolean isInTable(String s) {

        // Find probable index in the hash table
        int probableIndex = Math.abs(s.hashCode()) % 127;

        // Get linked list stored at the index
        LinkedList linkedListAtIndex = arrayList.get(probableIndex);

        // Loop over the linked list and check if String s is present as one of the keys in the node
        for (linkedListAtIndex.setCurrentNode(linkedListAtIndex.getHead()); linkedListAtIndex.getCurrentNode() != null; linkedListAtIndex.setCurrentNode(linkedListAtIndex.getCurrentNode().getLink()))
        {
            // If String s is present as one of the keys in the node
            if (s.equals(linkedListAtIndex.getCurrentNode().getKey())) {
                return true;
            }
        }
        // If String s is not present as one of the keys in the node
        return false;
    }

    /**
     * Function to get the code word corresponding to String s
     * @param s String whose code word is to be found
     * @return code word corresponding to String s
     */
    int getCodeWord(String s) {
        // Get index of String s in the hash table
        int actualIndex = Math.abs(s.hashCode()) % 127;

        // Stores the codeword that corresponds to String s in the hash table
        int codeWord = 0;

        // Stores linked list corresponding to where String s is stored as key
        LinkedList linkedListAtIndex = arrayList.get(actualIndex);

        // Loop over every node in the linked list
        for (linkedListAtIndex.setCurrentNode(linkedListAtIndex.getHead()); linkedListAtIndex.getCurrentNode() != null; linkedListAtIndex.setCurrentNode(linkedListAtIndex.getCurrentNode().getLink()))
        {
            // If the key of the node matches String s
            if (s.equals(linkedListAtIndex.getCurrentNode().getKey())) {

                // Update code word with the corresponding value of the node
                codeWord = linkedListAtIndex.getCurrentNode().getValue();
            }
        }
        // Return codeword found for String s
        return codeWord;
    }

    /**
     * Function to enter String (s + c) in the table
     * @param s_plus_c String to the inserted into the table
     */
    void enterInTable(String s_plus_c) {

        // Increment codeWodeCount by 1
        codeWordCount = codeWordCount + 1;

        //String key = "" + (char) codeWordCount;

        // Stores the key of the node
        String key = s_plus_c;

        // Stores the value of the node
        int value = codeWordCount;

        // Find the index in the arraylist where the key:value pair is to be stored
        int index = Math.abs(key.hashCode()) % 127;

        // Add string (s + c) in the hash table along with their value (which would be its codeword)
        arrayList.get(index).addAtEndNode(key, value);
    }
}
