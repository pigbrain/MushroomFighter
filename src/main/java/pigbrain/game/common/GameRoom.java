package pigbrain.game.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

import pigbrain.game.command.CommandTable;
import pigbrain.game.command.model.BombExplosionS2C;
import pigbrain.game.command.model.GameMotionInfoS2C;
import pigbrain.game.command.model.GameResultS2C;
import pigbrain.game.command.model.GameStatusS2C;
import pigbrain.game.command.model.HeartBeatS2C;
import pigbrain.game.command.model.GamePlayerInfoS2C;
import pigbrain.game.command.model.PlayerMissileS2C;
import pigbrain.game.common.GameConstant.Direction;
import pigbrain.game.common.GameConstant.GameStatus;
import pigbrain.game.common.GameConstant.MotionStatus;
import pigbrain.game.exception.PlayerException;
import pigbrain.game.logic.GameSystem;
import pigbrain.game.network.exception.CommandBuildException;
import pigbrain.game.network.link.Link;
import pigbrain.game.tick.Regulator;
import pigbrain.game.tick.Tick;

public class GameRoom implements Tick {

	private String id;
	private String roomName;
	
	@JsonIgnore
	private Member member1p;
	@JsonIgnore
	private Member member2p;
	
	private GameStatus status = GameStatus.WAIT;
	@JsonIgnore
	private List<Bomb> bombList = new ArrayList<Bomb>();
	@JsonIgnore
	private List<Missile> missileList = new ArrayList<Missile>();
	@JsonIgnore
	private Regulator readyRegulator = new Regulator(GameConstant.READY_TIME);
	@JsonIgnore
	private Regulator fightRegulator = new Regulator(GameConstant.FIGHT_TIME);
	@JsonIgnore
	private Regulator motionRegulator = new Regulator(0.5);
	@JsonIgnore
	private GameSystem gameSystem = GameSystem.getInstance();
	
	public GameRoom(String id, String roomName) {
		this.id = id;
		this.roomName = roomName;
	}
	
	public String getId() {
		return id;
	}
	
	public String getRoomName() {
		return roomName;
	}

	public Member getMember1p() {
		return member1p;
	}

	public void setMember1p(Member member1p) {
		this.member1p = member1p;
	}

	public Member getMember2p() {
		return member2p;
	}
	
	public void addBomb(Bomb bomb) {
		bombList.add(bomb);
	}

	public void addMissile(Missile missile) {
		missileList.add(missile);
	}
	
	public String setMember(Member member) throws PlayerException {
		if (member1p == null) {
			member1p = member;
			return GameConstant.PLYAER_POSITION_1;
		}
		
		if (member2p == null) {
			member2p = member;
			return GameConstant.PLYAER_POSITION_2;
		}
		
		throw new PlayerException("Setting member[" + member.getLink().getSecretKey() + "] position is fail");
	}
	
	public void setMember2p(Member member2p) {
		this.member2p = member2p;
	}
	
	public boolean hasMember() {
		return (member1p != null || member2p != null);
	}
	
	public int getMemberCount() {
		int memberCount = 0;
		if (member1p != null) {
			memberCount++;
		}
		if (member2p != null) {
			memberCount++;
		}
		
		return memberCount;
	}

	public GameStatus getGameStatus() {
		return status;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.status = gameStatus;
	}
	
	public void removeMember(String memberId) {
		if (member1p != null && member1p.getLink().getSecretKey().equals(memberId)) {
			member1p = null;
			return;
		}
		
		if (member2p != null && member2p.getLink().getSecretKey().equals(memberId)) {
			member2p = null;
			return;
		}
	}
	
	public Member getOtherMember(String memberId) {
		if (member1p != null && member1p.getLink().getSecretKey().equals(memberId)) {
			return member2p;
		} else {
			return member1p;
		}
	}

	@Override
	public void update(double delta) {
		if (status == GameStatus.WAIT) {
			updateWait();
			return;
		}
		
		if (status == GameStatus.READY) {
			updateReady(delta);
			return;
		}
		
		if (status == GameStatus.FIGHT) {
			updateMotion(delta);
			updateFight(delta);
			updateBomb(delta);
			updateMissile(delta);
			return;
		}
	}
	
