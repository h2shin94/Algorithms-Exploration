/**
 * Created by hyun-hoshin on 20/01/15.
 */
import java.util.ArrayList;
import uk.ac.cam.hhs24.SortUtil;

public class Heap extends ArrayList<Integer>{
    private int heapSize = 0;
    private int length = 0;

    public Heap(int[] a){
        super.add(null); //index needs to start at 1 for it to work.
        for (int x : a){
            super.add(x);
            length++;
        }
    }

    public void maxHeapify(int i){ //max heapify the subtree with root i, assuming left(i) and right(i) are max heaps
        int l = left(i);
        int r = right(i); //get left and right index

        int largest = 0;

        if (l <= heapSize && get(l) > get(i)){ //only do comparison if l is within heapsize, since it might not be heap.
            largest = l;
        }else{
            largest = i;
        }
        if (r <= heapSize && get(r) > get(largest)){
            largest = r;
        }

//        Above if sequence simply identifies the index with the largest value, first compare l and i, then r with the largest of l and i.

        if (largest != i){
            int temp = get(i);
            set(i, get(largest));
            set(largest, temp);
            //swap i with the largest.
            maxHeapify(largest);
        }

        //after swap, the swapped index l/r will not be root of a potentially non max heaps so recursively max heapify
        //on the largest index.
    }//max heapifying a leaf will not do anything since l ,r > heapsize so largest is i.

    public void buildMaxHeap(){
        heapSize = length; //heapSize is now length of the whole thing.
        for (int i = length/2; i >= 1; i--){
            maxHeapify(i);
        }
    } //everything thing indexed from length/2 + 1 is a leaf, so we maxHeapify everything that is not a leaf from bottom up
    //and this builds a max heap, since invariant of heapify is that both left and right is a max heap.

    public void heapSort(){
        buildMaxHeap();
        //build a max heap first, which means root is always the biggest.
        for (int i = length; i >= 2; i--){
            int temp = get(i);
            set(i, get(1));
            set(1, temp);
            heapSize--;
            maxHeapify(1);
        }
        //since root is always biggest, max is always at index 1. we can put it in it's correct final position by moving it to index n.
        //so we start at i = length, the final position, and swap item at index 1 with final position.
        //now final element is no longer in teh heap so we decrement heap size, and since new root that we swapped into index 1
        //may not satisfy max heap property (left and right still do) we just max heapify from 1 the root again to maintain the max heap.
        //and we repeat the process until we swapped the root with item at index 2 which means all sorted!
    }

    public static int parent(int i){ //parent index is just i/2. truncated, easily done by right bit shift
        return (i >> 1);
    }

    public static int left(int i){
        return (i << 1); //left is twice current index
    }

    public static int right(int i){
        return (i << 1) + 1; //twice plus 1.
    }

    public static void main(String[] args){
        Heap heap = new Heap(SortUtil.randomArray(50, 100));
        SortUtil.printArray(heap);
        heap.heapSort();
        SortUtil.printArray(heap);
    }
}
