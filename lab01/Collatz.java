/** Class that prints the Collatz sequence starting from a given number.
 *  @author YOUR NAME HERE
 */
public class Collatz {

    /** Returns the nextNumber in a Collatz sequence. */
    public static int nextNumber(int n) {
        if (n == 2) {
        return 1;
        }
        else if (n % 2 == 0){
            return n/2;
        }
        else{
            return (3 * n) + 1;
        }
    }

    /** in the pattern, 1 always follows 2, so I made that the conditional in the if statement to return 1, then I used else if and else statements to separate between evens and odds to apply the equations*/

    public static void main(String[] args) {
        int n = 5;
        System.out.print(n + " ");

        // Some starter code to test
        while (n != 1) {          
            n = nextNumber(n);          
            System.out.print(n + " ");
        }
        System.out.println();

    }
}

