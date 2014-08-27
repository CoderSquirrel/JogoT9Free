package br.com.schioDev.jogot9.fase3.obj;

import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenWidth;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenHeight;

import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

import br.com.schioDev.jogot9.Configuracoes.Assets;
import br.com.schioDev.jogot9.Configuracoes.Runner;

public class Rory extends CCSprite {
	public float positionX = screenWidth() / 2;
	public float positionY = 100;

	public Rory() {
		super(Assets.RORY);
		setPosition(positionX, positionY);
		this.schedule("update");
	}

	public void moveUp() {
		System.out.println("moveUp");
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {

			if (positionX > 30) {
				positionX -= 20;
			}
			setPosition(positionX, positionY);
		}
	}

	public void moveDown() {
		System.out.println("moveDown");
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {

			if (positionX < screenWidth() - 30) {
				positionX += 20;
			}
			setPosition(positionX, positionY);
		}
	}

	public void moveLeft() {

		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {

			if (positionY > 30) {
				positionY -= 20;
				if (scaleY_ < 0)
					this.scaleY_ *= -1;

			}
			setPosition(positionX, positionY);
		}
	}

	public void moveRight() {

		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {

			if (positionY < screenHeight() - 30) {
				positionY += 20;
				if (scaleY_ > 0)
					this.scaleY_ *= -1;

			}
			setPosition(positionX, positionY);
		}
	}

	public void explode() {
		this.unschedule("update");

		float dt = 0.2f;
		CCScaleBy a1 = CCScaleBy.action(dt, 2f);
		CCFadeOut a2 = CCFadeOut.action(dt);
		CCSpawn s1 = CCSpawn.actions(a1, a2);

		this.runAction(CCSequence.actions(s1));

	}

	public void update(float dt) {
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {
			this.setPosition(CGPoint.ccp(this.positionX, this.positionY));

		}
	}
}
