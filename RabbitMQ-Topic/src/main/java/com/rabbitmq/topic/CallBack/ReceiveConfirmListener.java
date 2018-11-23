package com.rabbitmq.topic.CallBack;


import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;


@Service("receiveConfirmListener")
public class ReceiveConfirmListener implements ChannelAwareMessageListener {







    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        try {

            System.out.println(
                    "消费端接收到消息:" + message.getMessageProperties() + ":" + new String(message.getBody()));

            //其中的false表示需要后面显示的调用basicAck，告诉MQ将msg删除
            // deliveryTag是消息传送的次数

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);


        } catch (Exception e) {

            e.printStackTrace();

            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);


        }
    }
}
