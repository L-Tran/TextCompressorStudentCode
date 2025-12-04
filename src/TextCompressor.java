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

    private static void compress() {

        String s = BinaryStdIn.readString();
        int n = s.length();
        BinaryStdOut.write(n);
        String[] words;
        words = s.split(" ");

        String[] dict = new String[16];
        dict[0] = "the";
        dict[1] = "be";
        dict[2] = "to";
        dict[3] = "of";
        dict[4] = "and";
        dict[5] = "a";
        dict[6] = "in";
        dict[7] = "that";
        dict[8] = "have";
        dict[9] = "I";
        dict[10] = "you";
        dict[11] = "it";
        dict[12] = "not";
        dict[13] = "that";
        dict[14] = "and";

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 15; j++) {
                if(words[i].equals(dict[j])) {
                    BinaryStdOut.write(j, 4);
                    BinaryStdOut.write(" ");
                }
                BinaryStdOut.write(words[i]);
                BinaryStdOut.write(" ");
            }
        }
        BinaryStdOut.close();
    }

    private static void expand() {
        String s = BinaryStdIn.readString();
        int n = BinaryStdIn.readInt();
        String[] words;
        words = s.split(" ");

        for(int i = 0; i < n; i++) {
            if(words[i].equals("0")) {
                BinaryStdOut.write("the");
            }
            else if (words[i].equals("1")) {
                BinaryStdOut.write("be");
            }
            else if (words[i].equals("3")) {
                BinaryStdOut.write("to");
            }
            else if (words[i].equals("3")) {
                BinaryStdOut.write("of");
            }
            else if (words[i].equals("4")) {
                BinaryStdOut.write("and");
            }
            else if (words[i].equals("5")) {
                BinaryStdOut.write("a");
            }
            else if (words[i].equals("6")) {
                BinaryStdOut.write("in");
            }
            else if (words[i].equals("7")) {
                BinaryStdOut.write("that");
            }
            else if (words[i].equals("8")) {
                BinaryStdOut.write("have");
            }
            else if (words[i].equals("9")) {
                BinaryStdOut.write("I");
            }
            else if (words[i].equals("10")) {
                BinaryStdOut.write("you");
            }
            else if (words[i].equals("11")) {
                BinaryStdOut.write("it");
            }
            else if (words[i].equals("12")) {
                BinaryStdOut.write("not");
            }
            else if (words[i].equals("13")) {
                BinaryStdOut.write("that");
            }
            else if (words[i].equals("14")) {
                BinaryStdOut.write("and");
            }
            else {
                BinaryStdOut.write(words[i]);
            }
        }

    }

    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
