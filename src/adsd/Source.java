package adsd;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_port;

public class Source extends Sim_entity {
	
	private Sim_port out;
    private double delay;
	
	public Source(String name, double delay) {
	  super(name);
      this.delay = delay;
      out = new Sim_port("Out");
      add_port(out);
	}
	
	public void body() {
		for (int i=0; i < 1000; i++) {
			sim_schedule(out, 0.0, 0);
	        sim_trace(1, "New request for the system.");
	        sim_pause(delay);
		}
	}
}
