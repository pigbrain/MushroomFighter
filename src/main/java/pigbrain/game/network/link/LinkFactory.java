package pigbrain.game.network.link;

import java.util.UUID;

import javax.websocket.Session;

import pigbrain.game.network.exception.InvalidSessionException;

public class LinkFactory {

	
	public static Link createLink(Session session) throws InvalidSessionException  {
		if (session == null) {
			throw new InvalidSessionException("Session must not be null");
		}
		
		Link link = new Link();
		
		link.setSession(session);
		link.setSessionId(session.getId());
		link.setSecretKey(UUID.randomUUID().toString());
		
		return link;
		
	}
	
}
