package depgraph;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import depgraph.Parser.Lexer;
import depgraph.Parser.Token;
import depgraph.Parser.TokenType;

public class TestLexer {
	private Lexer lexer = new Lexer();

	@Test
	public void testTokenizerReturnsIgnoredForUnkownStatements() {
		Token token = lexer.tokenize("unknownsymbol");
		assertEquals(TokenType.IGNORED, token.getToken());
	}

	@Test
	public void testTokenizerReturnsNodeAttrStmtForNodeStatements() {
		Token token = lexer.tokenize("node [somestuffwedontcareabout];");
		assertEquals(TokenType.NODE_ATTR_STMT, token.getToken());
	}

	@Test
	public void testTokenizerReturnsEdgeAttrStmtForEdgeStatements() {
		Token token = lexer.tokenize("edge [somestuffwedontcareabout]");
		assertEquals(TokenType.EDGE_ATTR_STMT, token.getToken());
	}

	@Test
	public void testTokenizerReturnsNodeStmtForNodeDefinitions() {
		Token token = lexer.tokenize("Node1 [attributes];");
		assertEquals(TokenType.NODE_STMT, token.getToken());
	}

	@Test
	public void testTokenizerReturnsNodeStmtForNodeDefinitionsWithoutAttributes() {
		Token token = lexer.tokenize("Node1;");
		assertEquals(TokenType.NODE_STMT, token.getToken());
	}

	@Test
	public void testTokenizerReturnsEdgeStmtForEdgeDefinitions() {
		Token token = lexer.tokenize("Node1 -> Node2 [attributes];");
		assertEquals(TokenType.EDGE_STMT, token.getToken());
	}

	@Test
	public void testTokenizerReturnsEdgeStmtForEdgeDefinitionsWithoutAttributes() {
		Token token = lexer.tokenize("Node1 -> Node2;");
		assertEquals(TokenType.EDGE_STMT, token.getToken());
	}
}
