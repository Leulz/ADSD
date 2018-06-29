package adsd;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_port;
import eduni.simjava.distributions.*;

public class Source extends Sim_entity {
	
	private Sim_port out;
    private Sim_negexp_obj delay;
	
	public Source(String name, double mean) {
	  super(name);
      out = new Sim_port("Out");
      add_port(out);
      delay = new Sim_negexp_obj("Delay", mean);
      add_generator(delay);
	}
	
	public void body() {
		for (int i=0; i < 1000; i++) {
			sim_schedule(out, 0.0, 0);
	        sim_trace(1, "New request for the system.");
			sim_pause(delay.sample());
		}
	}
}
