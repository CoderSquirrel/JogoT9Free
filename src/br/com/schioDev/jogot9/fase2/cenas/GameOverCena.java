package br.com.schioDev.jogot9.fase2.cenas;

import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenHeight;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenResolution;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenWidth;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import br.com.schioDev.jogot9.Configuracoes.Assets;
import br.com.schioDev.jogot9.Controles.Botao;
import br.com.schioDev.jogot9.Delegates.BotoesDelegate;
import br.com.schioDev.jogot9.Objetos.Fundo;
import br.com.schioDev.jogot9.fase2.cenas.JogoCena;

public class GameOverCena extends CCLayer implements BotoesDelegate {
	private Fundo fundo;
	private Botao inicio;

	public CCScene scene() {
		CCScene scene = CCScene.node();
		scene.addChild(this);
		return scene;
	}

	public GameOverCena() {

		this.fundo = new Fundo(Assets.FUNDO_F2);
		this.fundo.setPosition(screenResolution(CGPoint.ccp(
				screenWidth() / 2.0f, screenHeight() / 2.0f)));
		this.addChild(fundo);

		CCSprite title = CCSprite.sprite(Assets.GAMEOVER_FASE2);
		title.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2,
				screenHeight() - 130)));
		this.addChild(title);

		this.setIsTouchEnabled(true);
		this.inicio = new Botao(Assets.AGAIN);
		this.inicio.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2,
				screenHeight() - 300)));
		this.inicio.setDelegate(this);
		addChild(this.inicio);
	}

	@Override
	public void buttonClicked(Botao sender) {
		if (sender.equals(this.inicio)) {
			CCDirector.sharedDirector()
					.replaceScene(new JogoCena().criarJogo());
		}
	}
}
