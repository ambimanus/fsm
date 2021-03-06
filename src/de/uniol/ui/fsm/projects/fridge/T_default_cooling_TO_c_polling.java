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
 * FSM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with FSM.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.uniol.ui.fsm.projects.fridge;

import de.uniol.ui.fsm.model.FSM;
import de.uniol.ui.fsm.model.transitions.Transition;

public class T_default_cooling_TO_c_polling extends Transition {

	public T_default_cooling_TO_c_polling(FSM fsm, State_cooling source,
			State_c_polling dest) {
		super(fsm, null, source, dest);
	}

	public void action(Object... params) {
		// no-op
	}

	public boolean guard() {
		return true;
	}
}