import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Chat {
    static void envia_mensaje(byte[] buffer,String ip, int puerto) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress grupo = InetAddress.getByName(ip);
        DatagramPacket paquete = new DatagramPacket(buffer,buffer.length,grupo,puerto);
        socket.send(paquete);
        socket.close();
    }
    
    static byte[] recibe_mensaje(MulticastSocket socket, int longitud) throws IOException {
      byte[] buffer = new byte[longitud];
      DatagramPacket paquete = new DatagramPacket(buffer,buffer.length);
      socket.receive(paquete);
      return buffer;
    }

    static class Worker extends Thread {
        MulticastSocket socket;

        public Worker(MulticastSocket socket) {
            this.socket = socket;
        }

        public void run() {
            while(true) {
                try {
                    byte[] a = recibe_mensaje(socket, 100);
                    String mensaje = new String(a, "UTF-8");
                    System.out.println(mensaje);
                } catch(IOException e) { e.printStackTrace(); }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if(args.length != 1) {
            System.err.println("Se debera pasar por consola el nombre de usuario");
            System.exit(1);
        }

        InetAddress grupo = InetAddress.getByName("230.0.0.0");
        MulticastSocket socket = new MulticastSocket(50000);
        socket.joinGroup(grupo);

        Worker w = new Worker(socket);
        w.start();

        String nombre = args[0];
        BufferedReader b = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            String linea = b.readLine();
            System.out.print("\33[1A\33[2K");
            String formato = "-" + nombre + " escribe: " + linea.trim();
            envia_mensaje(formato.getBytes(), "230.0.0.0", 50000);
        }
    }
}