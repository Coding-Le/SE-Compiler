import java.io.IOException;
import java.io.*;
import java.util.*;

public class Runner {
	public String get_string(int length) throws IOException {
		String teststring = "1";
		for (int i = 0; i < length; i++) {
			char testchar = Math.random() > 0.5 ? '+':'-';
			teststring += testchar + new Integer((int) (Math.random()*10)).toString();
		}
		teststring += '\n';
		return teststring;
	}
}