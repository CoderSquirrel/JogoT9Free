package br.com.schioDev.jogot9.fase3.engines;

import java.util.Random;

import org.cocos2d.layers.CCLayer;

import br.com.schioDev.jogot9.Configuracoes.Assets;
import br.com.schioDev.jogot9.Configuracoes.Runner;
import br.com.schioDev.jogot9.fase3.obj.Anjo;

public class AnjoEngine extends CCLayer {

	private AnjoEngineDelegate delegate;
	Random r = new Random();

	public AnjoEngine() {
		this.schedule("meteorsEngine", 1.0f / 10f);
	}

	public void meteorsEngine(float dt) {

		// pause
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {
			int ram = r.nextInt(200);

			if (r.nextInt(ram) == 0) {
				this.getDelegate().createAnjo(new Anjo(Assets.ANJO));
			}

		}
	}

	public void setDelegate(AnjoEngineDelegate delegate) {
		this.delegate = delegate;
	}

	public AnjoEngineDelegate getDelegate() {
		return delegate;
	}

}
