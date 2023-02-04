/**
 * @Author: Shivam Patel
 * @Andrew_ID: shpatel
 * @Course: 95-771 Data Structures and Algorithms for Information Processing
 * @Assignment_Number: Project 5
 */

import java.io.*;

public class BadCompressor {
    public static void main( String args[]) throws IOException {

        // Initialize input stream with input file stored in args[0]
        DataInputStream in =
                new DataInputStream(
                        new BufferedInputStream(
                                new FileInputStream(args[0])));

        // Initialize output stream with output file stored in args[1]
        DataOutputStream out =
                new DataOutputStream(
                        new BufferedOutputStream(
                                new FileOutputStream(args[1])));

        // Stores a single byte of input
        byte byteIn;

        // Byte array acting as a buffer for I/O operations
        byte[] buffer = new byte[3];

        // Flag to know if the buffer is half full
        boolean bufferHalfFull = false;

        try {

            // Until all bytes are read from the input file
            while(true) {

                // Source: CMU 90-771 - Data Structures and Algorithms - Project 5
                // Read one byte from input file
                byteIn = in.readByte();

                // Convert input byte to int
                int t = (char)(byteIn & 0xFF);

                // Source: https://mkyong.com/java/java-how-to-convert-a-byte-to-a-binary-string/#:~:text=In%20Java%2C%20we%20can%20use,argument%20and%20returns%20a%20String.
                // Stores the binary value of the integer t
                String binary = Integer.toBinaryString(t);

                // Source: https://stackoverflow.com/questions/388461/how-can-i-pad-a-string-in-java
                // Convert binary to 12 bits binary
                String binary12Bits = String.format("%12s", binary).replace(' ', '0');

                // If buffer is not half full
                if (!bufferHalfFull) {

                    // Update first and second index of buffer
                    buffer[0] = (byte) Integer.parseInt(binary12Bits.substring(0, 8),2);
                    buffer[1] = (byte) Integer.parseInt(binary12Bits.substring(8, 12) + "0000",2);

                    // Write first and second index of buffer to output file
                    out.writeByte(buffer[0]);
                    out.writeByte(buffer[1]);

                    // Update bufferHalfFull flag
                    bufferHalfFull = true;
                }
                // If buffer is half full
                else {

                    // Update third index of buffer
                    buffer[2] = (byte) Integer.parseInt(binary12Bits.substring(4, 12),2);

                    // Write third index of buffer to output file
                    out.writeByte(buffer[2]);

                    // Reset buffer
                    buffer[0] = Byte.parseByte("00000000");
                    buffer[1] = Byte.parseByte("00000000");
                    buffer[2] = Byte.parseByte("00000000");

                    // Update bufferHalfFull flag
                    bufferHalfFull = false;
                }
            }
        }
        // Handles end of file exception
        catch(EOFException e) {
            // Close input and output files
            in.close();
            out.close();
        }
    }
}
