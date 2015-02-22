import java.util.ArrayList;

public class BookShelves {
    //in all this the lowest order bit is the first book and so on.

    int shelfWidth;
    int[] books;
    int[] widths;

    int noOfBooks;
    int noOfCombs; //number of combinations.
    public ArrayList<int[]> spaceLeft;

    public BookShelves(int width, int[] b, int[] w) { //setup the solution matrix for 1 shelf.
        shelfWidth = width;
        books = b;
        widths = w;

        noOfBooks = 0;
        for (int i = 0; i < b.length; i++) {
            noOfBooks += b[i];
        }

        if (noOfBooks > 31) {
            System.out.println("Sorry, my program's too stupid to cope with that many");
            return;
        }
        noOfCombs = 1 << noOfBooks;

        spaceLeft = new ArrayList<int[]>();
        spaceLeft.add(null);
        spaceLeft.add(new int[noOfCombs]); //number of elements in combinations of books.
        //Each array in the ArrayList will correspond to a shelf, with each cell being the space remaining for the column's books.

        for (int i = 0; i < noOfCombs; i++){
            spaceLeft.get(1)[i] = spaceRemaining(i);
        }
    }

    public int spaceRemaining(int comb){
        int maxBooks = 32 - Integer.numberOfLeadingZeros(comb); //number of bits we need to check.
        int bitNo = 1; //current bit number.
        int spaceLeft = shelfWidth;

        while(maxBooks > 0 && spaceLeft > 0){
            int currentBook = bitNo;
            if ((comb & 1) == 1){ //current book being considered
                int widthIndex = 0; //which widthindex to look at

                for(int i = 0; books[i] < currentBook; i++){
                    currentBook -= books[i];
                    widthIndex++;
                }//subtract number of books of each width until we find the book that current bit points to.

                spaceLeft -= widths[widthIndex];
            }
            maxBooks--;
            bitNo++;
            comb >>>= 1; //move on to next book
        }

        if(maxBooks > 0 || spaceLeft < 0){ //didn't get through all the books or ended up with negative space.
            return -1;
        }
        return spaceLeft; //return space remaining.
    }

    public ArrayList<Integer> twoWayCombs(int comb){
        int noOfBooksComb = Integer.bitCount(comb); //store number of books.

        ArrayList<Integer> splits = new ArrayList<Integer>((1 << (noOfBooksComb-1)) - 1);
        int[] splits2 = new int[(1 << noOfBooksComb-1) - 1]; //we need to store every two way spit.

        int[] bitShiftSet = new int[31]; //stores shifts needed to get to first set bit, second set bit etc.

        int maxSetPos = 32 - Integer.numberOfLeadingZeros(comb);//last bit we consider
        int index = 0;
        for (int i = 0; i < maxSetPos ; i++){ //i is number of shifts we've done
            if(((comb >>> i) & 1) == 1){
                bitShiftSet[index] = i;
                index++;
            }
        } //now we have all set bit positions in the first noOfBooksComb indexes of bitShiftSet

        int flattened = ((1 << noOfBooksComb) - 1); //now first noOfBooksComb is set as 1.

        for(int mask = 1; mask < flattened; mask++){ //work up from 1 to one less than flattened, since all of them is of no interest.
            int split = flattened & mask; //generate one half of the split
            int result = 0; //our result
            for(int i = 0; i < noOfBooksComb; i++){
                if ((split & 1) == 1){ //lowest bit set
                    result |= (1 << bitShiftSet[i]); //set the bit;
                }
                split >>>= 1; //consider next bit.
                // skips the bitShiftSet index where split is set in corresponding bit.
            }
            if (splits.indexOf((Object) (comb & ~(result))) == -1){//only add if complement doesn't already exist.
                splits.add(result);
            }
        }

        return splits;
    }

    public int shelves(){
        int currentShelf = 1;
        while(spaceLeft.get(currentShelf)[noOfCombs-1] == -1){ //we go until full combination of books for last shelf has space left.
            currentShelf++;

            spaceLeft.add(new int[noOfCombs]); //add a new shelf

            for (int comb = 0; comb < noOfCombs; comb++){ //the index 0 to noOfCombs directly corresponds to the comb we're considering.
                if(spaceLeft.get(currentShelf-1)[comb] >= 0){
                    spaceLeft.get(currentShelf)[comb] = shelfWidth;
                }else{
                    ArrayList<Integer> twoWaySplits = twoWayCombs(comb); //get all the masks for onehalf of the 2 way splits
                    int maxSpaceLeft = -1; //maxSpace for current shelf

                    for (int i = 0; i < twoWaySplits.size(); i++){
                        int group1 = twoWaySplits.get(i);
                        int group2 = (comb & ~(group1)); //and with complement to get the other group.

                        if(spaceLeft.get(1)[group1] != -1 && spaceLeft.get(currentShelf - 1)[group2] != -1){
                            //this spilt is a valid assignment of books to 1 shelf (current shelf) and previous shelf.
                            if (spaceLeft.get(1)[group1] > maxSpaceLeft){
                                maxSpaceLeft = spaceLeft.get(1)[group1];
                                //if this split's assignment to 1 shelf has more space then set it!
                            }
                        }

                        if(spaceLeft.get(1)[group2] != -1 && spaceLeft.get(currentShelf - 1)[group1] != -1) {
                            //Need to try the other way around as well..
                            if (spaceLeft.get(1)[group2] > maxSpaceLeft) {
                                maxSpaceLeft = spaceLeft.get(1)[group1];
                            }
                        }
                    }//now we hve maxSpaceLeft

                    spaceLeft.get(currentShelf)[comb] = maxSpaceLeft; //store the maxSpaceLeft
                }
            }


        }
        return spaceLeft.size()-1; //return number of shelves
    }

    public static void main(String[] args){
        int[] b = {5,5,4};
        int[] w = {4,5,6};
        BookShelves shelves = new BookShelves(10, b, w);
        System.out.println(shelves.shelves());
    }
}
