package br.com.schioDev.jogot9.fase2.obj;

import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenWidth;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import br.com.schioDev.jogot9.Configuracoes.Assets;
import br.com.schioDev.jogot9.Configuracoes.Runner;
import br.com.schioDev.jogot9.fase2.calibrate.Accelerometer;
import br.com.schioDev.jogot9.fase2.calibrate.AccelerometerDelegate;
import br.com.schioDev.jogot9.fase2.cenas.JogoCena;

public class Rose extends CCSprite implements AccelerometerDelegate {
	float positionX = screenWidth() / 3;
	float positionY = 50;
	public JogoCena scene;
	private Accelerometer accelerometer;
	private static final double NOISE = 1;
	public boolean moveALl = false;
	private float currentAccelX;
	private float currentAccelY;
	public boolean visible = true;

	public Rose() {
		super(Assets.ROSE);
		setPosition(positionX, positionY);
		this.schedule("update");
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
			if (moveALl) {
				if (this.currentAccelX < -NOISE) {
					this.positionX++;
				}

				if (this.currentAccelX > NOISE) {
					this.positionX--;
				}
			}
			if (this.currentAccelY < -NOISE) {
				this.positionY++;
			}

			if (this.currentAccelY > NOISE) {
				this.positionY--;
			}

			// Update Player Position
			this.setPosition(CGPoint.ccp(this.positionX, this.positionY));

		}
		checkIsOnTardis();

	}

	public void checkIsOnTardis() {
		CGRect tardis = scene.tardis.getBoundingBox();
		CGPoint pointTardis = tardis.origin;
		CGRect recTardis = CGRect.make(pointTardis.x, pointTardis.y,
				tardis.size.width, tardis.size.height);

		CGRect rose = this.getBoundingBox();
		CGPoint pointRose = rose.origin;
		CGRect recRose = CGRect.make(pointRose.x, pointRose.y, rose.size.width,
				rose.size.height);

		if (CGRect.intersects(recTardis, recRose)) {
			SUMIR();
			scene.tardis.SUMIR();
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
