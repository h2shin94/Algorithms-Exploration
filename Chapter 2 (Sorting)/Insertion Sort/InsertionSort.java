import uk.ac.cam.hhs24.*;

public class InsertionSort{
	int[] a;

	public static void main(String[] args) {
		InsertionSort inSort = new InsertionSort(SortUtil.randomArray(20, 50));
		SortUtil.printArray(inSort.a);
	}

	public InsertionSort(int[] a){
		for (int i = 1; i < a.length ;i++ ) { //a[0..i-1] is already sorted so start from 1
			int j = i-1; //start comparing one below i
			int key = a[i]; //current value to be inseted.

			while (j >= 0 && a[j] > key) { //shift values to the right while current position of j is greater than key value.
				//basically shifting it out the way to the right to make way for the key. we extract the key so each iteration is
				//not a three assignment swap but a simple shifting of values to the right until key finds it's position.
				a[j+1] = a[j];
				j--;
			}

			a[j+1] = key; //once while loop is done
		}
		this.a = a;
	}
}