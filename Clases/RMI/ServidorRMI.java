import java.rmi.Naming;

public class ServidorRMI
{
  public static void main(String[] args) throws Exception
  {
    initializeRMI();
    ClaseRMI obj = new ClaseRMI();

    // registra la instancia en el rmiregistry
    Naming.rebind(url,obj);
  }

  public static void initializeRMI() {
     String host = "localhost";
        try {
            //        LocateRegistry.createRegistry(1099);
            Registry registry = LocateRegistry.getRegistry(host, 1099);
            server = (ServidorRMI) registry.lookup("Reg");
            System.out.println("Success");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Fail");
        }  
  } 
}
