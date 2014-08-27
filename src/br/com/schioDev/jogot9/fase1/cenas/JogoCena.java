package br.com.schioDev.jogot9.fase1.cenas;

import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenHeight;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenResolution;
import static br.com.schioDev.jogot9.Configuracoes.DispConfig.screenWidth;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import android.view.MotionEvent;
import br.com.schioDev.jogot9.Configuracoes.Assets;
import br.com.schioDev.jogot9.Configuracoes.Runner;
import br.com.schioDev.jogot9.Delegates.PauseDelegate;
import br.com.schioDev.jogot9.Objetos.Fundo;
import br.com.schioDev.jogot9.Telas.Inicio;
import br.com.schioDev.jogot9.Telas.PauseTela;
import br.com.schioDev.jogot9.fase1.controles.JogoBotoes;
import br.com.schioDev.jogot9.fase1.engines.DalekEngine;
import br.com.schioDev.jogot9.fase1.engines.DalekEngineDelegate;
import br.com.schioDev.jogot9.fase1.obj.Dalek;
import br.com.schioDev.jogot9.fase1.obj.Score;
import br.com.schioDev.jogot9.fase2.cenas.Instrucoes;

public class JogoCena extends CCLayer implements DalekEngineDelegate,
		PauseDelegate {

	private CCLayer dalekLayer;
	private CCLayer scoreLayer;
	private CCLayer layerTop;

	public DalekEngine dalekEngine;

	private ArrayList<Dalek> dalekArray;

	private PauseTela pauseTela;

	private Score score;
	private Fundo fundo;

	public static CCScene criarJogo() {

		// Create Scene
		JogoCena layer = new JogoCena();
		CCScene scene = CCScene.node();
		scene.addChild(layer);

		return scene;
	}

	public JogoCena() {
		this.fundo = new Fundo(Assets.FUNDO_F1);
		this.fundo.setPosition(screenResolution(CGPoint.ccp(
				screenWidth() / 2.0f, screenHeight() / 2.0f)));
		this.addChild(fundo);

		JogoBotoes jogoBotoesLayer = JogoBotoes.jogoBotoes();
		jogoBotoesLayer.setDelegate(this);
		this.addChild(jogoBotoesLayer);

		this.dalekLayer = CCLayer.node();
		this.scoreLayer = CCLayer.node();

		this.adicionarObjetosDeJogo();
		this.layerTop = CCLayer.node();

		this.addChild(this.dalekLayer);
		this.addChild(this.scoreLayer);
		this.addChild(this.layerTop);

		this.setIsTouchEnabled(true);
	}

	public void adicionarObjetosDeJogo() {
		this.dalekArray = new ArrayList<Dalek>();
		this.dalekEngine = new DalekEngine();

		this.score = new Score();
		this.score.setDelegate(this);
		this.scoreLayer.addChild(this.score);
	}

	public void startGame() {
		Runner.check().setGamePlaying(true);
		Runner.check().setGamePaused(false);
		this.startEngines();
	}

	private void startEngines() {
		this.addChild(this.dalekEngine);
		this.dalekEngine.setDelegate(this);
	}

	@Override
	public void onEnter() {
		super.onEnter();
		this.startGame();

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
		// TODO Auto-generated method stub

	}

	private void pauseGame() {
		if (!Runner.check().isGamePaused() && Runner.check().isGamePlaying()) {
			Runner.setGamePaused(true);
		}
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

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		float x, y;
		for (int i = 0; i < dalekArray.size(); i++) {
			CGPoint location = CCDirector.sharedDirector().convertToGL(
					CGPoint.ccp(event.getX(), event.getY()));

			Dalek m = (Dalek) dalekArray.get(i);
			if (CGRect.containsPoint(m.getBoundingBox(), location)) {
				// if (m.getBoundingBox().contains(event.getX(), event.getY()))
				// {
				System.out.println("FUÉ");
				Method method;
				try {
					method = JogoCena.class.getMethod("dalekHit",
							CCSprite.class);

					method.invoke(this, dalekArray.get(i));

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
		return true;
	}

	public void dalekHit(CCSprite dalek) {
		System.out.println("dalekHit");
		((Dalek) dalek).shooted();
		this.score.increase();
	}

	@Override
	public void createDalek(Dalek dalek) {
		System.out.println("createDalek");
		this.dalekLayer.addChild(dalek);
		dalek.setDelegate(this);
		dalek.start();
		this.dalekArray.add(dalek);
	}

	@Override
	public void removeDalek(Dalek dalek) {
		this.dalekArray.remove(dalek);
	}

	public void startFinalScreen() {
		CCDirector.sharedDirector().replaceScene(new FimCena().scene());
	}
}
