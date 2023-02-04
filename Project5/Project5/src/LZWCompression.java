/**
 * @Author: Shivam Patel
 * @Andrew_ID: shpatel
 * @Course: 95-771 Data Structures and Algorithms for Information Processing
 * @Assignment_Number: Project 5
 */

import java.io.*;
import java.util.Objects;

public class LZWCompression {

    /**
     * Function to perform LZW compression
     * @param args Input arguments to the main file
     * @throws IOException Exception during IO operations
     */
    void lzw_compression(String[] args) throws IOException {
        System.out.println("Running compression");

        // Defines data input stream
        DataInputStream in;

        // Defines data output stream
        DataOutputStream out;

        // Stores the count of bytes read and written to input and output files respectively
        int bytesRead = 0;
        int bytesWritten = 0;

        // If user did not request to display bytesRead and bytesWritten
        if (!Objects.equals(args[1], "-v")) {

            // Initialize input and output stream with input and output files
            // with appropriate argument values
            in = new DataInputStream(
                    new BufferedInputStream(
                            new FileInputStream(args[1])));
            out = new DataOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(args[2])));
        }
        // If user did request to display bytesRead and bytesWritten
        else {

            // Initialize input and output stream with input and output files
            // with appropriate argument values
            in = new DataInputStream(
                    new BufferedInputStream(
                            new FileInputStream(args[2])));
            out = new DataOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(args[3])));
        }

        // Initialize hash table
        HashTable hashTable = new HashTable();

        // Stores value of String s
        String s = "";

        // Stores input byte
        byte byteIn;

        // Stores value of c
        String charIn;

        // Byte array acting as a buffer for I/O operations
        byte[] buffer = new byte[3];

        // Flag to know if the buffer is half full
        boolean bufferHalfFull = false;

        // Stores first four bits of buffer 1
        String firstFourBitsForBuffer1 = "";
        int t;

        try {
            // Source: CMU 90-771 - Data Structures and Algorithms - Project 5
            // Read byte from input
            byteIn = in.readByte();

            // Update count of bytes read
            bytesRead = bytesRead + 1;
            //int t = (char)(byteIn & 0xFF);

            // Update value of c
            charIn = "" + (char) (byteIn & 0xFF);

            // s = c
            s = charIn;

            // while(any input left)
            while(true) {

                // read(character c);
                byteIn = in.readByte();

                // Update count of bytes read
                bytesRead = bytesRead + 1;

                // Update value of c
                charIn = "" + (char) (byteIn & 0xFF);

                // if(s + c is in the table)
                if (hashTable.isInTable(s + charIn)) {
                    s = s + charIn;
                }
                else {
                    // output codeword(s);
                    t = hashTable.getCodeWord(s);

                    // Source: https://mkyong.com/java/java-how-to-convert-a-byte-to-a-binary-string/#:~:text=In%20Java%2C%20we%20can%20use,argument%20and%20returns%20a%20String.
                    String binary = Integer.toBinaryString(t);

                    // Source: https://stackoverflow.com/questions/388461/how-can-i-pad-a-string-in-java
                    String binary12Bits = String.format("%12s", binary).replace(' ', '0');

                    // If buffer is not half full
                    if (!bufferHalfFull) {
                        // Update buffer index 0
                        buffer[0] = (byte) Integer.parseInt(binary12Bits.substring(0, 8),2);
                        firstFourBitsForBuffer1 = binary12Bits.substring(8, 12);

                        // Update flag
                        bufferHalfFull = true;
                    }
                    // If buffer is not half full
                    else {
                        // Update buffer index 1 and 2
                        buffer[1] = (byte) Integer.parseInt((firstFourBitsForBuffer1 + binary12Bits.substring(0, 4)),2);
                        buffer[2] = (byte) Integer.parseInt(binary12Bits.substring(4, 12),2);

                        // Write buffer to output file
                        out.writeByte(buffer[0]);
                        out.writeByte(buffer[1]);
                        out.writeByte(buffer[2]);
                        bytesWritten = bytesWritten + 3;

                        // Reset buffer
                        buffer[0] = Byte.parseByte("00000000");
                        buffer[1] = Byte.parseByte("00000000");
                        buffer[2] = Byte.parseByte("00000000");

                        // Update flag
                        bufferHalfFull = false;
                    }

                    // Enter s + c into table
                    hashTable.enterInTable(s + charIn);

                    // If max value of code words is greater than 2^12 - 1
                    if (hashTable.getCodeWordCount() + 1 > (Math.pow(2, 12) - 1)) {

                        // Create a new hash table
                        hashTable = new HashTable();
                    }

                    // s = c
                    s = charIn;
                }
            }
        }
        // Handle end of file exception
        catch(EOFException e) {

            // output codeword(s);
            t = hashTable.getCodeWord(s);

            // Source: https://mkyong.com/java/java-how-to-convert-a-byte-to-a-binary-string/#:~:text=In%20Java%2C%20we%20can%20use,argument%20and%20returns%20a%20String.
            String binary = Integer.toBinaryString(t);

            // Source: https://stackoverflow.com/questions/388461/how-can-i-pad-a-string-in-java
            String binary12Bits = String.format("%12s", binary).replace(' ', '0');

            // If buffer is not half full
            if (!bufferHalfFull) {
                // Update first and second index of buffer
                buffer[0] = (byte) Integer.parseInt(binary12Bits.substring(0, 8),2);
                buffer[1] = (byte) Integer.parseInt(binary12Bits.substring(8, 12) + "0000",2);

                // Write first and second index of buffer to output file
                out.writeByte(buffer[0]);
                out.writeByte(buffer[1]);

                // Update total number of bytes written in output
                bytesWritten = bytesWritten + 2;
            }
            // If buffer is half full
            else {
                // Update first and second index of buffer
                buffer[1] = (byte) Integer.parseInt(firstFourBitsForBuffer1 + binary12Bits.substring(0, 4),2);
                buffer[2] = (byte) Integer.parseInt(binary12Bits.substring(4, 12),2);

                // Write all index of buffer to output file
                out.writeByte(buffer[0]);
                out.writeByte(buffer[1]);
                out.writeByte(buffer[2]);

                // Update total number of bytes written in output
                bytesWritten = bytesWritten + 3;
            }
            // Close input and output files
            in.close();
            out.close();
        }
        System.out.println("Compression completed!");

        // If the user requested to display the bytes read and bytes written, do so
        if (Objects.equals(args[1], "-v")) {
            System.out.println("bytes read = " + bytesRead + ", bytes written = " + bytesWritten);
        }
    }

    /**
     * Function to perform LZW decompression
     * @param args Input arguments to the main file
     * @throws IOException Exception during IO operations
     */
    void lzw_decompression(String[] args) throws IOException {
        System.out.println("Running decompression");

        // Defines data input stream
        DataInputStream in;

        // Defines data output stream
        DataOutputStream out;

        // Stores the count of bytes read and written to input and output files respectively
        int bytesRead = 0;
        int bytesWritten = 0;

        // If user did not request to display bytesRead and bytesWritten
        if (!Objects.equals(args[1], "-v")) {

            // Initialize input and output stream with input and output files
            // with appropriate argument values
            in = new DataInputStream(
                    new BufferedInputStream(
                            new FileInputStream(args[1])));
            out = new DataOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(args[2])));
        }
        // If user did request to display bytesRead and bytesWritten
        else {

            // Initialize input and output stream with input and output files
            // with appropriate argument values
            in = new DataInputStream(
                    new BufferedInputStream(
                            new FileInputStream(args[2])));
            out = new DataOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(args[3])));
        }

        // Create an array of strings for the hash table
        String[] arrayHashTable = new String[4096];

        // Populate first 256 indexes of array
        for (int i = 0; i < 256; i++) {
            arrayHashTable[i] = "" + (char) i;
        }

        // Stores the index until which the array is fixed
        int arrayHashTableFilledUntilIndex = 255;

        // Stores bytes from input stream
        byte byteIn1;
        byte byteIn2;

        // Flag to know if byte is half full
        boolean byteHalfFull;

        // Stores the first four bits when the byte is half full
        String firstFourBitsWhenByteHalfFull;


        // read(priorcodeword) and output its corresponding character;
        try {
            // Source: CMU 90-771 - Data Structures and Algorithms - Project 5
            // Read first two bytes from input file
            byteIn1 = in.readByte();
            byteIn2 = in.readByte();

            // Update count
            bytesRead = bytesRead + 2;

            // Stores corresponding int for input bytes
            int t1 = (char) ((char) byteIn1 & 0xFF);
            int t2 = (char) ((char) byteIn2 & 0xFF);

            // Source: https://mkyong.com/java/java-how-to-convert-a-byte-to-a-binary-string/#:~:text=In%20Java%2C%20we%20can%20use,argument%20and%20returns%20a%20String.
            String binary1 = Integer.toBinaryString(t1);

            // Generate 8 bits binary for binary1
            String binary1_8Bits = String.format("%8s", binary1).replace(' ', '0');
            if (binary1_8Bits.length() > 8) {
                binary1_8Bits = binary1_8Bits.substring(binary1_8Bits.length() - 8);
            }

            // Generate 8 bits binary for binary2
            String binary2 = Integer.toBinaryString(t2);
            String binary2_8Bits = String.format("%8s", binary2).replace(' ', '0');
            if (binary2_8Bits.length() > 8) {
                binary2_8Bits = binary2_8Bits.substring(binary2_8Bits.length() - 8);
            }

            // Stores priorcodeword in 12 bits
            String priorcodeword_12Bits = binary1_8Bits.substring(0, 8) + binary2_8Bits.substring(0, 4);

            // Stores priorcode word in int
            int priorcodeword = Integer.parseInt(priorcodeword_12Bits, 2);

            // Stores corresponding character of priorcodeword
            String correspondingCharacter = arrayHashTable[priorcodeword];

            //out.writeChars(correspondingCharacter);
            out.writeBytes(correspondingCharacter);

            // Update count
            bytesWritten = bytesWritten + correspondingCharacter.length();

            // Set first four bits for next byte
            firstFourBitsWhenByteHalfFull = binary2_8Bits.substring(4, 8);

            // Update flag
            byteHalfFull = true;


            // while(codewords are still left to be input)
            while (true) {

                // Stores int value of codeword
                int codeword;

                // Read byte from input file
                byteIn1 = in.readByte();

                // Update count
                bytesRead = bytesRead + 1;

                // Convert input byte to int
                t1 = (char) ((char) byteIn1 & 0xFF);

                // Convert t1 to 8 bits in binary
                binary1 = Integer.toBinaryString(t1);
                binary1_8Bits = String.format("%8s", binary1).replace(' ', '0');
                if (binary1_8Bits.length() > 8) {
                    binary1_8Bits = binary1_8Bits.substring(binary1_8Bits.length() - 8);
                }

                // If byte is not half full
                if (!byteHalfFull) {

                    // Read next byte from input file
                    byteIn2 = in.readByte();

                    // Update count
                    bytesRead = bytesRead + 1;

                    // Convert input byte to int
                    t2 = (char) ((char) byteIn2 & 0xFF);

                    // Convert t2 to 8 bits in binary
                    binary2 = Integer.toBinaryString(t2);
                    binary2_8Bits = String.format("%8s", binary2).replace(' ', '0');
                    if (binary2_8Bits.length() > 8) {
                        binary2_8Bits = binary2_8Bits.substring(binary2_8Bits.length() - 8);
                    }

                    // Stores codeword in 12 bits
                    String codeword_12Bits = binary1_8Bits.substring(0, 8) + binary2_8Bits.substring(0, 4);

                    // Update first four bits for next byte
                    firstFourBitsWhenByteHalfFull = binary2_8Bits.substring(4, 8);

                    // read(codeword);
                    codeword = Integer.parseInt(codeword_12Bits, 2);

                    // Update flag
                    byteHalfFull = true;
                }
                // If byte is half full
                else {
                    // Stores codeword in 12 bits
                    String codeword_12Bits = firstFourBitsWhenByteHalfFull + binary1_8Bits.substring(0, 8);

                    // read(codeword);
                    codeword = Integer.parseInt(codeword_12Bits, 2);

                    // Update flag
                    byteHalfFull = false;
                }

                // Stores the string to enter into the hash table
                String stringToEnterInArrayHashTable;

                // if (codeword not in the table)
                if (codeword > arrayHashTableFilledUntilIndex) {

                    // enter string(priorcodeword) + firstChar(string(priorcodeword)) into the table;
                    stringToEnterInArrayHashTable = arrayHashTable[priorcodeword]
                            + arrayHashTable[priorcodeword].charAt(0);

                    arrayHashTableFilledUntilIndex = arrayHashTableFilledUntilIndex + 1;

                    // If index is greater than array size
                    if (arrayHashTableFilledUntilIndex >= 4096) {

                        // Create new hash table
                        arrayHashTable = new String[4096];
                        for (int i = 0; i < 256; i++) {
                            arrayHashTable[i] = "" + (char) i;
                        }
                        arrayHashTableFilledUntilIndex = 255;
                        arrayHashTableFilledUntilIndex = arrayHashTableFilledUntilIndex + 1;
                    }

                    // Add string to hash table
                    arrayHashTable[arrayHashTableFilledUntilIndex] = stringToEnterInArrayHashTable;

                    // output string(priorcodeword) + firstChar(string(priorcodeword));
                    out.writeBytes(stringToEnterInArrayHashTable);
                    bytesWritten = bytesWritten + stringToEnterInArrayHashTable.length();
                }
                // if (codeword in the table)
                else {

                    // enter string(priorcodeword) + firstChar(string(codeword)) into the table;
                    stringToEnterInArrayHashTable = arrayHashTable[priorcodeword]
                            + arrayHashTable[codeword].charAt(0);


                    // Update count
                    arrayHashTableFilledUntilIndex = arrayHashTableFilledUntilIndex + 1;

                    // If index is greater than array size
                    if (arrayHashTableFilledUntilIndex >= 4096) {

                        // Create new hash table
                        arrayHashTable = new String[4096];
                        for (int i = 0; i < 256; i++) {
                            arrayHashTable[i] = "" + (char) i;
                        }
                        arrayHashTableFilledUntilIndex = 255;
                        arrayHashTableFilledUntilIndex = arrayHashTableFilledUntilIndex + 1;
                    }

                    // Add string to hash table
                    arrayHashTable[arrayHashTableFilledUntilIndex] = stringToEnterInArrayHashTable;

                    // output string(codeword);
                    out.writeBytes(arrayHashTable[codeword]);
                    bytesWritten = bytesWritten + arrayHashTable[codeword].length();
                }
                priorcodeword = codeword;
            }
        }
        // Handle end of file exception
        catch(EOFException e) {
            // Close input and output files
            in.close();
            out.close();
        }
        System.out.println("Decompression completed!");

        // If the user requested to display the bytes read and bytes written, do so
        if (Objects.equals(args[1], "-v")) {
            System.out.println("bytes read = " + bytesRead + ", bytes written = " + bytesWritten);
        }
    }

    public static void main(String[] args) throws IOException {

        /*
            Qn: Within the comments of the main routine of LZWCompression, describe how
            your program is working on ASCII files and on binary files. Explain whether
            your program works for both cases and state the degree of compression
            obtained on words.html, CrimeLatLonXY1990.csv, and 01_Overview.mp4.
         */

        /*
            An: The LZW compression and decompression works on both ASCII and binary files. The
            details of their working is shown in the comments in the Java files submitted in
            this project.

            Degree of compression:

            1. words.html
            Number of bytes in words.html = 2,493,547 bytes
            Number of bytes in words-compressed.html = 1,070,454 bytes

            Degree of compression = 2,493,547 / 1,070,454 = 2.33 (Some compression)

            2. CrimeLatLonXY1990.csv
            Number of bytes in CrimeLatLonXY1990.csv = 2,608,940 bytes
            Number of bytes in CrimeLatLonXY1990-compressed.csv = 1,283,751 bytes

            Degree of compression = 2,608,940 / 1,283,751 = 2.03 (Some compression)

            3. 01_Overview.mp4
            Number of bytes in 01_Overview.mp4 = 25,008,838 bytes
            Number of bytes in 01_Overview-compressed.mp4 = 33,773,775 bytes

            Degree of compression = 25,008,838 / 33,773,775 = 0.74 (Expansion rather than compression)
         */

        // Create new object of the LZWCompression class
        LZWCompression lzwCompression = new LZWCompression();

        // If user requested for compression
        if (Objects.equals(args[0], "-c")) {

            // Call compression logic
            lzwCompression.lzw_compression(args);
        }
        // If user requested for decompression
        else if (Objects.equals(args[0], "-d")) {

            // Call decompression logic
            lzwCompression.lzw_decompression(args);
        }
    }
}