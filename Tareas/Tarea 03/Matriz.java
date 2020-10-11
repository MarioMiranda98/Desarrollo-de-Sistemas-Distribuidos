import java.net.*;
import java.io.*;

public class Matriz {
    static Object lock = new Object();
    static int N = 4;
    static double[][] matrizA;
    static double[][] matrizB;
    static double[][] matrizC;

    static class Worker extends Thread {
        Socket conexion;

        public Worker(Socket conexion) {
            this.conexion = conexion;
        }

        public void run() {
            try {
                double[][] mA = new double[N / 2][N];
                double[][] mB = new double[N / 2][N];
                DataInputStream entrada = new DataInputStream(conexion.getInputStream());
                DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
                int x = entrada.readInt();

                if(x == 1) {
                    for(int i = 0; i < (N / 2); i++)
                        for(int j = 0; j < N; j++)
                            mA[i][j] = matrizA[i][j];
                    
                    for(int i = 0; i < (N / 2); i++)
                        for(int j = 0; j < N; j++)
                            mB[i][j] = matrizB[i][j];
                } else if(x == 2) {
                    for(int i = 0; i < (N / 2); i++)
                        for(int j = 0; j < N; j++)
                            mA[i][j] = matrizA[i][j];
                    
                    for(int i = (N / 2); i < N; i++)
                        for(int j = 0; j < N; j++)
                            mB[i - (N / 2)][j] = matrizB[i][j];
                } else if(x == 3) {
                    for(int i = (N / 2); i < N; i++)
                        for(int j = 0; j < N; j++)
                            mA[i - (N / 2)][j] = matrizA[i][j];
                    
                    for(int i = 0; i < (N / 2); i++)
                        for(int j = 0; j < N; j++)
                            mB[i][j] = matrizB[i][j];
                } else if(x == 4) {
                    for(int i = (N / 2); i < N; i++)
                        for(int j = 0; j < N; j++)
                            mA[i - (N / 2)][j] = matrizA[i][j];
                    
                    for(int i = (N / 2); i < N; i++)
                        for(int j = 0; j < N; j++)
                            mB[i - (N / 2)][j] = matrizB[i][j];                  
                }

                
                MatrizUtils.enviarMatriz(mA, N, N / 2, salida);
                MatrizUtils.enviarMatriz(mB, N, N / 2, salida);

                synchronized(lock) {
                    double[][] aux;
                    if(x == 1) {
                        aux = MatrizUtils.recibirMatriz(N / 2, N / 2, 0, 0, entrada);
                        copiarMatriz(aux, 0, 0, N / 2, N / 2);
                    }
                    else if(x == 2) {
                        aux = MatrizUtils.recibirMatriz(N, N / 2, N / 2, 0, entrada);
                        copiarMatriz(aux, N / 2, 0, N, N / 2);
                    } else if(x == 3) {
                        aux = MatrizUtils.recibirMatriz(N / 2, N, 0, N / 2, entrada);
                        copiarMatriz(aux, 0, N / 2, N / 2, N);
                    } else if(x == 4) {
                        aux = MatrizUtils.recibirMatriz(N, N, N / 2, N / 2, entrada);
                        copiarMatriz(aux, N / 2, N / 2, N, N);
                    }
                }

                entrada.close();
                salida.close();
                conexion.close();                
            } catch (Exception e) {
                e.printStackTrace();
            }

        }    
    }

    public static void main(String[] args) throws Exception {
        if(args.length != 1) {
            System.err.println("Uso:");
            System.err.println("java Matriz <nodo>");
            System.exit(0);
        }

        int nodo = Integer.valueOf(args[0]);

        if(nodo == 0) {
            matrizA = new double[N][N];
            matrizB = new double[N][N];
            matrizC = new double[N][N];

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    matrizA[i][j] = 2 * i + j;
                    matrizB[i][j] = 2 * i - j;
                    matrizC[i][j] = 0;
                }
            }

            matrizB = MatrizUtils.transponerMatriz(matrizB, N);

            ServerSocket servidor = new ServerSocket(5000);
            Worker[] w = new Worker[4];
            int i = 0;
            while(!(i == 4)) {
                Socket conexion = servidor.accept();
                w[i] = new Worker(conexion);
                w[i].start();
                i += 1;
            }


            i = 0;
            while(!(i == 4)) 
                w[i++].join();

            servidor.close();
            
