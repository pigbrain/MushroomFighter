package pigbrain.game.network.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import pigbrain.game.tick.Tick;

public class CommandRouteMonitor implements Tick {

	private static CommandRouteMonitor commandMonitor;
	
	private List<Command> commandList = new ArrayList<Command>();
	
	private CommandRouteMonitor() {
		// TODO Auto-generated constructor stub
	}
	

	public static CommandRouteMonitor getInstance() {
		if (commandMonitor == null) {
			commandMonitor = new CommandRouteMonitor();
		}
		
		return commandMonitor;
	}
	
	

	public boolean putCommand(Command command) {
		return commandList.add(command);
	}
	
	@Override
	public void update(double delta) {
		// TODO Auto-generated method stub
		
		try {
			for (int i = 0; ;) {

				if (commandList.isEmpty()) {
					break;
				}
				
				Command command = commandList.get(i);
				Future<Void> commandFuture = command.getCommandFuture();
				
				if (commandFuture.isDone()) {
					commandList.remove(i);
					continue;
				}
				
				if (commandFuture.isCancelled()) {
					command.getLink().getSession().close();
					commandList.remove(i);
					continue;
				}
				
				i++;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
