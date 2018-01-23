package io.topiacoin.util;

import java.util.HashMap;
import java.util.Map;

public class Notification {

    private String notificationName;
    private String classifier ;
    private Map<String, Object> notificationInfo;

    public Notification(String notificationName, String classifier, Map<String, Object> notificationInfo) {
        if (notificationName == null || notificationName.trim().length() == 0) {
            throw new IllegalArgumentException("Notification name must be specified.");
        }
        this.notificationName = notificationName;
        this.classifier = classifier;
        if ( notificationInfo != null ) {
            this.notificationInfo = new HashMap<String, Object>(notificationInfo);
        }
    }

    public String getNotificationName() {
        return notificationName;
    }

    public String getClassifier() {
        return classifier;
    }

    public Map<String, Object> getNotificationInfo() {
        return notificationInfo;
    }
}
