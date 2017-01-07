package linebot.tpb.service;

import akka.actor.ActorRef;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SessionSupplyServiceTest {

    @Autowired
    private SessionSupplyService sessionSupplyService;

    @Test
    public void test() throws InterruptedException {
        final String roomId1 = "ABC";
        final String roomId2 = "EFG";

        // Actor1を生成
        ActorRef sessionActor1 = sessionSupplyService.getSessionActor(roomId1);
        assertNotNull(sessionActor1);
        //System.out.println(sessionActor1.toString());

        // Actor2を生成
        ActorRef sessionActor2 = sessionSupplyService.getSessionActor(roomId2);
        assertNotNull(sessionActor2);

        // TEST: Actor1とActor2のpathが異なる
        assertNotEquals(sessionActor1.path().toString(), sessionActor2.path().toString());

        // Actor1を取得
        ActorRef sessionActor1_second = sessionSupplyService.getSessionActor(roomId1);
        assertNotNull(sessionActor1_second);
        //System.out.println(sessionActor1_second.toString());

        // TEST: 1回目の取得と2回目の取得で同じアクターが取得できている
        assertTrue(sessionActor1.equals(sessionActor1_second));

        // Actor1とActor2を停止
        sessionSupplyService.closeSession(roomId1);
        sessionSupplyService.closeSession(roomId2);

        // Actorが停止するのを待機
        Thread.sleep(1000);

        // Actor1を再度生成
        ActorRef sessionActor1_remake = sessionSupplyService.getSessionActor(roomId1);
        assertNotNull(sessionActor1_remake);
        //System.out.println(sessionActor1_remake.toString());

        // TEST: 初めに生成したアクターとremakeしたアクターが異なる
        assertFalse(sessionActor1.equals(sessionActor1_remake));

        // Actor1を停止
        sessionSupplyService.closeSession(roomId1);
    }

}
