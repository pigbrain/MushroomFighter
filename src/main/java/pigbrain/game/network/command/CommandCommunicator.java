package pigbrain.game.network.command;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Future;

import pigbrain.game.network.common.CommandConstant;
import pigbrain.game.network.exception.CommandBuildException;
import pigbrain.game.network.link.Link;



public class CommandCommunicator {
 
	private static CommandCommunicator commandCommunicator;
	private Queue<Command> commandList = new LinkedList<Command>();
	
	private CommandCommunicator() {

	}

	public static CommandCommunicator getInstance() {

		if (commandCommunicator == null) {
			commandCommunicator = new CommandCommunicator();
		}

		return commandCommunicator;
	}

	public synchronized boolean putCommand(Command command) {
		return commandList.add(command);
	}

	public synchronized Command getCommand() throws InterruptedException {
		return commandList.poll();
	}
	
	public void post(Link link, String commandId, Object object) throws CommandBuildException {
		
		if (commandId == null || commandId.isEmpty()) {
			throw new CommandBuildException("CommandId is invalid");
		}
		
		if (object == null) {
			throw new CommandBuildException("Parameter object must not be null");
		}

		Future<Void> commandFuture = link.getSession().getAsyncRemote().sendText(CommandBuilder.createPacket(commandId, object));
		
		Command command = new Command();
		command.setLink(link);
		command.setCommandId(commandId);
		command.setObject(object);
		command.setCommandFuture(commandFuture);
		
		CommandRouteMonitor.getInstance().putCommand(command);
		
	}
}
