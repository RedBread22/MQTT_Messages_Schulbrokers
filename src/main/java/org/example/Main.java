package org.example;

import org.eclipse.paho.client.mqttv3.*;

public class Main {
    public static void main(String[] args) {
        String topic = "$SYS/#";
        String broker = "tcp://itsp.htl-leoben.at:1883";
        String clientId = "MqttSubscriber";

        try {
            MqttClient client = new MqttClient(broker, clientId);
            client.connect();
            client.subscribe(topic);
            client.setCallback(new MqttCallback() {
                public void messageArrived(String topic, MqttMessage message) {
                    System.out.println("Topic: " + topic);
                    System.out.println("Message: " + new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    //empty
                }

                public void connectionLost(Throwable cause) {
                    System.out.println("Connection lost:" + cause);
                }
            });
        } catch (MqttException me) {
            System.out.println("Error subscribing to topic");
        }
    }
}
