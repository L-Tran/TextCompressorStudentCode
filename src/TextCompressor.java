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

    private static final int BITS_PER_CODE = 12; // Bits per code
    private static final int R = 128; // Number of input chars
    private static final int L = 4096; // Number of Codewords = 2^12
    private static final int EOF =  0x80; // EOF

    private static void compress() {
        // Read data into String text
        String text = BinaryStdIn.readString();

        // Initialize TST with ascii values
        TST prefixes = new TST();
        for (int i = 0; i < R; i++) {
            prefixes.insert(Character.toString((char) i), i);
        }

        // Count added codes
        int nextCode = EOF + 1;
        int index = 0;

        // Loop through text
        String prefix;
        while (index < text.length()) {
            // Prefix = longest coded word that matches text @ index
            prefix = prefixes.getLongestPrefix(text, index);
            // Write out that code
            BinaryStdOut.write(prefixes.lookup(prefix), BITS_PER_CODE);
            index += prefix.length();
            // If possible, look ahead to the next character & make sure codes are not used up
            if (index < text.length() && nextCode < L) {
                    // Append that character to prefix
                    // Associate prefix with the next code (if available)
                    prefixes.insert(prefix + text.charAt(index), nextCode);
                    nextCode++;
            }
        }
        // Write out EOF and close
        BinaryStdOut.write(EOF, BITS_PER_CODE);
        BinaryStdOut.close();
    }

    private static void expand() {

        int currentCode = BinaryStdIn.readInt(BITS_PER_CODE);
        int lookAhead = BinaryStdIn.readInt(BITS_PER_CODE);
        int nextCode = EOF + 1;

        // Initialize map with ascii
        String[] words = new String[L];
        for(int i = 0; i < R; i++) {
            words[i] = Character.toString((char) i);
        }

        // Go through entire compressed file
        while(lookAhead != EOF) {

            // Ensure # of codes are within budget
            if (nextCode < L) {
                // Edge case for repeated new code
                if (words[lookAhead] == null) {
                    words[nextCode] = words[currentCode] + words[currentCode].charAt(0);
                }
                // Add code
                else {
                    words[nextCode] = words[currentCode] + words[lookAhead].charAt(0);
                }
                nextCode++;
            }
            // Write out String for code & increment through compressed file
            BinaryStdOut.write(words[currentCode]);
            currentCode = lookAhead;
            lookAhead = BinaryStdIn.readInt(BITS_PER_CODE);
        }
        BinaryStdOut.write(words[currentCode]);
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
