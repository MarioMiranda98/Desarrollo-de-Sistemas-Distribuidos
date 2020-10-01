import java.net.*;
import java.nio.ByteBuffer;
import java.io.DataInputStream;

public class Servidor {
    final static int TAM_BYTES_DOUBLE = 8;
    final static int TOTAL_NUMEROS = 10000;

    public static void main(String[] args) {
        try {
            ServerSocket socket = new ServerSocket(9000);
            Socket socketCliente = socket.accept();

            DataInputStream dis = new DataInputStream(socketCliente.getInputStream());
            byte[] buffer = new byte[TAM_BYTES_DOUBLE * TOTAL_NUMEROS];
            read(dis, buffer, 0, TAM_BYTES_DOUBLE * TOTAL_NUMEROS);

            ByteBuffer b = ByteBuffer.wrap(buffer);

            for(int i = 0; i < TOTAL_NUMEROS; i++) System.out.println(b.getDouble());

            dis.close();
            socketCliente.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void read(DataInputStream dis, byte[] buffer, int posicion, int longitud) throws Exception {
        while(longitud > 0) {
            int n = dis.read(buffer, posicion, longitud);
            posicion += n;
            longitud -= n;
        }
    }
}