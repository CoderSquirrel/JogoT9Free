package br.com.schioDev.jogot9.fase3.obj;

import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenHeight;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenResolution;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenWidth;

import java.util.Random;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import br.com.schioDev.jogot9.Configuracoes.Runner;
import br.com.schioDev.jogot9.fase3.engines.AnjoEngineDelegate;

public class Anjo extends CCSprite {
	private AnjoEngineDelegate delegate;
	public float x, y;
	private Random r = new Random();

	public Anjo(String image) {

		super(image);
		x = r.nextInt(Math.round(screenWidth()));
		y = screenHeight();
	}

	public void start() {
		this.schedule("update");
	}

	public void update(float dt) {
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {
			y -= 1;
			this.setPosition(screenResolution(CGPoint.ccp(x, y)));
			if (y < 0) {
				removeMe();
				shooted();
			}
		}
	}

	public void setDelegate(AnjoEngineDelegate delegate) {
		this.delegate = delegate;
	}

	// hit
	public void shooted() {
		// Remove from Game Array
		this.delegate.removeAnjo(this);

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
