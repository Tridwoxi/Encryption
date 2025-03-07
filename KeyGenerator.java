/**
    State machine that yields keys by churning original.
 */
public class KeyGenerator {
    private String key;

    public KeyGenerator(String key) {
        if (key.length() != 56) {
            throw new Error("key must be 56 bit binary string");
        }
        this.key = key;
    }

    public String next() {
        // REASSIGN KEY

        // YIELD
        return key;
    }
}
