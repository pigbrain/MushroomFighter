
var motions = new (function() {
	var none = "NONE";
	var up = "UP";
	var right = "RIGHT";
	var left = "LEFT";
	var talent = "TALENT";
	var die = "DIE"
	
	this.getNone = function() {	return none; }
	this.getUp =  function() { return up; }
	this.getRight = function() { return right; }
	this.getLeft = function() { return left; }
	this.getDie = function() { return die; }
	this.getTalent = function() { return talent; }
})();


var directions = new (function() {
	var up = 1;
	var right = 2;
	var down = 3;
	var left = 4;
	
	this.getUpId = function() {	return up; }
	this.getRightId = function() { return right; }
	this.getDownId = function() { return down; }
	this.getLeftId = function() { return left; }
	
	this.getUpCode = function() {	return "UP"; }
	this.getRightCode = function() { return "RIGHT"; }
	this.getDownCode = function() { return "DOWN"; }
	this.getLeftCode = function() { return "LEFT"; }
})();

var talents = new (function() {
	var bomb = "BOMB";
	var shoot = "SHOOT";
	
	this.getBomb = function() {	return bomb; }
	this.getShoot = function() { return shoot; }
})();

var roomStatus = new (function() {
	var wait = "WAIT";
	var ready = "READY";
	var fight = "FIGHT";
	var end = "END";
	
	this.getWait = function() { return wait; }
	this.getReady = function() { return ready; }
	this.getFight = function() { return fight; }
	this.getEnd = function() { return end; }
})();

function Player(playerPosition, arenaWidth) {

	this.id = ""
	this.nickName = "";
	this.energy = 100;
	this.motionStatus = motions.getNone();
	this.position = playerPosition;
	
	this.xPosition = 0;
	this.yPosition = 0;
	
	this.direction = 1;
	this.speed = 5;
	
	this.moveFrame = 1;
	this.attackFrame = 20;
	this.defenseFrame = 15;
	
	this.playerImage = "";
	this.playerAnimation = "";
	
	if (playerPosition === "1p") {
		this.xPosition = 30;
		this.direction = 1;
	} else {
		this.xPosition = arenaWidth - 30;
		this.direction = -1;
	}
	
	this.loadImage = function(playerImageSrc, onLoadCallBack, onErrorCallBack) {
		this.playerImage = new Image();
		this.playerImage.src = playerImageSrc;
		this.playerImage.onload = onLoadCallBack;
		this.playerImage.onerror = onErrorCallBack;
	}
	
	this.initAnimation = function(playerAnimation, name, direction, vX, vY, x, y) {
		this.playerAnimation = playerAnimation;

		this.playerAnimation.regX = this.playerAnimation.spriteSheet.frameWidth / 2 | 0;
		this.playerAnimation.regY = this.playerAnimation.spriteSheet.frameHeight / 2 | 0;
        
		this.playerAnimation.name = name;
		this.playerAnimation.direction = direction;
		this.playerAnimation.vX = vX;
		this.playerAnimation.vY = vY;
		this.playerAnimation.x = x;
		this.playerAnimation.y = y;
		
		this.playerAnimation.currentFrame = 10;
		
	}
	
	this.playPlayerAnimation = function(animationName) {
		this.playerAnimation.gotoAndPlay(animationName);
	}
	
	this.move = function(direction, moveX, moveY) {
		
		if (this.direction != direction) {
			switch (direction) {
			case directions.getUpId() : 
				this.playerAnimation.gotoAndPlay("UP");
				break;
			case directions.getRightId() : 
				this.playerAnimation.gotoAndPlay("RIGHT");
				break;
			case directions.getDownId() : 
				this.playerAnimation.gotoAndPlay("DOWN");
				break;
			case directions.getLeftId() : 
				this.playerAnimation.gotoAndPlay("LEFT");
				break;
			}
			
			this.direction = direction; 
		}
		
		this.xPosition += moveX;
		this.yPosition += moveY;
		
		this.playerAnimation.x = this.xPosition;
		this.playerAnimation.y = this.yPosition;
		
	} 
}
	