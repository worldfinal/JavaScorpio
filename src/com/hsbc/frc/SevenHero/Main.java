package com.hsbc.frc.SevenHero;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import com.hsbc.frc.SevenHero.beans.Hero;
import com.hsbc.frc.SevenHero.beans.Heros;
import com.hsbc.frc.SevenHero.beans.Running_time;
import com.hsbc.frc.SevenHero.beans.SevenHeroConfig;

public class Main extends TimerTask{
  private Hero hero = null;
	private Timer timer = null;
	private int period;
	
	public Main(Hero hero, Timer timer) {
		this.hero = hero;
		this.timer = timer;
	}
	private static String[] input_qqarr = new String[]{"84378832","550702416", "2292805430", "2573944001", "2541510352", "1434827745", "1807316769", "1670765882", "2821437594"};
	private static String[] input_pswarr = new String[]{"november","fengrenchang","fengrenchang","fengrenchang","fengrenchang","fengrenchang","fengrenchang","fengrenchang", "november"};
	private static int[] periodarr = new int[]{60,60, 60, 60, 60, 60, 60, 60, 60}; 

	public static void main(String args[]) {
		long delay = 0;
		SevenHeroConfig shc = new SevenHeroConfig();
/*		shc.setHeros(new Heros());
		for (int i = 0; i < input_qqarr.length; i++) {
			Hero hero = new Hero();
			hero.setNumber(input_qqarr[i]);
			hero.setPassword(input_pswarr[i]);
			hero.setName("");
			hero.setDelay(30 * 1000 * 60);
			hero.setAddBuildingCard("false");
	
			shc.getHeros().addHero(hero);
		}
		BaseHero.saveXML(shc);
		if (delay >= 0) {
			return;
		}
		*/
		shc = BaseHero.readXML();
		Heros heros = shc.getHeros();
				
		for (int i = 0; i < heros.getHeroCount(); i++) {
			Hero hero = heros.getHero(i);
			Timer timer = new Timer();
			Main main = new Main(hero, timer);
			timer.schedule(main, delay, 60*60*24*1000);
			delay += 10*1000;
		}
		BaseHero.saveXML(shc);
		/*	
		for (int i = 0; i < input_qqarr.length; i++) {
			Hero hero = new Hero();
			hero.setNumber(input_qqarr[i]);
			hero.setPassword(input_pswarr[i]);	
			Timer timer = new Timer();
			Main main = new Main(hero, timer);
			timer.schedule(main, delay, 60*60*24*1000);
			main.setPeriod(periodarr[i]);
			delay += 2*1000;
		}*/
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	@Override
	public void run() {
		ScorpioHero sh = new ScorpioHero();
		sh.setHero(hero);
		sh.setTimer(timer);
		Thread t = new Thread(sh);
		t.start();
	}
	
}
