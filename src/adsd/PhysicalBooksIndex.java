package adsd;

import eduni.simjava.*;
import eduni.simjava.distributions.Sim_normal_obj;
import eduni.simjava.distributions.Sim_random_obj;

public class PhysicalBooksIndex extends Sim_entity {

	private Sim_port in, humanSciencesLibrary, exactSciencesLibrary, biologicalSciencesLibrary;
	private Sim_normal_obj delay;
	private Sim_random_obj prob;

	Sim_stat stat;

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

		stat = new Sim_stat();
		stat.add_measure(Sim_stat.THROUGHPUT);
		stat.add_measure(Sim_stat.RESIDENCE_TIME);
		stat.add_measure("Thread use", Sim_stat.STATE_BASED, false);
		stat.calc_proportions("Thread use", new double[] { 0, 1, 2, 3, 4});
		set_stat(stat);
	}

	public void body() {
		while (Sim_system.running()) {
			Sim_event e = new Sim_event();
			sim_get_next(e);
			double before = Sim_system.sim_clock();
			sim_process(delay.sample());
			sim_completed(e);
			
			double p = prob.sample();
			if (p < 0.5) {
				sim_schedule(humanSciencesLibrary, 0.0, 0);
				sim_trace(1, "Requested book is in the Human Sciences Library.");
				stat.update("Thread use", 1, before, Sim_system.sim_clock());
			} else if (p >= 0.5 && p < 0.8) {
				sim_schedule(exactSciencesLibrary, 0.0, 0);
				sim_trace(1, "Requested book is in the Exact Sciences Library.");
				stat.update("Thread use", 2, before, Sim_system.sim_clock());
			} else {
				sim_schedule(biologicalSciencesLibrary, 0.0, 0);
				sim_trace(1, "Requested book is in the Biological Sciences Library.");
				stat.update("Thread use", 3, before, Sim_system.sim_clock());
			}
			
		}
	}
}
