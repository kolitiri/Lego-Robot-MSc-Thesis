

import lejos.nxt.Sound;

public class StarWarsThread extends Thread {
	// Frequencies for defined notes. Add more as needed.
	final static int G3 = 392;
	final static int D3 = 311;
	final static int A = 466;
	final static int D4 = 587;
	final static int D4u = 622;
	final static int F = 370;
	final static int BEAT = 200;

	// StarWars Imperial March musical notes
	final static int STARWARS_INTRO[][] = { { G3, 2 * BEAT }, { 0, 1 * BEAT },
			{ G3, 2 * BEAT }, { 0, 1 * BEAT }, { G3, 2 * BEAT },
			{ 0, 1 * BEAT }, { D3, 2 * BEAT }, { A, 1 * BEAT },
			{ G3, 3 * BEAT }, { D3, 2 * BEAT }, { A, 1 * BEAT },
			{ G3, 3 * BEAT }, { 0, 2 * BEAT }, { D4, 2 * BEAT },
			{ 0, 1 * BEAT }, { D4, 2 * BEAT }, { 0, 1 * BEAT },
			{ D4, 2 * BEAT }, { 0, 1 * BEAT }, { D4u, 2 * BEAT },
			{ A, 1 * BEAT }, { F, 3 * BEAT }, { D3, 2 * BEAT },
			{ A, 1 * BEAT }, { G3, 2 * BEAT } };

	/*
	 * Constructor
	 */
	public StarWarsThread() {
		super();
		start();
	}

	public void run() {
		play();
	}

	public void play() {
		try {
			sleep(2000);
			int i = 0;
			while (i < STARWARS_INTRO.length) {
				Sound.playTone(STARWARS_INTRO[i][0], STARWARS_INTRO[i][1]);
				yield();
				sleep(BEAT);
				i++;
			}
		} catch (InterruptedException iex) {
		}
	}
}
