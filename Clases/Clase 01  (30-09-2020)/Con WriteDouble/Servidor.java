import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        try {
            ServerSocket socket = new ServerSocket(9000);
            for(int i = 0; i < 10000; i++) {
                Socket socketCliente = socket.accept();
                DataInputStream dis = new DataInputStream(socketCliente.getInputStream());
                
                System.out.println(dis.readDouble());
                
                dis.close();
                socketCliente.close();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}