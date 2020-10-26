import java.net.*;
import java.io.*;

public class Token {
  static DataInputStream entrada;
  static DataOutputStream salida;
  static boolean primeraVez = true;
  static String ip;
  static long token = 0;
  static int nodo;

  static class Worker extends Thread {
    public void run() {
      try {
        ServerSocket servidor = new ServerSocket(50000 + nodo);
        Socket conexion = servidor.accept();
        entrada = new DataInputStream(conexion.getInputStream()); 
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("Se debe pasar como parametros el numero de nodo y la IP del siguiente nodo");
      System.exit(1);
    }

    nodo = Integer.valueOf(args[0]);  
    ip = args[1];  

    Worker w = new Worker();
    w.start();
    
    Socket conexion = null;

    while(true) {
      try {
        conexion = new Socket(ip, 50000);
        break;
      }catch(Exception e) {
        Thread.sleep(500);
      }
    }

    salida = new DataOutputStream(conexion.getOutputStream());
    w.join();
    while(true) {
      if(nodo == 0) {
        if(primeraVez) primeraVez = false;
        else token = entrada.readLong();
      } else {
        token = entrada.readLong();
      }

      token += 1;
      System.out.println(token);
      salida.writeLong(token);
    }
  }
}