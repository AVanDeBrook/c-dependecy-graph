package depgraph.Parser;

import java.util.HashMap;
import java.util.StringTokenizer;

public class Node {

	private String name;
	private String functionName;
	private String prefix;
	private int[] connection;
	private HashMap<String, String> attributes = new HashMap<String, String>();

	public Node() {

	}

	public void setAttributes(HashMap<String, String> attributes) {
		this.attributes = attributes;
		this.functionName = getFunctionNameFromAttributes();
		StringTokenizer st = new StringTokenizer(this.functionName, "_");
		this.prefix = st.nextToken();
	}

	private String getFunctionNameFromAttributes() {
		return attributes.get(NodeAttributeType.LABEL.getKeyword());
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
