package de.uniol.ui.fsm.projects.fridge;

public class Configuration {
	
	/** instance-counter */
	public static int instance = -1;
	/** distinction-counter */
	public static int distinct = -1;
	
	public Configuration() {
		instance++;
		title = "New Configuration"
				+ (Configuration.instance == 0 ? ""
						: (" " + Configuration.instance));
	}

	/* Constants */
	/** Available model types */
	public static enum VARIATE {
		NONE, UNIFORM, NORMAL
	}
	/** Available model types */
	public static enum MODEL {
		ITERATIVE, LINEAR, COMPACT_LINEAR
	}
	/** Available strategies */
	public static enum SIGNAL {
		NONE, DSC, TLR
	}
	/** Available damping strategies */
	public static enum DAMPING {
		NONE, RANDOM, STATEFUL_HALF, STATEFUL_FULL
	}
	
	/* Global params */
	/** Title of this configuration */
	public String title = "New Configuration";

	/* Population params */
	/** The propability a fridge is set active at simulation start */
	public double ACTIVE_AT_START_PROPABILITY = 0.22;
	/** Amount of simulated fridges */
	public int POPULATION_SIZE = 5000;
	
	/** Variation of T_current */
	public VARIATE variate_Tcurrent = VARIATE.UNIFORM;
	/** Seed for Tcurrent variation generator */
	public long variate_Tcurrent_seed = Math.round(Math.random() * 100000000.0);
	/** Tcurrent default */
	public double variate_Tcurrent_default = 5.5;
	/** Tcurrent minimum */
	public double variate_Tcurrent_min = 3.0;
	/** Tcurrent maximum */
	public double variate_Tcurrent_max = 8.0;
	/** standard deviation for Tcurrent normal distribution */
	public double variate_Tcurrent_sdev = 0.75;
	
	/** Variation of m_c */
	public VARIATE variate_mc = VARIATE.NORMAL;
	/** Seed for mc variation generator */
	public long variate_mc_seed = Math.round(Math.random() * 100000000.0);
	/** mc default */
	public double variate_mc_default = 19.95;
	/** Thermal mass minimum */
	public double variate_mc_min = 7.9;
	/** Thermal mass maximum */
	public double variate_mc_max = 32.0;
	/** standard deviation for mc normal distribution */
	public double variate_mc_sdev = 3.5;
	
	/** Variation of A */
	public VARIATE variate_A = VARIATE.NORMAL;
	/** Seed for A variation generator */
	public long variate_A_seed = Math.round(Math.random() * 100000000.0);
	/** A default */
	public double variate_A_default = 3.21;
	/** A minimum */
	public double variate_A_min = 2.889;
	/** A maximum */
	public double variate_A_max = 3.531;
	/** standard deviation for A normal distribution */
	public double variate_A_sdev = 0.09;
	
	/** Variation of TO */
	public VARIATE variate_TO = VARIATE.NORMAL;
	/** Seed for TO variation generator */
	public long variate_TO_seed = Math.round(Math.random() * 100000000.0);
	/** TO default */
	public double variate_TO_default = 20.0;
	/** TO minimum */
	public double variate_TO_min = 15.0;
	/** TO maximum */
	public double variate_TO_max = 25.0;
	/** standard deviation for TO normal distribution */
	public double variate_TO_sdev = 1.5;
	
	/** Variation of eta */
	public VARIATE variate_eta = VARIATE.NORMAL;
	/** Seed for eta variation generator */
	public long variate_eta_seed = Math.round(Math.random() * 100000000.0);
	/** eta default */
	public double variate_eta_default = 3.0;
	/** eta minimum */
	public double variate_eta_min = 2.0;
	/** eta maximum */
	public double variate_eta_max = 4.0;
	/** standard deviation for eta normal distribution */
	public double variate_eta_sdev = 0.25;
}