package br.com.schioDev.jogot9.Telas;

import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenHeight;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenWidth;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenResolution;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.types.CGPoint;

import br.com.schioDev.jogot9.Configuracoes.Assets;
import br.com.schioDev.jogot9.Controles.BotoesMenu;
import br.com.schioDev.jogot9.Objetos.*;

public class Inicio extends CCLayer {

	private Fundo fundo;

	public CCScene cena() {
		CCScene cena = CCScene.node();
		cena.addChild(this);
		return cena;
	}

	public Inicio() {
		this.fundo = new Fundo(Assets.FUNDO_INICIO);
		this.fundo.setPosition(screenResolution(CGPoint.ccp(
				screenWidth() / 2.0f, screenHeight() / 2.0f)));
		this.addChild(this.fundo);

		BotoesMenu menuLayer = new BotoesMenu();
		this.addChild(menuLayer);
	}
}
