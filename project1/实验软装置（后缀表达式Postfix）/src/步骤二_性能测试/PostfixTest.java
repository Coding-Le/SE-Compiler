/*import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class PostfixTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testMain() throws IOException {
		int switchcase = System.in.read();
		Parser new_parser = new Parser();
		new_parser.expr();
		switch (switchcase) {
		case(1):
		//please input with "1-2" to test the first case
		assertEquals(new_parser.output_Postfix, "12-");
		break;
		
		case(2):
		//please input with "12" to test the second case
		assertEquals(new_parser.state, "false");
		assertEquals(new_parser.error_list, "12-");
		
		case(3):
		}
		
	}
}*/
