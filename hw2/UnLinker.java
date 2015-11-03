import java.io.*;
import java.util.*;
public class UnLinker
{
	public final String prefix1 = "www.";
	public final String prefix2 = "http://";
	public final String postfix4 = ".com";
	public final String postfix2 = ".org";
	public final String postfix3 = ".edu";
	public final String postfix5 = ".info";
	public final String postfix1 = ".tv";
	public  static int omit_num = 1;
	String clean(String tmp_text)
	{	
		int length = tmp_text.length();
		String result = new String();
			int pre_position1 = tmp_text.indexOf(prefix1);
			int pre_position2 = tmp_text.indexOf(prefix2);
			int pre_position;
			int pre_len;
			if (pre_position1 == -1 && pre_position2 == -1) {
				result += tmp_text;
				return result;
			} else if (pre_position1 == -1) {
				pre_position = pre_position2; 
				pre_len = 7;
			} else if (pre_position2 == -1) {
				pre_position = pre_position1;
				pre_len = 4;
			} else {
				pre_position = pre_position1 > pre_position2 ? pre_position2:pre_position1;
				pre_len = pre_position1 > pre_position2 ? 7:4;
			}
			result += tmp_text.substring(0, pre_position);
			int post_position1 = tmp_text.lastIndexOf(postfix1);
			int post_position2 = tmp_text.lastIndexOf(postfix2);
			int post_position3 = tmp_text.lastIndexOf(postfix3);
			int post_position4 = tmp_text.lastIndexOf(postfix4);
			int post_position5 = tmp_text.lastIndexOf(postfix5);
			int which_pos = 1;
			int post_position = post_position1;
			if (post_position < post_position2) {
				post_position = post_position2;
				which_pos = 2;
			}
			if (post_position < post_position3) {
				post_position = post_position3;
				which_pos = 3;
			}
			if (post_position < post_position4) {
				post_position = post_position4;
				which_pos = 4;
			}
			if (post_position < post_position5) {
				post_position = post_position5;
				which_pos = 5;
			}
			if (post_position == -1) {
				result += tmp_text;
				return result;
			}
			int len = 0;
			switch(which_pos) {
			case 1: len = 3; break;
			case 2:
			case 3: 
			case 4: len = 4; break;
			case 5: len = 5; break;
			}
			String tmp = tmp_text.substring(pre_position, post_position+len);
			String infix = tmp_text.substring(pre_position+pre_len, post_position);
			if (infix.indexOf(" ") == -1 && infix.indexOf(",") == -1 && infix.indexOf(":") == -1 && infix.indexOf("/") == -1) {
				Integer count = omit_num;
				result = result+"OMIT"+  count.toString();
				omit_num++;
			} else {
				int trim_pos = tmp.indexOf(" ");
				if (trim_pos == -1) {
					trim_pos = tmp.lastIndexOf(",");
				}
				if (trim_pos == -1) {
					int trim_pos1 = infix.indexOf("http://");
				    trim_pos = trim_pos1 > -1 ? trim_pos1+pre_len:-1;
					}
				if (trim_pos == -1) {
					trim_pos = tmp.indexOf("/");
				}
				if (trim_pos == -1) {
					trim_pos = tmp.indexOf(":");
				}
				String part1 = tmp.substring(0, trim_pos);
				String part2 = tmp.substring(trim_pos);
				result = result+clean(part1)+clean(part2);
			}
			result += tmp_text.substring(post_position+len);
			return result;
	}
	
	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(new File("UnLinker.in"));
		String text;
		UnLinker ul = new UnLinker();
		while (in.hasNextLine())
		{
			text = in.nextLine();
			omit_num = 1;
			System.out.println(ul.clean(text));
		}
		in.close();
	}
}
