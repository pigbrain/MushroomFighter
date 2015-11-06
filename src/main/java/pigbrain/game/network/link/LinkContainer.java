package pigbrain.game.network.link;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

import pigbrain.game.network.exception.InvalidLinkException;
import pigbrain.game.network.exception.InvalidSessionException;
public class LinkContainer {

	private Map<String, Link> linkMap = new ConcurrentHashMap<String, Link>();
	
	private static LinkContainer linkContainer;
	
	private LinkContainer() {
		
	}
	
	public static LinkContainer getInstance() {
		if (linkContainer == null) {
			linkContainer = new LinkContainer();
		}
		
		return linkContainer;
	}
	
	public boolean putLink(Link link) throws InvalidLinkException {
		
		if (link == null) {
			throw new InvalidLinkException("Link must not be null");
		}
		
		return linkMap.putIfAbsent(link.getSessionId(), link) != null ? true : false; 
		
	}
	
	public Link getLink(Session session) throws InvalidSessionException {
		if (session == null) {
			throw new InvalidSessionException("Session must not be null");
		}
		
		return linkMap.get(session.getId());
	}
	
	public Link getLink(String sessionId) {
		
		return linkMap.get(sessionId);
	}
	
	public void removeLink(Link link) throws InvalidSessionException, InvalidLinkException {
		if (link == null) {
			throw new InvalidLinkException("Link must not be null");
		}
		
		removeLink(link.getSession());
	}
	
	public void removeLink(Session session) throws InvalidSessionException, InvalidLinkException {
		
		if (session == null) {
			throw new InvalidSessionException("Session must not be null");
		}
		
		linkMap.remove(session.getId());
	}
	
	public int getSize() {
		
		return linkMap.size();
	}
}
