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
package de.uniol.ui.fsm.projects.fridge;

import de.uniol.ui.fsm.model.transitions.DefaultTransition;

public class T_default_TO_warming extends DefaultTransition {

	private BaseController bc;
	
	public T_default_TO_warming(BaseController fsm, State_warming dest) {
		super(fsm, dest);
		this.bc = fsm;
	}

	public void action(Object... params) {
		super.action(params);
		bc.setTDest(bc.getTmax());
	}
}