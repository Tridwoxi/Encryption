/* Example code from Prof Polak. Delete this file when no longer useful */
public class CharToBin {
    public static void main(String[] args) {
        char ch1 = 'A';
        char ch2 = '5';

        int num1 = (int) ch1;
        int num2 = (int) ch2;

        System.out.println("Numeric value of " + ch1 + " is " + num1);
        System.out.println("Numeric value of " + ch2 + " is " + num2);

        char asciiChar1 = (char) num1;
        char asciiChar2 = (char) num2;

        System.out.println("Char of " + num1 + " is " + asciiChar1);
        System.out.println("Char  of " + num2 + " is " + asciiChar2);

        String binary = Integer.toBinaryString(num1);
        System.out.println(binary);
        System.out.println("Binary representation of " + num1 + " is: " + binary);
        System.out.println("Make sure to convert to 8 bits!");
        int number = Integer.parseInt("01000001", 2);
        System.out.println("Integer value of binary " + binary + " is: " + number);

    }
}