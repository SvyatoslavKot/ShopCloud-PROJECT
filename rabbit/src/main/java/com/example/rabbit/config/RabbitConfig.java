package com.example.rabbit.config;


import com.rabbitmq.client.AMQP;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    //Logger logger = Logger.getLogger(RabbitConfiguration.class);

    //настраиваем соединение с RabbitMQ

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory("localhost");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        return rabbitAdmin;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setExchange("query-remote-exchange");
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    AMQP.BasicProperties prop = new AMQP.BasicProperties.Builder()
            .replyTo("query-remote-producer").build();

    //объявляем очередь с именем queue1
    @Bean
    public Queue myQueue1() {
        return new Queue("queue1");
    }

    //объявляем очередь с именем query-example-2
    @Bean
    public Queue myQueue2() {
        return new Queue("query-example-2");
    }


    //---Publish/Subscribe--
    @Bean
    public Queue myQueue3() {
        return new Queue("query-example-3-1");
    }

    @Bean
    public Queue myQueue4() {
        return new Queue("query-example-3-2");
    }

    @Bean
    public FanoutExchange fanoutExchangeA() {
        return new FanoutExchange("exchange-example-3");
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(myQueue3()).to(fanoutExchangeA());
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(myQueue4()).to(fanoutExchangeA());
    }
    //----------------------///--------///-------------------

    //---Routing queue--

    @Bean
    public Queue myQueue5() {
        return new Queue("routing-queue-1");
    }

    @Bean
    public Queue myQueue6() {
        return new Queue("routing-queue-2");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("routing-exchange");
    }


    @Bean
    public Binding errorBinding5() {
        return BindingBuilder.bind(myQueue5()).to(directExchange()).with("error");
    }

    @Bean
    public Binding errorBinding6() {
        return BindingBuilder.bind(myQueue6()).to(directExchange()).with("error");
    }

    @Bean
    public Binding infoBinding() {
        return BindingBuilder.bind(myQueue6()).to(directExchange()).with("info");
    }

    @Bean
    public Binding warningBinding() {
        return BindingBuilder.bind(myQueue6()).to(directExchange()).with("warning");
    }

    //----------------------///--------///-------------------
    //--Topic queue

    @Bean
    public Queue myQueue7() {
        return new Queue("topic-queue-1");
    }

    @Bean
    public Queue myQueue8() {
        return new Queue("topic-queue-2");
    }


    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topic-exchange");
    }
    /*
    http://localhost:8080/emit/quick.orange.something/queue1
    http://localhost:8080/emit/quick.orange.rabbit/queue1and2
    http://localhost:8080/emit/lazy.orange.somthing/queue2and1
    http://localhost:8080/emit/lazy.somthing.rabbit/queue2
    http://localhost:8080/emit/quick.something.rabbit/queue2
    http://localhost:8080/emit/lazy.Somthing/queue2
     */

    @Bean
    public Binding binding7() {
        return BindingBuilder.bind(myQueue7()).to(topicExchange()).with("*.orange.*");
    }

    @Bean
    public Binding binding8() {
        return BindingBuilder.bind(myQueue8()).to(topicExchange()).with("*.*.rabbit");
    }

    @Bean
    public Binding binding9() {
        return BindingBuilder.bind(myQueue8()).to(topicExchange()).with("lazy.#");
    }
    //------------------------//-----------------------//----------------------------//

    @Bean
    public Queue myQueue10() {

        return new Queue("query-remote-producer");
    }

    @Bean
    public DirectExchange queryRemoteExchange() {
        return new DirectExchange("query-remote-exchange");
    }

    @Bean
    public Binding recevedBinding() {
        return BindingBuilder.bind(myQueue10()).to(queryRemoteExchange()).with("query-remote-producer");
    }



    /*

    //объявляем контейнер, который будет содержать листенер для сообщений
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer1() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames("queue1");
        container.setMessageListener(new MessageListener() {
            //тут ловим сообщения из queue1
            public void onMessage(Message message) {
                System.out.println("received from queue1 : " + new String(message.getBody()));
                // logger.info("received from queue1 : " + new String(message.getBody()));
            }
        });
        return container;
    }
     */
}
