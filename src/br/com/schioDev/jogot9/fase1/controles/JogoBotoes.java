package br.com.schioDev.jogot9.fase1.controles;

import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenHeight;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenResolution;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.types.CGPoint;

import br.com.schioDev.jogot9.Configuracoes.Assets;
import br.com.schioDev.jogot9.Controles.Botao;
import br.com.schioDev.jogot9.Delegates.BotoesDelegate;
import br.com.schioDev.jogot9.fase1.cenas.JogoCena;

public class JogoBotoes extends CCLayer implements BotoesDelegate {

	private Botao pause;

	private JogoCena delegate;

	public static JogoBotoes jogoBotoes() {
		return new JogoBotoes();
	}

	public JogoBotoes() {
		this.setIsTouchEnabled(true);
		this.pause = new Botao(Assets.PAUSE);
		this.pause.setDelegate(this);
		this.pause.setPosition(screenResolution(CGPoint.ccp(40,
				screenHeight() - 30)));
		this.addChild(this.pause);

	}

	@Override
	public void buttonClicked(Botao sender) {
		if (sender.equals(this.pause)) {
			this.delegate.pauseGameAndShowLayer();
		}
	}

	public void setDelegate(JogoCena gameScene) {
		this.delegate = gameScene;

	}

}
