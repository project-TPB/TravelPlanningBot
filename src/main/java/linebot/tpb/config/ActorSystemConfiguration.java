package linebot.tpb.config;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import linebot.tpb.di.akka.SpringAkkaExtension;
import linebot.tpb.di.annotation.HeadActorRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActorSystemConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SpringAkkaExtension springExtension;

    @Autowired
    private ActorSystem system;

    @Bean
    public ActorSystem actorSystem() {
        ActorSystem system = ActorSystem.create("tpb-actor-system", akkaConfiguration());
        springExtension.initialize(applicationContext);
        return system;
    }

    @Bean
    public Config akkaConfiguration() {
        return ConfigFactory.load();
    }

    @Bean
    @HeadActorRef
    public ActorRef supervisor() {
        return system.actorOf(springExtension.props("headActor"), "head-actor");
    }

}
