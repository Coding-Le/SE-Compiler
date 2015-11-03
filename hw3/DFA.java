import java.util.*;
import java.io.*;

class DFA
{
	boolean recognizeString(int move[][], int accept_state[], String word)
	{
		int state_num = move.length;
		if (state_num > 0) {
			int character_num = move[0].length;
		} else {
			int character_num = 0;
		}
		int accept_state_num = accept_state.length;
		int length = word.length();
		int cur_state = 0;
		for (int i = 0; i < length; i++) {
			char temp = word.charAt(i);
			if (temp-'a'+1 > move[cur_state].length) {
				return false;
			}
			cur_state = move[cur_state][temp-'a'];
		}
		for (int j = 0; j < accept_state_num; j++) {
			if (cur_state == accept_state[j]) {
				return true;
			}
		}
		return false;
	}
	public static void main(String args[]) throws IOException
	{
		int n, m;
		BufferedReader in = new BufferedReader(new FileReader("DFA.in"));
		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		while (n != 0)
		{
			int[][] move = new int[n][m];
			for(int i=0; i<n; i++)
			{
				st = new StringTokenizer(in.readLine());
				for (int j=0; j<m; j++)
					move[i][j] = Integer.parseInt(st.nextToken());
			}
			String[] temp = in.readLine().split("\\s");
			int[] accept = new int[temp.length];
			for (int i=0; i<accept.length; i++) accept[i] = Integer.parseInt(temp[i]);
			String word = in.readLine();
			while (word.compareTo("#") != 0)
			{
				DFA dfa = new DFA();
				if (dfa.recognizeString(move, accept, word)) System.out.println("YES"); else System.out.println("NO");
				word = in.readLine();
			}
			st = new StringTokenizer(in.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
		}
	}
}