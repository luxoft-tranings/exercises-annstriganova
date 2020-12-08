package com.luxoft.jva.multithreading.ch06_atomic;

/**
 * In this exercise we will play volatile ping-pong:
 * <ul>
 * <li>Create classes {@link Ping} and {@link Pong} that implements {@link Runnable}.</li>
 * <li>Create class {@link Ball} that has two volatile fields ping and pong.</li>
 * </ul>
 * <p>
 * <p>
 * In loop
 * {@link Ping}:
 * <ul>
 * <li>Increase ping value by 1</li>
 * <li>Do nothing while current step != pong</li>
 * </ul>
 * <p>
 * <p>
 * {@link Pong}:
 * <ul>
 * <li>Do nothing while ping != current step</li>
 * <li>Increase pong value by 1</li>
 * </ul>
 *
 * @author BKuczynski.
 */
public class Exercise12 {
	private static Ball ball = new Ball();
	private static final int LOOP = 10;

	public static class Ping implements Runnable {
		@Override
		public void run() {
			for (int i = 0; i < LOOP; i++) {
				ball.ping++;
				System.out.println("Ping: " + ball.ping);
/*				while (ball.pong != i) {
					// do nothing
				}*/
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static class Pong implements Runnable {
		@Override
		public void run() {
			for (int i = 0; i < LOOP; i++) {
/*				while (ball.ping != i) {
					// do nothing
				}*/
				ball.pong++;
				System.out.println("Pong: " + ball.pong);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static class Ball {
		volatile int ping = 0;
		volatile int pong = 0;
	}

	public static void main(String[] args) {
		new Thread(new Ping()).start();
		new Thread(new Pong()).start();
	}
}
