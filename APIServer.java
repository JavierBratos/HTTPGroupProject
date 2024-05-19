import org.json.JSONObject;

public class APIServer {
    private static DataServer dataServer = new DataServer(); // La instancia del servidor de datos.
    private static final String CONTENT_TYPE_JSON = "application/json";

    // Maneja las solicitudes GET
    public static String handleGet(String endpoint) {
        if ("/alumnos".equals(endpoint)) {
            return formatResponse(200, dataServer.printAllAlumnos(), CONTENT_TYPE_JSON);
        }
        return formatResponse(404, "{\"error\":\"Resource not found\"}", CONTENT_TYPE_JSON);
    }

    // Maneja las solicitudes POST
    public static String handlePost(String endpoint, String body) {
        if ("/alumnos".equals(endpoint)) {
            Alumno alumno = parseAlumno(body);
            dataServer.addAlumno(alumno);
            return formatResponse(201, "{\"message\":\"Alumno added successfully\"}", CONTENT_TYPE_JSON);
        }
        return formatResponse(404, "{\"error\":\"Resource not found\"}", CONTENT_TYPE_JSON);
    }

    // Maneja las solicitudes PUT
    public static String handlePut(String endpoint, String body) {
        if (endpoint.startsWith("/alumnos/")) {
            int id = Integer.parseInt(endpoint.split("/")[2]);
            Alumno updatedAlumno = parseAlumno(body);
            dataServer.updateAlumno(id, updatedAlumno);
            return formatResponse(200, "{\"message\":\"Alumno updated successfully\"}", CONTENT_TYPE_JSON);
        }
        return formatResponse(404, "{\"error\":\"Resource not found\"}", CONTENT_TYPE_JSON);
    }

    // Maneja las solicitudes DELETE
    public static String handleDelete(String endpoint) {
        if (endpoint.startsWith("/alumnos/")) {
            int id = Integer.parseInt(endpoint.split("/")[2]);
            dataServer.removeAlumno(id);
            return formatResponse(200, "{\"message\":\"Alumno deleted successfully\"}", CONTENT_TYPE_JSON);
        }
        return formatResponse(404, "{\"error\":\"Resource not found\"}", CONTENT_TYPE_JSON);
    }

    // Maneja las solicitudes HEAD
    public static String handleHead(String endpoint) {
        if ("/alumnos".equals(endpoint)) {
            String headers = "Content-Type: " + CONTENT_TYPE_JSON + "\r\n" +
                    "Content-Length: " + calculateJsonContentLength(dataServer.printAllAlumnos());
            return formatResponseHeadersOnly(200, headers);
        }
        return formatResponseHeadersOnly(404, "Content-Type: " + CONTENT_TYPE_JSON);
    }

    // Formatea la respuesta HTTP con cuerpo
    private static String formatResponse(int statusCode, String content, String contentType) {
        return "HTTP/1.1 " + statusCode + " " + getStatusText(statusCode) + "\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "\r\n" +
                content;
    }

    // Formatea la respuesta HTTP solo con encabezados para HEAD
    private static String formatResponseHeadersOnly(int statusCode, String headers) {
        return "HTTP/1.1 " + statusCode + " " + getStatusText(statusCode) + "\r\n" +
                headers + "\r\n" +
                "\r\n";
    }

    // Parsea el cuerpo de la solicitud a un objeto Alumno
    private static Alumno parseAlumno(String json) {
        JSONObject jsonObject = new JSONObject(json);
        int id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        String lastname = jsonObject.getString("lastname");
        String phoneNumber = jsonObject.getString("phoneNumber");
        return new Alumno(id, name, lastname, phoneNumber);
    }

    // Devuelve el texto de estado HTTP basado en el cÃ³digo
    private static String getStatusText(int statusCode) {
        switch (statusCode) {
            case 200:
                return "OK";
            case 201:
                return "Created";
            case 404:
                return "Not Found";
            default:
                return "Error";
        }
    }

    // Calcula la longitud del contenido JSON como ejemplo
    private static int calculateJsonContentLength(String json) {
        return json.getBytes().length;
    }
}