	private void updateWait() {
		if (getMemberCount() != GameConstant.ROOM_MEMBER_COUNT) {
			return;
		}
		
		status = GameStatus.READY;
		
		post(CommandTable.PLAYER_INFO_S2C, new GamePlayerInfoS2C(member1p, member2p));
		post(CommandTable.GAME_STATUS_S2C, new GameStatusS2C(status.getId(), GameConstant.READY_TIME));
	}
	
	private void updateReady(double delta) {
		if (!readyRegulator.isReady(delta)) {
			return;
		}
		
		status = GameStatus.FIGHT;
		
		post(CommandTable.GAME_STATUS_S2C, new GameStatusS2C(status.getId(), GameConstant.FIGHT_TIME));
	}
	
	private void updateMotion(double delta) {
		if (!motionRegulator.isReady(delta)) {
			return;
		}

		post(CommandTable.PLAYER_MOTION_S2C, new GameMotionInfoS2C(member1p, member2p));
	}
	
	private void updateFight(double delta) {
		if (!fightRegulator.isReady(delta)) {
			return;
		}
		
		status = GameStatus.END;
		
		post(CommandTable.GAME_STATUS_S2C, new GameStatusS2C(status.getId(), 0));
		post(CommandTable.GAME_RESULT_S2C, new GameResultS2C(member1p, member2p));
	}
	
	private void updateBomb(double delta) {
		
		for (int i = 0; i < bombList.size(); ) {
			Bomb bomb = bombList.get(i);
			bomb.update(delta);
			
			if (!bomb.isExplostion()) {
				i++;
				continue;
			}
			
			bombList.remove(i);

			post(CommandTable.BOMB_EXPLOSION_S2C, new BombExplosionS2C(bomb.getId()));
			
			if (!member1p.isDead() && GameUtil.calculateDistance(bomb.getxPosition(), member1p.getxPosition(), bomb.getyPosition(), member1p.getyPosition()) <= GameConstant.BOMB_COLLISION_THRESHOLD) {
				member1p.setMotionStatus(MotionStatus.DIE);
				
				fightRegulator.setElapsedTime(GameConstant.FIGHT_TIME - 1);
			}
			if (!member2p.isDead() && GameUtil.calculateDistance(bomb.getxPosition(), member2p.getxPosition(), bomb.getyPosition(), member2p.getyPosition()) <= GameConstant.BOMB_COLLISION_THRESHOLD) {
				member2p.setMotionStatus(MotionStatus.DIE);
				
				fightRegulator.setElapsedTime(GameConstant.FIGHT_TIME - 1);
			}
		}
	}
	
	private void updateMissile(double delta) {

		for (int i = 0; i < missileList.size(); ) {
			Missile missile = missileList.get(i);
			int direction = missile.getDirection();
			double xPosition = missile.getxPosition();
			double yPosition = missile.getyPosition();
			
			if (direction == Direction.UP.getId()) {
				yPosition -= GameConstant.MISSILE_SPEED;
			} else if (direction == Direction.RIGHT.getId()) {
				xPosition += GameConstant.MISSILE_SPEED;
			} else if (direction == Direction.DOWN.getId()) {
				yPosition += GameConstant.MISSILE_SPEED;
			} else if (direction == Direction.LEFT.getId()) {
				xPosition -= GameConstant.MISSILE_SPEED;
			}   
			
			if ((xPosition < GameConstant.MIN_X_POSITION || GameConstant.MAX_X_POSITION < xPosition)
					|| (yPosition < GameConstant.MIN_Y_POSITION || GameConstant.MAX_Y_POSITION < yPosition)) {
				
				post(CommandTable.MISSILE_MOVE_S2C, new PlayerMissileS2C(missile.getId(),  true));
				i++;
				continue;
			}

			missileList.remove(i);
			
			missile.setxPosition(xPosition);
			missile.setyPosition(yPosition);
			
			post(CommandTable.MISSILE_MOVE_S2C, new PlayerMissileS2C(missile.getId(), missile.getDirection(), xPosition, yPosition));
		}
	}
	
	private void post(String commandId, Object object) {
		try {
			if (member1p != null) gameSystem.post(member1p.getLink(), commandId, object);
			if (member2p != null) gameSystem.post(member2p.getLink(), commandId, object);
		} catch (CommandBuildException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
