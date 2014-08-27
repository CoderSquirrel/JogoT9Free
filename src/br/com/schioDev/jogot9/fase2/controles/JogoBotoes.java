package br.com.schioDev.jogot9.fase2.controles;

import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenHeight;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenResolution;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenWidth;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.types.CGPoint;

import br.com.schioDev.jogot9.Configuracoes.Assets;
import br.com.schioDev.jogot9.Controles.Botao;
import br.com.schioDev.jogot9.Delegates.BotoesDelegate;
import br.com.schioDev.jogot9.fase2.cenas.JogoCena;

public class JogoBotoes extends CCLayer implements BotoesDelegate {

	private Botao shoot;
	private Botao pause;
	private JogoCena delegate;

	public static JogoBotoes jogoBotoes() {
		return new JogoBotoes();
	}

	public JogoBotoes() {
		// Enable Touch
		this.setIsTouchEnabled(true);

		this.shoot = new Botao(Assets.SHOOTBOTAO);
		this.pause = new Botao(Assets.PAUSE);

		// Set Buttons Delegates
		this.shoot.setDelegate(this);
		this.pause.setDelegate(this);

		// set position
		setButtonspPosition();

		// Add Buttons to Screen
		// addChild(leftControl);
		// addChild(rightControl);
		addChild(shoot);
		addChild(pause);

	}

	private void setButtonspPosition() {

		// Buttons Positions
		shoot.setPosition(screenResolution(CGPoint.ccp(screenWidth() - 40, 40)));

		pause.setPosition(screenResolution(CGPoint.ccp(40, screenHeight() - 30)));
	}

	@Override
	public void buttonClicked(Botao sender) {
		if (sender.equals(this.shoot)) {
			this.delegate.shoot();
		}

		if (sender.equals(this.pause)) {
			this.delegate.pauseGameAndShowLayer();
		}
	}

	public void setDelegate(JogoCena gameScene) {
		this.delegate = gameScene;

	}
}
