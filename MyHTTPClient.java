import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MyHTTPClient extends JFrame {
    private static final String API_KEY = "123"; // This should be your actual API key

    private JTextField serverField, endpointField, bodyField, headerKeyField, headerValueField;
    private JComboBox<String> methodBox;
    private JTextArea responseArea, headersArea;
    private JButton sendButton, addHeaderButton;
    private Map<String, String> headers;

    public MyHTTPClient() {
        setTitle("HTTP Client");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        headers = new HashMap<>();
        headers.put("Content-Type", "text/plain");
        headers.put("X-API-Key", API_KEY);

        // Set up the UI components
        serverField = new JTextField(30);
        endpointField = new JTextField(30);
        bodyField = new JTextField(30);
        headerKeyField = new JTextField(10);
        headerValueField = new JTextField(10);

        String[] methods = { "GET", "POST", "PUT", "DELETE", "HEAD" };
        methodBox = new JComboBox<>(methods);

        responseArea = new JTextArea(10, 50);
        responseArea.setEditable(false);

        headersArea = new JTextArea(5, 50);
        headersArea.setEditable(false);

        sendButton = new JButton("Send Request");
        addHeaderButton = new JButton("Add Header");

        // Set up the layout
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(10, 2));

        inputPanel.add(new JLabel("Server:"));
        inputPanel.add(serverField);
        inputPanel.add(new JLabel("Method:"));
        inputPanel.add(methodBox);
        inputPanel.add(new JLabel("Endpoint:"));
        inputPanel.add(endpointField);
        inputPanel.add(new JLabel("Body:"));
        inputPanel.add(bodyField);
        inputPanel.add(new JLabel("Header Key:"));
        inputPanel.add(headerKeyField);
        inputPanel.add(new JLabel("Header Value:"));
        inputPanel.add(headerValueField);
        inputPanel.add(new JLabel(""));
        inputPanel.add(addHeaderButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(headersArea), BorderLayout.CENTER);
        add(new JScrollPane(responseArea), BorderLayout.SOUTH);
        add(sendButton, BorderLayout.EAST);

        // Add action listeners
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendRequest();
            }
        });

        addHeaderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addHeader();
            }
        });
    }

    private void sendRequest() {
        String server = serverField.getText().trim();
        String method = methodBox.getSelectedItem().toString();
        String endpoint = endpointField.getText().trim();
        String body = bodyField.getText().trim();

        new Thread(() -> {
            try (Socket socket = new Socket(server, 8080);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                // Building the request header
                StringBuilder request = new StringBuilder();
                request.append(method).append(" ").append(endpoint).append(" HTTP/1.1\r\n");
                request.append("Host: ").append(server).append("\r\n");

                // Adding headers
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    request.append(header.getKey()).append(": ").append(header.getValue()).append("\r\n");
                }

                if (!body.isEmpty()) {
                    request.append("Content-Length: ").append(body.getBytes().length).append("\r\n");
                }

                request.append("\r\n");
                request.append(body);

                // Sending the request
                out.print(request.toString());
                out.flush();

                // Reading the response
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line).append("\n");
                }

                // Update the response area in the UI
                responseArea.setText(response.toString());

            } catch (IOException e) {
                responseArea.setText("Error in connecting or sending request: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

    private void addHeader() {
        String key = headerKeyField.getText().trim();
        String value = headerValueField.getText().trim();
        if (!key.isEmpty() && !value.isEmpty()) {
            headers.put(key, value);
            headersArea.append(key + ": " + value + "\n");
            headerKeyField.setText("");
            headerValueField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MyHTTPClient().setVisible(true);
            }
        });
    }
}
