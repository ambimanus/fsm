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

import de.uniol.ui.fsm.model.FSM;
import de.uniol.ui.fsm.model.SignalListener;
import de.uniol.ui.fsm.model.states.State;

/**
 * This class represents a transition. It contains a reference to the
 * surrounding FSM, to the source and to the destiny state which are connected
 * by this transition. It may contain a signal string to make this transition
 * event based.
 * 
 * @author <a href=
 *         "mailto:Christian%20Hinrichs%20%3Cchristian.hinrichs@uni-oldenburg.de%3E"
 *         >Christian Hinrichs, christian.hinrichs@uni-oldenburg.de</a>
 * 
 */
public abstract class Transition implements SignalListener {

	private FSM fsm;
	private State source;
	private State dest;
	private String signal = null;
	
	public Transition(FSM fsm, String signal, State source, State dest) {
		this.dest = dest;
		this.fsm = fsm;
		this.signal = signal;
		this.source = source;
		
		fsm.addSignalListener(this);
	}

	public FSM getFsm() {
		return fsm;
	}

	public State getSource() {
		return source;
	}

	public State getDest() {
		return dest;
	}

	public String getSignal() {
		return signal;
	}

	/**
	 * This method is called whenever a signal is dispatched by the surrounding
	 * FSM. It checks if the signal may be consumed and performs the appropriate
	 * actions if true. Returns true to indicate that the signal has been
	 * consumed by this transition, false otherwise.
	 * 
	 * @param name
	 * @param sender
	 * @param params
	 */
	public boolean signalReceived(String name, Object sender, Object... params) {
		boolean process = false;
		if (name == null) {
			// If we are a 'null transition' without event, the signal must also
			// be null and the sender of the signal must be the active source
			// state.
			process = signal == null && source.isActive()
					&& sender.equals(source);
		} else {
			// If we are an event-based transition, the signal must not be null
			// and must equal our signal string.
			process = signal != null && signal.equals(name);
		}
		// Check if this transition will become active
		if (process && guard()) {
//			if (fsm.getName().equals("Extension_TLR"))
//			System.out.println("Time=" + fsm.getClock() + " - "
//					+ this.getClass().getSimpleName() + " received Signal <"
//					+ signal + "> from " + sender.getClass().getSimpleName()
//					+ ", params=" + Arrays.toString(params));
			// If the sender state is not our source state, exit the sender
			// state
			if (sender != source && sender instanceof State) {
				((State) sender).exit(dest);
			}
			// Exit the source state
			source.exit(dest);
			// Perform transition actions
			action(params);
			// Enter the destiny state
			dest.entry(dest);
			// Return true to show that the signal has been consumed
			return true;
		}
		return false;
	}

	/**
	 * This abstract method represents the guard of this transition. Subclasses
	 * must fill this method with the boolean expressions which should be
	 * evaluated before making this transition active.
	 * 
	 * @return
	 */
	public abstract boolean guard();

	/**
	 * This method should be filled by subclasses with the actions of this
	 * transition. The parameters are those which were sent by the signal which
	 * initiated this transition, but may be null.
	 * 
	 * @param params
	 */
	public abstract void action(Object... params);
}