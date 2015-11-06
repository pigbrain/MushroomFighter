package pigbrain.game.network.websocket;

import java.io.IOException;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang3.StringUtils;

import pigbrain.game.network.command.Command;
import pigbrain.game.network.command.CommandBuilder;
import pigbrain.game.network.command.CommandCommunicator;
import pigbrain.game.network.command.CommandContainer;
import pigbrain.game.network.exception.CommandBuildException;
import pigbrain.game.network.exception.InvalidLinkException;
import pigbrain.game.network.exception.InvalidSessionException;
import pigbrain.game.network.link.Link;
import pigbrain.game.network.link.LinkContainer;
import pigbrain.game.network.link.LinkFactory;

@ClientEndpoint
@ServerEndpoint(value = "/events/")
public class WebSocketHandler {

	private LinkContainer linkContainer = LinkContainer.getInstance();
	private CommandContainer commandContainer = CommandContainer.getInstance();
	private CommandCommunicator commandCommunicator = CommandCommunicator.getInstance();

	@OnOpen
	public void onWebSocketConnect(Session session) {

		try {
			//session.setMaxIdleTimeout(10L);
			
			Link link = LinkFactory.createLink(session);
			
			if (!StringUtils.isEmpty(commandContainer.getConnectCommandId())) {
				Command command = new Command();
				command.setCommandId(commandContainer.getConnectCommandId());
				command.setLink(link);
				
				commandCommunicator.putCommand(command);
			}

			linkContainer.putLink(link);

		} catch (InvalidSessionException e) {
			e.printStackTrace();

			destroySession(session);
		} catch (InvalidLinkException e) {
			e.printStackTrace();

			destroySession(session);
		}

	}

	@OnMessage
	public void onWebSocketText(Session session, String message) {

		try {

			Link link = linkContainer.getLink(session);

			Command command = CommandBuilder.createCommand(link, message);

			commandCommunicator.putCommand(command);

		} catch (InvalidSessionException e) {
			e.printStackTrace();

			destroySession(session);
		} catch (CommandBuildException e) {
			e.printStackTrace();

			destroySession(session);
		}
	}

	@OnClose
	public void onWebSocketClose(Session session, CloseReason reason) {

		try {
			Link link = linkContainer.getLink(session);

			if (!StringUtils.isEmpty(commandContainer.getDisConnectCommandId())) {
				Command command = new Command();
				command.setCommandId(commandContainer.getDisConnectCommandId());
				command.setLink(link);
				
				commandCommunicator.putCommand(command);
			}
			
			linkContainer.removeLink(session);
			

		} catch (InvalidLinkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidSessionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		destroySession(session);
	}

	@OnError
	public void onWebSocketError(Throwable cause) {
		cause.printStackTrace(System.err);
	}

	private void destroySession(Session session) {
		try {
			linkContainer.removeLink(session);

			session.close();

		} catch (InvalidSessionException e) {
			e.printStackTrace();
		} catch (InvalidLinkException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
