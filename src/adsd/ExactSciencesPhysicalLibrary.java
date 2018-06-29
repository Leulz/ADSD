package adsd;

import eduni.simjava.*;
import eduni.simjava.distributions.Sim_normal_obj;
import eduni.simjava.distributions.Sim_random_obj;

public class ExactSciencesPhysicalLibrary extends Sim_entity {
	
	private Sim_port in, out;
	private Sim_normal_obj delay;
	private Sim_random_obj prob;

	Sim_stat stat;

	public ExactSciencesPhysicalLibrary(String name, double delayMean, double delayVariance) {
		super(name);

		in = new Sim_port("In");
		out = new Sim_port("Out");
		
		delay = new Sim_normal_obj("Delay", delayMean, delayVariance);
		prob = new Sim_random_obj("Probability");
		
		add_port(in);
		add_port(out);
		
		add_generator(delay);
		add_generator(prob);

		stat = new Sim_stat();
		stat.add_measure(Sim_stat.UTILISATION);
		set_stat(stat);
	}

	public void body() {
		while (Sim_system.running()) {
			Sim_event e = new Sim_event();
			sim_get_next(e);
			sim_process(delay.sample());
			sim_completed(e);
			sim_trace(1, "Book reserved in Exact Sciences Physical Library.");
		}
	}
}
