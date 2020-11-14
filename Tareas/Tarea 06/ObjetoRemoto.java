import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class ObjetoRemoto extends UnicastRemoteObject implements MatrizMethodsInterface {
    final static long serialVersionUID = 1L;
    public ObjetoRemoto() throws Exception {
        super();
    }

    public int[][] multiplica_matrices(int[][] matrizA, int[][] matrizB, int N) throws RemoteException {
        int[][] C = new int[N/2][N/2];
        
        for (int i = 0; i < N/2; i++)
          for (int j = 0; j < N/2; j++)
            for (int k = 0; k < N; k++)
              C[i][j] += matrizA[i][k] * matrizB[j][k];
        
        return C;
    }
}
