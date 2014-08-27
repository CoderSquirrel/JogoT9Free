package br.com.schioDev.jogot9.Telas;

import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenWidth;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenHeight;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenResolution;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor4B;

import br.com.schioDev.jogot9.Configuracoes.Assets;
import br.com.schioDev.jogot9.Controles.Botao;
import br.com.schioDev.jogot9.Delegates.BotoesDelegate;
import br.com.schioDev.jogot9.Delegates.PauseDelegate;

public class PauseTela extends CCLayer implements BotoesDelegate {

	private Botao resume;

	private PauseDelegate delegate;

	private CCColorLayer background;

	public PauseTela() {
		this.setIsTouchEnabled(true);

		this.background = CCColorLayer.node(ccColor4B.ccc4(0, 0, 0, 175),
				screenWidth(), screenHeight());
		this.addChild(this.background);

		this.resume = new Botao(Assets.RESUME);
		this.resume.setDelegate(this);
		this.resume.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2,
				screenHeight() - 250)));
		this.addChild(resume);

	}

	public void setDelegate(PauseDelegate delegate) {
		this.delegate = delegate;
	}

	@Override
	public void buttonClicked(Botao sender) {
		if (sender == this.resume) {
			this.delegate.resumeGame();
			this.removeFromParentAndCleanup(true);
		}
	}

}
