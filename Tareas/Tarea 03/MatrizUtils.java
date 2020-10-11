import java.io.*;

public class MatrizUtils {
    static void enviarMatriz(double[][] matriz, int filas, int columnas, DataOutputStream dos) throws IOException {
        for(int i = 0; i < columnas; i++)
            for(int j = 0; j < filas; j++)
                dos.writeDouble(matriz[i][j]);
        
        dos.flush();
    }

    static double[][] transponerMatriz(double[][] matriz, int N) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < i; j++) {
                double x = matriz[i][j];
                matriz[i][j] = matriz[j][i];
                matriz[j][i] = x;
            }
        }   

        return matriz;
    }

    static double[][] recibirMatriz(int filas, int columnas, int inicioFilas, int inicioColumnas, DataInputStream dis) throws IOException {
        double[][] tmp = new double[columnas][filas];

        for(int i = inicioColumnas; i < columnas; i++)
            for(int j = inicioFilas; j < filas; j++)
                tmp[i][j] = dis.readDouble();

        return tmp;
    }

    static void mostrarMatriz(String texto, double[][] matriz, int filas, int columnas) {
        System.out.println(texto);
        for(int i = 0; i < columnas; i++) {
            for(int j = 0; j < filas; j++) {
                System.out.print(matriz[i][j] + " ");
            } 
            System.out.println("");
        }

        System.out.println("");
    }

    static double calcularChecksum(double[][] matriz, int N) {
        double checksum = 0;
        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++)
                checksum += matriz[i][j];
        
        return checksum;
    }
}