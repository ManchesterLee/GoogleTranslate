package cn.manchesterlee.ui;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.JBColor;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * @author ManchesterLee  <a href="mailto:man.chester.lee.cn@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-01-14
 */
public class DialogFactory {
    private static final long FADEOUT_TIME = TimeUnit.SECONDS.toMillis(90);
    private static final Color COLOR = new JBColor(new Color(250, 251, 223), Color.DARK_GRAY);

    public static void showPopupWindow(Editor editor, String text) {
        ApplicationManager.getApplication()
                .invokeLater(() -> JBPopupFactory.getInstance()
                        .createHtmlTextBalloonBuilder(text, null, COLOR, null)
                        .setFadeoutTime(FADEOUT_TIME)
                        .setHideOnAction(true)
                        .createBalloon()
                        .show(JBPopupFactory.getInstance().guessBestPopupLocation(editor), Balloon.Position.below));
    }

    public static Notification notify(String content, NotificationType type) {
        Notification notification = new Notification("translate", "Google Translate", content, type);
        Notifications.Bus.notify(notification);
        return notification;
    }

    public static void closeNotify(Notification notification) {
        if (notification != null && !notification.isExpired()) {
            notification.expire();
        }
    }

}
