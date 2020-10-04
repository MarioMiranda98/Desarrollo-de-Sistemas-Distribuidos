import java.io.*;
import java.net.*;

public class Pi {
    static Object lock = new Object();
    static double pi = 0;

    static class Worker {
        Socket conexion;

        public Worker(Socket conexion) {
            this.conexion = conexion;
        }
    
        public void run() {
            try {
                DataInputStream entrada = new DataInputStream(conexion.getInputStream());
                DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
                double x = entrada.readDouble();
    
                synchronized(lock) {
                    pi += x;
                }
    
                entrada.close();
                salida.close();
                conexion.close();
            } catch(Exception e) { e.printStackTrace(); }
        }
    }

    public static void main(String[] args) throws Exception {
        if(args.length != 1) {
            System.err.println("Uso:");
            System.err.println("java Pi <nodo>");
            System.exit(0);
        }

        int nodo = Integer.valueOf(args[0]);
        if(nodo == 0) {

        } else {
            
        }
    }
}