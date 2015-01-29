/**
 * Created by hyun-hoshin on 17/01/15.
 */
import uk.ac.cam.hhs24.SortUtil;

public class BubbleSort{
    int[] a;

    public static void main(String[] args) {
        BubbleSort bSort = new BubbleSort(SortUtil.randomArray(20, 50));
        SortUtil.printArray(bSort.a);
    }

    public BubbleSort(int[] a){
        boolean swap = true;

        int n = a.length-2; //we compare from left to the right so we need to end at length-2 for first for loop

        while (swap){
            swap = false; //intially set the swap to falsefor next pass

            for (int i = 0; i <= n;i++ ){
                if (a[i] > a[i+1]){
                    int temp = a[i];
                    a[i] = a[i+1];
                    a[i+1] = temp;
                    swap = true;
                }
            }
            n--; //since last elemetn now in place no need to compare to last element.
        }
        this.a = a;
    }
}