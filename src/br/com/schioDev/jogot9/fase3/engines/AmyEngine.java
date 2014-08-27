package br.com.schioDev.jogot9.fase3.engines;

import java.util.Random;

import org.cocos2d.layers.CCLayer;

import br.com.schioDev.jogot9.Configuracoes.Assets;
import br.com.schioDev.jogot9.Configuracoes.Runner;
import br.com.schioDev.jogot9.fase3.obj.Amy;

public class AmyEngine extends CCLayer {

	private AmyEngineDelegate delegate;
	Random r = new Random();

	public AmyEngine() {
		this.schedule("amyEngine", 1.0f / 10f);
	}

	public void amyEngine(float dt) {

		// pause
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {
			int ram = r.nextInt(50);
			if (r.nextInt(ram) == 0) {
				this.getDelegate().createAmy(new Amy(Assets.AMY));
			}

		}
	}

	public void setDelegate(AmyEngineDelegate delegate) {
		this.delegate = delegate;
	}

	public AmyEngineDelegate getDelegate() {
		return delegate;
	}

}
