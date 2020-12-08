package com.luxoft.jva.multithreading.ch06_atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * EXTRA!
 *
 * In this exercise we will play ping-pong again but this time we will implement
 * whole stuff using synchronization.
 *
 * @author BKuczynski.
 */
public class Exercise14 {
	private static Ball ball = new Ball();
	private static final int LOOP = 30;
	private static final Object PING_SYNC = new Object();

	public static class Ping implements Runnable {
		@Override
		public void run() {
			for (int i = 0; i < LOOP; i++) {
				synchronized (PING_SYNC) {
					ball.ping.getAndIncrement();
					System.out.println("Ping: " + ball.ping);
					PING_SYNC.notify();
					try {
						PING_SYNC.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static class Pong implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i < LOOP; i++) {
				synchronized (PING_SYNC) {
					ball.pong.getAndIncrement();
					System.out.println("Pong: " + ball.pong);
					PING_SYNC.notify();
					try {
						// using limit here to prevent hang after last increment
						PING_SYNC.wait(3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static class Ball {
		AtomicInteger ping = new AtomicInteger(0);
		AtomicInteger pong = new AtomicInteger(0);
	}

	public static void main(String[] args) {
		new Thread(new Exercise14.Ping()).start();
		new Thread(new Exercise14.Pong()).start();
	}
}

