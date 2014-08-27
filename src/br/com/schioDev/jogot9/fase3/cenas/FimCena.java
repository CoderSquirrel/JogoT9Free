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
import br.com.schioDev.jogot9.Telas.Fim;
import br.com.schioDev.jogot9.fase2.cenas.Instrucoes;

public class FimCena extends CCLayer {
	private Fundo fundo;

	public CCScene scene() {
		CCScene scene = CCScene.node();
		scene.addChild(this);
		return scene;
	}

	public FimCena() {

		this.fundo = new Fundo(Assets.FINAL_F3);
		this.fundo.setPosition(screenResolution(CGPoint.ccp(
				screenWidth() / 2.0f, screenHeight() / 2.0f)));
		this.addChild(fundo);
		this.setIsTouchEnabled(true);

	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		CCDirector.sharedDirector().replaceScene(new Fim().scene());
		return super.ccTouchesBegan(event);
	}
}
