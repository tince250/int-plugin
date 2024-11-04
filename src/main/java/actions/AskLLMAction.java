package actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import toolwindow.ChatbotToolWindow;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;

public class AskLLMAction extends DumbAwareAction {

    private final ChatbotClient chatbotClient = new ChatbotClient();

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();
        String selectedText = caretModel.getCurrentCaret().getSelectedText();

        if (selectedText != null && !selectedText.isEmpty()) {
            String response = chatbotClient.promptLLM(selectedText);

            Project project = e.getProject();
            if (project != null) {
                ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Error Wizzard");
                if (toolWindow != null) {
                    ChatbotToolWindow chatbotToolWindow = ChatbotToolWindow.getInstance();
                    if (chatbotToolWindow != null) {
                        chatbotToolWindow.updateResponse(response);
                        toolWindow.show();
                    }
                }
            }
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();
        String selectedText = caretModel.getCurrentCaret().getSelectedText();
        e.getPresentation().setVisible(selectedText != null && !selectedText.isEmpty());
    }
}
