import java.util.ArrayList;

public class DNA{
    public static void main(String[] args) {
        max("CCGTCAGTCGCG", "TGTTTCGGAATGCAA");
    }


    public static int max(String s1, String s2){
        ArrayList<String> subs1 = subs(s1);

        return 0;
    }

    public static ArrayList<String> subs(String s) { //method to generate all subsequences
        ArrayList<String> subs = new ArrayList<String>();

        subs.add(s);

        int start = 0;
        int end = 0;
        int num = 0;
        int sublength = s.length() - 1; //length of current subsequence being generated, start with n-1. where n is length of string.

        while (sublength > 1) { //only generate down to 2 in this iterativemethod, length 1 can be done later.

            for (int i = start; i <= end; i++) { //outer loop, go through each subsequence of length sublength + 1
                String generator = subs.get(i); //previous subsequence being used to generate next subsequences of length 1 less.

                for (int j = 0; j < sublength; j++) { //loop to generate every sublist of length sublength from each of the one longer sublength.
                    subs.add(generator.substring(0, j) + generator.substring(j + 1, sublength + 1));

                    //add new subsequence by going through the current generator of previous substring, and adding a new subsequence by taking away a character from each possible position. starting with
                    //0 and up to sublength-1. Doesn't generate the subsequence with last character removed, need to do that outside the loop.

                    num++; //keep countof how many next subsequences generated
                }
                subs.add(generator.substring(0, sublength)); //just include everything from 0 to sublength-1 of generator, since generator has index up to sublength, since it's one longer than
                //sublength, going exclusive to sublength in the substring will miss out last character.
                num++;

            }

            start = end + 1; //now start with next set of subsequences
            end = start + num - 1; //created num amount of subsequence so we need to work from start to start + num-1
            sublength--;
            num = 0; //reset number of generated subsequences to 0.
        }

        return subs;
    }

    //Takes far to long to do anything!!!

    public static boolean match(){

        return false;
    }
}