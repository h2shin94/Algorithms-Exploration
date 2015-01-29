/**
 * Created by hyun-hoshin on 23/01/15.
 */

import uk.ac.cam.hhs24.SortUtil;

public class Counting {
    //Counting Sort

    public static int[] countSort(int[] a, int k){
        int[] b = new int[a.length];
        int[] c = new int[k+1]; //k is range of integers to be sorted.

        for (int i = 0; i < a.length; i++){
            c[a[i]]++; //now c[i] indicates number of instances of i.
        }
        for (int i = 1; i <= k; i++){
            c[i] += c[i-1]; //cumulatively addup the occurences so c[i] indicates how many numbers are less than or equal to.
        }
        for (int i = a.length-1; i >= 0; i--){
            b[c[a[i]]-1] = a[i]; //need to subtract 1 since we are 0 based indexes. so if n number of items, biggest item is bigger than or equal to
            //n items and that needs to go itno index n.
            c[a[i]]--;
        }
        //place value at a[i] into position indicated by c[a[i]], then decrement. Stable since
        //we place first a[i] into the last possible position that it can go, i.el c[a[i]] then decrement
        //c[a[i]] so that the value that is equal but further left goes into one position down and so on.
        //Surplus in teh cumulative sum value c[a[i]] is the region where the values of a[i] go.
        return b;
    }

    public static void main(String[] args){
        int[] sorted = countSort(SortUtil.randomArray(50, 100), 100);
        SortUtil.printArray(sorted);
    }

}
