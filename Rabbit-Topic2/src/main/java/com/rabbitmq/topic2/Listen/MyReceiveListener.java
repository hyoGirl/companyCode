package com.rabbitmq.topic2.Listen;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service("MyReceiveListener")
public class MyReceiveListener implements ChannelAwareMessageListener{


    @Override
    public void onMessage(Message message, Channel channel) throws Exception {



        //假设消费者处理出现了异常，看消息是否会回退
        try {
            //手动创建异常
            int a=100/0;
            System.out.println(System.currentTimeMillis());
            System.out.println("消费端接收到消息:" + ":" + new String(message.getBody()));

            //其中的false表示需要后面显示的调用basicAck，告诉MQ将msg删除
            // deliveryTag是消息传送的次数   true代表是确认所有的信息，false表示只确认当前消费者的第一个信息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        } catch (Exception e) {

            System.out.println("消费失败: " + new String(message.getBody()));
            e.printStackTrace();
          /*  if (message.getMessageProperties().getRedelivered()) {
                System.out.println("消息已重复处理失败,拒绝再次接收...");
                // 拒绝消息

//                deliveryTag:该消息的index
//                requeue：被拒绝的是否重新入队列

                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            } else {
                System.out.println("消息即将再次返回队列处理...");
                // requeue为是否重新回到队列

//                deliveryTag:该消息的index
//                multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
//                requeue：被拒绝的是否重新入队列
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
*/


        }
    }
}