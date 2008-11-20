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
package de.uniol.ui.fsm.projects.fridge.tlr_extension_stateful;

import de.uniol.ui.fsm.model.states.State;
import de.uniol.ui.fsm.model.transitions.Transition;

public class T_idle_TO_wait_active extends Transition {

	private TLR_extension_stateful ext;

	public T_idle_TO_wait_active(TLR_extension_stateful fsm, State_idle source,
			State_wait_active dest) {
		super(fsm, TLR_extension_stateful.EV_REDUCE, source, dest);
		this.ext = fsm;
	}

	public void action(Object... params) {
		ext.setTau_preload((Double) params[0]);
		ext.setTau_reduce((Double) params[1]);
	}

	public boolean guard() {
		return true;
	}

	public boolean signalReceived(String name, Object sender, Object... params) {
//		System.out.println("Time=" + ext.getClock() + " - "
//				+ this.getClass().getSimpleName() + " received Signal <"
//				+ name + "> from "
//				+ sender.getClass().getSimpleName() + ", params="
//				+ Arrays.toString(params));
		if (getSignal().equals(name) && guard()) {
			if (sender != getSource() && sender instanceof State) {
				((State) sender).exit(getDest());
			}
			getSource().exit(getDest());
			action(params);
			getDest().entry(getDest());
			return true;
		}
		return false;
	}
}