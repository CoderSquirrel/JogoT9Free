package br.com.schioDev.jogot9.fase2.cenas;

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
import br.com.schioDev.jogot9.Telas.Inicio;
import br.com.schioDev.jogot9.Telas.PauseTela;
import br.com.schioDev.jogot9.fase2.controles.JogoBotoes;
import br.com.schioDev.jogot9.fase2.engines.CyberEngine;
import br.com.schioDev.jogot9.fase2.engines.CyberEngineDelegate;
import br.com.schioDev.jogot9.fase2.engines.ShootEngineDelegate;
import br.com.schioDev.jogot9.fase2.obj.Cyber;
import br.com.schioDev.jogot9.fase2.obj.Rose;
import br.com.schioDev.jogot9.fase2.obj.Score;
import br.com.schioDev.jogot9.fase2.obj.Shoot;
import br.com.schioDev.jogot9.fase2.obj.Tardis;
import br.com.schioDev.jogot9.fase2.obj.Ten;

public class JogoCena extends CCLayer implements CyberEngineDelegate,
		ShootEngineDelegate, PauseDelegate {

	public CCLayer cyberLayer;
	private CCLayer scoreLayer;
	private CCLayer tenLayer;
	private CCLayer roseLayer;
	private CCLayer shootsLayer;
	private CCLayer layerTop;
	private CCLayer tardisLayer;

	public CyberEngine cyberEngine;

	public ArrayList<Cyber> cyberArray;
	private ArrayList<Ten> tenArray;
	private ArrayList<Rose> roseArray;
	private ArrayList<Shoot> shootArray;

	private PauseTela pauseTela;

	private Ten ten;
	public Rose rose;
	private Score score;
	public Tardis tardis;
	private boolean autoCalibration;
	private Fundo fundo;
	private JogoBotoes botoesJogosLayer;

	public static CCScene criarJogo() {

		// Create Scene
		JogoCena layer = new JogoCena();
		CCScene scene = CCScene.node();
		scene.addChild(layer);

		return scene;
	}

	public JogoCena() {
		this.fundo = new Fundo(Assets.FUNDO_F2);
		this.fundo.setPosition(screenResolution(CGPoint.ccp(
				screenWidth() / 2.0f, screenHeight() / 2.0f)));
		this.addChild(this.fundo);

		this.botoesJogosLayer = JogoBotoes.jogoBotoes();
		botoesJogosLayer.setDelegate(this);
		this.addChild(botoesJogosLayer);

		this.cyberLayer = CCLayer.node();
		this.roseLayer = CCLayer.node();
		this.tenLayer = CCLayer.node();
		this.scoreLayer = CCLayer.node();
		this.tardisLayer = CCLayer.node();
		this.addObjetosDeJogo();

		this.shootsLayer = CCLayer.node();
		this.layerTop = CCLayer.node();

		this.addChild(this.cyberLayer);
		this.addChild(this.roseLayer);
		this.addChild(this.tenLayer);
		this.addChild(this.shootsLayer);
		this.addChild(this.scoreLayer);
		this.addChild(this.layerTop);

		this.setIsTouchEnabled(true);
	}

	private void addObjetosDeJogo() {
		this.cyberArray = new ArrayList<Cyber>();
		this.cyberEngine = new CyberEngine();

		this.rose = new Rose();
		this.rose.scene = this;
		this.roseLayer.addChild(this.rose);

		this.ten = new Ten();
		this.ten.scene = this;
		this.tenLayer.addChild(this.ten);

		// score
		this.score = new Score();
		this.score.setDelegate(this);
		this.scoreLayer.addChild(this.score);

		// startgame
		this.roseArray = new ArrayList();
		this.roseArray.add(this.rose);

		this.tenArray = new ArrayList();
		this.tenArray.add(this.ten);

		this.shootArray = new ArrayList();
		this.ten.setDelegate(this);
	}

	public void startGame() {

		// Set Game Status
		// PAUSE
		Runner.check().setGamePlaying(true);
		Runner.check().setGamePaused(false);

		// Catch Accelerometer
		ten.catchAccelerometer();

		// pause
		// SoundEngine.sharedEngine().setEffectsVolume(1f);
		// SoundEngine.sharedEngine().setSoundVolume(1f);

		// startgame
		System.out.println("startgame");
		this.schedule("checkHits");
		this.startEngines();
	}

	@Override
	public void onEnter() {
		super.onEnter();

		// Start Game when transition did finish
		if (!this.autoCalibration) {
			this.startGame();
		}
	}

	// startgame
	public void checkHits(float dt) {

		this.checkRadiusHitsOfArray(this.cyberArray, this.shootArray, this,
				"cyberHit");

		this.checkRadiusHitsOfArray(this.cyberArray, this.roseArray, this,
				"roseHit");

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
					// System.out.println("Colision Detected: " + hit);
					result = true;

					Method method;
					try {
						method = JogoCena.class.getMethod(hit, CCSprite.class,
								CCSprite.class);

						method.invoke(gameScene, array1.get(i), array2.get(j));

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

	private void startEngines() {
		this.addChild(this.cyberEngine);
		this.cyberEngine.setDelegate(this);
	}

	@Override
	public void createCyber(Cyber cyber) {

		this.cyberLayer.addChild(cyber);
		cyber.setDelegate(this);
		cyber.start();
		this.cyberArray.add(cyber);

	}

	public boolean shoot() {
		ten.shoot();
		return true;
	}

	@Override
	public void createShoot(Shoot shoot) {

		this.shootsLayer.addChild(shoot);
		shoot.setDelegate(this);
		shoot.start();
		this.shootArray.add(shoot);

	}

	public void moveLeft() {
		ten.moveLeft();
	}

	public void moveRight() {
		ten.moveRight();
	}

	public void cyberHit(CCSprite cyber, CCSprite shoot) {
		((Cyber) cyber).shooted();
		((Shoot) shoot).explode();
		this.score.increase();
		verifyScore();
	}

	public void verifyScore() {
		if (this.score.score >= 9) {
			startFinalScreen();
		}
	}

	@Override
	public void removeCyber(Cyber cyber) {
		this.cyberArray.remove(cyber);
	}

	@Override
	public void removeShoot(Shoot shoot) {
		this.shootArray.remove(shoot);
	}

	public void roseHit(CCSprite cyber, CCSprite player) {
		CCDirector.sharedDirector().replaceScene(new GameOverCena().scene());
		((Cyber) cyber).shooted();
		((Rose) rose).explode();
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
		CCDirector.sharedDirector().replaceScene(new Inicio().cena());

	}

	public void startFinalScreen() {
		CCDirector.sharedDirector().replaceScene(new FimCena().scene());
	}
}
