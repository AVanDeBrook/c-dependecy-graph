package depgraph;

import java.util.List;

import depgraph.Reader.Reader;
import depgraph.Configurator.ConfigReturnType;
import depgraph.Configurator.Configurator;
import depgraph.Parser.*;

public class Manager {

	private static Configurator configurator;
	private static Reader reader;
	private static Parser parser;
	private static Manipulator manipulator;
	private static Graph graph;

	public static void main(String[] args) {
		configurator = new Configurator();
		reader = new Reader();
		parser = new Parser();
		manipulator = new Manipulator();

		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Manager() {

	}

	/**
	 * Method used by Configurator to provide the file location and kick off the
	 * process
	 */
	public static void start(String[] args) throws Exception {
        List<String> files = null;
        ConfigReturnType fileType = configurator.manageCmdLineArguments(args);

        if (fileType == ConfigReturnType.DIRECTORY) {
            files = reader.readDirectory(configurator.getDirectoryName());
        } else if (fileType == ConfigReturnType.FILE) {
            files = reader.readSingleFile(configurator.getFileName());
        }

        if (files == null) {
			return;
		}

        graph = new Graph();

		for (String singleFile : files) {
			Module module = parser.parse(singleFile);
			graph.addModule(module);
		}

		graph = manipulator.manipulate(graph);

		// TODO continue flow
	}
}