package pigbrain.game.network.command;

import java.io.IOException;

import javax.websocket.Session;

import pigbrain.game.network.exception.InvalidLinkException;
import pigbrain.game.network.exception.InvalidSessionException;
import pigbrain.game.network.link.Link;
import pigbrain.game.tick.Tick;

public class CommandExecutor implements Tick {

	private static CommandExecutor commandExecutor;
	private CommandCommunicator commandCommunicator = CommandCommunicator.getInstance();
	private CommandContainer commandContainer = CommandContainer.getInstance();

	private CommandExecutor() {

	}

	public static CommandExecutor getInstance() {
		if (commandExecutor == null) {
			commandExecutor = new CommandExecutor();
		}

		return commandExecutor;
	}

	@Override
	public void update(double delta) {
		while (processCommand()) {

		}
		
	}

	private boolean processCommand() {

		try {
			Command command = commandCommunicator.getCommand();
			if (command == null) {
				return false;
			}

			CommandCallBack commandCallBack = commandContainer.getCommandCallBack(command.getCommandId());
			if (commandCallBack == null) {
				closeLink(command.getLink());
				return true;
			}
			
			if (!commandCallBack.execute(command)) {
				closeLink(command.getLink());
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	
	private void closeLink(Link link) {
		try {
			link.getSession().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
