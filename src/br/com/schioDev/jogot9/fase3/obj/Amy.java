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
import br.com.schioDev.jogot9.fase3.engines.AmyEngineDelegate;

public class Amy extends CCSprite {
	private AmyEngineDelegate delegate;
	private float x, y;

	public Amy(String image) {
		super(image);
		x = new Random().nextInt(Math.round(screenWidth() - 10));
		y = new Random().nextInt(Math.round(screenHeight() - 30));
		System.out.println("criou");
	}

	public void start() {
		this.schedule("update");
	}

	public void update(float dt) {
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {

			this.setPosition(screenResolution(CGPoint.ccp(x, y)));
		}
	}

	public void setDelegate(AmyEngineDelegate delegate) {
		this.delegate = delegate;
	}

	// hit
	public void shooted() {

		// Remove from Game Array
		this.delegate.removeAmy(this);

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
