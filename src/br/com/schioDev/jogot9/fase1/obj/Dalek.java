package br.com.schioDev.jogot9.fase1.obj;

import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenHeight;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenWidth;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenResolution;

import java.util.Random;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

import br.com.schioDev.jogot9.Configuracoes.Runner;
import br.com.schioDev.jogot9.fase1.engines.DalekEngineDelegate;

public class Dalek extends CCSprite {

	private DalekEngineDelegate delegate;
	public float x, y, x1;
	Random r = new Random();
	int i;

	public Dalek(String image) {
		super(image);
		do {
			x1 = r.nextInt(((int) screenWidth() - 40));
		} while (x1 < 20);
		x = x1;
		y = screenHeight();

	}

	public void start() {
		this.schedule("update");
	}

	public void update(float dt) {

		// pause
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {
			do {
				i = r.nextInt(10);
			} while (i == 0);
			y -= r.nextInt(10);
			this.setPosition(screenResolution(CGPoint.ccp(x, y)));

			if (y < 0)
				shooted();
		}
	}

	public void setDelegate(DalekEngineDelegate delegate) {
		this.delegate = delegate;
	}

	// hit
	public void shooted() {
		this.delegate.removeDalek(this);
		this.unschedule("update");
		float dt = 0.2f;
		CCScaleBy a1 = CCScaleBy.action(dt, 0.5f);
		CCFadeOut a2 = CCFadeOut.action(dt);
		CCSpawn s1 = CCSpawn.actions(a1, a2);
		CCCallFunc c1 = CCCallFunc.action(this, "removeMe");
		this.runAction(CCSequence.actions(s1, c1));

	}

	public void removeMe() {
		this.removeFromParentAndCleanup(true);
	}

}
