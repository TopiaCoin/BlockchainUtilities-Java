package io.topiacoin.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
     * @return A reference to the default, shared notification center.
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

    /**
     * Constructs a new Notification Center.
     */
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
     *
     * @param handler          The Handler object that is being registered
     * @param notificationName The name of the notifications that this handler wishes to receive.  If null, the handler
     *                         will receive notifications with any name that match the classifier.
     * @param classifier       The classifier of the notifications that this handler wishes to receive.  If null, the
     *                         handler will receive notifications with any classifier that match the notification name.
     */
    public void addHandler(NotificationHandler handler, String notificationName, String classifier) {

        String key = calculateKey(notificationName, classifier);
        List<NotificationHandler> handlers = _registrationMap.get(key);
        if (handlers == null) {
            handlers = new ArrayList<NotificationHandler>();
            _registrationMap.put(key, handlers);
        }

        handlers.add(handler);
    }

    /**
     * Removes the specified handler from the notification center's dispatch table.  If the handler is registered for
     * multiple notification names and/or classifiers, it is removed from all of them.
     *
     * @param handler The Handler that is being removed from the Notification Center.  It will be unregisterd from
     *                <b>all</b> notifications that it was registered to receive.
     */
    public void removeHandler(NotificationHandler handler) {
        for (List<NotificationHandler> handlerList : _registrationMap.values()) {
            handlerList.remove(handler);
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
    public void removeHandler(NotificationHandler handler, String notificationName, String classifier) {
        String key = calculateKey(notificationName, classifier);
        List<NotificationHandler> handlers = _registrationMap.get(key);
        if (handlers != null) {
            handlers.remove(handler);
        }
    }

    /**
     * Creates a notification with a given name, classifier, and information and posts it to the notification center.
     * The notification will be dispatched to all handlers whose registration criteria match the notification.
     * Notification name must be specified. Classifier and notification info are optional.
     *
     * @param notificationName The name of the notification being posted.  This cannot be null.
     * @param classifier       The optional classifier of the notification being posted.
     * @param notificationInfo A Map containing additional info that is being posted with this notification.
     *
     * @throws IllegalArgumentException If the notification name is not specified.
     */
    public void postNotification(String notificationName, String classifier, Map<String, Object> notificationInfo) {
        Notification notification = new Notification(notificationName, classifier, notificationInfo);
        postNotification(notification);
    }

    /**
     * Posts the specified notification to the notification center. The notification will be dispatched to all handlers
     * whose registration criteria match the notification.
     *
     * @param notification The notification that is to be send to the handlers registered to receive this notification.
     */
    public void postNotification(Notification notification) {
        Set<NotificationHandler> handlersToNotify = new HashSet<NotificationHandler>();

        String key = calculateKey(notification.getNotificationName(), notification.getClassifier());
        List<NotificationHandler> specificHandlers = _registrationMap.get(key);
        if (specificHandlers != null) {
            handlersToNotify.addAll(specificHandlers);
        }

        key = calculateKey(notification.getNotificationName(), null);
        List<NotificationHandler> typeHandlers = _registrationMap.get(key);
        if (typeHandlers != null) {
            handlersToNotify.addAll(typeHandlers);
        }

        key = calculateKey(null, notification.getClassifier());
        List<NotificationHandler> classifierHandlers = _registrationMap.get(key);
        if (classifierHandlers != null) {
            handlersToNotify.addAll(classifierHandlers);
        }

        for (NotificationHandler curHandler : handlersToNotify) {
            curHandler.handleNotification(notification);
        }
    }

    // -------- Internal Methods --------

    /**
     * Calculates the key used to locate the handlers for this particular notification name and classifier.
     *
     * @param notificationName The name of the notification
     * @param classifier       The classifier of the notification
     *
     * @return A String containing the key where the list of handlers are stored.
     */
    private String calculateKey(String notificationName, String classifier) {
        return notificationName + ":" + classifier;
    }

}
