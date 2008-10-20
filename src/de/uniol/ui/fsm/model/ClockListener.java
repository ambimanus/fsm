package de.uniol.ui.fsm.model;

/**
 * This interface defines the ability to be notified when the system clock
 * changes.
 * 
 * @author <a href=
 *         "mailto:Christian%20Hinrichs%20%3Cchristian.hinrichs@uni-oldenburg.de%3E"
 *         >Christian Hinrichs, christian.hinrichs@uni-oldenburg.de</a>
 * 
 */
public interface ClockListener {

	public void clock(long clock);
}