package toolwindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import toolwindow.ChatbotToolWindow;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

public class ChatbotToolWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        ChatbotToolWindow chatbotToolWindow = new ChatbotToolWindow();
        ContentFactory contentFactory = ContentFactory.getInstance();
        Content content = contentFactory.createContent(chatbotToolWindow.getContent(), "Error Chatbot", false);
        toolWindow.getContentManager().addContent(content);
    }

}