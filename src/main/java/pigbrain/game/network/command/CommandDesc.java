package pigbrain.game.network.command;

public class CommandDesc {

	private Class parameterClass;
	private CommandCallBack commandCallBack;

	public CommandDesc(Class parameterClass, CommandCallBack commandCallBack) {
		this.parameterClass = parameterClass;
		this.commandCallBack = commandCallBack;
	}
	
	public Class getParameterClass() {
		return parameterClass;
	}

	public void setParameterClass(Class parameterClass) {
		this.parameterClass = parameterClass;
	}

	public CommandCallBack getCommandCallBack() {
		return commandCallBack;
	}

	public void setCommandCallBack(CommandCallBack commandCallBack) {
		this.commandCallBack = commandCallBack;
	}

}
