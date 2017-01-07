package linebot.tpb.actor;

import akka.actor.UntypedActor;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.MessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("sessionActor")
@Scope("prototype")
public class SessionActor extends UntypedActor {

    private String roomId;

    public SessionActor(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Event) {
            Event event = (Event) message;

            if (!roomId.equals(event.getSource().getSenderId())) {
                throw new Exception("roomId is not match");
            }

            // TODO: handle the event
        }
        else {
            unhandled(message);
        }
    }

}
