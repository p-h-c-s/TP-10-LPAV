
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class Server
{


    static ArrayList<ClientHandler> ar = new ArrayList<>();


    static int i = 0;

    public static void main(String[] args) throws IOException
    {

        ServerSocket ss = new ServerSocket(9000);

        Socket s;


        while (true)
        {

            s = ss.accept();

            System.out.println("Novo cliente recebido : " + s);

            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            System.out.println("Criando worker para o cliente");


            ClientHandler mtch = new ClientHandler(s,"client " + i, dis, dos);

            Thread t = new Thread(mtch);

            System.out.println("Adicionando cliente a lista");

            ar.add(mtch);


            t.start();

            i++;

        }
    }
}
