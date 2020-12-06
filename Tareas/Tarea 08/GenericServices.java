import java.io.*;
import java.net.*;

public class GenericServices {
    public GenericServices() {}
    
    static String hacerConsulta(String cuerpo, String metodo, String endpoint) {
        try {
            URL url = new URL(URL_MAQUINA + endpoint);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            
            conexion.setDoOutput(true);
            conexion.setRequestMethod(metodo);
            conexion.setRequestProperty(REQUEST_KEY, VALUE_KEY);

            if(cuerpo.length() > 0) {
                String parametros = "usuario=" + URLEncoder.encode(cuerpo, "UTF-8");
                OutputStream os = conexion.getOutputStream();
                os.write(parametros.getBytes());
                os.flush();
            }

            if(conexion.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new RuntimeException("Codigo de error HTTP: " + conexion.getResponseCode());

            BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            String respuestaServidor;
            String respuesta = "";

            while((respuestaServidor = br.readLine()) != null) respuesta += respuestaServidor;
            conexion.disconnect();

            return respuesta;
        } catch(Exception e) { e.printStackTrace(); }
        
        return "Hola";
    }

    private final static String URL_MAQUINA = "http://localhost:8080/Servicio/rest/ws/";
    private final static String REQUEST_KEY = "Content-Type";
    private final static String VALUE_KEY = "application/x-www-form-urlencoded";
}