public class Usuario {
    public Usuario() {}

    String getEmail() { return this.email; }
    String getNombre() { return this.nombre; }
    String getApellidoPaterno() { return this.apellidoPaterno; }
    String getApellidoMaterno() { return this.apellidoMaterno; }
    String getFechaNacimiento() { return this.fechaNacimiento; }
    String getTelefono() { return this.telefono; }
    String getGenero() { return this.genero; }
    byte[] getFoto() { return this.foto; }

    void setEmail(String email) { this.email = email; }
    void setNombre(String nombre) { this.nombre = nombre; }
    void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }
    void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }
    void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    void setTelefono(String telefono) { this.telefono = telefono; }
    void setGenero(String genero) { this.genero = genero; }
    void setFoto(byte[] foto) { this.foto = foto; }

    public String toString() {
        return "Email: " + email + "\n" +
                "Nombre: " + nombre + "\n" +
                "Apellido Paterno: " + apellidoPaterno + "\n" +
                "Apellido Materno: " + apellidoMaterno + "\n" + 
                "Fecha de nacimiento: " + fechaNacimiento + "\n" + 
                "Telefono: " + telefono + "\n" + 
                "Genero: " + genero + "\n" +
                "Foto: null";
    }

    private String email;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String fechaNacimiento;
    private String telefono;
    private String genero;
    private byte[] foto;
}