            if(N == 4) {
                MatrizUtils.mostrarMatriz("Matriz A", matrizA, N, N);
                MatrizUtils.mostrarMatriz("Matriz B (Transpuesta)", matrizB, N, N);
                MatrizUtils.mostrarMatriz("Matriz C", matrizC, N, N);
                System.out.println("Checksum: " + MatrizUtils.calcularChecksum(matrizC, N));
            } else 
                System.out.println("Checksum: " + MatrizUtils.calcularChecksum(matrizC, N));

        }else {            
            double[][] auxA;
            double[][] auxB;
            double[][] auxC = new double[N / 2][N / 2];
            
            if(nodo == 1) {    
                Socket conexion = new Socket("localhost", 5000);

                DataInputStream entrada = new DataInputStream(conexion.getInputStream());
                DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
                salida.writeInt(nodo);

                auxA = MatrizUtils.recibirMatriz(N, N / 2, 0, 0, entrada);
                auxB = MatrizUtils.recibirMatriz(N, N / 2, 0, 0, entrada);

                for(int i = 0; i < (N / 2); i++) {
                    for(int j = 0; j < (N / 2); j++) {
                        double suma = 0;
                        for(int k = 0; k < N; k++) {
                            suma += auxA[i][k] * auxB[j][k];
                            auxC[i][j] = suma;
                        }
                    }
                }
                
                MatrizUtils.enviarMatriz(auxC, N / 2, N / 2, salida);
                
                entrada.close();
                salida.close();
                conexion.close();
            } else if(nodo == 2) {
                Socket conexion = new Socket("localhost", 5000);

                DataInputStream entrada = new DataInputStream(conexion.getInputStream());
                DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
                salida.writeInt(nodo);

                auxA = MatrizUtils.recibirMatriz(N, N / 2, 0, 0, entrada);
                auxB = MatrizUtils.recibirMatriz(N, N / 2, 0, 0, entrada);

                for(int i = 0; i < (N / 2); i++) {
                    for(int j = 0; j < (N /2); j++) {
                        double suma = 0;
                        for(int k = 0; k < N; k++) {
                            suma += auxA[i][k] * auxB[j][k];
                            auxC[i][j] = suma;
                        }
                    }
                }

                MatrizUtils.enviarMatriz(auxC, N / 2, N / 2, salida);
                
                entrada.close();
                salida.close();
                conexion.close();
            } else if(nodo == 3) {
                Socket conexion = new Socket("localhost", 5000);

                DataInputStream entrada = new DataInputStream(conexion.getInputStream());
                DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
                salida.writeInt(nodo);

                auxA = MatrizUtils.recibirMatriz(N, N / 2, 0, 0, entrada);
                auxB = MatrizUtils.recibirMatriz(N, N / 2, 0, 0, entrada);

                for(int i = 0; i < (N / 2); i++) {
                    for(int j = 0; j < (N / 2); j++) {
                        double suma = 0;
                        for(int k = 0; k < N; k++) {
                            suma += auxA[i][k] * auxB[j][k];
                            auxC[i][j] = suma;
                        }
                    }
                }

                MatrizUtils.enviarMatriz(auxC, N / 2, N / 2, salida);
                
                entrada.close();
                salida.close();
                conexion.close();
            } else if(nodo == 4) {
                Socket conexion = new Socket("localhost", 5000);

                DataInputStream entrada = new DataInputStream(conexion.getInputStream());
                DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
                salida.writeInt(nodo);
                
                auxA = MatrizUtils.recibirMatriz(N, N / 2, 0, 0, entrada);
                auxB = MatrizUtils.recibirMatriz(N, N / 2, 0, 0, entrada);

                for(int i = 0; i < (N / 2); i++) {
                    for(int j = 0; j < (N / 2); j++) {
                        double suma = 0;
                        for(int k = 0; k < N; k++) {
                            suma += auxA[i][k] * auxB[j][k];
                            auxC[i][j] = suma;
                        }
                    }
                }

                MatrizUtils.enviarMatriz(auxC, N / 2, N / 2, salida);
                
                entrada.close();
                salida.close();
                conexion.close();
            }
        }
    }

    private static void copiarMatriz(double[][] matrizOrigen, int inicioFilas, int inicioColumnas, int filas, int columnas) {
        for(int i = inicioColumnas; i < columnas; i++) {
            for(int j = inicioFilas; j < filas; j++) {
                matrizC[i][j] = matrizOrigen[i][j];
            }
        }
    }
}