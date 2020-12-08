package com.luxoft.jva.multithreading.ch06_atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * In this exercise we will play atomic ping-pong again:
 * <ul>
 * <li>Create classes {@link Ping} and {@link Pong} that implements {@link Runnable}.</li>
 * <li>Create class {@link Ball} that has two {@link AtomicInteger} fields ping and pong.</li>
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
public class Exercise13 {
	private static Ball ball = new Ball();
	private static final int LOOP = 100;

	public static class Ping implements Runnable {
		@Override
		public void run() {
			for (int i = 0; i < LOOP; i++) {
				ball.ping.getAndIncrement();
				System.out.println("Ping: " + ball.ping);
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
				ball.pong.getAndIncrement();
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
		AtomicInteger ping = new AtomicInteger(0);
		AtomicInteger pong = new AtomicInteger(0);
	}

	public static void main(String[] args) {
		new Thread(new Exercise13.Ping()).start();
		new Thread(new Exercise13.Pong()).start();
	}
}