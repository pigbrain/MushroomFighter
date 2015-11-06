package pigbrain.game.command.model;

import pigbrain.game.common.Member;


public class GamePlayerInfoS2C {

	private Member player1;
	private Member player2;
	
	public GamePlayerInfoS2C() {
		
	}
	public GamePlayerInfoS2C(Member player1, Member player2) {
		this.player1 = player1;
		this.player2 = player2;
	}

	public Member getPlayer1() {
		return player1;
	}

	public void setPlayer1(Member player1) {
		this.player1 = player1;
	}

	public Member getPlayer2() {
		return player2;
	}

	public void setPlayer2(Member player2) {
		this.player2 = player2;
	}
	
	

}
