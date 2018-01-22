package io.topiacoin.util;

import java.util.HashMap;
import java.util.Map;

public class Notification {

    private String notificationName;
    private Object classifier ;
    private Map<String, Object> notificationInfo;

    public Notification(String notificationName, Object classifier, Map<String, Object> notificationInfo) {
        this.notificationName = notificationName;
        this.classifier = classifier;
        if ( notificationInfo != null ) {
            this.notificationInfo = new HashMap<String, Object>(notificationInfo);
        }
    }

    public String getNotificationName() {
        return notificationName;
    }

    public Object getClassifier() {
        return classifier;
    }

    public Map<String, Object> getNotificationInfo() {
        return notificationInfo;
    }
}
