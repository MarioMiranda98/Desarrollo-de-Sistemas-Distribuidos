import java.rmi.Naming;

public class ServidorMatriz {
    public static void main(String[] args) throws Exception {
        Naming.rebind("rmi://localhost/nodo" + args[0], new ObjetoRemoto());
    }
}