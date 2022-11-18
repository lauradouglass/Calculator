//importing useful libraries
import java.util.*;
import java.lang.Math;
import java.net.*;
import java.io.*;


public class Calculator {
    /* Returns true if operator is valid */
    private boolean operator(char sign) {
        return sign == '+' || sign == '-' || sign == '*' || sign == '/' || sign == '^';
    }

    //create stacks to handle the operators and values to be evaluated
    private Stack<Character> operatorStack;
    private Stack<Double> numberStack;

    public Calculator() {
        operatorStack = new Stack<Character>();
        numberStack = new Stack<Double>();
    }

    /*implementing PEMDAS:
    If current token in operator stack has a greater-than/equal-to precedence,
    then we evaluate the operator and top 2 values on value stack,
    before continueing processing into stack
     */
    private int pemdas(char sign) {
        if (sign == '+') {
            return 1;
        }
        if (sign == '-') {
            return 1;
        }
        if (sign == '*') {
            return 2;
        }
        if (sign == '/') {
            return 2;
        }
        if (sign == '^') {
            return 3;
        }
        return 0;
    }

    private boolean error;

    //reading, parsing and stacking user input
    public void readUser(String userIn) throws IOException {
        //get each toke from user input
        String[] allTokens = userIn.split(" ");

        //seperate tokens into respective stacks
        for (int i = 0; i < allTokens.length; i++) {
            String newToken = allTokens[i];
            char exp = newToken.charAt(0);
            if (exp >= '0' && exp <= '9') {
                double values = Double.parseDouble(newToken);
                numberStack.push(values); //push parsed values into stack we created earlier
            } else if (operator(exp)) {
                if (operatorStack.empty() || pemdas(exp) > pemdas(operatorStack.peek())) {
                    operatorStack.push(exp);
                } else {
                    while (!operatorStack.empty() && pemdas(exp) <= pemdas(operatorStack.peek())) {
                        char operatorRead = operatorStack.peek();
                        operatorStack.pop();
                        readOperator(operatorRead);
                    }
                    operatorStack.push(exp);
                }
            } else if (exp == '(') { //to read parenthesis
                operatorStack.push(exp);
            } else if (exp == ')') {
                while (!operatorStack.empty() && operator(operatorStack.peek())) {
                    char operatorRead = operatorStack.peek();
                    operatorStack.pop();
                    readOperator(operatorRead);
                }
                if (!operatorStack.empty() && operatorStack.peek() == '(') {
                    operatorStack.pop();
                } else {
                    System.out.println("Please remember to close you parenthesis");
                    error = true;
                }

            }

        }
        //operator stack need to be emptied when all input tokens have been read
        // to avoid program stopping and ouputing an expression error
        while (!operatorStack.empty() && operator(operatorStack.peek())) {
            char operatorRead = operatorStack.peek();
            operatorStack.pop();
            readOperator(operatorRead);
        }
        //when program has been run,
        // if there are no errors and evaluation is complete,
        // output the result to user
        //GO BBACK UP AND CREATE ERROR bool !!!

        if (!error) {
            //look at the result from the number stack
            double finalResult = numberStack.peek();
            numberStack.pop();
            if (!operatorStack.empty() || !numberStack.empty()) {
                System.out.println("Expression error. Please follow the given instructions");
            } else {
                //show user final result
                System.out.println("The result is " + finalResult);
                //file to store final result
                //write all input to file first
                try(FileWriter logging = new FileWriter("logfile2.txt", true);
                    BufferedWriter buffer = new BufferedWriter(logging);
                    PrintWriter print = new PrintWriter(buffer))
                {
                    print.println("Result: " + finalResult + " \n" + "__________________________________________\n");
                }
            }
        }
    }

    //next we will read in the operator and implement cases for potential operator errors
    //move readOperator above readUser


