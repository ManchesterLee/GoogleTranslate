package cn.manchesterlee.action;

import cn.manchesterlee.parser.GoogleTranslateResultParser;
import cn.manchesterlee.translator.GoogleTranslator;
import cn.manchesterlee.translator.Translator;
import cn.manchesterlee.ui.DialogFactory;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;

/**
 * @author ManchesterLee  <a href="mailto:man.chester.lee.cn@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2018/12/27
 */
public class TranslateAction extends AnAction {
    private Notification notification;
    private Translator translator = new GoogleTranslator(new GoogleTranslateResultParser());

    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        SelectionModel model = editor.getSelectionModel();
        String selectedText = model.getSelectedText();
        selectedText = selectedText == null ? "" : selectedText.trim();
        if (selectedText.isEmpty()) {
            return;
        }
        translate(editor, selectedText);
    }

    private void translate(Editor editor, String text) {
        DialogFactory.notify("Translate...", NotificationType.INFORMATION);
        try {
            String result = translator.translate("en_US", text);
            DialogFactory.showPopupWindow(editor, result);
        } catch (Exception e) {
            DialogFactory.notify("Translate failed", NotificationType.ERROR);
        }
    }

}
