package br.com.schioDev.jogot9.fase3.cenas;

import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenHeight;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenResolution;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenWidth;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import br.com.schioDev.jogot9.Configuracoes.Assets;
import br.com.schioDev.jogot9.Configuracoes.Runner;
import br.com.schioDev.jogot9.Delegates.PauseDelegate;
import br.com.schioDev.jogot9.Objetos.Fundo;
import br.com.schioDev.jogot9.Telas.PauseTela;
import br.com.schioDev.jogot9.fase3.controles.BotoesJogo;
import br.com.schioDev.jogot9.fase3.engines.AmyEngine;
import br.com.schioDev.jogot9.fase3.engines.AmyEngineDelegate;
import br.com.schioDev.jogot9.fase3.engines.AnjoEngine;
import br.com.schioDev.jogot9.fase3.engines.AnjoEngineDelegate;
import br.com.schioDev.jogot9.fase3.obj.Amy;
import br.com.schioDev.jogot9.fase3.obj.Anjo;
import br.com.schioDev.jogot9.fase3.obj.Rory;
import br.com.schioDev.jogot9.fase3.obj.Score;

public class JogoCena extends CCLayer implements AnjoEngineDelegate,
		AmyEngineDelegate, PauseDelegate {
	// Layers
	public CCLayer anjosLayer;
	private CCLayer scoreLayer;
	public CCLayer roryLayer;
	// private CCLayer acertosLayer;
	public CCLayer amyLayer;
	private CCLayer layerTop;

	private AnjoEngine anjoEngine;
	private AmyEngine amyEngine;

	public ArrayList<Anjo> anjoArray;
	public ArrayList<Rory> roryArray;
	public ArrayList<Amy> amyArray;

	private PauseTela pauseTela;

	public Rory rory;
	private Score score;
	private Fundo fundo;

	public static CCScene criarJogo() {
		JogoCena layer = new JogoCena();
		CCScene cena = CCScene.node();
		cena.addChild(layer);
		return cena;
	}

	public JogoCena() {
		this.fundo = new Fundo(Assets.FUNDO_F3);
		this.fundo.setPosition(screenResolution(CGPoint.ccp(
				screenWidth() / 2.0f, screenHeight() / 2.0f)));
		this.addChild(this.fundo);

		this.anjosLayer = CCLayer.node();
		this.roryLayer = CCLayer.node();
		this.amyLayer = CCLayer.node();
		this.scoreLayer = CCLayer.node();
		this.addObjetosDeJogo();

		this.layerTop = CCLayer.node();

		this.addChild(this.anjosLayer);
		this.addChild(this.roryLayer);
		this.addChild(this.amyLayer);
		this.addChild(this.scoreLayer);
		this.addChild(this.layerTop);

		BotoesJogo botoesJogoLayer = BotoesJogo.botoesJogo();
		botoesJogoLayer.setDelegate(this);
		this.addChild(botoesJogoLayer);
		this.setIsTouchEnabled(true);
	}

	public void addObjetosDeJogo() {
		this.anjoArray = new ArrayList<Anjo>();
		this.anjoEngine = new AnjoEngine();

		this.amyArray = new ArrayList<Amy>();
		this.amyEngine = new AmyEngine();

		this.rory = new Rory();
		this.roryLayer.addChild(this.rory);
		this.roryArray = new ArrayList<Rory>();
		this.roryArray.add(rory);

		this.score = new Score();
		this.score.setDelegate(this);
		this.scoreLayer.addChild(this.score);

	}

	public void iniciarJogo() {
		Runner.check().setGamePlaying(true);
		Runner.check().setGamePaused(false);

		this.schedule("checkHits");

		this.iniciarEngines();
	}

	public void checkHits(float dt) {

		this.checkRadiusHitsOfArray(this.anjoArray, this.amyArray, this,
				"anjoHit");

		this.checkRadiusHitsOfArray(this.anjoArray, this.roryArray, this,
				"roryHit");

		this.checkRadiusHitsOfArray(this.amyArray, this.roryArray, this,
				"amyHit");

	}
	@Override
	public void onEnter() {
		super.onEnter();
		this.iniciarJogo();
	}
	private boolean checkRadiusHitsOfArray(List<? extends CCSprite> array1,
			List<? extends CCSprite> array2, JogoCena gameScene, String hit) {

		boolean result = false;

		for (int i = 0; i < array1.size(); i++) {
			// Get Object from First Array
			CGRect rect1 = getBoarders(array1.get(i));

			for (int j = 0; j < array2.size(); j++) {
				// Get Object from Second Array
				CGRect rect2 = getBoarders(array2.get(j));

				// Check Hit!
				if (CGRect.intersects(rect1, rect2)) {
					System.out.println("Colision Detected: " + hit);
					result = true;

					Method method;
					try {
						if (hit.equals("amyHit")) {
							method = JogoCena.class.getMethod(hit,
									CCSprite.class);
							method.invoke(gameScene, array1.get(i));
						} else {
							System.out.println("meteoroHit");
							method = JogoCena.class.getMethod(hit,
									CCSprite.class, CCSprite.class);

							method.invoke(gameScene, array1.get(i),
									array2.get(j));
						}
					} catch (SecurityException e1) {
						e1.printStackTrace();
					} catch (NoSuchMethodException e1) {
						e1.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return result;
	}

	public CGRect getBoarders(CCSprite object) {
		CGRect rect = object.getBoundingBox();
		CGPoint GLpoint = rect.origin;
		CGRect GLrect = CGRect.make(GLpoint.x, GLpoint.y, rect.size.width,
				rect.size.height);

		return GLrect;
	}

	private void iniciarEngines() {

		this.addChild(anjoEngine);
		this.anjoEngine.setDelegate(this);
		this.addChild(amyEngine);
		this.amyEngine.setDelegate(this);
	}

	public void anjoHit(CCSprite anjo, CCSprite amy) {
		((Anjo) anjo).shooted();
		((Amy) amy).shooted();
		this.score.decrement();
	}

	public void roryHit(CCSprite anjo, CCSprite rory) {
		((Anjo) anjo).shooted();
		((Rory) rory).explode();
		CCDirector.sharedDirector().replaceScene(new GameOverCena().scene());
	}

	public void amyHit(CCSprite amy) {
		((Amy) amy).shooted();
		this.score.increase();
	}

	@Override
	public void resumeGame() {
		if (Runner.check().isGamePaused() || !Runner.check().isGamePlaying()) {

			// Resume game
			this.pauseTela = null;
			Runner.setGamePaused(false);
			this.setIsTouchEnabled(true);
		}
	}

	@Override
	public void quitGame() {
	}

	@Override
	public void pauseGameAndShowLayer() {
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {
			this.pauseGame();
		}

		if (Runner.check().isGamePaused() && Runner.check().isGamePlaying()
				&& this.pauseTela == null) {

			this.pauseTela = new PauseTela();
			this.layerTop.addChild(this.pauseTela);
			this.pauseTela.setDelegate(this);
		}
	}

	private void pauseGame() {
		if (!Runner.check().isGamePaused() && Runner.check().isGamePlaying()) {
			Runner.setGamePaused(true);
		}
	}

	@Override
	public void createAmy(Amy amy) {
		this.amyLayer.addChild(amy);
		amy.setDelegate(this);
		amy.start();
		this.amyArray.add(amy);
	}

	@Override
	public void removeAmy(Amy amy) {
		this.amyArray.remove(amy);
	}

	@Override
	public void createAnjo(Anjo anjo) {
		this.anjosLayer.addChild(anjo);
		anjo.setDelegate(this);
		anjo.start();
		this.anjoArray.add(anjo);

	}

	@Override
	public void removeAnjo(Anjo anjo) {
		this.anjoArray.remove(anjo);
	}

	public void moverPraCima() {
		System.out.println("moverPraCima");
		rory.moveUp();
	}

	public void moverPraBaixo() {
		System.out.println("moverPraBaixo");
		rory.moveDown();
	}

	public void moverPraEsquerda() {
		rory.moveLeft();
	}

	public void moverPraDireita() {
		rory.moveRight();
	}

	public void startFinalScreen() {
		CCDirector.sharedDirector().replaceScene(new FimCena().scene());
	}

}
