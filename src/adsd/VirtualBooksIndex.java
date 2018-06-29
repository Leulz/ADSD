package adsd;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;
import eduni.simjava.distributions.Sim_normal_obj;
import eduni.simjava.distributions.Sim_random_obj;

public class VirtualBooksIndex extends Sim_entity {
	
	private Sim_port in, humanitiesLibrary, computerScienceLibrary, miscellaneousLibrary;
	private Sim_normal_obj delay;
	private Sim_random_obj prob;

	public VirtualBooksIndex(String name, double delayMean, double delayVariance) {
		super(name);

		in = new Sim_port("In");
		humanitiesLibrary = new Sim_port("HumanitiesOut");
		computerScienceLibrary = new Sim_port("ComputerOut");
		miscellaneousLibrary = new Sim_port("MiscOut");
		
		delay = new Sim_normal_obj("Delay", delayMean, delayVariance);
		prob = new Sim_random_obj("Probability");
		
		add_port(in);
		add_port(humanitiesLibrary);
		add_port(computerScienceLibrary);
		add_port(miscellaneousLibrary);
		
		add_generator(delay);
		add_generator(prob);
	}

	public void body() {
		while (Sim_system.running()) {
			Sim_event e = new Sim_event();
			sim_get_next(e);
			sim_process(delay.sample());
			sim_completed(e);
			
			double p = prob.sample();
			if (p < 0.1) {
				sim_schedule(humanitiesLibrary, 0.0, 0);
				sim_trace(1, "Requested book is in the Virtual Humanities Library.");
			} else if (p >= 0.1 && p < 0.7) {
				sim_schedule(computerScienceLibrary, 0.0, 0);
				sim_trace(1, "Requested book is in the Virtual Computer Science Library.");
			} else {
				sim_schedule(miscellaneousLibrary, 0.0, 0);
				sim_trace(1, "Requested book is in the Virtual Miscellaneous Library.");
			}
		}
	}
}
