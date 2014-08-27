package br.com.schioDev.jogot9.fase2.obj;

import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenWidth;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenHeight;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenResolution;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCIntervalAction;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.actions.tile.CCWavesTiles3D;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import br.com.schioDev.jogot9.Configuracoes.Assets;
import br.com.schioDev.jogot9.fase2.cenas.JogoCena;

public class Tardis extends CCSprite {

	public JogoCena scene;

	public Tardis() {
		super(Assets.TARDIS);
		setPosition(screenResolution(CGPoint.ccp(150, screenHeight() / 2)));
		// this.schedule("update");
	}

	public void SUMIR() {
		System.out.println("sumir com a tardis");
		// Pop Actions
		float dt = 0.1f;
		CCScaleBy a1 = CCScaleBy.action(dt, 0.5f);
		// CCTurnOffTiles t = CCTurnOffTiles.action(dt);
		// CCSpawn s1 = CCSpawn.actions(a1, t);
		CCFadeOut a2 = CCFadeOut.action(dt);
		CCIntervalAction d = CCWavesTiles3D.action(dt);
		// CCIntervalAction B = CCBlink.action(dt);
		CCSpawn s1 = CCSpawn.actions(a1, d);

		// Call RemoveMe
		CCCallFunc c1 = CCCallFunc.action(this, "removeMe");

		// Run actions!
		this.runAction(CCSequence.actions(s1, c1));

		scene.startFinalScreen();
	}

	public void removeMe() {
		this.removeFromParentAndCleanup(true);
	}
}
