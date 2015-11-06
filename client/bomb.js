
function Bomb(id, xPosition, yPosition) {

	this.id = id;
	
	this.xPosition = xPosition;
	this.yPosition = yPosition;
	
	this.bombImage = "";
	this.bombAnimation = "";

	this.explosionImage = "";
	this.explosionAnimation = "";
	
	this.explosionTick = 100;
	
	this.loadImageCount = 0;
	
	this.loadImage = function(bombImageSrc, explosionImageSrc, onLoadCallBack, onErrorCallBack) {
		this.bombImage = new Image();
		this.bombImage.src = bombImageSrc;
		this.bombImage.onload = onLoadCallBack;
		this.bombImage.onerror = onErrorCallBack;
		
		this.explosionImage = new Image();
		this.explosionImage.src = explosionImageSrc;
		this.explosionImage.onload = onLoadCallBack;
		this.explosionImage.onerror = onErrorCallBack;
	}

	
	this.initAnimation = function(bombAnimation, explosionAnimation) {
			
		this.bombAnimation = bombAnimation;

		this.bombAnimation.regX = this.bombAnimation.spriteSheet.frameWidth / 2 | 0;
		this.bombAnimation.regY = this.bombAnimation.spriteSheet.frameHeight / 2 | 0;
		this.bombAnimation.vX = 0;
		this.bombAnimation.vY = 0;
		this.bombAnimation.x = 0;
		this.bombAnimation.y = 0;

		this.bombAnimation = bombAnimation;

		this.bombAnimation.currentFrame = 10;
		
		this.explosionAnimation = explosionAnimation;

		this.explosionAnimation.regX = this.explosionAnimation.spriteSheet.frameWidth / 2 | 0;
		this.explosionAnimation.regY = this.explosionAnimation.spriteSheet.frameHeight / 2 | 0;
		this.explosionAnimation.vX = 0;
		this.explosionAnimation.vY = 0;
		this.explosionAnimation.x = 0;
		this.explosionAnimation.y = 0;

		this.explosionAnimation = explosionAnimation;

		this.explosionAnimation.currentFrame = 10;
	}
	
	this.playBombAnimation = function() {
		this.bombAnimation.x = this.xPosition;
		this.bombAnimation.y = this.yPosition;
		this.bombAnimation.gotoAndPlay("COUNTDOWN");
	} 
	
	this.playExplosionAnimation = function() {
		this.explosionAnimation.x = this.xPosition;
		this.explosionAnimation.y = this.yPosition;
		this.explosionAnimation.gotoAndPlay("EXPLOSION");
	} 
}
	