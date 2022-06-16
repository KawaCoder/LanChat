import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class Client
{
    // initialize socket and input output streams
    private Socket socket = null;

    // constructor to put ip address and port
    public Client(String address, int port, String pass, String id) throws IOException {



        // establish a connection
        try
        {

            socket = new Socket(address, port);
            System.out.println("\n[*] Connection established\n");


        } catch(IOException u){
            System.out.println(u);
        }


// to send data to the server
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        // to read data coming from the server
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // to read data from the keyboard
        BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
        String line = "";


        //sending pass
        out.writeBytes(pass+"\n");


        //checking server response

        line = reader.readLine();
        if (line.equals("pass ok")) {
            System.out.println("[*] Passphrase verified\n");


        } else if (line.equals("bad pass")) {
            System.out.println("[!] Bad passphrase\n");
            socket.close();


        }else{
            System.out.println(line);

        }


        final String[] str = new String[1];
        String str1 = "";

        Thread t = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        if ((str[0] = kb.readLine()).equals("exit")) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // send to the server
                    try {
                        out.writeBytes(id + ": " + str[0] + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();

        while(!Objects.equals(str1, "exit")) {
            str1 = reader.readLine();

            System.out.println(str1);
            if (str1.equals("Over")){

                break;

            }

        }

        // close connection.
        out.close();
        reader.close();
        kb.close();
        socket.close();





    }
    public static void main(String address, int port, String pass, String id)
    {
        try {
            Client client = new Client(address, port, pass, id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}