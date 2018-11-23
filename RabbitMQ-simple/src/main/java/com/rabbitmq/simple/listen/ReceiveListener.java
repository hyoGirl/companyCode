package com.rabbitmq.simple.listen;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import java.io.IOException;

/**
 * @Auther: Administrator
 * @Date: 2018/7/16 0016 16:47
 * @Description:
 */
public class ReceiveListener implements ChannelAwareMessageListener {

    private Logger logger= LoggerFactory.getLogger(ReceiveListener.class);


    /**
     接收到消息，处理业务成功则调用channel.basicAck()方法，
     告诉mq服务器消费端处理成功，mq服务器就会删除该消息，

     如果处理失败可以调用basicNack()方法，
     调用该方法之后，服务器会自动的重新发送该消息，
     让消费端重新处理，直到消费端返回basicAsk
     */



    @Override
    public void onMessage(Message message, Channel channel) throws Exception {


        byte[] body = message.getBody();
        try {
            //业务逻辑
            logger.info("消费 receive msg : " + new String(body));
            // 消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //手动确认确认消息成功消费
        } catch (Exception e) {
            logger.info("消费失败: " + new String(body));
            // ack返回false，并重新回到队列，api里面解释得很清楚
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }


    }
}
