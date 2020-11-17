import java.rmi.Naming;

public class ClienteMatriz {
    static int N = 4;
    static final String URL = "rmi://localhost/nodo";

    public static void main(String[] args) throws Exception {
        int[][] matrizA = new int[N][N];
        int[][] matrizB = new int[N][N];
        int[][] matrizC = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrizA[i][j] = 2 * i - j;
                matrizB[i][j] = 2 * i + j;
                matrizC[i][j] = 0;
            }
        }

        matrizB = transponerMatriz(matrizB);

        MatrizMethodsInterface remoto = (MatrizMethodsInterface) Naming.lookup(URL + "0");
        int[][] tmpC0 = remoto.multiplica_matrices(parte_matriz(matrizA, 0), parte_matriz(matrizB, 0), N);
        
        MatrizMethodsInterface remoto1 = (MatrizMethodsInterface) Naming.lookup(URL + "1");
        int[][] tmpC1 = remoto1.multiplica_matrices(parte_matriz(matrizA, 0), parte_matriz(matrizB, N / 2), N);
    
        MatrizMethodsInterface remoto2 = (MatrizMethodsInterface) Naming.lookup(URL + "2");
        int[][] tmpC2 = remoto2.multiplica_matrices(parte_matriz(matrizA, N / 2), parte_matriz(matrizB, 0), N);

        MatrizMethodsInterface remoto3 = (MatrizMethodsInterface) Naming.lookup(URL + "3");
        int[][] tmpC3 = remoto3.multiplica_matrices(parte_matriz(matrizA, N / 2), parte_matriz(matrizB, N / 2), N);
    
        acomoda_matriz(matrizC, tmpC0, 0, 0);
        acomoda_matriz(matrizC, tmpC1, 0, N / 2);
        acomoda_matriz(matrizC, tmpC2, N / 2, 0);
        acomoda_matriz(matrizC, tmpC3, N / 2, N / 2);

        if(N == 4) {
            muestraMatriz(matrizA, "Matriz A");
            muestraMatriz(matrizB, "Matriz B");
            muestraMatriz(matrizC, "Matriz C");

            System.out.println("Checksum: " + calcularCheksum(matrizC));
        } else 
            System.out.println("Checksum: " + calcularCheksum(matrizC));
    }

    static void muestraMatriz(int[][] matriz, String titulo) {
        System.out.println(titulo);
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                System.out.print(matriz[i][j] + " ");
            }

            System.out.println("");
        }

        System.out.println("");
    }

    static int[][] transponerMatriz(int[][] matriz) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < i; j++) {
                int x = matriz[i][j];
                matriz[i][j] = matriz[j][i];
                matriz[j][i] = x;
            }
        }   

        return matriz;
    }

    static int[][] parte_matriz(int[][] matrizA,int inicio) {
        int[][] matriz = new int[N/2][N];
        
        for (int i = 0; i < N/2; i++)
          for (int j = 0; j < N; j++)
            matriz[i][j] = matrizA[i + inicio][j];
        
        return matriz;
    }

    static void acomoda_matriz(int[][] matrizC,int[][] matrizA,int renglon,int columna) {
        for (int i = 0; i < N/2; i++)
            for (int j = 0; j < N/2; j++)
                matrizC[i + renglon][j + columna] = matrizA[i][j];
    }

    static double calcularCheksum(int[][] matriz) {
        double checksum = 0;
        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++)
                checksum += matriz[i][j];
        
        return checksum;
    }
}
