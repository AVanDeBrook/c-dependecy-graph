package depgraph.Configurator;

import java.io.File;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

// @formatter:off
/**
 * Responsible for parsing and handling command-line arguments passed to the
 * program.
 *
 * List of possible flags:
 * - s DOT file to process
 * - d directory of DOT files to process.
 * - h print help menu
 *
 * Run in gradle using (replace ... with desired arguments): gradle run --args="..."
 *
 * Otherwise pass arguments normally.
 */
// @formatter:on
public class Configurator {
	/**
	 * Name of file, if the passed argument is a single file.
	 */
	private String nameOfFile;

	/**
	 * Name of a directory, if the passed argument is a directory.
	 */
	private String nameOfDirectory;

    private static Logger logger;
    private static Handler[] handlers;
	/**
	 * No-arg constructor. Initializes class attributes to null-strings.
	 */
	public Configurator() {
		nameOfDirectory = "";
        nameOfFile = "";
        //init logger to project level logger
        logger = Logger.getLogger("depgraph");
        handlers = logger.getHandlers();
	}

	/**
	 * Parses command-line arguments and processes/responds to them.
	 *
	 * @param args Command-line arguments passed from main.
	 * @return Type of handle for the reader to process (FILE, DIRECTORY, NONE)
	 */
	public ConfigType manageCmdLineArguments(String[] args) {
		ConfigType typeToReturn = ConfigType.NONE;
        boolean printHelp = false;

        //errors are logged as severe here to make sure they are outputted to the user despite verbosity level
		for (int i = 0; i < args.length; i++) {
			if ((args[i].charAt(0) == '-') && (args[i].length() == 2)) {
				switch (args[i].charAt(1)) {
				case 's':
					try {
                        if (this.processSingleFile(args[++i])){
                            logger.finest("Single file selected ...");
                            typeToReturn = ConfigType.FILE;
                        }
					} catch (ArrayIndexOutOfBoundsException ex) {
                        logger.severe("Error (option '-s'): Incorrect format");
                		printHelp = false;
					}
					break;
				case 'd':
					try {
                        if (this.processDirectory(args[++i])){
                            logger.finest("Directory selected ...");
                            typeToReturn = ConfigType.DIRECTORY;
                        }
					} catch (ArrayIndexOutOfBoundsException ex) {
                        logger.severe("Error (option '-d'): Incorect format");
						printHelp = false;
					}
                    break;
                case 'v':
                    processVerbosity(Integer.parseInt(args[++i]));
                    break;
				case 'h':
					printHelp = true;
					break;
                default:
                    logger.severe(String.format("Error: Unkown option: %s\n", args[i]));
                    break;
				}
			} else {
				printHelp = true;
			}
		}

		if (printHelp)
			this.help();

		return typeToReturn;
    }

    private void processVerbosity(int levelofVerbosity){
        /** severe / warning / info / config / fine / finer / finest */
        /** 0 (you dont want much print statements) */
        /** 6 (you want many print statements)      */
        //theres only one handler -- stderr

        switch(levelofVerbosity){
            case 0: handlers[0].setLevel(Level.SEVERE);
                break;
            case 1: handlers[0].setLevel(Level.WARNING);
                break;
            case 2: handlers[0].setLevel(Level.INFO);
                break;
            case 3: handlers[0].setLevel(Level.CONFIG);
                break;
            case 4: handlers[0].setLevel(Level.FINE);
                break;
            case 5: handlers[0].setLevel(Level.FINER);
                break;
            case 6: handlers[0].setLevel(Level.FINEST);
                break;
            default:
                break;
        }

    }

	/**
	 * Prints help menu to stdout.
	 */
	private void help() {
		System.out.println("USAGE: [ -s <file name> | -d <directory name> | -h ]\n");
		System.out.println("Welcome to the C Dependency Graph Tool.\n");
		System.out.println("FLAG\tDESCRIPTION");
		System.out.println("-h\tPrint the help menu");
		System.out.println("-s\tProcess a single file");
		System.out.println("-d\tProcess a directory");
	}

	/**
	 * Checks if the passed file exists and sets the class attribute if it does.
	 *
	 * @param fileName name of the file.
	 * @return True if param was an existing file and class attribute was set,
	 * false otherwise.
	 */
	private boolean processSingleFile(String fileName) {
		File singleFile = new File(fileName);
		boolean success = false;

		if (singleFile.isFile()) {
			this.nameOfFile = fileName;
			success = true;
		} else {
			System.err.println("\nError: File name provided cannot resolve to a file");
		}

		return success;
	}

	/**
	 * Checks if the passed directory exists and sets the class attribute if it
	 * does.
	 *
	 * @param directoryName name of the directory.
	 * @return True if param was an existing directory and class attribute was
	 * set, false otherwise.
	 */
	private boolean processDirectory(String directoryName) {
		File directory = new File(directoryName);
		boolean success = false;

		if (directory.isDirectory()) {
			this.nameOfDirectory = directoryName;
			success = true;
		} else {
			System.err.println("\nError: Directory name provided cannot resolve to a directory");
		}

		return success;
	}

	/* Setters and Getters */

	public String getFileName() {
		return nameOfFile;
	}

	public String getDirectoryName() {
		return nameOfDirectory;
	}
}
