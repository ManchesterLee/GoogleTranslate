package cn.manchesterlee.action;

import cn.manchesterlee.net.Callback;
import cn.manchesterlee.net.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.JBColor;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ManchesterLee  <a href="mailto:man.chester.lee.cn@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2018/12/27
 */
public class TranslateAction extends AnAction {
    private static final long FADEOUT_TIME = TimeUnit.SECONDS.toMillis(90);
    private static final Color COLOR = new JBColor(new Color(250, 251, 223), Color.DARK_GRAY);
    private static final String API_TRANSLATE = "http://translate.google.cn/translate_a/single?client=gtx&dt=t&dj=1&ie=UTF-8&sl=auto&tl=zh_CN&q=";
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final List<String> INVALID_CHARACTERS = Collections.unmodifiableList(
            Arrays.asList("/\\*\\*", "\\*", "\n")
    );
    private Notification notification;


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
        selectedText = filterCharacter(selectedText);
        translate(editor, selectedText);
    }

    private void translate(Editor editor, String text) {
        showNotification("Translating...", NotificationType.INFORMATION);
        try {
            HttpUtils.call(API_TRANSLATE + URLEncoder.encode(text, "UTF-8"), "GET", new Callback() {
                @Override
                public void onSuccess(String result) {
                    closeNotification(notification);
                    try {
                        Map map = objectMapper.readValue(result, Map.class);
                        if (map.containsKey("sentences")) {
                            List list = (List) map.get("sentences");
                            if (!list.isEmpty()) {
                                StringBuilder sb = new StringBuilder();
                                for (Object o : list) {
                                    Map m = (Map) o;
                                    if (m.containsKey("trans")) {
                                        sb.append((String) m.get("trans"));
                                    }
                                }
                                showPopupWindow(editor, sb.toString());
                            }
                        }
                    } catch (Throwable ignore) {
                        showNotification("Translate failed", NotificationType.ERROR);
                    }
                }

                @Override
                public void onFailure(String message) {
                    showNotification("Translate failed", NotificationType.ERROR);
                }
            });
        } catch (UnsupportedEncodingException e) {
            showNotification("Translate failed", NotificationType.ERROR);
        }
    }

    private void showPopupWindow(Editor editor, String text) {
        ApplicationManager.getApplication()
                .invokeLater(() -> JBPopupFactory.getInstance()
                        .createHtmlTextBalloonBuilder(text, null, COLOR, null)
                        .setFadeoutTime(FADEOUT_TIME)
                        .setHideOnAction(true)
                        .createBalloon()
                        .show(JBPopupFactory.getInstance().guessBestPopupLocation(editor), Balloon.Position.below));
    }

    private void showNotification(String content, NotificationType type) {
        notification = new Notification("translator", "Google Translator", content, type);
        Notifications.Bus.notify(notification);
    }

    private void closeNotification(Notification notification) {
        if (notification != null && !notification.isExpired()) {
            notification.expire();
        }
    }

    private String filterCharacter(String text) {
        for (String invalidCharacter : INVALID_CHARACTERS) {
            text = text.replaceAll(invalidCharacter, "");
        }
        return text;
    }

}
