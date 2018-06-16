package adsd;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_port;
import eduni.simjava.distributions.Sim_normal_obj;
import eduni.simjava.distributions.Sim_random_obj;

public class Programmer extends Sim_entity{
	
	private Sim_port in;
	private Sim_port out;
	
	private Sim_normal_obj commitDelay;
	private Sim_random_obj prob;
	
	private static final int MAX_COMMITS = 1000;
	
	public Programmer(String name, double commitPeriodMean, double commitPeriodVariance) {
		super(name);
		in = new Sim_port("In");
		out = new Sim_port("Out");
		add_port(in);
		add_port(out);
		
		commitDelay = new Sim_normal_obj("Delay", commitPeriodMean, commitPeriodVariance);
		prob = new Sim_random_obj("Probability");
		add_generator(commitDelay);
		add_generator(prob);

	}
	
	public void body() {
		for (int i=0; i < prob.sample() * MAX_COMMITS; i++) {
			sim_schedule(out, 0.0, 0);
			sim_trace(1, "New request from processor.");
			sim_pause(commitDelay.sample());
		}
    }
	
}
