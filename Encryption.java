import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

/*
    Configuration settings for the project.
 */
final class Config {
    // Do not edit the following unless you have a new box and table—find those at the bottom of RoundFunction.java
    public static final int CHUNK_SIZE_BYTES = 8;
    public static final int SECRET_KEY_BYTES = 7;

    // You may edit the following as desired
    public static final int NUMBER_OF_ROUNDS = 10;
    public static final int KEY_LEFT_ROTATE = 1;
}

/**
    This is the main file of the Encryption project. It performs reading and writing of files, pre- and post-processing (i.e. padding and binary conversion), high level organization, and user interaction. 

    @author John Meridios and Darryl Wang
 */
public class Encryption {
    public static void main(String[] args) {
        // TESTS
        run_tests();

        // USER INPUT
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Encrypt or decrypt: ");
        String mode = scanner.nextLine();
        System.out.printf("Input file: ");
        String input_file = scanner.nextLine();
        System.out.printf("Password: ");
        String secret_key = scanner.nextLine();
        System.out.printf("Output file: ");
        String output_file = scanner.nextLine();
        scanner.close();

        // PREPROCESSING
        String input = read_file(input_file);
        secret_key = convert_to_binary(secret_key);
        if (mode.toLowerCase().startsWith("e")) {
            input = pad_plaintext(input);
            input = convert_to_binary(input);
        }
        ArrayList<String> chunks = split_into_chunks(input);

        // ENCRYPTION or DECRYPTION 
        for (int index = 0; index < chunks.size(); index++) {
            String chunk = chunks.get(index);
            chunks.set(index, RoundFunction.round_functions(chunk, secret_key, mode));
        }

        // OUTPUT
        StringBuilder string_builder = new StringBuilder();
        for (String chunk : chunks) {
            string_builder.append(chunk);
        }
        String output = string_builder.toString();
        if (!mode.toLowerCase().startsWith("e")) {
            output = convert_from_binary(output).replace("\0", "");
        }
        write_file(output_file, output);
    }

    public static String read_file(String filename) {
        StringBuilder string_builder = new StringBuilder();
        try (FileReader file_reader = new FileReader(filename)) {
            int character;
            while ((character = file_reader.read()) != -1) {
                string_builder.append((char) character);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return string_builder.toString();
    }

    public static String pad_plaintext(String ascii_chars) {
        // Null padding is a possibly non-secure but simple way to ensure length
        int chunk_size = Config.CHUNK_SIZE_BYTES;
        int additional_depth = chunk_size - ascii_chars.length() % chunk_size;
        return ascii_chars + new String(new char[additional_depth]);
    }

    public static String convert_to_binary(int ascii_char) {
        String result = Integer.toBinaryString(ascii_char);
        while (result.length() < Byte.SIZE) {
            result = "0" + result;
        }
        return result;
    }

    public static String convert_to_binary(String ascii_string) {
        StringBuilder string_builder = new StringBuilder();
        for (int index = 0; index < ascii_string.length(); index++) {
            string_builder.append(convert_to_binary(ascii_string.charAt(index)));
        }
        return string_builder.toString();
    }

    public static ArrayList<String> split_into_chunks(String padded_string) {
        ArrayList<String> chunks = new ArrayList<>();
        int chunk_size = Config.CHUNK_SIZE_BYTES * Byte.SIZE;
        for (int index = 0; index < padded_string.length(); index += chunk_size) {
            chunks.add(padded_string.substring(index, index + chunk_size));
        }
        return chunks;
    }

    public static String convert_from_binary(String binary_string) {
        StringBuilder string_builder = new StringBuilder();
        for (int index = 0; index < binary_string.length(); index += Byte.SIZE) {
            String substring = binary_string.substring(index, index + Byte.SIZE);
            string_builder.append((char) Integer.parseInt(substring, 2));
        }
        return string_builder.toString();
    }

    public static void write_file(String filename, String contents) {
        try (FileWriter file_writer = new FileWriter(filename)) {
            file_writer.write(contents);
            file_writer.flush();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void run_tests() {
        String[] CHUNKS = {
                "1111111111111111111111111111111111111111111111111111111111111111",
                "0000000000000000000000000000000000000000000000000000000000000000",
                "0000000000000000000000000000000000000000000000000000000000000000",
                "1100110010000000000001110101111100010001100101111010001001001100",
                "1111111111111111111111111111111111111111111111111111111111111111",
                "0000000000000000000000000000000000000000000000000000000000000000",
                "0000000000000000000000000000000000000000000000000000000000000000",
                "0101011010001110111001000111100001001110010001100110000011110101",
                "0011000101110111011100100101001001001101011010100110011111010111",
        };
        String[] KEYS = {
                "11111111111111111111111111111111111111111111111111111111",
                "11111111111111111111111111111111111111111111111111111111",
                "00000000000000000000000000000000000000000000000000000000",
                "00000000000000000000000000000000000000000000000000000000",
                "11111111111111111111111111111111111111111111111111111111",
                "11111111111111111111111111111111111111111111111111111111",
                "00000000000000000000000000000000000000000000000000000000",
                "11111111111111111111111111111111111111111111111111111111",
                "00000000000000000000000000000000000000000000000000000000",
        };

        for (int index = 0; index < 4 + 1; index++) {
            String result = RoundFunction.round_functions(CHUNKS[index], KEYS[index], "e");
            System.out.println("TEST" + (index + 1) + ": " + result);
        }

        for (int index = 4; index < 9; index++) {
            String result = RoundFunction.round_functions(CHUNKS[index], KEYS[index], "d");
            System.out.println("TEST" + (index + 1) + ": " + result);
        }
    }
}
