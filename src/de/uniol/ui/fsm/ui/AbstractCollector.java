package de.uniol.ui.fsm.ui;

/**
 * This abstract class defines base methods which must be implemented by all
 * statistical collectors in this software.
 * 
 * @author Chh
 */
public abstract class AbstractCollector {
	
	/** Name of this collector, for displaying purposes only */
	protected String name;
	
	/**
	 * Creates a new collector with the given name.
	 * 
	 * @param name
	 */
	public AbstractCollector(String name) {
		this.name = name;
	}
	
	/**
	 * @return the name of this collector
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Adds a new data point defined by the given time (x) and value
	 * (y) params.
	 * 
	 * @param time
	 * @param value
	 */
	public abstract void addObservation(double time, double value);
	
	/**
	 * @return the collected data as twodimensional array
	 */
	public abstract double[][] getResults();
	
	/**
	 * @return amount of collected data points
	 */
	public abstract int getSize();
	
	/**
	 * @param index
	 * @return data point at the specified index of the internal array (not
	 *         time point!)
	 */
	public abstract double[] getObservation(int index);
	
	/** Clears the contained data */
	public abstract void clear();
}