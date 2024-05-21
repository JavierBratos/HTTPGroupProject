import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private static final int DEFAULT_PORT = 8080;
    private static final String EXPECTED_API_KEY = "a123";
    private API api = new API("localhost", 8080); // API instance

    private Map<String, Map<String, Handler>> routeHandlers = new HashMap<>();

    public void on(String method, String endpoint, Handler handler) {
        Map<String, Handler> methodHandlers = routeHandlers.getOrDefault(method, new HashMap<>());
        methodHandlers.put(endpoint, handler);
        routeHandlers.put(method, methodHandlers);
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            ExecutorService executorService = Executors.newCachedThreadPool();

            while (true) {
                Socket socket = serverSocket.accept();
                executorService.submit(new RequestHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class RequestHandler implements Runnable {
        private Socket socket;

        public RequestHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

                String requestLine = reader.readLine();
                if (requestLine == null || requestLine.isEmpty())
                    return;

                String[] tokens = requestLine.split(" ");
                if (tokens.length < 2)
                    return;

                String method = tokens[0];
                String endpoint = tokens[1];

                Map<String, Handler> methodHandlers = routeHandlers.getOrDefault(method, new HashMap<>());
                Handler handler = methodHandlers.get(endpoint);

                String response;
                if (handler != null && validateApiKey(reader)) {
                    response = handler.handle();
                } else {
                    response = "HTTP/1.1 401 Unauthorized\r\n\r\nUnauthorized: Invalid API Key";
                }

                writer.println(response);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private boolean validateApiKey(BufferedReader reader) throws IOException {
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                if (line.toLowerCase().startsWith("x-api-key:")) {
                    return line.substring(10).trim().equals(EXPECTED_API_KEY);
                }
            }
            return false;
        }
    }

    public interface Handler {
        String handle();
    }

    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
        HttpServer server = new HttpServer();

        server.on("GET", "/alumnos", () -> server.api.handleGet("/alumnos"));
        server.on("POST", "/alumnos", () -> server.api.handlePost("/alumnos", "{json body}"));
        server.on("PUT", "/alumnos", () -> server.api.handlePut("/alumnos", "{json body}"));
        server.on("DELETE", "/alumnos", () -> server.api.handleDelete("/alumnos"));
        server.on("HEAD", "/alumnos", () -> server.api.handleHead("/alumnos"));

        server.start(port);
    }
}
