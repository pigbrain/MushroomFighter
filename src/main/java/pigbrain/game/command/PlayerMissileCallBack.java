package pigbrain.game.command;

import java.util.Random;
import java.util.UUID;

import pigbrain.game.command.model.PlayerBombC2S;
import pigbrain.game.command.model.PlayerBombS2C;
import pigbrain.game.command.model.FighterHitS2C;
import pigbrain.game.command.model.PlayerMoveC2S;
import pigbrain.game.command.model.PlayerMoveS2C;
import pigbrain.game.command.model.GameRoomCreateC2S;
import pigbrain.game.command.model.PlayerMissileC2S;
import pigbrain.game.command.model.PlayerMissileS2C;
import pigbrain.game.common.Bomb;
import pigbrain.game.common.GameConstant;
import pigbrain.game.common.GameConstant.Direction;
import pigbrain.game.common.GameConstant.Talent;
import pigbrain.game.common.GameRoom;
import pigbrain.game.common.Member;
import pigbrain.game.common.GameConstant.MotionStatus;
import pigbrain.game.common.Missile;
import pigbrain.game.logic.GameRoomContainer;
import pigbrain.game.logic.GameSystem;
import pigbrain.game.logic.MemberContainer;
import pigbrain.game.network.command.Command;
import pigbrain.game.network.command.CommandCallBack;

public class PlayerMissileCallBack implements CommandCallBack {

	private GameSystem gameSystem = GameSystem.getInstance();
	private MemberContainer memberContainer = gameSystem.getMemberContainer();
	private GameRoomContainer gameRoomContainer = gameSystem.getGameRoomContainer();
	
	@Override
	public boolean execute(Command command) throws Exception {

		PlayerMissileC2S request = (PlayerMissileC2S) command.getObject();
		
		Talent talent = request.getTalent();
		
		Member member = memberContainer.getMember(command.getLink().getSecretKey());
		
		if (!member.canShoot()) {
			return true;
		}
	
		member.doShoot();
				
		String gameRoomId = member.getGameRoomId();
		GameRoom gameRoom = gameRoomContainer.getGameRoom(gameRoomId);
		Member otherMember = gameRoom.getOtherMember(command.getLink().getSecretKey());

		String missileId = UUID.randomUUID().toString();
		Missile missile = new Missile(missileId, member.getDirection(), member.getxPosition(), member.getyPosition());
		gameRoom.addMissile(missile);
		
		PlayerMissileS2C response = new PlayerMissileS2C();
		response.setId(missileId);
		response.setTalent(talent);
		response.setDirection(member.getDirection());
		response.setxPosition(member.getxPosition());
		response.setyPosition(member.getyPosition());
		
		gameSystem.post(member.getLink(), CommandTable.PLAYER_MISSILE_S2C, response);
		gameSystem.post(otherMember.getLink(), CommandTable.PLAYER_MISSILE_S2C, response);
		
		return true;
	}
	

}
