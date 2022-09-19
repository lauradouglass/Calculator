//simple calculator
/*import java.util.Scanner;
import java.lang.Math;

public class Calculator {
    public static void main(String[] args){
        //object to take user inputs
        while(true) {
            Scanner userin = new Scanner(System.in);
            System.out.println("Input 2 numbers");
            double num1 = userin.nextDouble();
            double num2 = userin.nextDouble();


            Scanner toquit = new Scanner(System.in);
            System.out.println("type quit to quit, otherwise skip");
            String que = toquit.nextLine();

            double ans = 0;
            String weird = "quit";

            // quit to quit, print to print logfile
            if(weird.equals(que)){
                System.out.println("Goodbye!");
                System.exit(1);
            }


            //intialize operators as integers, use switch cases later on to call a block of code for each char
            int operand;
            System.out.println("Select one of the following [1 for +, 2 for -, 3 for *, 4 for /, 5 for ^]");
            operand = userin.nextInt();
            /*switch cases for each operand.
            case 1 for addition, 2 for subtraction, 3 for multiplication, 4 for division and 5 for power

            switch (operand) {
                case 1:
                    ans = num1 + num2;
                    System.out.println(ans);
                    break;
                case 2:
                    ans = num1 - num2;
                    System.out.println(ans);
                    break;
                case 3:
                    ans = num1 * num2;
                    System.out.println(ans);
                    break;
                case 4:
                    ans = num1 / num2;
                    System.out.println(ans);
                    break;
                case 5:
                    ans = Math.pow(num1, num2);
                    System.out.println(ans);
                    break;
                default:
                    System.out.println("Invalid operator, please re-run");
            }
        }

    }
} */

//better calculator to be implemented in full calculator
/*
import java.util.Scanner;
import java.lang.Math;

public class Calculator {
    public static void main(String[] args){
        //object to take user inputs
        while(true) {
            //take user input
            Scanner userin = new Scanner(System.in);
            System.out.print("Enter an expression to compute. Ex.: 2 + 2 (including spaces): ");
            String exp = userin.nextLine();
            double ans = 0;

            // create tokens that make up the input
            String[] tokens = exp.split(" ");

            //using switch cases, determine the operation to be carried
            switch (tokens[1].charAt(0)) {
                case '+':
                    ans = Integer.parseInt(tokens[0]) + Integer.parseInt(tokens[2]);
                    System.out.println(ans);
                    break;
                case '-':
                    ans = Integer.parseInt(tokens[0]) - Integer.parseInt(tokens[2]);
                    System.out.println(ans);
                    break;
                case '*':
                    ans = Integer.parseInt(tokens[0]) * Integer.parseInt(tokens[2]);
                    System.out.println(ans);
                    break;
                case '/':
                    ans = Integer.parseInt(tokens[0]) / Integer.parseInt(tokens[2]);
                    System.out.println(ans);
                    break;
                case '^':
                    ans = Math.pow(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[2]));
                    System.out.println(ans);
                    break;
                default:
                    System.out.println("Invalid operator, please enter a valid input.");
            }

            //to handle 'quit' command
            Scanner toquit = new Scanner(System.in);
            System.out.println("Type quit to quit, otherwise skip");
            String que = toquit.nextLine();
            String weird = "quit";
            if(weird.equals(que)){
                System.out.println("Goodbye!");
                System.exit(1);
            }
            else{
                continue;
            }

        }

    }
}*/