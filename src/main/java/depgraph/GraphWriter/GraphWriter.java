package depgraph.GraphWriter;

import java.io.*;
import java.util.*;

import depgraph.Parser.Edge;
import depgraph.Parser.Module;
import depgraph.Parser.Node;

/**
 * Class to read graph templates, fill them out and write the resulting
 * graphviz DOT graph to a ".dot" file.
 */
public class GraphWriter {

    /**
     * General form of a node defintion in DOT based on the official grammar.
     */
    public final String NODE_DEFINITION = "%node.id% [label=\"%node.label%\"];";

    /**
     * General form of an edge definition in DOT based on the official grammar.
     */
    public final String EDGE_DEFINITION = "%edge.src.id% -> %edge.dest.id%;";

    /**
     * Configurable path to the graph template. Called "graph.temp" by default.
     */
    private String graphTemplatePath;

    /**
     * Configurable path to subgraph template. Called "subgraph.temp" by default.
     */
    private String subgraphTemplatePath;

    /**
     * Contents of the graph template file for reference throughout the class.
     */
    private String graphTemplate;

    /**
     * Contents of the subgraph template file for reference throughout the class.
     */
    private String subgraphTemplate;

    /**
     * List of edges created by the Parser class. Needed when creating the
     * connections between nodes and modules in the dependency graph.
     */
    private List<Edge> edges;

    /**
     * List of modules created by the Parser class. Needed when creating the
     * representation of modules in the dependency graph.
     */
    private List<Module> modules;

    /**
     * No-arg constructor
     */
    public GraphWriter() {
        graphTemplatePath = "templates/graph.temp";
        subgraphTemplatePath = "templates/subgraph.temp";
        graphTemplate = "";
        subgraphTemplate = "";
    }

    /**
     * Construct a GraphWriter with predefined modules and edges.
     *
     * @param modules List of modules (created and passed from the Parser class)
     * @param edges List of edges (created and passed from the Parser class)
     */
    public GraphWriter(List<Module> modules, List<Edge> edges) {
        graphTemplatePath = "templates/graph.temp";
        subgraphTemplatePath = "templates/subgraph.temp";
        graphTemplate = "";
        subgraphTemplate = "";

        this.edges = edges;
        this.modules = modules;
    }

    /**
     *  Reads graph and subgraph templates from the graphTemplatePath and subgraphTemplatePath.
     *  By default, these are set to "templates/graph.temp" and "templates/subgraph.temp"
     */
    public void readTemplates() {
        graphTemplate = readTemplate(graphTemplatePath);
        subgraphTemplate = readTemplate(subgraphTemplatePath);
    }

    /**
     * Draws graph with a the default name.
     *
     * @throws Exception when there is an error creating the output file.
     */
    public void drawGraph() throws Exception {
        drawGraph("out.dot");
    }

    /**
     * Core function to build and write the rearranged graph.
     *
     * TODO details on drawGraph function
     *
     * @param fileName Name of the file to write to.
     * @throws Exception If there is an error creating the graph or writing it to a file.
     */
    public void drawGraph(String fileName) throws Exception {
        ArrayList<String> publicFunctionSubgraphs = new ArrayList<String>();
        String graph = graphTemplate;
        String subgraphClusters = "";
        for (Module module : modules)
            publicFunctionSubgraphs.add(renderPublicSubgraph(module));

        for (String subgraph : publicFunctionSubgraphs)
            subgraphClusters += subgraph + "\n";

        graph = graph.replaceAll("%graph.subgraph_cluster%", subgraphClusters);

        writeToFile(fileName, graph);
    }

    private String renderPublicSubgraph(Module module) {
        String nodeDefs = "";
        String subgraph = subgraphTemplate;

        for (Node node : module.getNodes()) {
            if (node.isPublic()) {
                String nodeString = NODE_DEFINITION;
                nodeString = nodeString.replaceAll("%node.id%", node.getNodeId());
                nodeString = nodeString.replaceAll("%node.label%", node.getNodeLabel());
                nodeDefs += nodeString + "\n";
            }
        }

        subgraph = subgraph.replaceAll("%subgraph.visibility%", "pub");
        subgraph = subgraph.replaceAll("%subgraph.visibility_long%", "Public");
        subgraph = subgraph.replaceAll("%subgraph.modulePrefix%", module.getModulePrefix());
        subgraph = subgraph.replaceAll("%subgraph.node_defs%", nodeDefs);

        return subgraph;
    }

    private void writeToFile(String fileName, String graph) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)));
            writer.write(graph);
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Reads template from templatePath and returns the resulting string.
     * File is read character-by-character, so the string will be an exact copy (including whitespace).
     *
     * @param templatePath Path to the specific template to read.
     * @return Contents of the template file.
     */
    private String readTemplate(String templatePath) {
        BufferedReader reader;
        String template = "";
        try {
            reader = new BufferedReader(new FileReader(new File(templatePath)));

            while (reader.ready())
                template += (char)reader.read();

            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return template;
    }

    /* Setters and Getters */

    public String getGraphTemplatePath() {
        return this.graphTemplatePath;
    }

    public void setGraphTemplatePath(String graphTemplatePath) {
        this.graphTemplatePath = graphTemplatePath;
    }

    public String getSubgraphTemplatePath() {
        return this.subgraphTemplatePath;
    }

    public void setSubgraphTemplatePath(String subgraphTemplatePath) {
        this.subgraphTemplatePath = subgraphTemplatePath;
    }

    public String getGraphTemplate() {
        return this.graphTemplate;
    }

    public String getSubgraphTemplate() {
        return this.subgraphTemplate;
    }

    public List<Module> getModules() {
        return this.modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public List<Edge> getEdges() {
        return this.edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges= edges;
    }
}
