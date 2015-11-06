package pigbrain.game.network.command;

public interface CommandCallBack {

	public boolean execute(Command command) throws Exception;
}
