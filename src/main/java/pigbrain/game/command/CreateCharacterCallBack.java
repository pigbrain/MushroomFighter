package pigbrain.game.command;

import pigbrain.game.command.model.FighterCreateC2S;
import pigbrain.game.command.model.FighterCreateS2C;
import pigbrain.game.common.Bomb;
import pigbrain.game.common.Member;
import pigbrain.game.common.GameConstant.MotionStatus;
import pigbrain.game.logic.GameSystem;
import pigbrain.game.logic.MemberContainer;
import pigbrain.game.network.command.Command;
import pigbrain.game.network.command.CommandCallBack;

public class CreateCharacterCallBack implements CommandCallBack {

	private GameSystem gameSystem = GameSystem.getInstance();
	private MemberContainer memberContainer = gameSystem.getMemberContainer();
	
	@Override
	public boolean execute(Command command) throws Exception {
		
		FighterCreateC2S request = (FighterCreateC2S) command.getObject();
		
		Member member = new Member();
		member.setId(command.getLink().getSecretKey());
		member.setLink(command.getLink());
		member.setNickName(request.getNickName());
		member.setMotionStatus(MotionStatus.RIGHT);
		
		memberContainer.putMember(command.getLink().getSecretKey(), member);
		
		FighterCreateS2C response = new FighterCreateS2C();
		response.setId(command.getLink().getSecretKey());
		
		gameSystem.post(command.getLink(), CommandTable.CREATE_CHARACTER_S2C, response);

		return true;
	}

}
