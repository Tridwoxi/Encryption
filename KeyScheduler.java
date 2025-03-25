import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
    The transformation function. Contains queue and stack of keys for encryption/decryption. 

    @apiNote Instantiate with full key. Call {@code next_encryption_key} and {@code next_decryption_key} for round keys in order. Will throw if more keys than exist are requested. 

    @implNote Created as a wrapper for {@link KeyGenerator}. Implementation using stack and queue required by specification. 
 */
public class KeyScheduler {
    private KeyGenerator key_generator;
    private Queue<String> forward;
    private Stack<String> reverse;

    public KeyScheduler(String full_key) {
        key_generator = new KeyGenerator(full_key);
        forward = new LinkedList<>();
        reverse = new Stack<>();

        for (int i = 0; i < Config.NUMBER_OF_ROUNDS; i++) {
            String round_key = key_generator.next();
            forward.add(round_key);
            reverse.push(round_key);
        }
    }

    public String next_encryption_key() {
        return forward.remove();
    }

    public String next_decryption_key() {
        return reverse.pop();
    }
}

/**
    State machine that yields new keys.

    @apiNote Instantiate with full key. Call {@code next} to get the next key. Should not be used directly but rather as part of {@link KeyScheduler}.

    @implNote Implemented as a generator function. Done by churning the original with rotate_left and returning the first 32 bits as a round key. 
 */
class KeyGenerator {
    private String full_key;
    private String left_half;
    private String right_half;

    public KeyGenerator(String full_key) {
        int key_length = Config.SECRET_KEY_BYTES * Byte.SIZE;
        if (full_key.length() != key_length) {
            throw new Error("Expected key bytes: " + Config.SECRET_KEY_BYTES);
        }
        this.left_half = full_key.substring(0, key_length / 2);
        this.right_half = full_key.substring(key_length / 2, key_length);
    }

    public String next() {
        left_half = rotate(left_half);
        right_half = rotate(right_half);
        full_key = left_half + right_half;
        return full_key.substring(0, Config.CHUNK_SIZE_BYTES * Byte.SIZE / 2);
    }

    private String rotate(String string) {
        int amount = Config.KEY_LEFT_ROTATE;
        return string.substring(amount) + string.substring(0, amount);
    }
}
