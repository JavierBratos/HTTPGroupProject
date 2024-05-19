import java.util.HashMap;
import java.util.Map;

public class APIClient {
    private String server;
    private int port;

    public APIClient(String server, int port) {
        this.server = server;
        this.port = port;
    }

    public String getAlumnos() {
        return sendRequest("GET", "/alumnos", null, new HashMap<>());
    }

    public String addAlumno(String json) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return sendRequest("POST", "/alumnos", json, headers);
    }

    public String updateAlumno(int id, String json) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return sendRequest("PUT", "/alumnos/" + id, json, headers);
    }

    public String deleteAlumno(int id) {
        return sendRequest("DELETE", "/alumnos/" + id, null, new HashMap<>());
    }

    private String sendRequest(String method, String endpoint, String body, Map<String, String> headers) {
        MyHTTPClient.sendRequest(this.server, this.port, method, endpoint, headers, body);
        return "Response handled";
    }
}