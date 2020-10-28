package depgraph;

import java.util.List;

import depgraph.Configurator.*;
import depgraph.Parser.Edge;
import depgraph.Parser.Node;
import depgraph.Parser.Parser;
import depgraph.Reader.Reader;

public class Manager {

	private static Configurator configurator;
	private static Reader reader;
    private static Parser parser;
    // Currently unused; commenting to supress warnings
	// private static Manipulator manipulator;

	public static void main(String[] args) {
		configurator = new Configurator();
		reader = new Reader();
        parser = new Parser();
        // Currently unused; commenting to supress warnings
		// manipulator = new Manipulator();

		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void start(String[] args) throws Exception {
		/*
		 * For development only: To run Manager using Eclipse, uncomment the
		 * following two lines and comment out the third
		 */
        // String[] testArgs = { "-s", "test\\dot-files\\adc_8c_ae0b9ae6e4ef2dbf771dcc0ea30901ae2_cgraph.dot" };
        // ConfigType fileType = configurator.manageCmdLineArguments(testArgs);
        List<String> files = null;
		ConfigType fileType = configurator.manageCmdLineArguments(args);

		if (fileType == ConfigType.DIRECTORY) {
			files = reader.readDirectory(configurator.getDirectoryName());
		} else if (fileType == ConfigType.FILE) {
			files = reader.readSingleFile(configurator.getFileName());
		}

		if (files != null) {
            parser.parse(files);
        }

        // for(Node node : parser.getNodes()){
        //     System.out.println("\nNode ID: "+node.getNodeId()+"\tNode Name: "+node.getNodeLabel());
        // }
        // for(Edge edge : parser.getEdges()){
        //     System.out.println("\nSrc Node: "+edge.getSourceNode()+"\tDest Node: "+edge.getDestinationNode());
        // }

        // TODO Call manipulator
        // TODO Call graph writer
        // TODO Call Dot runner
	}
}
