/******************************************************************************
 *  Compilation:  javac TextCompressor.java
 *  Execution:    java TextCompressor - < input.txt   (compress)
 *  Execution:    java TextCompressor + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   abra.txt
 *                jabberwocky.txt
 *                shakespeare.txt
 *                virus.txt
 *
 *  % java DumpBinary 0 < abra.txt
 *  136 bits
 *
 *  % java TextCompressor - < abra.txt | java DumpBinary 0
 *  104 bits    (when using 8-bit codes)
 *
 *  % java DumpBinary 0 < alice.txt
 *  1104064 bits
 *  % java TextCompressor - < alice.txt | java DumpBinary 0
 *  480760 bits
 *  = 43.54% compression ratio!
 ******************************************************************************/

/**
 *  The {@code TextCompressor} class provides static methods for compressing
 *  and expanding natural language through textfile input.
 *
 *  @author Zach Blick, Logan Tran
 */
public class TextCompressor {

    private static final int R = 256; // Number of input chars
    private static final int L = 4096; // Number of Codewords = 2^12
    private static final int WIDTH = 12; // Codeword width
    private static final int EOF =  0x80; // EOF

    private static void compress() {
        // Read data into String text
        String text = BinaryStdIn.readString();
        TST prefixes = new TST();
        int index = 0;
        String prefix = ;
        while (index < text.length()) {
            // Prefix = longest coded word that matches text @ index
            // Write out that code
            // If possible, look ahead to the next character
            // Append that character to prefix
            // Associate prefix with the next code (if available)
            // Index += prefix.length
        }
        // Write out EOF and close
        BinaryStdOut.write(EOF);
        BinaryStdOut.close();
    }

    private static void expand() {

    }

    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
