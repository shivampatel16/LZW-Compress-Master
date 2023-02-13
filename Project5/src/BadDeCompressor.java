/**
 * @Author: Shivam Patel
 * @Andrew_ID: shpatel
 * @Course: 95-771 Data Structures and Algorithms for Information Processing
 * @Assignment_Number: Project 5
 */

import java.io.*;

public class BadDeCompressor {
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

        // Flag to know if the 12 bit byte is half full
        boolean byteHalfFull = false;

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
                String binary1 = Integer.toBinaryString(t);

                // Convert binary1 to 8 bits binary
                String binary1_8Bits = String.format("%8s", binary1).replace(' ', '0');

                // If byte is not half full
                if (!byteHalfFull) {

                    // Read a byte from input file
                    byteIn = in.readByte();

                    // Convert byte to integer
                    t = (char)(byteIn & 0xFF);

                    // Stores the binary values of int t
                    String binary2 = Integer.toBinaryString(t);

                    // Source: https://stackoverflow.com/questions/388461/how-can-i-pad-a-string-in-java
                    // Convert binary2 to 8 bits binary
                    String binary2_8Bits = String.format("%8s", binary2).replace(' ', '0');

                    // Form 8 bits binary to be written to the output file
                    String binary8bits = binary1_8Bits.substring(4, 8) + binary2_8Bits.substring(0, 4);

                    // Write byte to output file
                    out.writeByte((byte) Integer.parseInt(binary8bits, 2));

                    // Update byteHalfFull flag
                    byteHalfFull = true;
                }
                // If byte is half full
                else {
                    // Write binary1_8Bits to output file
                    out.writeByte((byte) Integer.parseInt(binary1_8Bits, 2));

                    // Update byteHalfFull flag
                    byteHalfFull = false;
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
