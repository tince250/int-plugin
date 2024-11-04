package toolwindow;

import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.awt.*;

public class ChatbotToolWindow {

    private static ChatbotToolWindow instance;
    private JPanel contentPanel;
    private JTextArea responseArea;

    public ChatbotToolWindow() {
        contentPanel = new JPanel(new BorderLayout());
        responseArea = new JTextArea();
        responseArea.setEditable(false);
        contentPanel.add(new JScrollPane(responseArea), BorderLayout.CENTER);

        instance = this;
    }

    public static ChatbotToolWindow getInstance() {
        return instance;
    }

    public JPanel getContent() {
        return contentPanel;
    }

    public void updateResponse(String response) {
        responseArea.setText(response);
    }
}
