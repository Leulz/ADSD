package adsd;

import eduni.simjava.Sim_system;

public class LibrarySubsystem {
	
	public static void main(String[] args) {
		Sim_system.initialise();
		
		Source source = new Source("Source", 50);
		CentralLibrary centralLib = new CentralLibrary("CentralLibrary", 100, 65);
		PhysicalBooksIndex physIndex = new PhysicalBooksIndex("PhysicalIndex", 165, 93);
		VirtualBooksIndex virtualIndex = new VirtualBooksIndex("VirtualIndex", 213, 51);
		
		HumanSciencesPhysicalLibrary humanPhysLib = new HumanSciencesPhysicalLibrary("HumanPhysLib", 85, 33);
		ExactSciencesPhysicalLibrary exactPhysLib = new ExactSciencesPhysicalLibrary("ExactPhysLib", 31, 20);
		BiologicalSciencesPhysicalLibrary bioPhysLib = new BiologicalSciencesPhysicalLibrary("BioPhysLib", 77, 12);
		
		HumanitiesVirtualLibrary humanVirtualLib = new HumanitiesVirtualLibrary("HumanitiesVirtualLib", 30, 12);
		ComputerScienceVirtualLibrary computerLib = new ComputerScienceVirtualLibrary("ComputerVirtualLib", 130, 23);
		MiscellaneousVirtualLibrary miscLib = new MiscellaneousVirtualLibrary("MiscVirtualLib", 65, 5);
		
		Sim_system.link_ports("Source", "Out", "CentralLibrary", "In");
		
		Sim_system.link_ports("CentralLibrary", "PhysicalIndexOut", "PhysicalIndex", "In");
		Sim_system.link_ports("CentralLibrary", "VirtualIndexOut", "VirtualIndex", "In");
		
		Sim_system.link_ports("PhysicalIndex", "HumanitiesOut", "HumanPhysLib", "In");
		Sim_system.link_ports("PhysicalIndex", "ExactOut", "ExactPhysLib", "In");
		Sim_system.link_ports("PhysicalIndex", "BiologicalOut", "BioPhysLib", "In");
		
		Sim_system.link_ports("VirtualIndex", "HumanitiesOut", "HumanitiesVirtualLib", "In");
		Sim_system.link_ports("VirtualIndex", "ComputerOut", "ComputerVirtualLib", "In");
		Sim_system.link_ports("VirtualIndex", "MiscOut", "MiscVirtualLib", "In");
		
		Sim_system.run();
	}
}
