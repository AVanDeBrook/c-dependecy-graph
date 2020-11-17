package depgraph.Parser;

/**
 * Basically a data structure to represent node definitions. Based on a
 * node_stmt from the formal grammar definition for DOT.
 *
 * From the official DOT language grammar: node_stmt: node_id [attr_list]
 * node_id: ID [port]
 */
public class Node {

	/**
	 * node_id in the formal grammar definition for DOT.
	 */
	private String nodeId;

	/**
	 * Node label appears as the first attribute in auto-generated attribute
	 * lists. Takes the form label="XXX_XxxXxx".
	 */
	private String nodeLabel;

	/**
	 * The first 3-4 letters of a function to represent the module.
	 */
	private String modulePrefix;

	/**
	 * This node is a root if the nodeLabel matches the graph name (what appears
	 * next to diagraph at the top of the file)
	 */
	private boolean isRoot;

	/**
	 * If true, it is public, if false, it is private
	 */
	private boolean isPublic;

	/**
	 * No-arg constructor.
	 */
	public Node() {

	}

	/* Setters and Getters */

	public String getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeLabel() {
		return this.nodeLabel;
	}

	public void setNodeLabel(String nodeLabel) {
		this.nodeLabel = nodeLabel;
	}

	public String getModulePrefix() {
		return this.modulePrefix;
	}

	public void setModulePrefix(String modulePrefix) {
		this.modulePrefix = modulePrefix;
	}

	public boolean isRoot() {
		return this.isRoot;
	}

	public void setIsRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public boolean isPublic() {
		return this.isPublic;
	}

	public void setIsPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	@Override
	public String toString() {
		return String.format("Node\n\tNODE ID: %s\n\tNODE LABEL: %s\n\tMODULE PREFIX: %s\n\tROOT: %b", this.nodeId,
				this.nodeLabel, this.modulePrefix, this.isRoot);
	}
}
