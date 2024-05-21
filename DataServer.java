import java.util.HashMap;
import java.util.Map;

public class DataServer {
    private Map<Integer, Alumno> alumnosDatabase;

    public DataServer() {
        alumnosDatabase = new HashMap<>();
    }

    // Adds an Alumno to the database
    public void addAlumno(Alumno alumno) {
        alumnosDatabase.put(alumno.getId(), alumno);
    }

    // Retrieves an Alumno by ID
    public Alumno getAlumno(int id) {
        return alumnosDatabase.get(id);
    }

    // Removes an Alumno from the database by ID
    public void removeAlumno(int id) {
        alumnosDatabase.remove(id);
    }

    // Updates an existing Alumno in the database
    public void updateAlumno(int id, Alumno updatedAlumno) {
        if (alumnosDatabase.containsKey(id)) {
            alumnosDatabase.put(id, updatedAlumno);
        }
    }

    // Retrieves all Alumnos in a string format
    public String getAllAlumnos() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        boolean first = true;
        for (Alumno alumno : alumnosDatabase.values()) {
            if (!first) {
                sb.append(", ");
            }
            sb.append("{");
            sb.append("\"id\": ").append(alumno.getId()).append(", ");
            sb.append("\"name\": \"").append(alumno.getName()).append("\", ");
            sb.append("\"lastname\": \"").append(alumno.getLastname()).append("\", ");
            sb.append("\"phoneNumber\": \"").append(alumno.getPhoneNumber()).append("\"");
            sb.append("}");
            first = false;
        }
        sb.append("]");
        return sb.toString();
    }
}