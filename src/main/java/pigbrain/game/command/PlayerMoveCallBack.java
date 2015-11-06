package pigbrain.game.command;

import org.eclipse.jetty.websocket.client.io.UpgradeConnection;



import pigbrain.game.command.model.PlayerMoveC2S;
import pigbrain.game.command.model.PlayerMoveS2C;
import pigbrain.game.common.GameConstant;
import pigbrain.game.common.GameRoom;
import pigbrain.game.common.GameUtil;
import pigbrain.game.common.Member;
import pigbrain.game.common.GameConstant.Direction;
import pigbrain.game.common.GameConstant.MotionStatus;
import pigbrain.game.logic.GameRoomContainer;
import pigbrain.game.logic.GameSystem;
import pigbrain.game.logic.MemberContainer;
import pigbrain.game.network.command.Command;
import pigbrain.game.network.command.CommandCallBack;

public class PlayerMoveCallBack implements CommandCallBack {

	private GameSystem gameSystem = GameSystem.getInstance();
	private MemberContainer memberContainer = gameSystem.getMemberContainer();
	private GameRoomContainer gameRoomContainer = gameSystem.getGameRoomContainer();
	
	@Override
	public boolean execute(Command command) throws Exception {
		
		PlayerMoveC2S request = (PlayerMoveC2S) command.getObject();
	
		Member member = memberContainer.getMember(command.getLink().getSecretKey());
		MotionStatus motionStatus = member.getMotionStatus();
		
		if (motionStatus == MotionStatus.TALENT) {
			return true;
		}
		
		if (member.isDead()) {
			return true;
		}
		
		int direction = request.getDirection();
		
		double xPosition = member.getxPosition();
		double yPosition = member.getyPosition();
		double moveX = 0;
		double moveY = 0;
		
		if (direction == Direction.UP.getId()) {
	
			yPosition -= GameConstant.PLAYER_SPEED;
			moveY = -GameConstant.PLAYER_SPEED;
			member.setMotionStatus(MotionStatus.UP);
			
		} else if (direction == Direction.RIGHT.getId()) {
			
			xPosition += GameConstant.PLAYER_SPEED;
			moveX = GameConstant.PLAYER_SPEED;
			member.setMotionStatus(MotionStatus.RIGHT);
			
		} else if (direction == Direction.DOWN.getId()) {
			
			yPosition += GameConstant.PLAYER_SPEED;
			moveY = GameConstant.PLAYER_SPEED;
			member.setMotionStatus(MotionStatus.DOWN);
			
		} else if (direction == Direction.LEFT.getId()) {
			
			xPosition -= GameConstant.PLAYER_SPEED;
			moveX = -GameConstant.PLAYER_SPEED;
			member.setMotionStatus(MotionStatus.LEFT);
		}   

		if (xPosition < GameConstant.MIN_X_POSITION || GameConstant.MAX_X_POSITION < xPosition) {
			return true;
		}
		if (yPosition < GameConstant.MIN_Y_POSITION || GameConstant.MAX_Y_POSITION < yPosition) {
			return true;
		}
		
		String gameRoomId = member.getGameRoomId();
		GameRoom gameRoom = gameRoomContainer.getGameRoom(gameRoomId);
		Member otherMember = gameRoom.getOtherMember(command.getLink().getSecretKey());
			
		
		if (GameUtil.calculateDistance(xPosition, otherMember.getxPosition(), yPosition, otherMember.getyPosition()) <= GameConstant.PLAYER_COLLISION_THRESHOLD) {
			return true;
		}

		member.setxPosition(xPosition);
		member.setyPosition(yPosition);
		member.setDirection(direction);
		
		PlayerMoveS2C response = new PlayerMoveS2C();
		response.setId(member.getId());
		response.setMove(GameConstant.PLAYER_SPEED);
		response.setMoveX(moveX);
		response.setMoveY(moveY);
		response.setDirection(member.getDirection());
		
		gameSystem.post(member.getLink(), CommandTable.PLAYER_MOVE_S2C, response);
		gameSystem.post(otherMember.getLink(), CommandTable.PLAYER_MOVE_S2C, response);
		
		return true;
	}

}
