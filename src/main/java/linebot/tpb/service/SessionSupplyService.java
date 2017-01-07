package linebot.tpb.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import linebot.tpb.di.akka.SpringAkkaExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SessionSupplyService {

    private Map<String, ActorRef> sessionActorMap = new HashMap<>();

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private SpringAkkaExtension springAkkaExtension;

    public ActorRef getSessionActor(String roomId) {
        ActorRef actor = sessionActorMap.get(roomId);

        if (actor == null) {
            actor = actorSystem.actorOf(springAkkaExtension.props("sessionActor", roomId), "host-" + roomId);
            sessionActorMap.put(roomId, actor);
        }

        return actor;
    }

    public void closeSession(String roomId) {
        ActorRef actor = sessionActorMap.get(roomId);
        sessionActorMap.remove(roomId);
        actorSystem.stop(actor);
    }

}
