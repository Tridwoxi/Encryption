
public class Encryption {
    // NOTE TO JOHN: I moved this function here from "RoundFunction.java".
    public static void processStringInChunks(String input, String key) {
        int chunk_size = 8;
        int length = input.length();

        for (int i = 0; i < length; i += chunk_size) {
            // Extract 8-character chunk or pad if needed
            String chunk = input.substring(i, Math.min(i + chunk_size, length));

            // Pad the chunk if it's less than 8 characters
            if (chunk.length() < chunk_size) {
                chunk = String.format("%-8s", chunk); // Pads with spaces
            }

            // Call the round_function() on the chunk
            round_function(chunk, key);
        }
    }    
}
