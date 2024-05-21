import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

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
    public String getAllAlumnos() {
        JSONArray jsonArray = new JSONArray();
        for (Alumno alumno : alumnosDatabase.values()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", alumno.getId());
            jsonObject.put("name", alumno.getName());
            jsonObject.put("lastname", alumno.getLastname());
            jsonObject.put("phoneNumber", alumno.getPhoneNumber());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }
}
