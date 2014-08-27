package br.com.schioDev.jogot9.fase2.obj;

import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenWidth;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenHeight;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenResolution;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import br.com.schioDev.jogot9.Configuracoes.Assets;
import br.com.schioDev.jogot9.Configuracoes.Runner;
import br.com.schioDev.jogot9.fase2.calibrate.Accelerometer;
import br.com.schioDev.jogot9.fase2.calibrate.AccelerometerDelegate;
import br.com.schioDev.jogot9.fase2.cenas.JogoCena;
import br.com.schioDev.jogot9.fase2.engines.*;

public class Ten extends CCSprite implements AccelerometerDelegate {

	private static final double NOISE = 1;

	private ShootEngineDelegate delegate;
	public JogoCena scene;
	float positionX = screenWidth() / 2;
	float positionY = 100;
	public boolean visible = true;

	public Accelerometer accelerometer;

	private float currentAccelX;

	private float currentAccelY;

	public Ten() {
		super(Assets.TEN);
		setPosition(positionX, positionY);
		this.schedule("update");
	}

	public void shoot() {
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {
			delegate.createShoot(new Shoot(positionX, positionY + 50));
		}
	}

	public void setDelegate(ShootEngineDelegate delegate) {
		this.delegate = delegate;
	}

	public void moveLeft() {
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {

			if (positionX > 30) {
				positionX -= 10;
			}
			setPosition(positionX, positionY);
		}
	}

	public void moveRight() {
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {

			if (positionX < screenWidth() - 30) {
				positionX += 10;
			}
			setPosition(positionX, positionY);
		}
	}

	public void explode() {

		// Stop Shoot
		this.unschedule("update");

		// Pop Actions
		float dt = 0.2f;
		CCScaleBy a1 = CCScaleBy.action(dt, 2f);
		CCFadeOut a2 = CCFadeOut.action(dt);
		CCSpawn s1 = CCSpawn.actions(a1, a2);

		// Run actions!
		this.runAction(CCSequence.actions(s1));

	}

	public void catchAccelerometer() {
		Accelerometer.sharedAccelerometer().catchAccelerometer();
		this.accelerometer = Accelerometer.sharedAccelerometer();
		this.accelerometer.setDelegate(this);
	}

	@Override
	public void accelerometerDidAccelerate(float x, float y) {
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {

			// Read acceleration
			this.currentAccelX = x;
			this.currentAccelY = y;

		}

	}

	public void update(float dt) {
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {

			// fazer primeiro com tudo zero depois colocar essa constant
			if (this.currentAccelX < -NOISE) {
				if (screenWidth() - 40 > positionX) {
					this.positionX++;
				}
			}

			if (this.currentAccelX > NOISE) {

				if (positionX > 40) {
					this.positionX--;

				}
			}

			if (this.currentAccelY < -NOISE) {
				if (screenHeight() - 40 > positionY) {
					this.positionY++;

				}
			}

			if (this.currentAccelY > NOISE) {
				if (positionY > 40) {
					this.positionY--;

				}
			}

			this.setPosition(CGPoint.ccp(this.positionX, this.positionY));
			 checkIsOnTardis();

		}
	}

	 public void checkIsOnTardis() {
	 CGRect tardis = scene.tardis.getBoundingBox();
	 CGPoint pointTardis = tardis.origin;
	 CGRect recTardis = CGRect.make(pointTardis.x, pointTardis.y,
	 tardis.size.width, tardis.size.height);
	
	 CGRect ten = this.getBoundingBox();
	 CGPoint pointTen = ten.origin;
	 CGRect recTen = CGRect.make(pointTen.x, pointTen.y, ten.size.width,
	 ten.size.height);
	
	 if (CGRect.intersects(recTardis, recTen)) {
	 System.out.println("tão junto");
	 scene.rose.moveALl = true;
	 scene.rose.catchAccelerometer();
	 SUMIR();
	
	 }
	 }

	public void SUMIR() {

		// PLay explosion Remove from Game Array

		// Stop Shoot
		this.unschedule("update");

		// Pop Actions
		float dt = 0.2f;
		CCScaleBy a1 = CCScaleBy.action(dt, 0.5f);
		CCFadeOut a2 = CCFadeOut.action(dt);
		CCSpawn s1 = CCSpawn.actions(a1, a2);

		// Call RemoveMe
		CCCallFunc c1 = CCCallFunc.action(this, "removeMe");

		// Run actions!
		this.runAction(CCSequence.actions(s1, c1));

	}

	public void removeMe() {
		this.removeFromParentAndCleanup(true);
	}
}
