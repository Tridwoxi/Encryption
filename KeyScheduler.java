import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
    The transformation function. Contains queue and stack of keys for encryption/decryption. 

    @apiNote Instantiate with full key. Call {@code next_encryption_key} and {@code next_decryption_key} for round keys. Will return null if insufficeint keys for decryption. Either get the encryption keys all at once and then the decryption keys later (recommended) or call them alternating and mark them as pairs. Do not arbritrarily mix the order of getting encryption and decryption keys.

    @implNote Created as a wrapper for {@link KeyGenerator}. Implementation using queue considered harmful but required by specification. 
 */
public class KeyScheduler {
    private KeyGenerator key_generator;
    private Queue<String> forward;
    private Stack<String> reverse;

    public KeyScheduler(String full_key) {
        key_generator = new KeyGenerator(full_key);
        forward = new LinkedList<>();
        reverse = new Stack<>();
    }

    public String next_encryption_key() {
        String round_key = key_generator.next();
        forward.add(round_key);
        reverse.push(round_key);
        return forward.remove();
    }

    public String next_decryption_key() {
        return reverse.empty() ? null : reverse.pop();
    }
}

/**
    State machine that yields new keys.

    @apiNote Instantiate with full key. Call {@code next} to get the next key. Should not be used directly but rather as part of {@link KeyScheduler}.

    @implNote Implemented as a generator function. Done by churning the original with rotate_left and returning the first 32 bits as a round key. 
 */
class KeyGenerator {
    private String key;
    private String left_half;
    private String right_half;

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
