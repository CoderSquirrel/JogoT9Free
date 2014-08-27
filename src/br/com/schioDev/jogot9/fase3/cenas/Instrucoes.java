package br.com.schioDev.jogot9.fase3.cenas;

import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenHeight;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenResolution;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenWidth;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;

import android.view.MotionEvent;
import br.com.schioDev.jogot9.Configuracoes.Assets;
import br.com.schioDev.jogot9.Controles.Botao;
import br.com.schioDev.jogot9.Delegates.BotoesDelegate;
import br.com.schioDev.jogot9.Objetos.Fundo;

public class Instrucoes extends CCLayer implements BotoesDelegate {
	private Fundo fundo;

	public CCScene scene() {
		CCScene scene = CCScene.node();
		scene.addChild(this);
		return scene;
	}

	public Instrucoes() {

		this.fundo = new Fundo(Assets.INST_F3);
		this.fundo.setPosition(screenResolution(CGPoint.ccp(
				screenWidth() / 2.0f, screenHeight() / 2.0f)));
		this.addChild(fundo);

		// CCSprite title = CCSprite.sprite(Assets.FINAL_F2);
		// title.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2,
		// screenHeight() - 130)));
		// this.addChild(title);

		this.setIsTouchEnabled(true);
		// this.inicio = new Botao(Assets.JOGAR);
		// this.inicio.setPosition(screenResolution(CGPoint.ccp(screenWidth() /
		// 2,
		// screenHeight() - 300)));
		// this.inicio.setDelegate(this);
		// addChild(this.inicio);
	}

	@Override
	public void buttonClicked(Botao sender) {
		// if (sender.equals(this.inicio)) {
		CCDirector.sharedDirector().replaceScene(new JogoCena().criarJogo());
		// }
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		System.out.println("ccTouchesBegan");
		CCDirector.sharedDirector().replaceScene(new JogoCena().criarJogo());
		return true;
	}
}
