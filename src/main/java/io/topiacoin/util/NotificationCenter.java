package io.topiacoin.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The Notification Center acts as a central hub where classes can subscribe to and post notifications and events. The
 * particular implementation of this class depends on the platform and whether that platform has native code that
 * provides the specified functionality (such as NSNotificationCenter on Mac and iOS).
 * <p>
 * When a notification is posted to the notification center, it is synchronously dispatched to all registered handlers.
 * If a handler wishes to handle a notification asynchronously, they are responsible for triggering the asynchronous
 * processing.
 */
public class NotificationCenter {

    private static NotificationCenter _instance;

    /**
     * Returns the default notification center. The notification center is a singleton in the application to insure that
     * all handlers are connected to the same instance.
     *
     * @return
     */
    public static NotificationCenter defaultCenter() {
        synchronized (NotificationCenter.class) {
            if (_instance == null) {
                _instance = new NotificationCenter();
            }
        }

        return _instance;
    }

    private Map<String, List<NotificationHandler>> _registrationMap;

    private NotificationCenter() {
        _registrationMap = new HashMap<String, List<NotificationHandler>>();
    }

    /**
     * Adds an entry to the notification center's dispatch table with a handler, and an optional notification name and
     * object classifier.
     * <p>
     * Specify a notification name to register to only receive notifications with this name. If you pass null, the
     * notification center doesn’t use a notification’s name to decide whether to deliver it to the handler.
     * <p>
     * Specify a classifier to only receive notifications sent with this classifier. If you pass null, the notification
     * center doesn’t use a notification’s sender to decide whether to deliver it to the handler.
     */
    public void addHandler(String notificationName, Object classifier, NotificationHandler handler) {

        String key = notificationName + ":" + classifier ;
        List<NotificationHandler> handlers = _registrationMap.get(key) ;
        if ( handlers == null ) {
            handlers = new ArrayList<NotificationHandler>();
            _registrationMap.put(key, handlers) ;
        }

        handlers.add(handler) ;
    }

    /**
     * Removes the specified handler from the notification center's dispatch table.  If the handler is registered
     * for multiple notification names and/or classifiers, it is removed from all of them.
     */
    public void removeHandler(NotificationHandler handler) {
        for ( List<NotificationHandler> handlerList : _registrationMap.values()) {
            handlerList.remove(handler) ;
        }
    }

    /**
     * Removes matching entries from the notification center's dispatch table.
     * <p>
     * Specify a notification name to remove only entries that specify this notification name. When null, the receiver
     * does not use notification names as criteria for removal.
     * <p>
     * Specify a classifier to remove only entries that specify this classifier. When null, the receiver does not use
     * notification classifier as criteria for removal.
     */
    public void removeHandler(NotificationHandler handler, String notificationName, Object classifier) {
        String key = notificationName + ":" + classifier ;
        List<NotificationHandler> handlers = _registrationMap.get(key) ;
        if ( handlers != null ) {
            handlers.remove(handler);
        }
    }

    /**
     * Creates a notification with a given name, classifier, and information and posts it to the notification center.
     * The notification will be dispatched to all handlers whose registration criteria match the notification.
     * Notification name must be specified. Classifier and notification info are optional.
     */
    public void postNotification(String notificationName, Object classifier, Map<String, Object> notificationInfo) {
        Notification notification = new Notification(notificationName, classifier, notificationInfo) ;
        postNotification(notification);
    }

    /**
     * Posts the specified notification to the notification center. The notification will be dispatched to all handlers
     * whose registration criteria match the notification. Notification name must be specified. Classifier and
     * notification info are optional.
     */
    public void postNotification(Notification notification) {
        Set<NotificationHandler> handlersToNotify = new HashSet<NotificationHandler>() ;

        String key = notification.getNotificationName() + ":" + notification.getClassifier() ;
        List<NotificationHandler> specificHandlers = _registrationMap.get(key) ;
        if ( specificHandlers != null ) {
            handlersToNotify.addAll(specificHandlers);
        }

        key = notification.getNotificationName() + ":" + null ;
        List<NotificationHandler> typeHandlers = _registrationMap.get(key) ;
        if ( typeHandlers != null ) {
            handlersToNotify.addAll(typeHandlers);
        }

        key = null + ":" + notification.getClassifier() ;
        List<NotificationHandler> classifierHandlers = _registrationMap.get(key) ;
        if ( classifierHandlers != null ) {
            handlersToNotify.addAll(classifierHandlers);
        }

        for ( NotificationHandler curHandler : handlersToNotify ) {
            curHandler.handleNotification(notification);
        }
    }

}