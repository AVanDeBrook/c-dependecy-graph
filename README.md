# Dependency Graph
* ERAU DB EECS Senior Design Project. Generates dependency graphs based on Graphviz dot file(s)

# Installation
This project has one external dependency which is the [Graphviz](https://graphviz.org) tools. Specifically, `dot`.

It can be downloaded [here](https://graphviz.org/download/). `dot` needs to be on he system path in order for the program to work correctly.

# Running
The program can be run two different ways. 

## From Source Code
The first and most consistent one requires [Gradle](https://gradle.org/install/).

Clone the GitHub repository
```bash
git clone https://github.com/AVanDeBrook/c-dependency-graph.git
```

Use the following (in command-line) to build and run the project.
```bash
gradle build run
```

Use `--args=""` to pass arguments to the program. See below for examples.

Running the program with the `-h` flag.
```bash
gradle build run --args="-h"
```

Passing a directory.
```bash
gradle build run --args="-d some-dir"
```

## From executable
The second method of running the program is by downloading the `depgraph.zip` from the [releases page](https://github.com/AVanDeBrook/c-dependency-graph/releases)

Download and extract the archive. Adding the `bin` folder to your system path will make the program easier to run, however, at the moment there is likely a flaw with the way the program finds the template files it needs to create the intermediate graph before rendering the image. Because of this, the program must be run from the directory that it was installed to.

For example, if the program is installed in `~/Programming/depgraph`, you would need to do the following to run the program:
```bash
cd ~/Programming/depgraph

# For Linux
export PATH=$PATH:$PWD/bin
# For Windows
set PATH=%PATH%;%cd%/bin

# Print help menu
depgraph -h
```

**Note that the program can accept multiple command line arguments.**
 
 # Command Line Arguments
 The following is a list of flags that the program recognizes and accepts.
 
 Flag | Description | Usage
 --- | --- | ---
 `-h` | Prints help menu | `depgraph -h` or `gradle run --args="-h"`
 `-s` | Process a single file | `depgraph -s <file>` or `gradle run --args="-s <file>`
 `-d` | Process a directory | ` depgraph -d <directory>` or `gradle run --args="-d <directory>`
 `-v` | Set logging level/verbosity. Defaults to quiet when not specified. <ul><li>`0` - Severe</li><li>`1` - Warning</li><li>`2` - Info</li><li>`3` - Fine</li></ul> | `depgraph -v <0-3>` or `gradle run --args="-v <0-3>`
 `-L` | Redirect logger output to a file | `depgraph -L <file>` or `gradle run --args="-L <file>`
 `-o` | Set the output file name and type. Follows the form `<file-name>.<file-type>`. Possible file types: <ul><li>`dot`</li><li>`xdot`</li><li>`ps`</li><li>`pdf`</li><li>`svg`</li><li>`fig`</li><li>`png`</li><li>`gif`</li><li>`jpg`</li><li>`jpeg`</li></ul> | `depgraph -o <file>` or `gradle run --args="-o <file>`
 `-F` | <p>Filter expression. Takes the form:</p><p>`{module [, module]} => {module [, module]}`</p> | <p>`depgraph -F {module [, module]} => {module [, module]}`</p><p>or</p><p>`gradle run --args="{module [, module]} => {module [, module]}"`</p>
 
 
 # Documentation
 * Documentation can be found on [the project web page](https://avandebrook.github.io/c-dependency-graph/) as well as in [our wiki](https://github.com/AVanDeBrook/c-dependency-graph/wiki)
