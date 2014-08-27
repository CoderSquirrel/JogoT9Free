package br.com.schioDev.jogot9.fase2.engines;

import java.util.Random;

import org.cocos2d.layers.CCLayer;

import br.com.schioDev.jogot9.Configuracoes.Assets;
import br.com.schioDev.jogot9.Configuracoes.Runner;
import br.com.schioDev.jogot9.fase2.obj.Cyber;

public class CyberEngine extends CCLayer {
	private CyberEngineDelegate delegate;

	public CyberEngine() {
		this.schedule("cyberEngine", 1.0f / 10f);
	}

	public void cyberEngine(float dt) {

		// pause
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {

			if (new Random().nextInt(20) == 0) {
				this.getDelegate().createCyber(new Cyber(Assets.CYBER));
			}

		}
	}

	public void setDelegate(CyberEngineDelegate delegate) {
		this.delegate = delegate;
	}

	public CyberEngineDelegate getDelegate() {
		return delegate;
	}

}
