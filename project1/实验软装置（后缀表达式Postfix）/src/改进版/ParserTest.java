import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class ParserTest {
	public Parser myparser;
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testExpr() throws IOException {
		myparser.expr();
	}

	@Test
	public void testRest() {
		fail("Not yet implemented");
	}

	@Test
	public void testTerm() {
		
		//fail("Not yet implemented");
	}

	@Test
	public void testMatch() throws IOException {
		myparser.lookahead = '+';
		myparser.match('+');
		assertEquals(myparser.lookahead, '0');
	}

}
