package linebot.tpb;

import akka.actor.ActorRef;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import linebot.tpb.di.annotation.HeadActorRef;
import org.springframework.beans.factory.annotation.Autowired;

@LineMessageHandler
public class EventGateway {

    @Autowired
    @HeadActorRef
    private ActorRef headActor;

    @EventMapping
    public void handleEvent(Event event) throws Exception {
        headActor.tell(event, ActorRef.noSender());
    }

}
