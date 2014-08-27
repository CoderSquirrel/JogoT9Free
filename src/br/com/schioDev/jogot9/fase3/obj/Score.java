package br.com.schioDev.jogot9.fase3.obj;

import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenHeight;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenWidth;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.opengl.CCBitmapFontAtlas;

import br.com.schioDev.jogot9.fase3.cenas.JogoCena;


public class Score extends CCLayer {

	private int score;
	private CCBitmapFontAtlas saves;

	private JogoCena delegate;

	public void setDelegate(JogoCena delegate) {
		this.delegate = delegate;
	}

	public Score() {
		this.score = 0;
		this.saves = CCBitmapFontAtlas.bitmapFontAtlas(
				String.valueOf(this.score), "UniSansBold_AlphaNum_50.fnt");

		this.saves.setScale((float) 240 / 240);
		this.saves.setRotation(-90);

		this.setPosition(screenWidth() - 50, screenHeight() - 50);

		this.addChild(this.saves);
	}

	public void increase() {
		score++;
		this.saves.setString(String.valueOf(this.score));

		if (score == 9) {
			this.delegate.startFinalScreen();
		}

	}

	public void decrement() {
		score--;
		this.saves.setString(String.valueOf(this.score));

	}

}
