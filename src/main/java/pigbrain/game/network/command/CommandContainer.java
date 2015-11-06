package pigbrain.game.network.command;

import java.util.HashMap;
import java.util.Map;

import pigbrain.game.network.exception.CommandSearchException;

public class CommandContainer {
	
	private static CommandContainer commandContainer;
	
	private Map<String, CommandDesc> commandMap = new HashMap<String, CommandDesc>();

	private String connectCommandId = null;
	private String disConnectCommandId = null;
	
	private CommandCallBack connectCallBack = null;
	private CommandCallBack disConnectCallBack = null;
	
	private CommandContainer() {
	}
	
	public static CommandContainer getInstance() {
		if (commandContainer == null) {
			commandContainer = new CommandContainer();
			
		}
		
		return commandContainer;
	}
	
	public String getConnectCommandId() {
		return connectCommandId;
	}

	public void setConnectCommandId(String connectCommandId) {
		this.connectCommandId = connectCommandId;
	}

	public String getDisConnectCommandId() {
		return disConnectCommandId;
	}

	public void setDisConnectCommandId(String disConnectCommandId) {
		this.disConnectCommandId = disConnectCommandId;
	}

	public CommandCallBack getConnectCallBack() {
		return connectCallBack;
	}

	public void setConnectCallBack(CommandCallBack connectCallBack) {
		this.connectCallBack = connectCallBack;
	}

	public CommandCallBack getDisConnectCallBack() {
		return disConnectCallBack;
	}

	public void setDisConnectCallBack(CommandCallBack disConnectCallBack) {
		this.disConnectCallBack = disConnectCallBack;
	}

	public boolean putConnectCallBack(String commandId, CommandCallBack commandCallBack) {
		if (commandMap.containsKey(commandId)) {
			return false;
		} 
		
		connectCommandId = commandId;
		connectCallBack = commandCallBack;
		
		return true;
	}
	
	public boolean putDisconnectCallBack(String commandId, CommandCallBack commandCallBack) {
		if (commandMap.containsKey(commandId)) {
			return false;
		} 
		
		commandMap.put(commandId, new CommandDesc(null, commandCallBack));
		
		disConnectCommandId = commandId;
		disConnectCallBack = commandCallBack;
		
		return true;
	}
	
	public boolean putCallBack(String commandId, Class parameterClass, CommandCallBack commandCallBack) {
		
		if (commandMap.containsKey(commandId)) {
			return false;
		} 
		
		commandMap.put(commandId, new CommandDesc(parameterClass, commandCallBack));
		
		return true;
	}
	
	
	public CommandCallBack getCommandCallBack(String commandId) throws CommandSearchException {
		CommandDesc commandDesc = getCommandDesc(commandId);
		return commandDesc.getCommandCallBack();
	}
	
	public Class getParameterClass(String commandId) throws CommandSearchException {
		CommandDesc commandDesc = getCommandDesc(commandId);
		return commandDesc.getParameterClass();
	}
	
	public CommandDesc getCommandDesc(String commandId) throws CommandSearchException {
		CommandDesc commandDesc = commandMap.get(commandId);
		if (commandDesc == null) {
			throw new CommandSearchException("commandId[" + commandId + "] don't be found");
		}
		
		return commandDesc;
	}
}
