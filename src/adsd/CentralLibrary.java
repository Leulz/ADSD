package adsd;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;
import eduni.simjava.distributions.Sim_normal_obj;
import eduni.simjava.distributions.Sim_random_obj;

public class CentralLibrary extends Sim_entity{
	
	private Sim_port in;
	private Sim_port physicalIndexOut;
	private Sim_port virtualIndexOut;
	
	private Sim_normal_obj delay;
	private Sim_random_obj prob;
	
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

	}
	
	public void body() {
		while (Sim_system.running()) {
			Sim_event e = new Sim_event();
			sim_get_next(e);
			sim_process(delay.sample());
			sim_completed(e);
			
			double p = prob.sample();
			if (p < 0.4) {
				sim_trace(1, "Book found in Central Library, request finished!.");
			} else if (p >= 0.4 && p < 0.7) {
				sim_schedule(physicalIndexOut, 0.0, 0);
				sim_trace(1, "Sending request to search for the book in the physical index.");
			} else {
				sim_schedule(virtualIndexOut, 0.0, 0);
				sim_trace(1, "Sending request to search for the book in the virtual index.");
			}
			
		}
    }
	
}