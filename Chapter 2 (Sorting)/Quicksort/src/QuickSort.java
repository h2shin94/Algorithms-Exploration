/**
 * Created by hyun-hoshin on 20/01/15.
 */
import java.util.ArrayList;
import java.util.Arrays;
import uk.ac.cam.hhs24.SortUtil;
import java.util.Random;

public class QuickSort extends ArrayList<Integer>{ //extend arraylist to make everything easier and array limitless
    public static void main(String args[]){
        QuickSort quick = new QuickSort();
        quick.sort(0, quick.size());
        SortUtil.printArray(quick);
        QuickSort find = new QuickSort();
        SortUtil.printArray(find);
        find.findMinMax();

        System.out.println("Finding ith element!");
        QuickSort findi = new QuickSort();
        System.out.printf("The %d smallest element: %d\n", 5, findi.findi(0, findi.size(), 5));
        findi.sort(0, findi.size());
        SortUtil.printArray(findi);
    }

    public QuickSort(){
        super(SortUtil.randomArraylist(20, 100));
    }

    public int partition(int p,int r){
        int i = p-1;
        int j = p;
        int pivot = super.get(r-1);

        for (;j < r-1; j++){
            if (super.get(j) <= pivot){
                int temp = super.get(++i);
                super.set(i, super.get(j));
                super.set(j, temp);
            }
            //i is head of the smaller portion.
            //j is one ahead the larger portion, as soon as j finds a smaller one, increment i and swap with j.
            //once j reaches r-1, i.e. the pivot, all partitioned.
        }

        super.set(r-1, super.get(++i)); //swap pivot with first large element which is at i+1
        super.set(i, pivot);
        return i; //return positin of pivot
    }

    public int randomizedPartition(int p, int r){
        Random random = new Random();
        int pivot = random.nextInt(r-p)+p; //choose a random index between p and r. r-p gives number of elements in the array, so find 0 to n exclusive random, then add p
        //to get p to r exclusive!!!
        //then add p to get the random index.
        int temp = get(r-1);
        set(r-1, get(pivot));
        set(pivot, temp);
        //swap around r-1 with pivot random position, so now we can just run partition as normal and r-1 has a random element!!
        return partition(p, r);
    }

    public void sort(int p, int r){
        if (p < r-1){ //at least 2 elements, if 1 or 0 just return
            int q = randomizedPartition(p, r);
            sort(p, q); //q exclusive
            sort(q, r); //r exclusive
        }
        return;
    }

    public void findMinMax(){
        //finding minimum or maximum independently always involves n-1 number of comparisons. But if you want to find both you
        //don't have to do 2n-2 comparisons, just a maximum of 3n/2, by keeping tabs on the current minimum and maximum, and instead
        //of working through every item individually and comparing with min and max, you work pairwise, compare elements with each other
        //and compare smaller with min and larger with max and swap if necessary

        int min;
        int max;
        int start; //start index

        if (size() % 2 == 0){
            //even number of elements, compare first two to set the initial min and max
            if (get(0) <= get(1)){
                min = get(0);
                max = get(1);
            }else{
                min = get(1);
                max = get(0);
            }
            //set start to 2 since 0 and 1 have been accounted for
            start = 2;
        }else{
            //odd numbe of elements, set min and max both to first element and start at 1.
            min = get(0);
            max = get(0);
            start = 1;
        }

        for (int i = start; i < size()-1; i++){ //only up to size()-2, so 1 below last index since we compare two at a time.
            if (get(i) <= get(i+1)) { //i less than or equal to i+1
                if (get(i) < min) {
                    min = get(i);
                }if (get(i+1) > max){
                    max = get(i+1);
                }
            }else{ //i is greater than i+1
                if (get(i+1) < min) {
                    min = get(i+1);
                }if (get(i) > max){
                    max = get(i);
                }
            }
        }

        System.out.println("Min: " + min + " Max: " + max);
    }

    public int findi(int p, int r, int i){ //It works!!!!
        //finds the ith smallest number with 1 being the smallest
        if(p+1 == r){
            //only 1 element at p, then we've found ith element, it's the only possibility!
            //We never enter a partition of size 0 since i is never less than or equal to 0 since 1 is always
            //the smallest and if greater partition has no elements then it is necessary that i is <= n+1 since that's
            //all the elements in the array!!
            //so once a one elements array is reached, it is necessary i is 1!!

            return get(p);
        }
        //else we partition!!
        int q = partition(p, r); //partition the thing
        int n = q-p; //n is number of elements in the lower part.
        if (i == n+1) { //i is the relative position of the pivot!! then pivot is the ith smallest!
            return get(q); //return pivot
        }else if(i <= n){
            //i is within the first n numbers
            return findi(p, q, i); //find ith smallest within p to q!!
        }else{
            //this is when i > n+1 so look in the bigger half for the i-(n+1) element
            //since the bigger half will start at the n+2 element, since n smallest was in first half, pivot was n+1 element and so start
            //of bigger half will be n+2 element, so if i is n+2, then we need 1 as the order statistic!!
            return findi(q+1, r, i-(n+1));
        }

        //REMEMBER you HAVE to RETURN recursive calls, since it's the recursive call that evalutes to teh value you want!!
    }
}
