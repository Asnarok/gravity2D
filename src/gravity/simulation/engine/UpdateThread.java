package gravity.simulation.engine;

import gravity.simulation.Simulator;

public class UpdateThread implements Runnable {

	@Override
	public void run() {
		long millis = System.currentTimeMillis();
		long timerMillis;
		long delta;
		while(true) {
			while(Simulator.running) {
				delta = System.currentTimeMillis()-millis;
				if(delta >= Simulator.oneUpdateTime*1000) {
					timerMillis = System.currentTimeMillis();
					Engine.updateWorld();
					Simulator.currentUpdateDuration = (int) (System.currentTimeMillis()-timerMillis);
					millis = System.currentTimeMillis();
				}else {
					try {
						Thread.sleep((long) (Simulator.oneUpdateTime*1000-delta));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
