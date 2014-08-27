package br.com.schioDev.jogot9.Controles;

import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenHeight;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenResolution;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenWidth;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

import br.com.schioDev.jogot9.Configuracoes.Assets;
import br.com.schioDev.jogot9.Delegates.BotoesDelegate;
import br.com.schioDev.jogot9.fase1.cenas.Instrucoes;

public class BotoesMenu extends CCLayer implements BotoesDelegate {
	private Botao jogar;

	public BotoesMenu() {
		this.setIsTouchEnabled(true);
		this.jogar = new Botao(Assets.JOGAR);
		this.jogar.setDelegate(this);

		this.jogar.setPosition(screenResolution(CGPoint.ccp(
				(float) (screenWidth() / 1.6), screenHeight() - 300)));
		addChild(jogar);
	}

	@Override
	public void buttonClicked(Botao sender) {
		if (sender.equals(this.jogar)) {
			System.out.println("Button clicked: Play");
			CCDirector.sharedDirector()
					.replaceScene(
							CCFadeTransition.transition(1.0f,
									new Instrucoes().scene()));
		}
	}

}
