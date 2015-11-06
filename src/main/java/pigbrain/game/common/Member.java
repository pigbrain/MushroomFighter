package pigbrain.game.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import pigbrain.game.command.CommandTable;
import pigbrain.game.command.model.HeartBeatS2C;
import pigbrain.game.common.GameConstant.Direction;
import pigbrain.game.common.GameConstant.MotionStatus;
import pigbrain.game.logic.GameSystem;
import pigbrain.game.network.exception.CommandBuildException;
import pigbrain.game.network.link.Link;
import pigbrain.game.tick.Regulator;
import pigbrain.game.tick.Tick;

public class Member implements Tick {

	private String id;

	@JsonIgnore
	private Link link;
	private String nickName;

	private String gameRoomId;

	private int direction;
	private double energy;
	private double xPosition;
	private double yPosition;

	private MotionStatus motionStatus = MotionStatus.NONE;

	@JsonIgnore
	private Regulator bombRegulator = new Regulator(10L);

	@JsonIgnore
	private Regulator shootRegulator = new Regulator(1L);
	
	@JsonIgnore
	private Regulator heartBeatRegulator = new Regulator(10L);

	public void init() {
		motionStatus = MotionStatus.NONE;
		energy = GameConstant.ENERGY;

		bombRegulator.stop();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getGameRoomId() {
		return gameRoomId;
	}

	public void setGameRoomId(String gameRoomId) {
		this.gameRoomId = gameRoomId;
	}

	public MotionStatus getMotionStatus() {
		return motionStatus;
	}

	public void setMotionStatus(MotionStatus motionStatus) {
		this.motionStatus = motionStatus;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}

	public double getxPosition() {
		return xPosition;
	}

	public void setxPosition(double xPosition) {
		this.xPosition = xPosition;
	}

	public double getyPosition() {
		return yPosition;
	}

	public void setyPosition(double yPosition) {
		this.yPosition = yPosition;
	}

	public void doBombCountDown(int time) {
		bombRegulator.setUpdateTick(time);
		bombRegulator.start();
	}

	public boolean isBombCountDown() {
		return bombRegulator.isActive();
	}
	
	public void doShoot() {
		shootRegulator.start();
	}
	
	public boolean canShoot() {
		return !shootRegulator.isActive();
	}

	public boolean hasGameRoom() {
		return !StringUtils.isEmpty(gameRoomId);
	}
	
	public boolean isDead() {
		return motionStatus == MotionStatus.DIE;
	}

	@Override
	public void update(double delta) {

		updateHeartBeat(delta);

		updateBomb(delta);
		
		updateShoot(delta);
	}

	private void updateHeartBeat(double delta) {

		if (!heartBeatRegulator.isReady(delta)) {
			return;
		}

		try {
			HeartBeatS2C heartBeat = new HeartBeatS2C();
			heartBeat.setId(link.getSecretKey());

			GameSystem.getInstance().post(link, CommandTable.HEARTBEAT_S2C,	heartBeat);
		} catch (CommandBuildException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateBomb(double delta) {

		if (!bombRegulator.isReady(delta)) {
			return;
		}

		bombRegulator.stop();
	}
	

	private void updateShoot(double delta) {

		if (!shootRegulator.isReady(delta)) {
			return;
		}

		shootRegulator.stop();
	}
}
