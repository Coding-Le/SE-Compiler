import java.util.*;
import java.io.*;

class NFA
{
	boolean recognizeString(int move[][][], int accept_state[], String word)
	{
		int length = word.length();
		int accept_state_num = accept_state.length;
		int []cur_state = new int[move.length];
		int cur_state_length = 1;
		cur_state[0] = 0;
		for (int i = 0; i <= length; i++) {
			for (int j = 0; j < cur_state_length; j++) {
				int e_closure_length = move[cur_state[j]][0].length;
				if (e_closure_length > 0) {
					for (int k = 0; k < e_closure_length; k++) {
						int visit_state;
						for (visit_state = 0; visit_state < cur_state_length; visit_state++) {
							if (cur_state[visit_state] == move[cur_state[j]][0][k]) {
								break;
							}
						}
						if (visit_state == cur_state_length) {
						cur_state[cur_state_length++] = move[cur_state[j]][0][k];
						}
					}
				}
			}
			if (i == length) {
				break;
			}
			char temp = word.charAt(i);
			int []new_state_arr = new int[move.length];
			int new_state_length = 0;
			for (int j = 0; j < cur_state_length; j++) {
				int transfer_num = move[cur_state[j]][temp-'a'+1].length;
					for (int k = 0; k < transfer_num; k++) {
						if (j == 0) {
							new_state_arr[new_state_length++] = move[cur_state[j]][temp-'a'+1][k];
						} else {
							int visit_state;
							for (visit_state = 0; visit_state < new_state_length; visit_state++) {
								if (new_state_arr[visit_state] == move[cur_state[j]][temp-'a'+1][k]) {
									break;
								}
							}
							if (visit_state == new_state_length) {
								new_state_arr[new_state_length++] = move[cur_state[j]][temp-'a'+1][k];
							}
						}
					}
			}
			cur_state = new_state_arr;
			cur_state_length = new_state_length;
		}
		for (int i = 0; i < accept_state_num; i++) {
			for (int j = 0; j < cur_state_length; j++) {
				if (cur_state[j] == accept_state[i]) {
					return true;
				}
			}
		}
		return false;
	}
	public static void main(String args[]) throws IOException
	{
		int n, m;
		BufferedReader in = new BufferedReader(new FileReader("NFA.in"));
		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		while (n != 0)
		{
			int[][][] move = new int[n][m][];
			for(int i=0; i<n; i++)
			{
				String line = in.readLine();
				int k = 0;
				for (int j=0; j<m; j++)
				{
					while (line.charAt(k) != '{') k++;
					k++;
					String states = "";
					while (line.charAt(k) != '}')
					{
						states = states + line.charAt(k);
						k++;
					}
					states = states.trim();
					if (states.length() > 0)
					{
						String[] stateArray = states.split(",");
						move[i][j] = new int[stateArray.length];
						for (int l=0; l<move[i][j].length; l++) move[i][j][l] = Integer.parseInt(stateArray[l].trim());
					}
					else move[i][j] = new int[0];
				}
			}
			String[] temp = in.readLine().split("\\s");
			int[] accept = new int[temp.length];
			for (int i=0; i<accept.length; i++) accept[i] = Integer.parseInt(temp[i]);
			String word = in.readLine();
			while (word.compareTo("#") != 0)
			{
				NFA nfa = new NFA();
				if (nfa.recognizeString(move, accept, word)) System.out.println("YES"); else System.out.println("NO");
				word = in.readLine();
			}
			st = new StringTokenizer(in.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
		}
	}
}
