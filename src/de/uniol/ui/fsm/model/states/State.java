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
package de.uniol.ui.fsm.model.states;

import java.util.ArrayList;

import de.uniol.ui.fsm.model.ActivationListener;
import de.uniol.ui.fsm.model.FSM;

/**
 * This abstract class represents a state. It contains a reference to the
 * surrounding FSM and to the parent state (may be null). Manages a list of
 * activation listener.
 * 
 * @author <a href=
 *         "mailto:Christian%20Hinrichs%20%3Cchristian.hinrichs@uni-oldenburg.de%3E"
 *         >Christian Hinrichs, christian.hinrichs@uni-oldenburg.de</a>
 * 
 */
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
	
	/**
	 * Registers the given activation listener.
	 * 
	 * @param al
	 */
	public void addActivationListener(ActivationListener al) {
		if (!activationListeners.contains(al)) {
			activationListeners.add(al);
		}
	}
	
	/**
	 * Unregisters the given activation listener.
	 * 
	 * @param al
	 */
	public void removeActivationListener(ActivationListener al) {
		activationListeners.remove(al);
	}

	/**
	 * Notifies the registered activation listeners. Will be called from
	 * {@link #entry(State)} and {@link #exit(State)}.
	 */
	protected void notifyActivationListeners() {
		for (ActivationListener al : activationListeners) {
			al.activationChanged(active, this);
		}
	}

	/**
	 * This method is called when this state is entered by an active transition.
	 * The parameter defines the destiny state of the transition which can be
	 * this, but can also be a child state of this.<br>
	 * Callers should check if this state is already active, and only call this
	 * if not.
	 * 
	 * @param dest
	 */
	public void entry(State dest) {
		// Only proceed if dest is this, or a child of this.
		if (dest.isChildOf(this)) {
			// Notify parent if not done yet
			if (parent != null && !parent.isActive()) {
				parent.entry(dest);
			}
			// Set active
			active = true;
			// Perform entry actions
			entryAction();
			// Notify listeners about finished activation
			notifyActivationListeners();
			// If this is the destiny state, dispatch signals
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

	/**
	 * This method is called when this state is exited by an active transition.
	 * The parameter defines the destiny state of the transition which can be
	 * this, a child state of this, or any other state. Performs exit actions
	 * and deactivates this only, if this state is active now, and if the
	 * destiny state lies outside this state.
	 * 
	 * @param dest
	 */
	public void exit(State dest) {
		// Check if we should proceed
		if (active && !dest.isChildOf(this)) {
			// Perform exit actions
			exitAction();
			// Deactivate
			active = false;
			// Notify listeners about finished deactivation
			notifyActivationListeners();
			// Exit parent if appropriate.
			if (parent != null && !dest.isChildOf(parent)) {
				parent.exit(dest);
			}
		}
	}

	/**
	 * Tests if the given state is a child of this, or is this itself.
	 * 
	 * @param s
	 * @return
	 */
	public boolean isChildOf(State s) {
		if (s == this) {
			return true;
		}
		if (parent != null) {
			return parent == s || parent.isChildOf(s);
		}
		return false;
	}

	/**
	 * This method should be filled by subclasses with the entry actions of this
	 * state.
	 */
	protected abstract void entryAction();

	/**
	 * This method should be filled by subclasses with the exit actions of this
	 * state.
	 */
	protected abstract void exitAction();
}