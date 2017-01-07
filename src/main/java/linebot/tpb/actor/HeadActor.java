package linebot.tpb.actor;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.linecorp.bot.model.event.Event;
import linebot.tpb.service.SessionSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("headActor")
@Scope("prototype")
public class HeadActor extends UntypedActor {

    @Autowired
    private SessionSupplyService sessionSupplyService;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Event) {
            Event event = (Event) message;

            String senderId = event.getSource().getSenderId();
            if (senderId == null) {
                throw new Exception("sender id is null");
            }

            ActorRef sessionActor = sessionSupplyService.getSessionActor(senderId);
            sessionActor.tell(event, getSelf());
        }
        else {
            unhandled(message);
        }
    }

}
