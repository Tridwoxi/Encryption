
public class RoundFunction {
    // For testing; to be deleted later.
    public static void main(String[] args) {
        String input = "10000000000000000000000000000000";
        String key = "00000000000000000000000000000000";
        String result = f_function(input, key);
        System.out.println(result);
    }

    public static void processStringInChunks(String input, String key) {
        int chunkSize = 8;
        int length = input.length();

        for (int i = 0; i < length; i += chunkSize) {
            // Extract 8-character chunk or pad if needed
            String chunk = input.substring(i, Math.min(i + chunkSize, length));

            // Pad the chunk if it's less than 8 characters
            if (chunk.length() < chunkSize) {
                chunk = String.format("%-8s", chunk); // Pads with spaces
            }

            // Call the round_function() on the chunk
            round_function(chunk, key);
        }
    }

    /**
        The function for a round as given by the Rijndael model.

        @param input    represents the original plaintext on the first itteration, or the progressively more jumbled output of the round function as steps increase
        @param key      represents the step-n first 4 characters of the input key given from the Transform generator created by a wonderful subcontractor of DacatDeveloplment

        @return

     */
    //note for Darryl; I cannot create a full 10 round code without the transformer parameter,
    //so I will do so when you finish that

    public static String round_function(String input, String key) {
        String left = input.substring(0, 31);
        String right = input.substring(32, 64);
        String left1 = right;
        right = exclusive_or(left, f_function(right, key));
        input = left1 + right;
        return input;
    }

    /**
        The Rijndael f function {@code f}. Accepts part of the codeblock and a key. XORs using {@link exclusive_or}, substitues each byte using {@link substitute}, and permutes using {@link permute} as part of the encryption process.
    
        @param right_input    part of the codeblock, binary string of length 32
        @param key_n      the key used for salting, binary string of length 32
        @return         a funny amalgamation of the input and key
     */
    public static String f_function(String right_input, String key_n) {
        String result;
        result = exclusive_or(right_input, key_n);
        result = substitute(result);
        result = permute(result);
        return result;
    }

    /**
        Permutes a binary string of length 32 by looking up positions in {@link PERMUTATION_TABLE}.
    
        @param input    part of the codeblock, binary string of length 32
        @return         rearranged copy of the input
     */
    public static String permute(String input) {
        String result = "";
        for (int i = 0; i < input.length(); i++) {
            result += input.charAt(PERMUTATION_TABLE[i] - 1); // ptable is 1 indexed
        }
        return result;
    }

    /** 
        Substiutes each chunk of 8 digits of a binary string by looking it up in {@link SUBSTITUTION_BOX}. Returns a copy. 
    
        @param input    part of the codeblock, binary string with length a multiple of 8
        @return         substitued copy of input 
     */
    public static String substitute(String input) {
        String result = "";
        for (int i = 0; i < input.length(); i += 8) {
            int index = Integer.parseInt(input.substring(i, i + 8), 2);
            result += SUBSTITUTION_BOX[index];
        }
        return result;
    }

    /**
        Exclusive OR for binary strings. Arguments must be of same length. 
    
        @param string1  binary string
        @param string2  binary string
        @return         exclusive OR of inputs
     */
    public static String exclusive_or(String string1, String string2) {
        String result = "";
        for (int index = 0; index < string1.length(); index++) {
            result += string1.charAt(index) == string2.charAt(index) ? "0" : "1";
        }
        return result;
    }

    // @formatter:off
    public static final int[] PERMUTATION_TABLE = {16, 7, 30, 11, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 20, 6, 22, 21, 4, 25};
    
    public static final String[] SUBSTITUTION_BOX = {"01100011", "01111100", "01110111", "01111011", "11110010", "01101011", "01101111", "11000101", "00110000", "00000001", "01100111", "00101011", "11111110", "11010111", "10101011", "01110110", "11001010", "10000010", "11001001", "01111101", "11111010", "01011001", "01000111", "11110000", "10101101", "11010100", "10100010", "10101111", "10011100", "10100100", "01110010", "11000000", "10110111", "11111101", "10010011", "00100110", "00110110", "00111111", "11110111", "11001100", "00110100", "10100101", "11100101", "11110001", "01110001", "11011000", "00110001", "00010101", "00000100", "11000111", "00100011", "11000011", "00011000", "10010110", "00000101", "10011010", "00000111", "00010010", "10000000", "11100010", "11101011", "00100111", "10110010", "01110101", "00001001", "10000011", "00101100", "00011010", "00011011", "01101110", "01011010", "10100000", "01010010", "00111011", "11010110", "10110011", "00101001", "11100011", "00101111", "10000100", "01010011", "11010001", "00000000", "11101101", "00100000", "11111100", "10110001", "01011011", "01101010", "11001011", "10111110", "00111001", "01001010", "01001100", "01011000", "11001111", "11010000", "11101111", "10101010", "11111011", "01000011", "01001101", "00110011", "10000101", "01000101", "11111001", "00000010", "01111111", "01010000", "00111100", "10011111", "10101000", "01010001", "10100011", "01000000", "10001111", "10010010", "10011101", "00111000", "11110101", "10111100", "10110110", "11011010", "00100001", "00010000", "11111111", "11110011", "11010010", "11001101", "00001100", "00010011", "11101100", "01011111", "10010111", "01000100", "00010111", "11000100", "10100111", "01111110", "00111101", "01100100", "01011101", "00011001", "01110011", "01100000", "10000001", "01001111", "11011100", "00100010", "00101010", "10010000", "10001000", "01000110", "11101110", "10111000", "00010100", "11011110", "01011110", "00001011", "11011011", "11100000", "00110010", "00111010", "00001010", "01001001", "00000110", "00100100", "01011100", "11000010", "11010011", "10101100", "01100010", "10010001", "10010101", "11100100", "01111001", "11100111", "11001000", "00110111", "01101101", "10001101", "11010101", "01001110", "10101001", "01101100", "01010110", "11110100", "11101010", "01100101", "01111010", "10101110", "00001000", "10111010", "01111000", "00100101", "00101110", "00011100", "10100110", "10110100", "11000110", "11101000", "11011101", "01110100", "00011111", "01001011", "10111101", "10001011", "10001010", "01110000", "00111110", "10110101", "01100110", "01001000", "00000011", "11110110", "00001110", "01100001", "00110101", "01010111", "10111001", "10000110", "11000001", "00011101", "10011110", "11100001", "11111000", "10011000", "00010001", "01101001", "11011001", "10001110", "10010100", "10011011", "00011110", "10000111", "11101001", "11001110", "01010101", "00101000", "11011111", "10001100", "10100001", "10001001", "00001101", "10111111", "11100110", "01000010", "01101000", "01000001", "10011001", "00101101", "00001111", "10110000", "01010100", "10111011", "00010110"};
}

