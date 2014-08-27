package br.com.schioDev.jogot9.fase2.obj;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.opengl.CCBitmapFontAtlas;

import br.com.schioDev.jogot9.fase2.cenas.JogoCena;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenWidth;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenHeight;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenResolution;

public class Score extends CCLayer {

	public int score;
	private CCBitmapFontAtlas text;

	private JogoCena delegate;

	public void setDelegate(JogoCena delegate) {
		this.delegate = delegate;
	}

	public Score() {
		this.score = 0;

		this.text = CCBitmapFontAtlas.bitmapFontAtlas(
				String.valueOf(this.score), "UniSansSemiBold_Numbers_240.fnt");

		this.text.setScale((float) 240 / 240);
		this.text.setRotation(-90);
		this.setPosition(screenWidth() - 50, screenHeight() - 50);
		this.addChild(this.text);
	}

	public void increase() {
		score++;
		this.text.setString(String.valueOf(this.score));

		if (score == 9) {
			this.delegate.startFinalScreen();
		}

	}

}
