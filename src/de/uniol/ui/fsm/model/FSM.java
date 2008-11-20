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
package de.uniol.ui.fsm.model;

import java.util.ArrayList;
import java.util.Iterator;

import de.uniol.ui.fsm.model.states.NullState;
import de.uniol.ui.fsm.model.states.State;

/**
 * This is the central class of this framework. It represents a FSM which is
 * timed by an external reference clock and is able to dispatch sent signals to
 * listening {@link SignalListener} objects.
 * 
 * @author <a href=
 *         "mailto:Christian%20Hinrichs%20%3Cchristian.hinrichs@uni-oldenburg.de%3E"
 *         >Christian Hinrichs, christian.hinrichs@uni-oldenburg.de</a>
 * 
 */
public class FSM {

	private String name;
	private long clock = 0L;
//	private Transition defaultTransition = null;
//	private ArrayList<State> states = new ArrayList<State>();
	private ArrayList<Signal> waitingSignals = new ArrayList<Signal>();
	private ArrayList<SignalListener> signalListeners = new ArrayList<SignalListener>();
	private ArrayList<ClockListener> clockListeners = new ArrayList<ClockListener>();

	/**
	 * Represents a signal which consists of a name, a sender object, and an
	 * array of parameters.
	 * 
	 * @author <a href=
	 *         "mailto:Christian%20Hinrichs%20%3Cchristian.hinrichs@uni-oldenburg.de%3E"
	 *         >Christian Hinrichs, christian.hinrichs@uni-oldenburg.de</a>
	 * 
	 */
	private class Signal {
		protected String name;
		protected Object sender;
		protected Object[] params;
		public Signal(String name, Object sender, Object[] params) {
			super();
			this.name = name;
			this.sender = sender;
			this.params = params;
		}
	}
	
	public FSM(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public long getClock() {
		return clock;
	}
	
//	public Transition getDefaultTransition() {
//		return defaultTransition;
//	}
//
//	public void setDefaultTransition(Transition defaultTransition) {
//		this.defaultTransition = defaultTransition;
//	}
//
//	public void addState(State s) {
//		if (!states.contains(s)) {
//			states.add(s);
//		}
//	}
//	
//	public void removeState(State s) {
//		states.remove(s);
//	}
//
//	public State getCurrentState() {
//		for (State s : states) {
//			if (s.isActive()) {
//				return s;
//			}
//		}
//		return null;
//	}

	/**
	 * Registers the given signal listener.
	 * 
	 * @param sl
	 */
	public void addSignalListener(SignalListener sl) {
		if (!signalListeners.contains(sl)) {
			signalListeners.add(sl);
		}
	}
	
	/**
	 * Unregisters the given signal listener.
	 * 
	 * @param sl
	 */
	public void removeSignalListener(SignalListener sl) {
		signalListeners.remove(sl);
	}

	/**
	 * This method should be used to 'send' a signal to this FSM. The signal is
	 * added to the list of waiting signals and will be dispatched by the active
	 * state after the next clock.
	 * 
	 * @param name
	 * @param sender
	 * @param params
	 */
	public void signal(String name, Object sender, Object... params) {
		waitingSignals.add(new Signal(name, sender, params));
	}

	/**
	 * Dispatches waiting signals. This method will be called by the currently
	 * active state upon clock changes. May also be called intermediate if
	 * desired.
	 * 
	 * @param current
	 * @return whether at least one signal has been consumed by a registered
	 *         listener
	 */
	public boolean dispatchSignals(State current) {
		boolean consumed = false;
		Iterator<Signal> it = waitingSignals.iterator();
		while (it.hasNext()) {
			Signal s = it.next();
			it.remove();
//			if (name.equals("TLR_extension_stateful"))
//				System.out.println("Time=" + getClock() + " - Signal <"
//						+ s.name + "> from "
//						+ s.sender.getClass().getSimpleName() + ", params="
//						+ Arrays.toString(s.params) + ", currentState="
//						+ current.getClass().getSimpleName());
			for (SignalListener sl : signalListeners) {
				consumed = sl.signalReceived(s.name, s.sender, s.params)
						|| consumed;
			}
		}
		waitingSignals.clear();
		return consumed;
	}
	
	/**
	 * Registers the given clock listener.
	 * 
	 * @param cl
	 */
	public void addClockListener(ClockListener cl) {
		if (!clockListeners.contains(cl)) {
			clockListeners.add(cl);
		}
	}
	
	/**
	 * Unregisters the given clock listener.
	 * 
	 * @param cl
	 */
	public void removeClockListener(ClockListener cl) {
		clockListeners.remove(cl);
	}

	/**
	 * Increases the internal clock counter. This should be called by an
	 * external reference clock.
	 */
	public void clock() {
		if (clock == 0L) {
			// Upon system startup, add a null signal to activate the first
			// state.
			signal(null, new NullState(this));
		}
		// Increase clock counter
		clock++;
		// Notify listeners. The actions, state changes etc. will be
		// performed by them.
		for (ClockListener cl : clockListeners) {
			cl.clock(clock);
		}
	}
}