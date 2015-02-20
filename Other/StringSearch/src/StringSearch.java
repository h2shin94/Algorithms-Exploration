/**
 * Created by hyun-hoshin on 19/02/15.
 */
public class StringSearch {
    private int[] transition;
    private static char[] letters = new char[26];
    private String search;
    private String pattern;


    public StringSearch(String s, String p){
        search = s;
        pattern = p;

        for (int i = 0; i < 25; i++){ //store letters a to z.
            letters[i] = (char) (97 +  i);
        }
    }

    public void makeTransitions(){
        int m = pattern.length();
        int n = search.length();

        transition = new int[(m+1)*26];


        for (int q = 0; q <= m; q++){ //work up each state
            int k = 0; //k is the next state
            for(int i = 0; i <= 25; i++) {
                k = Integer.min(m + 1, q + 2); //max k is m or q+1
                String pqa = "";
                do {
                    k--; //work down k until k first letters of P is suffix of (q first letters of P)+newLetter
                    pqa = (pattern.substring(0, q) + letters[i]); //find the max prefix of P for state q with additional letter.
                } while (!(k == 0) && !pattern.substring(0, k).equals(pqa.substring(pqa.length()-k)));
                //check for k==0, since then it's a prefix no matter what Pq is! And end when 0 to k-1 of P equals last
                //k characters of pqa, since then that is max size of prefix of P for pqa.
                transition[q * 26 + i] = k;
            }
        }

        //I store the transitions encoded in the index state * 26 + letter, we have 26 letters for each state.
    }

    public int findInstances(){
        int n = search.length();
        int m = pattern.length();
        int count = 0;
        int q = 0; //start state.
        for(int i = 0; i < n; i++){
            int c = search.charAt(i);
            if(c < 97 || c > 122){ //only consider lower cases!
                q = 0;
            }else {
                q = transition[q * 26 + (search.charAt(i) - 97)];
                count += m == q ? 1 : 0;
            }
        }

        return count;
    }

    public static void main(String[] args){
        StringSearch shakesOUS = new StringSearch("yeah ous ousous baby ous ous dudy ous woody", "ous");
        shakesOUS.makeTransitions();
        System.out.println(shakesOUS.findInstances());
    }
}
