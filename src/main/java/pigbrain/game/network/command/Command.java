package pigbrain.game.network.command;

import java.util.concurrent.Future;


import pigbrain.game.network.link.Link;

public class Command {

	private String commandId;
	private Link link;
	private Object object;
	private Future<Void> commandFuture;

	public Command() {

	}

	public Command(String commandId, Link link) {
		this.commandId = commandId;
		this.link = link;
	}

	public String getCommandId() {
		return commandId;
	}

	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}


	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Future<Void> getCommandFuture() {
		return commandFuture;
	}

	public void setCommandFuture(Future<Void> commandFuture) {
		this.commandFuture = commandFuture;
	}

}
