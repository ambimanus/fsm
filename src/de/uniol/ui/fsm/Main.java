package de.uniol.ui.fsm;

import de.uniol.ui.fsm.projects.fridge.BaseController;

public class Main {

	private final static long steps = 20000;
	private final static double speed = 1000.0;
	
	public static void main(String[] args) {
		BaseController bc = new BaseController();
		for (long l = 0L; l < steps; l++) {
			bc.clock();
//			System.out.println("Clock: " + bc.getClock());
			try {
				Thread.sleep(Math.round(1000.0 / speed));
			} catch (InterruptedException e) {
			}
		}
	}
}