package br.com.schioDev.jogot9.fase1.engines;

import java.util.Random;

import org.cocos2d.layers.CCLayer;

import br.com.schioDev.jogot9.Configuracoes.Assets;
import br.com.schioDev.jogot9.Configuracoes.Runner;
import br.com.schioDev.jogot9.fase1.obj.Dalek;

public class DalekEngine extends CCLayer {

	private DalekEngineDelegate delegate;

	public DalekEngine() {

		this.schedule("dalekEngine", 1.0f / 10f);
	}

	public void dalekEngine(float dt) {
		// pause
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {

			if (new Random().nextInt(10) == 0) {
				this.getDelegate().createDalek(new Dalek(Assets.DALEK));
			}

		}
	}

	public void setDelegate(DalekEngineDelegate delegate) {
		this.delegate = delegate;
	}

	public DalekEngineDelegate getDelegate() {
		return delegate;
	}

}
