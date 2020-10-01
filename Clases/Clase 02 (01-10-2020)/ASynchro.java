public class ASynchro extends Thread {
  static long n = 0;
  static Object obj = new Object();
  public void run() {
    for (int i = 0; i < 100000; i++)
      synchronized(obj) {
        n++;
      }
  }
  public static void main(String[] args) throws Exception {
    ASynchro t1 = new ASynchro();
    ASynchro t2 = new ASynchro();
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println(n);
  }
}
