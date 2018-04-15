package actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.FeedResponse;
import data.Message;
import data.NewsAgentResponse;
import services.FeedService;
import services.NewsAgentService;

import java.util.UUID;

import static data.Message.Sender.BOT;
import static data.Message.Sender.USER;

public class MessageActor extends UntypedActor {

    //Define another actor Reference
    private final ActorRef out;
    private NewsAgentResponse newsAgentResponse;
    //Object of Feed Service
    private FeedService feedService = new FeedService();
    //Object of NewsAgentService
    private NewsAgentService newsAgentService = new NewsAgentService();

    //Self-Reference the Actor
    public MessageActor(ActorRef out) {

        this.out = out;
    }

    //PROPS
    public static Props props(ActorRef out) {
        return Props.create(MessageActor.class, out);
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        //send back the response
        ObjectMapper mapper = new ObjectMapper();
        Message messageObject = new Message();
        if (message instanceof String) {

            messageObject.text = (String) message;
            messageObject.sender = Message.Sender.USER;
            out.tell(mapper.writeValueAsString(messageObject), self());
            newsAgentResponse=newsAgentService.getNewsAgentresponse(messageObject.text,UUID.randomUUID());
            String query = newsAgentService.getNewsAgentresponse("Find " + message, UUID.randomUUID()).query;
            FeedResponse feedResponse = feedService.getFeedByQuery(query);
            messageObject.text = (feedResponse.title == null) ? "No results found" : "Showing results for: " + query;
            messageObject.feedResponse = feedResponse;
            messageObject.sender = Message.Sender.BOT;
            out.tell(mapper.writeValueAsString(messageObject), self());
        }
    }
}