import java.io.IOException;
import java.io.*;
import java.util.*;

public class Runner {
	public String get_string() throws IOException {
		String teststring = "1";
		for (int i = 0; i < 100000; i++) {
			char testchar = Math.random() > 0.5 ? '+':'-';
			teststring += testchar + new Integer((int) (Math.random()*10)).toString();
		}
		teststring += '\n';
		return teststring;
	}
}
