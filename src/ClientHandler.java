import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;


class ClientHandler implements Runnable
{
    Scanner scn = new Scanner(System.in);
    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isloggedin;

    // constructor
    public ClientHandler(Socket s, String name,
                         DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.s = s;
        this.isloggedin=true;
    }

    @Override
    public void run() {

        String received;
        try {
            dos.writeUTF("Comece a enviar mensagens. Escreva 'logout' para desligar a conexao");
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true)
        {
            try
            {

                // receive the string
                received = dis.readUTF();

                System.out.println(received);

                if(received.equals("logout")){
                    this.isloggedin=false;
                    this.s.close();
                    Server.ar.remove(this);
                    break;
                }

                //propaga as mensagens para todos os usuarios, menos o que enviou a mensagem
                for (ClientHandler mc : Server.ar) {

                    if (!mc.equals(this)) {
                        mc.dos.writeUTF(received);
                    }
                }
            } catch (IOException e) {

                e.printStackTrace();
            }

        }
        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
