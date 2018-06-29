package adsd;

import eduni.simjava.*;
import eduni.simjava.distributions.Sim_normal_obj;
import eduni.simjava.distributions.Sim_random_obj;

public class CentralLibrary extends Sim_entity{
	
	private Sim_port in;
	private Sim_port physicalIndexOut;
	private Sim_port virtualIndexOut;
	
	private Sim_normal_obj delay;
	private Sim_random_obj prob;

	Sim_stat stat;

	
	public CentralLibrary(String name, double delayMean, double delayVariance) {
		super(name);
		in = new Sim_port("In");
		physicalIndexOut = new Sim_port("PhysicalIndexOut");
		virtualIndexOut = new Sim_port("VirtualIndexOut");
		add_port(in);
		add_port(physicalIndexOut);
		add_port(virtualIndexOut);
		
		delay = new Sim_normal_obj("Delay", delayMean, delayVariance);
		prob = new Sim_random_obj("Probability");
		
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
			if (p < 0.4) {
				sim_trace(1, "Book found in Central Library, request finished!.");
				stat.update("Thread use", 1, before, Sim_system.sim_clock());
			} else if (p >= 0.4 && p < 0.7) {
				sim_schedule(physicalIndexOut, 0.0, 0);
				sim_trace(1, "Sending request to search for the book in the physical index.");
				stat.update("Thread use", 2, before, Sim_system.sim_clock());
			} else {
				sim_schedule(virtualIndexOut, 0.0, 0);
				sim_trace(1, "Sending request to search for the book in the virtual index.");
				stat.update("Thread use", 3, before, Sim_system.sim_clock());
			}
			
		}
    }
	
}