    private void readOperator(char sign) {
        double num1, num2;
        //check for errors first
        if (numberStack.empty()) {
            System.out.println("Expression Error: Please enter a new expression.");
            error = true;
            return;
        } else {
            num2 = numberStack.peek();
            numberStack.pop();
        }
        if (numberStack.empty()) {
            System.out.println("Expression error: Please enter a new expression.");
            error = true;
            return;
        } else {
            num1 = numberStack.peek();
            numberStack.pop();
        }
        //evaluate number stack values, using the following simple calculations
        double result = 0;
        if (sign == '+') {
            result = num1 + num2;
        } else if (sign == '-') {
            result = num1 - num2;
        } else if (sign == '*') {
            result = num1 * num2;
        } else if (sign == '/') {
            result = num1 / num2;
        }
        else if(sign == '^') {
            result =  Math.pow(num1, num2);
        }else {
            error = true;
            System.out.println("Invalid operator. Please enter valid input");
        }
        //output final stack value after evaluation, which is the result to be sent to logfile
        numberStack.push(result);
    }

    //take user input and call calculator on input, using 'calculating method
    public static void main(String[] args) throws IOException {
        try {
            new FileWriter("logfile.txt", false).close();
            new FileWriter("logfile2.txt", false).close();
        } finally {}

        //object to take user inputs
        //take user input
        while (true) {
            Scanner userIn = new Scanner(System.in);
            System.out.print("Enter an expression to evaluate.\n" + "For -ve numbers, do ( 0 - 2 ) for -2.\n" + "Please include whitespace Ex. 2 + 2 - 1 * 3): \n");
            String expression = userIn.nextLine();
            //write all input to file first
            try(FileWriter logging = new FileWriter("logfile.txt", true);
                BufferedWriter buffer = new BufferedWriter(logging);
                PrintWriter print = new PrintWriter(buffer))
            {
                print.println("Expression: " + expression + " \n" + "__________________________________________\n");
            }


            //new object to call functions from above to eval user input
            Calculator beginCalculation = new Calculator();
            beginCalculation.readUser(expression);

            //printing
            /* the server will receive 2 separate files from the client 'Calculator'
            and then merge those two files into a single file
            when user says 'print', the files (expression file and result file)
            are sent to the server, where they are merged
            the final (merged) file is then sent back to the client
            and output to the user*/
            //setting up socket connection to send .txt file containing all user input expressions
            Scanner toPrint = new Scanner(System.in);
            System.out.println("type print to print, hit enter to skip");
            String ppp = toPrint.nextLine();
            String wwjd = "print";
            if(wwjd.equals(ppp)){
                System.out.println("Your file is being prepared, please wait.");
                try {
                    Socket socket = new Socket("localhost", 4999);
                    BufferedReader bufferR = new BufferedReader(new FileReader("logfile.txt"));
                    String fromBuffer = new String();
                    while(bufferR.ready())
                        fromBuffer += bufferR.readLine() + "\n";
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                    output.writeUTF(fromBuffer);
                    System.out.println("Completed transfer of expression file!");

                    bufferR = new BufferedReader(new FileReader("logfile2.txt"));
                    fromBuffer = new String();
                    while(bufferR.ready())
                        fromBuffer += bufferR.readLine() + '\n';
                    output = new DataOutputStream(socket.getOutputStream());
                    output.writeUTF(fromBuffer);
                    System.out.println("Completed transfer of result file!");

                    DataInputStream inputS = new DataInputStream(socket.getInputStream());
                    String readIn = inputS.readUTF();
                    System.out.println(readIn);
                } catch (IOException ie) {
                    System.out.println("Error connecting");
                }
            }

            //quitting
            Scanner toQuit = new Scanner(System.in);
            System.out.println("type quit to quit, hit enter to skip");
            String que = toQuit.nextLine();
            String weird = "quit";
            if(weird.equals(que)){
                System.out.println("Goodbye!");
                System.exit(1);
            }


        }

    }
}


