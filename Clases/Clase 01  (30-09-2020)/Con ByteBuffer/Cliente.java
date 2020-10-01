import java.net.*;
import java.nio.ByteBuffer;
import java.io.DataOutputStream;
import java.io.IOException;

public class Cliente {
    final static int TAM_BYTES_DOUBLE = 8;
    final static int TOTAL_NUMEROS = 10000;
    static double mandar = 0;

    public static void main(String[] args) {
        long tiempoIncio = System.currentTimeMillis();
        try {
            Socket socket = new Socket("localhost", 9000);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            ByteBuffer b = ByteBuffer.allocate(TAM_BYTES_DOUBLE * TOTAL_NUMEROS);

            for(int i = 0; i < TOTAL_NUMEROS; i++) b.putDouble(++mandar);

            byte[] buffer = b.array();
            dos.write(buffer);

            dos.close();
            socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
        }
        
        long tiempoFinal = System.currentTimeMillis();
        System.out.println(tiempoFinal - tiempoIncio);
    }
}