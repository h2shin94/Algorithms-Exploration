import java.util.Random;
import uk.ac.cam.hhs24.SortUtil;

public class MergeSort {
    int[] a;

    public static void main(String[] args) {
        MergeSort mSort = new MergeSort(SortUtil.randomArray(20, 50));
        mSort.doRecursive();
        SortUtil.printArray(mSort.a);
        System.out.println();
        MergeSort bSort = new MergeSort(SortUtil.randomArray(16, 50)); //only input size a perfect power of 2 works for now!!
        bSort.bottomUp();
        SortUtil.printArray(bSort.a);
    }

    public MergeSort(int[] a) {
        this.a = a;
    }

    public void merge(int p, int q, int r) {
        //p to q is first sub array q exclusive, q to r is second subarray, r exlusive.
        int n1 = q - p; //# of elements in left subarray, p to q exclusive so q-p elements
        int n2 = r - q; //we don't include the r so just r-q

        int[] left = new int[n1];
        int[] right = new int[n2];

        for (int i = 0; i < n1; i++) {
            left[i] = a[p + i];
        }
    //Both start at p and q since start index is inclusive and end index is exclusive!!!
        for (int j = 0; j < n2; j++) {
            right[j] = a[q + j];
        }

        int i = 0;
        int j = 0;

        for (int k = p; k < r; k++) {
            if (i == n1) { //end of left array reached
                a[k] = right[j];
                j++;
            } else if (j == n2) { //end of right array reached
                a[k] = left[i];
                i++;
            } else { //both array has elements
                if (left[i] <= right[j]) {
                    a[k] = left[i];
                    i++;
                } else { //r[j] < l[i]
                    a[k] = right[j];
                    j++;
                }
            }
        }
        return;
    }

    public void doRecursive() {
        recursive(0, a.length); //0 to a.length exclusive
    }

    public void recursive(int p, int q) {
        if (p < (q - 1)) { //at least 2 elements. if p+1 = q, just one element so leave it alone.
            int k = (p + q) / 2;
            recursive(p, k);
            recursive(k, q);
            merge(p, k, q); //p to k is first sub array, k to q is second sub array as required by merge!
            //if only 2 elements, p + 2 = q so k will be  p+1, as required! so we get p to p+1 as first array so
            //just p, and p+1 to q as second array so just p+1!! end index exclusive!!!
        }
        return;
    }


//The bottom up version involves working through array of sizes 1, 2, 4, 8 etc, and merging each one, so merging
//pairs of array of size 1, then pairs of array size 2 then 4 etc until it's all sorted. We still use the same merge idea.

    public void bottomUp() {
        int width; //indicates current width size

        for (width = 1; width < a.length/2; width = 2 * width) { //work up the widths in powers of 2. only need to do it while width is
            //less than a.length, since once you merge two arrays at least half the size in total array you have total array merged!

            //i works up the array from the beggining in steps of 2*width. and within the for loop we merge i to i+width, and i+width to 2*width
            //so for first iteration we have width = 1, so we merge 0 to 1 and 1 to 2, remember final index is EXCLUSIVE
            //so literally just merging single elements.
            //the merge works on the original array so all works out fine!!

            //then after i has worked through the whole array we move on to the next width, so when width is 2, we merge 0 to 2 with 2 to 4
            //etc, then 4 to 6 and 6 to 8. Since last index is exclusive it all works fine!!
            for (int i = 0; i < a.length; i = i + 2 * width) {
                int left = i;
                int middle = i + width;
                int right = i + 2 * width;

                merge(left, middle, right);
            }

        }
        return;
    }
    //IMPORTANT, In this pseudocode we don't account for when the middle/right exceeds the length of the array when width hasn't lined up perfectly
    //so will FAIL if input size is not a perfect power of 2!!
}