package de.uniol.ui.fsm.model.transitions;

import de.uniol.ui.fsm.model.ActivationListener;
import de.uniol.ui.fsm.model.ClockListener;
import de.uniol.ui.fsm.model.FSM;
import de.uniol.ui.fsm.model.states.State;

public abstract class TimerTransition extends Transition implements
		ClockListener, ActivationListener {

	protected final static String EV_TM = "tm";
	
	private boolean waiting = false;
	private long waitStart = 0L;
	private long waitDelay = 0L;
	
	public TimerTransition(FSM parent, State source, State dest, long delay) {
		super(parent, EV_TM, source, dest);
		this.waitDelay = delay;
		parent.addClockListener(this);
		source.addActivationListener(this);
	}
	
	public void clock(long clock) {
		if (waiting && clock >= waitStart + waitDelay) {
			cancel();
			signalReceived(EV_TM, getSource());
		}
	}
	
	protected void tm() {
		waitStart = getFsm().getClock();
		waiting = true;
	}
	
	protected void cancel() {
		waiting = false;
		waitStart = 0L;
	}

	public boolean isWaiting() {
		return waiting;
	}

	public void activationChanged(boolean active, Object sender) {
		if (sender == getSource()) {
			if (active) {
				tm();
			} else {
				cancel();
			}
		}
	}

	public long getWaitDelay() {
		return waitDelay;
	}

	public void setWaitDelay(long waitDelay) {
		this.waitDelay = waitDelay;
	}
}