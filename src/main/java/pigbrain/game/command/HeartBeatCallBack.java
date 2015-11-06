package pigbrain.game.command;

import pigbrain.game.common.Member;
import pigbrain.game.logic.GameSystem;
import pigbrain.game.logic.MemberContainer;
import pigbrain.game.network.command.Command;
import pigbrain.game.network.command.CommandCallBack;

public class HeartBeatCallBack implements CommandCallBack {

	private GameSystem gameSystem = GameSystem.getInstance();
	private MemberContainer memberContainer = gameSystem.getMemberContainer();
	
	@Override
	public boolean execute(Command command) throws Exception {
			
		Member member = memberContainer.getMember(command.getLink().getSecretKey());
		
		System.out.println("receive heartbeat from " + member.getNickName() + "[" +member.getLink().getSecretKey() + "]");
		
		return true;
	}

}
