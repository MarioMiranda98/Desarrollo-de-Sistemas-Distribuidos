import java.io.*;
import java.net.*;

public class Pi {
    static Object lock = new Object();
    static double pi = 0;

    static class Worker extends Thread{
        Socket conexion;

        public Worker(Socket conexion) {
            this.conexion = conexion;
        }
        
        //Algoritmo 1
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
        if(nodo == 0) { //Algoritmo 2
            ServerSocket servidor = new ServerSocket(50000);
            Worker[] w = new Worker[3];
            int i = 0;
            while(!(i == 3)) {
                Socket conexion = servidor.accept();
                w[i] = new Worker(conexion);
                w[i].start();
                i += 1;
            }

            double suma = 0;
            i = 0; //La variable ya esta antes declarada, asi que solo le reasigno 0

            while(!(i == 10000000)) {
                suma = 4.0 / ((8 * i) + 1) + suma;
                i += 1;
            }

            synchronized(lock) {
                pi += suma;
            }

            i = 0; //Reasigno el valor de 0 para no desbordar mi arreglo
            while(i != 3) {
                w[i].join();
                i += 1;
            }

            servidor.close();
            System.out.println("Valor de pi: " + pi);
        } else { //Algoritmo 3
            Socket conexion = null;
            
            //Conexion con re-intento.
            while(true) {
                try {
                    conexion = new Socket("localhost", 50000);
                    break;
                } catch(Exception e) {
                    Thread.sleep(100);
                }
            }

            //Aca ya conecto el socket.
            DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
            
            double suma = 0;
            int i = 0;
            while(!(i == 10000000)) {
                suma = 4.0 / ((8 * i) + (2 * (nodo - 1) + 3)) + suma;
                i += 1;
            }

            suma = (nodo % 2) == 0 ? suma : (-1) * suma;
            salida.writeDouble(suma);
            salida.flush();

            salida.close();
            conexion.close();
        }
    }
}