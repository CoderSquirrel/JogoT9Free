package br.com.schioDev.jogot9.Configuracoes;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import android.hardware.SensorManager;

public class DispConfig {
	private static SensorManager sensormanager;

	public static void setSensorManager(SensorManager sensorManagerRef) {
		sensormanager = sensorManagerRef;
	}

	public static CGPoint screenResolution(CGPoint gcPoint) {
		return gcPoint;
	}

	public static float screenWidth() {
		return winSize().width;
	}

	public static float screenHeight() {
		return winSize().height;
	}

	public static CGSize winSize() {
		return CCDirector.sharedDirector().winSize();
	}

	public static SensorManager getSensormanager() {
		return sensormanager;
	}

}
