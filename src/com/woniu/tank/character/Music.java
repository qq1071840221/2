package com.woniu.tank.character;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
/**
 * 音乐播放
 * @author admin
 *
 */
public class Music implements Runnable{

	private String src;
	
	public Music(String src) {
		super();
		this.src = src;
	}

	/**
	 * 播放音乐
	 */
//	public void play() {
//		try {
//			File file = new File(src);
//			if (file.exists()) {
//				AudioClip ac = Applet.newAudioClip(file.toURL());
//				ac.loop();
//			}
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
//	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			File file = new File(src);
			if (file.exists()) {
				AudioClip ac = Applet.newAudioClip(file.toURL());
				ac.loop();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}

