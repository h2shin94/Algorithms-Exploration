import java.util.Arrays;
import java.util.Random;

public class MergeSort{
	int[] a;

	public static void main(String[] args) {
		MergeSort mSort = new MergeSort(SortUtil.randomArray(20, 50));
		mSort.doRecursive();
		SortUtil.printArray(mSort.a);
	}

	public MergeSort(int[] a){
		this.a = a;
	}

	public void merge(int p, int q, int r){
        //p to q is first sub array, q+1 to r is second subarray.
        int n1 = q-p+1; //# of elements in left subarray, p to q inclusive to q-p+1 elements
        int n2 = r-q; //we don't include the q so just r-q

        int[] left = new int[n1];
        int[] right = new int[n2];

        for (int i = 0; i < n1; i++){ //copy the stuff to merge across to left and right subarray.
            left[i] = a[p+i];
        }

        for (int j = 0; j < n2; j++){
            right[j] = a[q+j+1];
        }

        int i = 0;
        int j = 0;

        for (int k = p; k <= r ;k++ ) {
            if (i == n1) { //end of left array reached
                a[k] = right[j];
                j++;
            }else if(j == n2){ //end of right array reached
                a[k] = left[i];
                i++;
            }else { //both array has elements
                if (left[i] <= right[j]) {
                    a[k] = left[i];
                    i++;
                }else { //r[j] < l[i]
                    a[k] = right[j];
                    j++;
                }
            }
        }
        return;
    }

	public void doRecursive(){
		recursive(0, a.length-1);
	}

	public void recursive(int p, int q){
		if (p < q) { //at least 2 elemetns. if p = q, just one element so leave it alone.
			int k = (p + q) / 2;
			recursive(p, k);
			recursive(k+1, q);
			merge(p, k, q); //p to k is first sub array, k+1 to r is second sub array as required by merge!
			//if only 2 elements, p = k so both the above will only have 1 element each as required.
		}
		return;
	}
}

class SortUtil{
	public static void main(String[] args) {
		int[] a = randomArray(20, 100);
		printArray(a);
	}

	public static int[] randomArray(int n, int r){
		int[] a = new int[n];
		Random random = new Random();
		for (int i = 0;i < n ;i++ ) {
			a[i] = random.nextInt(r);
		}
		return a;
	}

	public static int[] randomArray(int n){
		int[] a = new int[n];
		Random random = new Random();
		for (int i = 0;i < n ;i++ ) {
			a[i] = random.nextInt();
		}
		return a;
	}

	public static void printArray(int[] a){
		System.out.print("{" + a[0]);
		for (int i = 1;i < a.length ;i++ ) {
			System.out.print(", " + a[i]);
		}
		System.out.println("}");
	}
}