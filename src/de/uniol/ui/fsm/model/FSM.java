package de.uniol.ui.fsm.model;

import java.util.ArrayList;
import java.util.Iterator;

import de.uniol.ui.fsm.model.states.NullState;
import de.uniol.ui.fsm.model.states.State;

public class FSM {

	private String name;
	private long clock = 0L;
//	private Transition defaultTransition = null;
//	private ArrayList<State> states = new ArrayList<State>();
	private ArrayList<Signal> waitingSignals = new ArrayList<Signal>();
	private ArrayList<SignalListener> signalListeners = new ArrayList<SignalListener>();
	private ArrayList<ClockListener> clockListeners = new ArrayList<ClockListener>();
	
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

	public void addSignalListener(SignalListener sl) {
		if (!signalListeners.contains(sl)) {
			signalListeners.add(sl);
		}
	}
	
	public void removeSignalListener(SignalListener sl) {
		signalListeners.remove(sl);
	}
	
	public void signal(String name, Object sender, Object... params) {
		waitingSignals.add(new Signal(name, sender, params));
	}
	
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
	
	public void addClockListener(ClockListener cl) {
		if (!clockListeners.contains(cl)) {
			clockListeners.add(cl);
		}
	}
	
	public void removeClockListener(ClockListener cl) {
		clockListeners.remove(cl);
	}
	
	public void clock() {
		if (clock == 0L) {
			signal(null, new NullState(this));
		}
		clock++;
		for (ClockListener cl : clockListeners) {
			cl.clock(clock);
		}
	}
}