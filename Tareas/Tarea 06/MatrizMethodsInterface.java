import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MatrizMethodsInterface extends Remote {
    public int[][] multiplica_matrices(int[][] matrizA, int[][] matrizB, int N) throws RemoteException;
}