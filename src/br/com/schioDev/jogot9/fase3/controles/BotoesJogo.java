package br.com.schioDev.jogot9.fase3.controles;

import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenHeight;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenWidth;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenResolution;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.types.CGPoint;

import br.com.schioDev.jogot9.Configuracoes.Assets;
import br.com.schioDev.jogot9.Controles.Botao;
import br.com.schioDev.jogot9.Delegates.BotoesDelegate;
import br.com.schioDev.jogot9.fase3.cenas.*;

public class BotoesJogo extends CCLayer implements BotoesDelegate {
	private Botao bt_baixo, bt_cima, bt_esquerda, bt_direita, bt_pause;

	private JogoCena delegate;

	public static BotoesJogo botoesJogo() {
		return new BotoesJogo();
	}

	public BotoesJogo() {
		this.setIsTouchEnabled(true);

		this.bt_cima = new Botao(Assets.UP);
		this.bt_baixo = new Botao(Assets.DOWN);
		this.bt_esquerda = new Botao(Assets.LEFT);
		this.bt_direita = new Botao(Assets.RIGHT);
		this.bt_pause = new Botao(Assets.PAUSE);

		this.bt_baixo.setDelegate(this);
		this.bt_cima.setDelegate(this);
		this.bt_direita.setDelegate(this);
		this.bt_esquerda.setDelegate(this);
		this.bt_pause.setDelegate(this);

		setPosicaoBotoes();
		addChild(bt_cima);
		addChild(bt_baixo);
		addChild(bt_esquerda);
		addChild(bt_direita);
		addChild(bt_pause);

	}

	private void setPosicaoBotoes() {

		bt_direita.setPosition(screenResolution(CGPoint.ccp(screenWidth() - 60,
				130)));
		bt_esquerda.setPosition(screenResolution(CGPoint.ccp(
				screenWidth() - 60, 30)));
		bt_baixo.setPosition(screenResolution(CGPoint.ccp(screenWidth() - 30,
				80)));
		bt_cima.setPosition(screenResolution(CGPoint
				.ccp(screenWidth() - 90, 80)));

		bt_pause.setPosition(screenResolution(CGPoint.ccp(40,
				screenHeight() - 30)));
	}

	@Override
	public void buttonClicked(Botao sender) {
		if (sender.equals(this.bt_cima)) {
			System.out.println("clicou bt_baixo");

			this.delegate.moverPraCima();
		}

		if (sender.equals(this.bt_baixo)) {
			System.out.println("clicou bt_baixo");

			this.delegate.moverPraBaixo();
		}

		if (sender.equals(this.bt_esquerda)) {
			this.delegate.moverPraEsquerda();
		}

		if (sender.equals(this.bt_direita)) {
			this.delegate.moverPraDireita();
		}

		if (sender.equals(this.bt_pause)) {
			this.delegate.pauseGameAndShowLayer();
		}
	}

	public void setDelegate(JogoCena cena) {
		this.delegate = cena;

	}
}
