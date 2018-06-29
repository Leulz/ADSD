package adsd;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;
import eduni.simjava.distributions.Sim_normal_obj;
import eduni.simjava.distributions.Sim_random_obj;

public class PhysicalBooksIndex extends Sim_entity {

	private Sim_port in, humanSciencesLibrary, exactSciencesLibrary, biologicalSciencesLibrary;
	private Sim_normal_obj delay;
	private Sim_random_obj prob;

	public PhysicalBooksIndex(String name, double delayMean, double delayVariance) {
		super(name);

		in = new Sim_port("In");
		humanSciencesLibrary = new Sim_port("HumanitiesOut");
		exactSciencesLibrary = new Sim_port("ExactOut");
		biologicalSciencesLibrary = new Sim_port("BiologicalOut");
		
		delay = new Sim_normal_obj("Delay", delayMean, delayVariance);
		prob = new Sim_random_obj("Probability");
		
		add_port(in);
		add_port(humanSciencesLibrary);
		add_port(exactSciencesLibrary);
		add_port(biologicalSciencesLibrary);
		
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
			if (p < 0.5) {
				sim_schedule(humanSciencesLibrary, 0.0, 0);
				sim_trace(1, "Requested book is in the Human Sciences Library.");
			} else if (p >= 0.5 && p < 0.8) {
				sim_schedule(exactSciencesLibrary, 0.0, 0);
				sim_trace(1, "Requested book is in the Exact Sciences Library.");
			} else {
				sim_schedule(biologicalSciencesLibrary, 0.0, 0);
				sim_trace(1, "Requested book is in the Biological Sciences Library.");
			}
			
		}
	}
}
