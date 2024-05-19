import java.util.HashMap;
import java.util.Map;

public class DataServer {
    private Map<Integer, Alumno> alumnosDatabase;

    public DataServer() {
        alumnosDatabase = new HashMap<>();
    }

    // Method POST
    public void addAlumno(Alumno alumno) {
        alumnosDatabase.put(alumno.getId(), alumno);
    }

    // Not a method
    public Alumno getAlumno(int id) {
        return alumnosDatabase.get(id);
    }

    // Method DELETE
    public void removeAlumno(int id) {
        alumnosDatabase.remove(id);
    }

    // Method PUT
    public void updateAlumno(int id, Alumno updatedAlumno) {
        if (alumnosDatabase.containsKey(id)) {
            alumnosDatabase.put(id, updatedAlumno);
        }
    }

    // Method GET
    public void printAllAlumnos() {
        for (Alumno alumno : alumnosDatabase.values()) {
            System.out.println("Alumno ID: " + alumno.getId() + ", Name: " + alumno.getName() +
                    ", Lastname: " + alumno.getLastname() + ", Phone: " + alumno.getPhoneNumber());
        }
    }
}
