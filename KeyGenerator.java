/* 
    TODO:
    - put everything in a queue
    - documentation
 */

/**
    State machine that yields keys by churning original.
 */
public class KeyGenerator {
    private String key;
    private String left_half;
    private String right_half;

    public static void main(String[] args) {
        String example = "aaaaaaaabbbbbbbbccccccccddddddddeeeeeeeeffffffffgggggggg";
        KeyGenerator kg = new KeyGenerator(example);
        for (int i = 0; i < 10; i++) {
            System.out.println(kg.next());
        }
    }

    public KeyGenerator(String key) {
        if (key.length() != 56) {
            throw new Error("key must be 56 bit binary string");
        }
        this.left_half = key.substring(0, 28);
        this.right_half = key.substring(28, 56);
    }

    public String next() {
        left_half = rotate_left(left_half);
        right_half = rotate_left(right_half);
        key = left_half + right_half;
        return key.substring(0, 32);
    }

    private String rotate_left(String string) {
        return string.substring(1) + string.substring(0, 1);
    }
}
