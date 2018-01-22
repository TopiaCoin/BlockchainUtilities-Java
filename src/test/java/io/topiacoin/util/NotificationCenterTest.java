package io.topiacoin.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class NotificationCenterTest {

    private static class TestNotificationHandler implements NotificationHandler {

        List<Notification> receivedNotifications = new ArrayList<Notification>();

        public void handleNotification(Notification notification) {
            receivedNotifications.add(notification);
        }
    }


    @Test
    public void testNotificationDeliveryMatrixWithNotifications() {
        String notificationName = "testNotification";
        String otherNotificationName = "otherTestNotification";
        Object classifier = "classifier";
        Object otherClassifier = "otherClassifier";
        Map<String, Object> notificationInfo = null;

        // Counters for tracking how many notifications should have been received by each handler.
        int expectedTCReceived = 0;
        int expectedTOCReceived = 0;
        int expectedTReceived = 0;

        int expectedOTCReceived = 0;
        int expectedOTOCReceived = 0;
        int expectedOTReceived = 0;

        int expectedCReceived = 0;
        int expectedOCReceived = 0;

        // Create the notifications that we will be posting.
        Notification tcNotification = new Notification(notificationName, classifier, notificationInfo);
        Notification tocNotification = new Notification(notificationName, otherClassifier, notificationInfo);
        Notification tNotification = new Notification(notificationName, null, notificationInfo);

        Notification otcNotification = new Notification(otherNotificationName, classifier, notificationInfo);
        Notification otocNotification = new Notification(otherNotificationName, otherClassifier, notificationInfo);
        Notification otNotification = new Notification(otherNotificationName, null, notificationInfo);

        Notification cNotification = new Notification(null, classifier, notificationInfo);
        Notification ocNotification = new Notification(null, otherClassifier, notificationInfo);

        NotificationCenter notificationCenter = NotificationCenter.defaultCenter();

        // Create the Notification Handlers that we will be testing with
        TestNotificationHandler tcHandler = new TestNotificationHandler();
        TestNotificationHandler tocHandler = new TestNotificationHandler();
        TestNotificationHandler tHandler = new TestNotificationHandler();

        TestNotificationHandler otcHandler = new TestNotificationHandler();
        TestNotificationHandler otocHandler = new TestNotificationHandler();
        TestNotificationHandler otHandler = new TestNotificationHandler();

        TestNotificationHandler cHandler = new TestNotificationHandler();
        TestNotificationHandler ocHandler = new TestNotificationHandler();

        // Add the handlers with the appropriate types and classifiers
        notificationCenter.addHandler(notificationName, classifier, tcHandler);
        notificationCenter.addHandler(notificationName, otherClassifier, tocHandler);
        notificationCenter.addHandler(notificationName, null, tHandler);

        notificationCenter.addHandler(otherNotificationName, classifier, otcHandler);
        notificationCenter.addHandler(otherNotificationName, otherClassifier, otocHandler);
        notificationCenter.addHandler(otherNotificationName, null, otHandler);

        notificationCenter.addHandler(null, classifier, cHandler);
        notificationCenter.addHandler(null, otherClassifier, ocHandler);

        // Post the Type/Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(tcNotification);

        expectedTCReceived++;
        expectedTReceived++;
        expectedCReceived++;

        assertEquals("Type/Classifier ReceiveCount Wrong", expectedTCReceived, tcHandler.receivedNotifications.size());
        assertEquals("Type/Other Classifier ReceiveCount Wrong", expectedTOCReceived, tocHandler.receivedNotifications.size());
        assertEquals("Type ReceiveCount Wrong", expectedTReceived, tHandler.receivedNotifications.size());
        assertEquals("Other Type/Classifier ReceiveCount Wrong", expectedOTCReceived, otcHandler.receivedNotifications.size());
        assertEquals("Other Type/Other Classifier ReceiveCount Wrong", expectedOTOCReceived, otocHandler.receivedNotifications.size());
        assertEquals("Other Type ReceiveCount Wrong", expectedOTReceived, otHandler.receivedNotifications.size());
        assertEquals("Classifier ReceiveCount Wrong", expectedCReceived, cHandler.receivedNotifications.size());
        assertEquals("Other Classifier ReceiveCount Wrong", expectedOCReceived, ocHandler.receivedNotifications.size());

        // Post the Type/Other Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(tocNotification);

        expectedTOCReceived++;
        expectedTReceived++;
        expectedOCReceived++;

        assertEquals("Type/Classifier ReceiveCount Wrong", expectedTCReceived, tcHandler.receivedNotifications.size());
        assertEquals("Type/Other Classifier ReceiveCount Wrong", expectedTOCReceived, tocHandler.receivedNotifications.size());
        assertEquals("Type ReceiveCount Wrong", expectedTReceived, tHandler.receivedNotifications.size());
        assertEquals("Other Type/Classifier ReceiveCount Wrong", expectedOTCReceived, otcHandler.receivedNotifications.size());
        assertEquals("Other Type/Other Classifier ReceiveCount Wrong", expectedOTOCReceived, otocHandler.receivedNotifications.size());
        assertEquals("Other Type ReceiveCount Wrong", expectedOTReceived, otHandler.receivedNotifications.size());
        assertEquals("Classifier ReceiveCount Wrong", expectedCReceived, cHandler.receivedNotifications.size());
        assertEquals("Other Classifier ReceiveCount Wrong", expectedOCReceived, ocHandler.receivedNotifications.size());

        // Post the Type Only Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(tNotification);

        expectedTReceived++;

        assertEquals("Type/Classifier ReceiveCount Wrong", expectedTCReceived, tcHandler.receivedNotifications.size());
        assertEquals("Type/Other Classifier ReceiveCount Wrong", expectedTOCReceived, tocHandler.receivedNotifications.size());
        assertEquals("Type ReceiveCount Wrong", expectedTReceived, tHandler.receivedNotifications.size());
        assertEquals("Other Type/Classifier ReceiveCount Wrong", expectedOTCReceived, otcHandler.receivedNotifications.size());
        assertEquals("Other Type/Other Classifier ReceiveCount Wrong", expectedOTOCReceived, otocHandler.receivedNotifications.size());
        assertEquals("Other Type ReceiveCount Wrong", expectedOTReceived, otHandler.receivedNotifications.size());
        assertEquals("Classifier ReceiveCount Wrong", expectedCReceived, cHandler.receivedNotifications.size());
        assertEquals("Other Classifier ReceiveCount Wrong", expectedOCReceived, ocHandler.receivedNotifications.size());

        // Post the Other Type/Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(otcNotification);

        expectedOTCReceived++;
        expectedOTReceived++;
        expectedCReceived++;

        assertEquals("Type/Classifier ReceiveCount Wrong", expectedTCReceived, tcHandler.receivedNotifications.size());
        assertEquals("Type/Other Classifier ReceiveCount Wrong", expectedTOCReceived, tocHandler.receivedNotifications.size());
        assertEquals("Type ReceiveCount Wrong", expectedTReceived, tHandler.receivedNotifications.size());
        assertEquals("Other Type/Classifier ReceiveCount Wrong", expectedOTCReceived, otcHandler.receivedNotifications.size());
        assertEquals("Other Type/Other Classifier ReceiveCount Wrong", expectedOTOCReceived, otocHandler.receivedNotifications.size());
        assertEquals("Other Type ReceiveCount Wrong", expectedOTReceived, otHandler.receivedNotifications.size());
        assertEquals("Classifier ReceiveCount Wrong", expectedCReceived, cHandler.receivedNotifications.size());
        assertEquals("Other Classifier ReceiveCount Wrong", expectedOCReceived, ocHandler.receivedNotifications.size());

        // Post the Other Type/Other Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(otocNotification);

        expectedOTOCReceived++;
        expectedOTReceived++;
        expectedOCReceived++;

        assertEquals("Type/Classifier ReceiveCount Wrong", expectedTCReceived, tcHandler.receivedNotifications.size());
        assertEquals("Type/Other Classifier ReceiveCount Wrong", expectedTOCReceived, tocHandler.receivedNotifications.size());
        assertEquals("Type ReceiveCount Wrong", expectedTReceived, tHandler.receivedNotifications.size());
        assertEquals("Other Type/Classifier ReceiveCount Wrong", expectedOTCReceived, otcHandler.receivedNotifications.size());
        assertEquals("Other Type/Other Classifier ReceiveCount Wrong", expectedOTOCReceived, otocHandler.receivedNotifications.size());
        assertEquals("Other Type ReceiveCount Wrong", expectedOTReceived, otHandler.receivedNotifications.size());
        assertEquals("Classifier ReceiveCount Wrong", expectedCReceived, cHandler.receivedNotifications.size());
        assertEquals("Other Classifier ReceiveCount Wrong", expectedOCReceived, ocHandler.receivedNotifications.size());

        // Post the Other Type Only Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(otNotification);

        expectedOTReceived++;

        assertEquals("Type/Classifier ReceiveCount Wrong", expectedTCReceived, tcHandler.receivedNotifications.size());
        assertEquals("Type/Other Classifier ReceiveCount Wrong", expectedTOCReceived, tocHandler.receivedNotifications.size());
        assertEquals("Type ReceiveCount Wrong", expectedTReceived, tHandler.receivedNotifications.size());
        assertEquals("Other Type/Classifier ReceiveCount Wrong", expectedOTCReceived, otcHandler.receivedNotifications.size());
        assertEquals("Other Type/Other Classifier ReceiveCount Wrong", expectedOTOCReceived, otocHandler.receivedNotifications.size());
        assertEquals("Other Type ReceiveCount Wrong", expectedOTReceived, otHandler.receivedNotifications.size());
        assertEquals("Classifier ReceiveCount Wrong", expectedCReceived, cHandler.receivedNotifications.size());
        assertEquals("Other Classifier ReceiveCount Wrong", expectedOCReceived, ocHandler.receivedNotifications.size());

        // Post the Classifier Only Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(cNotification);

        expectedCReceived++;

        assertEquals("Type/Classifier ReceiveCount Wrong", expectedTCReceived, tcHandler.receivedNotifications.size());
        assertEquals("Type/Other Classifier ReceiveCount Wrong", expectedTOCReceived, tocHandler.receivedNotifications.size());
        assertEquals("Type ReceiveCount Wrong", expectedTReceived, tHandler.receivedNotifications.size());
        assertEquals("Other Type/Classifier ReceiveCount Wrong", expectedOTCReceived, otcHandler.receivedNotifications.size());
        assertEquals("Other Type/Other Classifier ReceiveCount Wrong", expectedOTOCReceived, otocHandler.receivedNotifications.size());
        assertEquals("Other Type ReceiveCount Wrong", expectedOTReceived, otHandler.receivedNotifications.size());
        assertEquals("Classifier ReceiveCount Wrong", expectedCReceived, cHandler.receivedNotifications.size());
        assertEquals("Other Classifier ReceiveCount Wrong", expectedOCReceived, ocHandler.receivedNotifications.size());

        // Post the Other Classifier Only Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(ocNotification);

        expectedOCReceived++;

        assertEquals("Type/Classifier ReceiveCount Wrong", expectedTCReceived, tcHandler.receivedNotifications.size());
        assertEquals("Type/Other Classifier ReceiveCount Wrong", expectedTOCReceived, tocHandler.receivedNotifications.size());
        assertEquals("Type ReceiveCount Wrong", expectedTReceived, tHandler.receivedNotifications.size());
        assertEquals("Other Type/Classifier ReceiveCount Wrong", expectedOTCReceived, otcHandler.receivedNotifications.size());
        assertEquals("Other Type/Other Classifier ReceiveCount Wrong", expectedOTOCReceived, otocHandler.receivedNotifications.size());
        assertEquals("Other Type ReceiveCount Wrong", expectedOTReceived, otHandler.receivedNotifications.size());
        assertEquals("Classifier ReceiveCount Wrong", expectedCReceived, cHandler.receivedNotifications.size());
        assertEquals("Other Classifier ReceiveCount Wrong", expectedOCReceived, ocHandler.receivedNotifications.size());

        System.out.println("Type/Classifier Handler notification Count: " + tcHandler.receivedNotifications.size());
        System.out.println("Type/Other Classifier Handler notification Count: " + tocHandler.receivedNotifications.size());
        System.out.println("Type/No Classifier Handler notification Count: " + tHandler.receivedNotifications.size());
        System.out.println("Other Type/Classifier Handler notification Count: " + otcHandler.receivedNotifications.size());
        System.out.println("Other Type/Other Classifier Handler notification Count: " + otocHandler.receivedNotifications.size());
        System.out.println("Other Type/No Classifier Handler notification Count: " + otHandler.receivedNotifications.size());
        System.out.println("No Type/Classifier Handler notification Count: " + cHandler.receivedNotifications.size());
        System.out.println("No Type/Other Classifier Handler notification Count: " + ocHandler.receivedNotifications.size());
    }


    @Test
    public void testNotificationDeliveryMatrixWithNameAndClassifier() {
        String notificationName = "testNotification";
        String otherNotificationName = "otherTestNotification";
        Object classifier = "classifier";
        Object otherClassifier = "otherClassifier";
        Map<String, Object> notificationInfo = null;

        // Counters for tracking how many notifications should have been received by each handler.
        int expectedTCReceived = 0;
        int expectedTOCReceived = 0;
        int expectedTReceived = 0;

        int expectedOTCReceived = 0;
        int expectedOTOCReceived = 0;
        int expectedOTReceived = 0;

        int expectedCReceived = 0;
        int expectedOCReceived = 0;

        NotificationCenter notificationCenter = NotificationCenter.defaultCenter();

        // Create the Notification Handlers that we will be testing with
        TestNotificationHandler tcHandler = new TestNotificationHandler();
        TestNotificationHandler tocHandler = new TestNotificationHandler();
        TestNotificationHandler tHandler = new TestNotificationHandler();

        TestNotificationHandler otcHandler = new TestNotificationHandler();
        TestNotificationHandler otocHandler = new TestNotificationHandler();
        TestNotificationHandler otHandler = new TestNotificationHandler();

        TestNotificationHandler cHandler = new TestNotificationHandler();
        TestNotificationHandler ocHandler = new TestNotificationHandler();

        // Add the handlers with the appropriate types and classifiers
        notificationCenter.addHandler(notificationName, classifier, tcHandler);
        notificationCenter.addHandler(notificationName, otherClassifier, tocHandler);
        notificationCenter.addHandler(notificationName, null, tHandler);

        notificationCenter.addHandler(otherNotificationName, classifier, otcHandler);
        notificationCenter.addHandler(otherNotificationName, otherClassifier, otocHandler);
        notificationCenter.addHandler(otherNotificationName, null, otHandler);

        notificationCenter.addHandler(null, classifier, cHandler);
        notificationCenter.addHandler(null, otherClassifier, ocHandler);

        // Post the Type/Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(notificationName, classifier, notificationInfo);

        expectedTCReceived++;
        expectedTReceived++;
        expectedCReceived++;

        assertEquals("Type/Classifier ReceiveCount Wrong", expectedTCReceived, tcHandler.receivedNotifications.size());
        assertEquals("Type/Other Classifier ReceiveCount Wrong", expectedTOCReceived, tocHandler.receivedNotifications.size());
        assertEquals("Type ReceiveCount Wrong", expectedTReceived, tHandler.receivedNotifications.size());
        assertEquals("Other Type/Classifier ReceiveCount Wrong", expectedOTCReceived, otcHandler.receivedNotifications.size());
        assertEquals("Other Type/Other Classifier ReceiveCount Wrong", expectedOTOCReceived, otocHandler.receivedNotifications.size());
        assertEquals("Other Type ReceiveCount Wrong", expectedOTReceived, otHandler.receivedNotifications.size());
        assertEquals("Classifier ReceiveCount Wrong", expectedCReceived, cHandler.receivedNotifications.size());
        assertEquals("Other Classifier ReceiveCount Wrong", expectedOCReceived, ocHandler.receivedNotifications.size());

        // Post the Type/Other Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(notificationName, otherClassifier, notificationInfo);

        expectedTOCReceived++;
        expectedTReceived++;
        expectedOCReceived++;

        assertEquals("Type/Classifier ReceiveCount Wrong", expectedTCReceived, tcHandler.receivedNotifications.size());
        assertEquals("Type/Other Classifier ReceiveCount Wrong", expectedTOCReceived, tocHandler.receivedNotifications.size());
        assertEquals("Type ReceiveCount Wrong", expectedTReceived, tHandler.receivedNotifications.size());
        assertEquals("Other Type/Classifier ReceiveCount Wrong", expectedOTCReceived, otcHandler.receivedNotifications.size());
        assertEquals("Other Type/Other Classifier ReceiveCount Wrong", expectedOTOCReceived, otocHandler.receivedNotifications.size());
        assertEquals("Other Type ReceiveCount Wrong", expectedOTReceived, otHandler.receivedNotifications.size());
        assertEquals("Classifier ReceiveCount Wrong", expectedCReceived, cHandler.receivedNotifications.size());
        assertEquals("Other Classifier ReceiveCount Wrong", expectedOCReceived, ocHandler.receivedNotifications.size());

        // Post the Type Only Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(notificationName, null, notificationInfo);

        expectedTReceived++;

        assertEquals("Type/Classifier ReceiveCount Wrong", expectedTCReceived, tcHandler.receivedNotifications.size());
        assertEquals("Type/Other Classifier ReceiveCount Wrong", expectedTOCReceived, tocHandler.receivedNotifications.size());
        assertEquals("Type ReceiveCount Wrong", expectedTReceived, tHandler.receivedNotifications.size());
        assertEquals("Other Type/Classifier ReceiveCount Wrong", expectedOTCReceived, otcHandler.receivedNotifications.size());
        assertEquals("Other Type/Other Classifier ReceiveCount Wrong", expectedOTOCReceived, otocHandler.receivedNotifications.size());
        assertEquals("Other Type ReceiveCount Wrong", expectedOTReceived, otHandler.receivedNotifications.size());
        assertEquals("Classifier ReceiveCount Wrong", expectedCReceived, cHandler.receivedNotifications.size());
        assertEquals("Other Classifier ReceiveCount Wrong", expectedOCReceived, ocHandler.receivedNotifications.size());

        // Post the Other Type/Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(otherNotificationName, classifier, notificationInfo);

        expectedOTCReceived++;
        expectedOTReceived++;
        expectedCReceived++;

        assertEquals("Type/Classifier ReceiveCount Wrong", expectedTCReceived, tcHandler.receivedNotifications.size());
        assertEquals("Type/Other Classifier ReceiveCount Wrong", expectedTOCReceived, tocHandler.receivedNotifications.size());
        assertEquals("Type ReceiveCount Wrong", expectedTReceived, tHandler.receivedNotifications.size());
        assertEquals("Other Type/Classifier ReceiveCount Wrong", expectedOTCReceived, otcHandler.receivedNotifications.size());
        assertEquals("Other Type/Other Classifier ReceiveCount Wrong", expectedOTOCReceived, otocHandler.receivedNotifications.size());
        assertEquals("Other Type ReceiveCount Wrong", expectedOTReceived, otHandler.receivedNotifications.size());
        assertEquals("Classifier ReceiveCount Wrong", expectedCReceived, cHandler.receivedNotifications.size());
        assertEquals("Other Classifier ReceiveCount Wrong", expectedOCReceived, ocHandler.receivedNotifications.size());

        // Post the Other Type/Other Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(otherNotificationName, otherClassifier, notificationInfo);

        expectedOTOCReceived++;
        expectedOTReceived++;
        expectedOCReceived++;

        assertEquals("Type/Classifier ReceiveCount Wrong", expectedTCReceived, tcHandler.receivedNotifications.size());
        assertEquals("Type/Other Classifier ReceiveCount Wrong", expectedTOCReceived, tocHandler.receivedNotifications.size());
        assertEquals("Type ReceiveCount Wrong", expectedTReceived, tHandler.receivedNotifications.size());
        assertEquals("Other Type/Classifier ReceiveCount Wrong", expectedOTCReceived, otcHandler.receivedNotifications.size());
        assertEquals("Other Type/Other Classifier ReceiveCount Wrong", expectedOTOCReceived, otocHandler.receivedNotifications.size());
        assertEquals("Other Type ReceiveCount Wrong", expectedOTReceived, otHandler.receivedNotifications.size());
        assertEquals("Classifier ReceiveCount Wrong", expectedCReceived, cHandler.receivedNotifications.size());
        assertEquals("Other Classifier ReceiveCount Wrong", expectedOCReceived, ocHandler.receivedNotifications.size());

        // Post the Other Type Only Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(otherNotificationName, null, notificationInfo);

        expectedOTReceived++;

        assertEquals("Type/Classifier ReceiveCount Wrong", expectedTCReceived, tcHandler.receivedNotifications.size());
        assertEquals("Type/Other Classifier ReceiveCount Wrong", expectedTOCReceived, tocHandler.receivedNotifications.size());
        assertEquals("Type ReceiveCount Wrong", expectedTReceived, tHandler.receivedNotifications.size());
        assertEquals("Other Type/Classifier ReceiveCount Wrong", expectedOTCReceived, otcHandler.receivedNotifications.size());
        assertEquals("Other Type/Other Classifier ReceiveCount Wrong", expectedOTOCReceived, otocHandler.receivedNotifications.size());
        assertEquals("Other Type ReceiveCount Wrong", expectedOTReceived, otHandler.receivedNotifications.size());
        assertEquals("Classifier ReceiveCount Wrong", expectedCReceived, cHandler.receivedNotifications.size());
        assertEquals("Other Classifier ReceiveCount Wrong", expectedOCReceived, ocHandler.receivedNotifications.size());

        // Post the Classifier Only Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(null, classifier, notificationInfo);

        expectedCReceived++;

        assertEquals("Type/Classifier ReceiveCount Wrong", expectedTCReceived, tcHandler.receivedNotifications.size());
        assertEquals("Type/Other Classifier ReceiveCount Wrong", expectedTOCReceived, tocHandler.receivedNotifications.size());
        assertEquals("Type ReceiveCount Wrong", expectedTReceived, tHandler.receivedNotifications.size());
        assertEquals("Other Type/Classifier ReceiveCount Wrong", expectedOTCReceived, otcHandler.receivedNotifications.size());
        assertEquals("Other Type/Other Classifier ReceiveCount Wrong", expectedOTOCReceived, otocHandler.receivedNotifications.size());
        assertEquals("Other Type ReceiveCount Wrong", expectedOTReceived, otHandler.receivedNotifications.size());
        assertEquals("Classifier ReceiveCount Wrong", expectedCReceived, cHandler.receivedNotifications.size());
        assertEquals("Other Classifier ReceiveCount Wrong", expectedOCReceived, ocHandler.receivedNotifications.size());

        // Post the Other Classifier Only Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(null, otherClassifier, notificationInfo);

        expectedOCReceived++;

        assertEquals("Type/Classifier ReceiveCount Wrong", expectedTCReceived, tcHandler.receivedNotifications.size());
        assertEquals("Type/Other Classifier ReceiveCount Wrong", expectedTOCReceived, tocHandler.receivedNotifications.size());
        assertEquals("Type ReceiveCount Wrong", expectedTReceived, tHandler.receivedNotifications.size());
        assertEquals("Other Type/Classifier ReceiveCount Wrong", expectedOTCReceived, otcHandler.receivedNotifications.size());
        assertEquals("Other Type/Other Classifier ReceiveCount Wrong", expectedOTOCReceived, otocHandler.receivedNotifications.size());
        assertEquals("Other Type ReceiveCount Wrong", expectedOTReceived, otHandler.receivedNotifications.size());
        assertEquals("Classifier ReceiveCount Wrong", expectedCReceived, cHandler.receivedNotifications.size());
        assertEquals("Other Classifier ReceiveCount Wrong", expectedOCReceived, ocHandler.receivedNotifications.size());

        System.out.println("Type/Classifier Handler notification Count: " + tcHandler.receivedNotifications.size());
        System.out.println("Type/Other Classifier Handler notification Count: " + tocHandler.receivedNotifications.size());
        System.out.println("Type/No Classifier Handler notification Count: " + tHandler.receivedNotifications.size());
        System.out.println("Other Type/Classifier Handler notification Count: " + otcHandler.receivedNotifications.size());
        System.out.println("Other Type/Other Classifier Handler notification Count: " + otocHandler.receivedNotifications.size());
        System.out.println("Other Type/No Classifier Handler notification Count: " + otHandler.receivedNotifications.size());
        System.out.println("No Type/Classifier Handler notification Count: " + cHandler.receivedNotifications.size());
        System.out.println("No Type/Other Classifier Handler notification Count: " + ocHandler.receivedNotifications.size());
    }


    @Test
    public void testRegisterForMultipleTypes() {
        String notificationName = "testNotification";
        String otherNotificationName = "otherTestNotification";
        Object classifier = "classifier";
        Object otherClassifier = "otherClassifier";
        Map<String, Object> notificationInfo = null;

        // Counters for tracking how many notifications should have been received by each handler.
        int expectedReceived = 0;

        // Create the notifications that we will be posting.
        Notification tcNotification = new Notification(notificationName, classifier, notificationInfo);
        Notification tocNotification = new Notification(notificationName, otherClassifier, notificationInfo);
        Notification tNotification = new Notification(notificationName, null, notificationInfo);

        Notification otcNotification = new Notification(otherNotificationName, classifier, notificationInfo);
        Notification otocNotification = new Notification(otherNotificationName, otherClassifier, notificationInfo);
        Notification otNotification = new Notification(otherNotificationName, null, notificationInfo);

        Notification cNotification = new Notification(null, classifier, notificationInfo);
        Notification ocNotification = new Notification(null, otherClassifier, notificationInfo);

        NotificationCenter notificationCenter = NotificationCenter.defaultCenter();

        // Create the Notification Handlers that we will be testing with
        TestNotificationHandler handler = new TestNotificationHandler();

        // Add the handlers with the appropriate types and classifiers
        notificationCenter.addHandler(notificationName, classifier, handler);
        notificationCenter.addHandler(notificationName, otherClassifier, handler);
        notificationCenter.addHandler(notificationName, null, handler);

        notificationCenter.addHandler(otherNotificationName, classifier, handler);
        notificationCenter.addHandler(otherNotificationName, otherClassifier, handler);
        notificationCenter.addHandler(otherNotificationName, null, handler);

        notificationCenter.addHandler(null, classifier, handler);
        notificationCenter.addHandler(null, otherClassifier, handler);

        // Post the Type/Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(tcNotification);

        expectedReceived++;
        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Post the Type/Other Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(tocNotification);

        expectedReceived++;
        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Post the Type Only Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(tNotification);

        expectedReceived++;
        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Post the Other Type/Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(otcNotification);

        expectedReceived++;
        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Post the Other Type/Other Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(otocNotification);

        expectedReceived++;
        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Post the Other Type Only Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(otNotification);

        expectedReceived++;
        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Post the Classifier Only Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(cNotification);

        expectedReceived++;
        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Post the Other Classifier Only Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(ocNotification);

        expectedReceived++;
        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        System.out.println("Type/Classifier Handler notification Count: " + handler.receivedNotifications.size());

    }


    @Test
    public void testNotificationsNotReceivedAfterGlobalUnregister() {
        String notificationName = "testNotification";
        String otherNotificationName = "otherTestNotification";
        Object classifier = "classifier";
        Object otherClassifier = "otherClassifier";
        Map<String, Object> notificationInfo = null;

        // Counters for tracking how many notifications should have been received by each handler.
        int expectedReceived = 0;

        // Create the notifications that we will be posting.
        Notification tcNotification = new Notification(notificationName, classifier, notificationInfo);

        // Create the Notification Handlers that we will be testing with
        TestNotificationHandler handler = new TestNotificationHandler();

        NotificationCenter notificationCenter = NotificationCenter.defaultCenter();

        // Add the handlers with the appropriate types and classifiers
        notificationCenter.addHandler(notificationName, classifier, handler);

        // Post the Type/Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(tcNotification);

        expectedReceived++;
        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Unregister the handler from all types
        notificationCenter.removeHandler(handler);

        // Post the Type/Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(tcNotification);

        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());
    }


    @Test
    public void testNotificationsNotReceivedAfterSpecificUnregister() {
        String notificationName = "testNotification";
        String otherNotificationName = "otherTestNotification";
        Object classifier = "classifier";
        Object otherClassifier = "otherClassifier";
        Map<String, Object> notificationInfo = null;

        // Counters for tracking how many notifications should have been received by each handler.
        int expectedReceived = 0;

        // Create the notifications that we will be posting.
        Notification tcNotification = new Notification(notificationName, classifier, notificationInfo);
        Notification tocNotification = new Notification(notificationName, otherClassifier, notificationInfo);
        Notification tNotification = new Notification(notificationName, null, notificationInfo);

        Notification otcNotification = new Notification(otherNotificationName, classifier, notificationInfo);
        Notification otocNotification = new Notification(otherNotificationName, otherClassifier, notificationInfo);
        Notification otNotification = new Notification(otherNotificationName, null, notificationInfo);

        Notification cNotification = new Notification(null, classifier, notificationInfo);
        Notification ocNotification = new Notification(null, otherClassifier, notificationInfo);

        NotificationCenter notificationCenter = NotificationCenter.defaultCenter();

        // Create the Notification Handlers that we will be testing with
        TestNotificationHandler handler = new TestNotificationHandler();

        // Add the handlers with the appropriate types and classifiers
        notificationCenter.addHandler(notificationName, classifier, handler);
        notificationCenter.addHandler(notificationName, otherClassifier, handler);
        notificationCenter.addHandler(notificationName, null, handler);

        notificationCenter.addHandler(otherNotificationName, classifier, handler);
        notificationCenter.addHandler(otherNotificationName, otherClassifier, handler);
        notificationCenter.addHandler(otherNotificationName, null, handler);

        notificationCenter.addHandler(null, classifier, handler);
        notificationCenter.addHandler(null, otherClassifier, handler);

        // Post the Type/Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(tcNotification);

        expectedReceived++;
        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Post the Type/Other Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(tocNotification);

        expectedReceived++;
        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Post the Type Only Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(tNotification);

        expectedReceived++;
        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Post the Other Type/Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(otcNotification);

        expectedReceived++;
        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Post the Other Type/Other Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(otocNotification);

        expectedReceived++;
        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Post the Other Type Only Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(otNotification);

        expectedReceived++;
        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Post the Classifier Only Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(cNotification);

        expectedReceived++;
        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Post the Other Classifier Only Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(ocNotification);

        expectedReceived++;
        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Unregister from specific notification types (other Name and Other Classifier)
        notificationCenter.removeHandler(handler, otherNotificationName, otherClassifier);
        notificationCenter.removeHandler(handler, otherNotificationName, null);
        notificationCenter.removeHandler(handler, null, otherClassifier);

        // Post the Type/Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(tcNotification);

        expectedReceived++;
        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Post the Type/Other Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(tocNotification);

        expectedReceived++;
        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Post the Type Only Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(tNotification);

        expectedReceived++;
        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Post the Other Type/Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(otcNotification);

        expectedReceived++;
        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Post the Other Type/Other Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(otocNotification);

        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Post the Other Type Only Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(otNotification);

        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Post the Classifier Only Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(cNotification);

        expectedReceived++;
        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        // Post the Other Classifier Only Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(ocNotification);

        assertEquals("Receive Count is Wrong", expectedReceived, handler.receivedNotifications.size());

        System.out.println("Type/Classifier Handler notification Count: " + handler.receivedNotifications.size());
    }

    @Test
    public void testPostedNotificationMatchesReceivedNotification() {
        String notificationName = "testNotification";
        String otherNotificationName = "otherTestNotification";
        Object classifier = "classifier";
        Object otherClassifier = "otherClassifier";
        Map<String, Object> notificationInfo = new HashMap<String, Object>();

        notificationInfo.put("key1", "value1");
        notificationInfo.put("key2", "value2");

        // Create the notifications that we will be posting.
        Notification tcNotification = new Notification(notificationName, classifier, notificationInfo);

        // Create the Notification Handlers that we will be testing with
        TestNotificationHandler handler = new TestNotificationHandler();

        NotificationCenter notificationCenter = NotificationCenter.defaultCenter();

        // Add the handlers with the appropriate types and classifiers
        notificationCenter.addHandler(notificationName, classifier, handler);

        // Post the Type/Classifier Notification and verify the handlers to make sure the right handlers received it.
        notificationCenter.postNotification(tcNotification);

        assertEquals(1, handler.receivedNotifications.size());

        Notification receivedNotification = handler.receivedNotifications.get(0) ;

        assertEquals ( notificationName, receivedNotification.getNotificationName());
        assertEquals(classifier, receivedNotification.getClassifier());
        assertEquals(notificationInfo, receivedNotification.getNotificationInfo());
    }
}
