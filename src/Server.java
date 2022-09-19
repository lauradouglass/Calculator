//server file
import java.net.*;
import java.io.*;
import java.lang.*;
/* the server will receive 2 separate files from the client 'Calculator'
and then merge those two files into a single file
when user says 'print', the files (expression file and result file)
are sent to the server, where they are merged
the final (merged) file is then sent back to the client
and output to the user
 */

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket sSocket = new ServerSocket(4999);
        sSocket.setSoTimeout(0);

        while(true) {
            try {
                Socket acceptingS = sSocket.accept();
                DataInputStream inputS = new DataInputStream(acceptingS.getInputStream());
                String readIn = inputS.readUTF();
                System.out.println("Expression file received");
                FileOutputStream fileO = new FileOutputStream("yourcalculations.txt");
                byte[] b = readIn.getBytes();
                fileO.write(b);

                readIn = inputS.readUTF();
                System.out.println("Result file received");
                b = readIn.getBytes();
                fileO.write(b);

                BufferedReader bufferR = new BufferedReader(new FileReader("yourcalculations.txt"));
                String fromBuffer = new String();
                while (bufferR.ready())
                    fromBuffer += bufferR.readLine() + '\n';
                DataOutputStream output = new DataOutputStream(acceptingS.getOutputStream());
                output.writeUTF(fromBuffer);
            } catch (IOException e) {
                System.out.println("Error Connecting");
            }
        }

    }
}

