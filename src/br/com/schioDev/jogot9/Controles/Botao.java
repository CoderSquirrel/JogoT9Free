package br.com.schioDev.jogot9.Controles;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import android.view.MotionEvent;
import br.com.schioDev.jogot9.Delegates.BotoesDelegate;

public class Botao extends CCLayer {
	private CCSprite botaoImagem;
	private BotoesDelegate delegate;

	public Botao(String botaoImagem) {
		this.setIsTouchEnabled(true);
		this.botaoImagem = CCSprite.sprite(botaoImagem);
		addChild(this.botaoImagem);
	}

	public void setDelegate(BotoesDelegate sender) {
		this.delegate = sender;
	}

	@Override
	protected void registerWithTouchDispatcher() {
		CCTouchDispatcher.sharedDispatcher()
				.addTargetedDelegate(this, 0, false);
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {

		CGPoint touchLocation = CGPoint.make(event.getX(), event.getY());
		touchLocation = CCDirector.sharedDirector().convertToGL(touchLocation);
		touchLocation = this.convertToNodeSpace(touchLocation);

		// Check Button touched
		if (CGRect.containsPoint(this.botaoImagem.getBoundingBox(),
				touchLocation)) {
			delegate.buttonClicked(this);
		}

		return true;
	}

}
