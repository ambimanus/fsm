package de.uniol.ui.fsm.model.states;

import java.util.ArrayList;

import de.uniol.ui.fsm.model.ActivationListener;
import de.uniol.ui.fsm.model.FSM;

public abstract class State {

	private String name;
	private FSM fsm;
	private State parent;
	private boolean active = false;
	private ArrayList<ActivationListener> activationListeners = new ArrayList<ActivationListener>();
	
	public State(String name, FSM fsm, State parent) {
		this.name = name;
		this.fsm = fsm;
		this.parent = parent;
	}
	
	public String getName() {
		return name;
	}

	public FSM getFsm() {
		return fsm;
	}
	
	public State getParent() {
		return parent;
	}

	public boolean isActive() {
		return active;
	}
	
	public void addActivationListener(ActivationListener al) {
		if (!activationListeners.contains(al)) {
			activationListeners.add(al);
		}
	}
	
	public void removeActivationListener(ActivationListener al) {
		activationListeners.remove(al);
	}
	
	protected void notifyActivationListeners() {
		for (ActivationListener al : activationListeners) {
			al.activationChanged(active, this);
		}
	}

	public void entry(State dest) {
		if (dest.isChildOf(this)) {
			if (parent != null && !parent.isActive()) {
				parent.entry(dest);
			}
			active = true;
			entryAction();
			notifyActivationListeners();
			if (dest == this) {
				// Dispatch waiting signals
				boolean consumed = fsm.dispatchSignals(this);
				if (!consumed) {
					// No signal was consumed, activate default transition(s)
					fsm.signal(null, this);
					fsm.dispatchSignals(this);
				}
			}
		}
	}
	
	public void exit(State dest) {
		if (!dest.isChildOf(this)) {
			exitAction();
			active = false;
			notifyActivationListeners();
			if (parent != null && !dest.isChildOf(parent)) {
				parent.exit(dest);
			}
		}
	}
	
	public boolean isChildOf(State s) {
		if (s == this) {
			return true;
		}
		if (parent != null) {
			return parent == s || parent.isChildOf(s);
		}
		return false;
	}

	protected abstract void entryAction();
	protected abstract void exitAction();
}