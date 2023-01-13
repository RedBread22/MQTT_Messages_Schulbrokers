package org.example;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Main2 {

    public static void main(String[] args) {

        String topic = "$SYS/#";
        String broker = "tcp://itsp.htl-leoben.at:1883";
        String clientId = "MqttSubscriber";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: " + broker);
            mqttClient.connect(connOpts);
            System.out.println("Connected");
            mqttClient.subscribe(topic);
            System.out.println("Subscribed to topic: " + topic);
            mqttClient.setCallback((MqttCallback) new MqttCallbackImpl());
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }
}

class MqttCallbackImpl implements MqttCallback {

    int messageCount = 0;

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost!");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        messageCount++;
        System.out.println("Topic: " + topic + "  Message: " + new String(message.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        try {
            System.out.println("Delivery complete - " + token.getMessage().toString());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}