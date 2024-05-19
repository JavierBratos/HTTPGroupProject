import java.util.HashMap;
import java.util.Map;

public class DataServer {
    private Map<Integer, Alumno> alumnosDatabase;

    public DataServer() {
        alumnosDatabase = new HashMap<>();
    }

    public void addAlumno(Alumno alumno) {
        alumnosDatabase.put(alumno.getId(), alumno);
    }

    public Alumno getAlumno(int id) {
        return alumnosDatabase.get(id);
    }

    public void removeAlumno(int id) {
        alumnosDatabase.remove(id);
    }

    public void updateAlumno(int id, Alumno updatedAlumno) {
        if (alumnosDatabase.containsKey(id)) {
            alumnosDatabase.put(id, updatedAlumno);
        }
    }

    public void printAllAlumnos() {
        for (Alumno alumno : alumnosDatabase.values()) {
            System.out.println("Alumno ID: " + alumno.getId() + ", Name: " + alumno.getName() +
                               ", Lastname: " + alumno.getLastname() + ", Phone: " + alumno.getPhoneNumber());
        }
    }
}
