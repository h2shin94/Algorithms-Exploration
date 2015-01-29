/**
 * Created by hyun-hoshin on 23/01/15.
 */
import uk.ac.cam.hhs24.SortUtil;

public class Radix {
    //Radix sort
    //assume we get a positive number.
    public static int[] radixSort(int[] a){
        int max = 0;
        for (int i = 0; i < a.length; i++){
            if (a[i] > max) max = a[i];
        }

        int d = (""+max).length();
        //get number of digits.

        for (int i = 1; i <= d; i++){ //countsort isn't in place, requires a new array so we keep assigning new array a
            //as result of countsort, so probably not the best sorting method.
            a = countSort(a, i);
        }
        return a;
    }

    public static int[] countSort(int[] a, int d){
        //count sort just on digit.
        int c[] = new int[10]; //only 0 to 9 on each digit.
        int b[] = new int[a.length];

        int power = 1; //power of 10 to extract dth digit.
        for (int j = 1; j <= d; j++){
            power *= 10;
        }

        for (int i = 0; i < a.length; i++){

            int val = (a[i] % power) / (power/10); //get digit we are working on.
            //if digit is 2 for example, power is 100 and number is 525, so the mod leaves 25, then div by 10 leaves 2.
            //as required!

            c[val]++; //now c[i] indicates number of instances of digit i.
        }

        for (int i = 1; i < 10; i++){
            c[i] += c[i-1]; //cumulatively addup the occurences so c[i] indicates how many numbers are less than or equal to.
        }

        for (int i = a.length-1; i >= 0; i--){
            int val = (a[i] % power) / (power/10); //get the digit again
            b[c[val]-1] = a[i]; //need to subtract 1 since we are 0 based indexes. so if n number of items, biggest item is bigger than or equal to
            //n items and that needs to go into index n.
            c[val]--;
        }
        //place value at a[i] into position indicated by c[a[i]], then decrement. Stable since
        //we place first a[i] into the last possible position that it can go, i.el c[a[i]] then decrement
        //c[a[i]] so that the value that is equal but further left goes into one position down and so on.
        //Surplus in teh cumulative sum value c[a[i]] is the region where the values of a[i] go.
        return b;
    }

    public static void main(String[] args){
        int sorted[] = radixSort(SortUtil.randomArray(10000, 999));
        SortUtil.printArray(sorted);
    }
}

//Not very space efficient.