import java.io.DataOutputStream;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        double mandar = 0;
        long tiempoInicio = System.currentTimeMillis();
        try {
            for(int i = 0; i < 10000; i++) {
                Socket socket = new Socket("localhost", 9000);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                
                dos.writeDouble(++mandar);

                dos.close();
                socket.close();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        long tiempoFinal = System.currentTimeMillis();
        System.out.println(tiempoFinal - tiempoInicio);
    }
}