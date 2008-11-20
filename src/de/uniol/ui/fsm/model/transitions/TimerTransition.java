/*
 * FSM Copyright (C) 2008 Christian Hinrichs
 * 
 * FSM is copyright under the GNU General Public License.
 * 
 * This file is part of FSM.
 * 
 * FSM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * AdaptiveFridge is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with FSM.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.uniol.ui.fsm.model.transitions;

import de.uniol.ui.fsm.model.ActivationListener;
import de.uniol.ui.fsm.model.ClockListener;
import de.uniol.ui.fsm.model.FSM;
import de.uniol.ui.fsm.model.states.State;

/**
 * This is a timer transition. It may not listen to a specific signal, but
 * rather will be activated upon activation of the source state. A counter is
 * then initiated which counts a predefined number of clocks. If the counter is
 * finished, and the state is still active, the transition will be taken.
 * 
 * @author <a href=
 *         "mailto:Christian%20Hinrichs%20%3Cchristian.hinrichs@uni-oldenburg.de%3E"
 *         >Christian Hinrichs, christian.hinrichs@uni-oldenburg.de</a>
 * 
 */
public abstract class TimerTransition extends Transition implements
		ClockListener, ActivationListener {

	/** Signal string which identifies the timer signal */
	protected final static String EV_TM = "tm";
	
	/** whether we are active and waiting for execution */
	private boolean waiting = false;
	/** start time when we were activated */
	private long waitStart = 0L;
	/** delay we have to wait before execution */
	private long waitDelay = 0L;
	
	public TimerTransition(FSM parent, State source, State dest, long delay) {
		super(parent, EV_TM, source, dest);
		this.waitDelay = delay;
		parent.addClockListener(this);
		source.addActivationListener(this);
	}
	
	public void clock(long clock) {
		if (waiting && waitDelay >= 0L && clock >= waitStart + waitDelay) {
			cancel();
			signalReceived(EV_TM, getSource());
		}
	}
	
	/**
	 * Starts the timer
	 */
	protected void tm() {
		waitStart = getFsm().getClock();
		waiting = true;
	}
	
	/**
	 * Cancels the timer
	 */
	protected void cancel() {
		waiting = false;
		waitStart = 0L;
	}

	public boolean isWaiting() {
		return waiting;
	}

	/**
	 * This will be called when the source state's activation changes. If the
	 * state becomes active, the timer will be started, and cancelled otherwise.
	 * 
	 * @param active
	 * @param sender
	 */
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