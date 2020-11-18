package depgraph.Configurator;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.ArrayList;

// @formatter:off
/**
 * Responsible for parsing and handling command-line arguments passed to the
 * program.
 *
 * List of possible flags:
 * - s DOT file to process
 * - d directory of DOT files to process.
 * - h print help menu
 * - v verbosity of logger
 * - L specify output file used by the logger
 *
 * Run in gradle using (replace ... with desired arguments): gradle run --args="..."
 *
 * Otherwise pass arguments normally.
 */
// @formatter:on
public class Configurator {

	private static Logger logger;

	private static Handler[] handlers;

	private static FileHandler fileHandler;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tc]\nSource: %2$s\n%4$s:\t%5$s\n%6$s\n\n");
	}

	/**
	 * Name of file, if the passed argument is a single file.
	 */
	private String nameOfFile;

	/**
	 * Name of a directory, if the passed argument is a directory.
	 */
	private String nameOfDirectory;

	private boolean filtered;

	private ArrayList<String> sourceFilterList;

	private ArrayList<String> destinationFilterList;

	/**
	 * No-arg constructor. Initializes class attributes to null-strings.
	 */
	public Configurator() {
		nameOfDirectory = "";
		nameOfFile = "";
		logger = Logger.getLogger("depgraph");
		handlers = logger.getHandlers();
		filtered = false;
		sourceFilterList = new ArrayList<String>();
		destinationFilterList = new ArrayList<String>();
	}

	/**
	 * Parses command-line arguments and processes/responds to them.
	 *
	 * @param args Command-line arguments passed from main.
	 * @return Type of handle for the reader to process (FILE, DIRECTORY, NONE)
	 */
	public ConfigType manageCmdLineArguments(String[] args) {
		logger.fine("Processing command line arguments...");
		ConfigType typeToReturn = ConfigType.NONE;
		boolean printHelp = false;
		for (int i = 0; i < args.length; i++) {
			if ((args[i].charAt(0) == '-') && (args[i].length() == 2)) {
				switch (args[i].charAt(1)) {
				case 's':
					try {
						logger.fine("Single file selected...");
						if (this.processSingleFile(args[++i])) {
							typeToReturn = ConfigType.FILE;
						}
					} catch (ArrayIndexOutOfBoundsException ex) {
						System.out.println("Incorrect format for option -s");
						printHelp = false;
					}
					break;
				case 'd':
					try {
						logger.fine("Directory selected...");
						if (this.processDirectory(args[++i])) {
							typeToReturn = ConfigType.DIRECTORY;
						}
					} catch (ArrayIndexOutOfBoundsException ex) {
						System.out.println("Incorect format for option -d");
						printHelp = false;
					}
					break;
				case 'v':
					processVerbosity(Integer.parseInt(args[++i]));
					break;
				case 'h':
					printHelp = true;
					printHelp();
					break;
				case 'L':
					try {
						// log file prints next to manager in the project tree
						// overwrites file if it exists
						fileHandler = new FileHandler("./src/main/java/depgraph/" + args[++i] + ".log");
					} catch (ArrayIndexOutOfBoundsException ex) {
						System.out.println("Incorect format for option -L");
						printHelp = false;
					} catch (Exception e) {
						logger.severe("File Handler could not be created" + e);
					}
					fileHandler.setLevel(handlers[0].getLevel());
					fileHandler.setFormatter(new SimpleFormatter());
					logger.addHandler(fileHandler);
					break;
				case 'F':
					String filterArg = "";
					filtered = true;
					for (int j = i + 1; j < args.length; j++, i++) {
						if (!(args[j].charAt(0) == '-') || args[j].equals("->"))
							filterArg += args[j];
					}
					getModuleFilters(filterArg.replaceAll(" ", ""));
					break;
				default:
					System.out.println(String.format("Unkown option: %s", args[i]));
					break;
				}
			} else {
				printHelp = true;
			}
		}
		if (printHelp) {
			printHelp();
		}
		return typeToReturn;
	}

	private void processVerbosity(int levelofVerbosity) {
		switch (levelofVerbosity) {
		case 0:
			for (Handler handy : logger.getHandlers()) {
				handy.setLevel(Level.SEVERE);
			}
			System.out.println("Verbosity set to SEVERE");
			break;
		case 1:
			for (Handler handy : logger.getHandlers()) {
				handy.setLevel(Level.WARNING);
			}
			System.out.println("Verbosity set to WARNING");
			break;
		case 2:
			for (Handler handy : logger.getHandlers()) {
				handy.setLevel(Level.INFO);
			}
			System.out.println("Verbosity set to INFO");
			break;
		case 3:
			for (Handler handy : logger.getHandlers()) {
				handy.setLevel(Level.FINE);
			}
			System.out.println("Verbosity set to FINE");
			break;
		default:
			for (Handler handy : logger.getHandlers()) {
				handy.setLevel(Level.INFO);
			}
			System.out.println("Verbosity defaulted to INFO");
			break;
		}

	}

	/**
	 * Prints help menu to stdout.
	 */
	private void printHelp() {
		System.out.println("\nWelcome to the C Dependency Graph Tool!\n");
		System.out.println("FLAG\tDESCRIPTION\t\tUSAGE");
		System.out.println("-h\tPrint the help menu\t-h");
		System.out.println("-s\tProcess a single file\t-s <file path with extension .dot>");
		System.out.println("-d\tProcess a directory\t-d <directory path>");
		System.out.println("-v\tSet logging verbosity\t-v <0-3>");
		System.out.println("-L\tSet logger output file\t-L <file path>");
		System.out.println("-o\tName program output\t-o <name>");
		System.out.println();
	}

	/**
	 * Checks if the passed file exists and sets the class attribute if it does.
	 *
	 * @param fileName name of the file.
	 * @return True if param was an existing file and class attribute was set, false
	 *         otherwise.
	 */
	private boolean processSingleFile(String fileName) {
		File singleFile = new File(fileName);
		boolean success = false;
		logger.fine("Validating file input...");
		if (singleFile.isFile()) {
			logger.fine("File is valid");
			this.nameOfFile = fileName;
			success = true;
		} else {
			System.out.println("File name provided cannot resolve to a file");
		}

		return success;
	}

	/**
	 * Checks if the passed directory exists and sets the class attribute if it
	 * does.
	 *
	 * @param directoryName name of the directory.
	 * @return True if param was an existing directory and class attribute was set,
	 *         false otherwise.
	 */
	private boolean processDirectory(String directoryName) {
		File directory = new File(directoryName);
		boolean success = false;
		logger.fine("Validating directory input...");
		if (directory.isDirectory()) {
			logger.fine("Directory is valid");
			this.nameOfDirectory = directoryName;
			success = true;
		} else {
			System.out.println("Directory name provided cannot resolve to a directory");
		}

		return success;
	}

	private void getModuleFilters(String arg) {
		String[] filters = arg.split("->");

		if (filters[0].startsWith("{")) {
			for (String key : filters[0].split(",")) {
				if (key.contains("{"))
					sourceFilterList.add(key.replaceAll("\\{", ""));
				else if (key.contains("}"))
					sourceFilterList.add(key.replaceAll("\\}", ""));
				else
					sourceFilterList.add(key);
			}
		} else {
			sourceFilterList.add(filters[0]);
		}

		if (filters[1].startsWith("{")) {
			for (String key : filters[1].split(",")) {
				if (key.contains("{"))
					destinationFilterList.add(key.replaceAll("\\{", ""));
				else if (key.contains("}"))
					destinationFilterList.add(key.replaceAll("\\}", ""));
				else
					destinationFilterList.add(key);
			}
		} else {
			destinationFilterList.add(filters[1]);
		}
	}

	/* Setters and Getters */

	public String getFileName() {
		return nameOfFile;
	}

	public String getDirectoryName() {
		return nameOfDirectory;
	}

	public boolean isFiltered() {
		return filtered;
	}

	public ArrayList<String> getSourceFilterList() {
		return sourceFilterList;
	}

	public ArrayList<String> getDestinationFilterList() {
		return destinationFilterList;
	}
}
