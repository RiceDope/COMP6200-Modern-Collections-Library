/**
 * Script that implements the functionality of ArrayList, Stack, Queue, SortedArray.
 * For use as a general purpose dynamic collections library
 * 
 * GitHub: https://github.com/RiceDope/COMP6200-Modern-Collections-Library
 * 
 * Created: 30/09/2024
 * Last Updated 03/11/2024
 * 
 * @Author Rhys Walker
 */

package com.rwalker;

import java.lang.StringBuilder;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

@SuppressWarnings({"unchecked", "unused"})
public class Sequence<E> implements Iterable<E> {

    // Runtime variables
    private int endPointer = 0; // Track our current final index
    private int startPointer = 0; // Track our current first index
    private int removed = 0; // Not used infrastructure in place for length and such

    // Subject to change in constructor
    private int initialSize = 100;
    private E[] array = (E[]) new Object[initialSize]; // Default size of array if one is not chosen
    private double growthRate = 1.5; // Default growth rate if one isnt chosen

    // Allow for the setting of functionality and general use
    private boolean enforceSort = false;
    private Comparator<E> defaultComparator; // User can set in constructor or in runtime

    // Developer settings for adjustment
    private int minumumExpansion = 1; // Decide on minimum expansion requirements

    /*
        =================================================
                    OVERLOADED CONSTRUCTOR                          
        =================================================
    */

    /**
     * Default constructor using default growth rate and size values
     */
    public Sequence(){
        
        // Default values are used

    }

    /**
     * Allows for specification of just the initial size of the array
     * @param size Int initial size of the array
     */
    public Sequence(int size){
        if (size  > 0){
            initialSize = size;
            array = (E[]) new Object[size];
        } else {
            throw new NegativeArraySizeException("Cannot use size of 0 or less");
        }
    }

    /**
     * Allows for the specification of a comparator
     * @param comparator Default comparator to be used in sort
     */
    public Sequence(Comparator<E> comparator){
        defaultComparator = comparator;
    }

    /**
     * Allows for specification of initial array size and a comparator
     * @param size Int initial size of the array
     * @param comparator Default comparator to be used in sort
     */
    public Sequence(int size, Comparator<E> comparator){
        if (size  > 0){
            initialSize = size;
            array = (E[]) new Object[size];
        } else {
            throw new NegativeArraySizeException("Cannot use size of 0 or less");
        }
        defaultComparator = comparator;
    }

    /**
     * Allows for specification of just the growth rate
     * @param growthRate Double initial growth rate of the array
     */
    public Sequence(double customGrowthRate){
        if (customGrowthRate >  1.0){
            this.growthRate = customGrowthRate;
        } else {
            throw new IllegalArgumentException("Cannot have a growth rate of 1 or less than 1");
        }
    }

    /**
     * Allows for specification of growth rate and default comparator
     * @param growthRate Double initial growth rate of the array
     * @param comparator Default comparator to be used in sort
     */
    public Sequence(double customGrowthRate, Comparator<E> comparator){
        if (customGrowthRate >  1.0){
            this.growthRate = customGrowthRate;
        } else {
            throw new IllegalArgumentException("Cannot have a growth rate of 1 or less than 1");
        }
        defaultComparator = comparator;
    }

    /**
     * Allows for specification of both starting size and growth rate
     * @param size Int starting size of the array
     * @param growthRate Double initial growth rate of the array
     */
    public Sequence(int size, double customGrowthRate){
        if (size  > 0){
            initialSize = size;
            array = (E[]) new Object[size];
        } else {
            throw new NegativeArraySizeException("Cannot use size of 0 or less");
        }
        if (customGrowthRate >  1.0){
            this.growthRate = customGrowthRate;
        } else {
            throw new IllegalArgumentException("Cannot have a growth rate of 1 or less than 1");
        }
    }

    /**
     * Allows for specification of starting size, growth rate and a comparator
     * @param size Int starting size of the array
     * @param growthRate Double initial growth rate of the array
     * @param comparator Default comparator to be used in sort
     */
    public Sequence(int size, double customGrowthRate, Comparator<E> comparator){

        if (size  > 0){
            initialSize = size;
            array = (E[]) new Object[size];
        } else {
            throw new NegativeArraySizeException("Cannot use size of 0 or less");
        }
        if (customGrowthRate >  1.0){
            this.growthRate = customGrowthRate;
        } else {
            throw new IllegalArgumentException("Cannot have a growth rate of 1 or less than 1");
        }
        defaultComparator = comparator;
    }

    /* 
        ====================================================
                                END                                      
        ====================================================
    */

    // TODO: isFull?
    // TODO: strictEquals?
    // TODO: Remove Dupilicates

    /*
     * ======================================================
     *                  ARRAY OPERATIONS
     * ======================================================
     */

    /**
     * Inserts a term at a specific index. Rest of the list gets shuffled
     * @param index index to insert the value at
     * @param value value to be inserted
     */
    public void insert(int index, E value){

        // Calculate index we want to insert at
        int insertionIndex = startPointer + index;

        // Check if index is out of bounds
        if (insertionIndex > endPointer){
            throw new IndexOutOfBoundsException("Cannot insert into index outside of list");
        } else if (insertionIndex < startPointer) {
            throw new IndexOutOfBoundsException("Cannot insert into negative index");
        } else {
            E curTerm;
            E nextTerm = value; // first value to insert is value
            if (rawLength() > endPointer+1){
                for (int i = insertionIndex; i < endPointer+1; i++){
                    // Set our term to be inserted in this run
                    curTerm = nextTerm;
                    // Save the current term in that spot
                    nextTerm = array[i];
                    // Overwrite
                    array[i] = curTerm;
                }
            } else {
                // Use reformat as expand() doesnt change current array
                reformat(true);
                for (int i = insertionIndex; i < endPointer+1; i++){
                    // Set our term to be inserted in this run
                    curTerm = nextTerm;
                    // Save the current term in that spot
                    nextTerm = array[i];
                    // Overwrite
                    array[i] = curTerm;
                }
            }
            endPointer++; // Adjust as we have expanded by 1
        }
    }

    /**
     * Add an item to the array. Automatically deals with expansion of the array
     * @param item The item to add
     */
    public void append(E item){

        if (!enforceSort){
            // Just regular append operation when enforcing
            if (item != null){
                addToEnd(item);
            } else {
                throw new IllegalArgumentException("null not allowed for append");
            }
        } else if (enforceSort){
            // Sorted append operation
            if (item != null){
                if (length() == 0){ // No point sorting on the first insert
                    addToEnd(item);
                } else {
                    /*
                    * First calculate the index for the term to be inserted into (Binary search)
                    * Then use the insert operation to do this
                    */

                    int index = BinarySearch.findInsertionIndex(array, startPointer, endPointer, item, defaultComparator);
                    int appendIndex = index-startPointer; // Convert from subarray index
                    insert(appendIndex, item);

                    // addToEnd(item);
                    // sort();
                }
            } else {
                throw new IllegalArgumentException("null not allowed for append");
            }
        }
    }

    /**
     * Add all terms from the given Sequence to this Sequence
     * @param toApp The Sequence to add from
     */
    public void appendAll(Sequence<E> toApp) {

        for (int i = 0; i < toApp.length(); i++) {
            append(toApp.get(i));
        }

    }

    /**
     * Replace a specifc item in the array
     * @param value The value to insert
     * @param index The index to insert at
     */
    public void replace(int index, E value) {

        // Force null check
        if (value == null){
            throw new IllegalArgumentException("null not allowed for replace");
        } else {
            // Adjust in case 0 is not the starting point
            int indexToAdjust = startPointer+index;

            if (indexToAdjust > endPointer || index < 0){ // If in unset positions
                throw new ArrayIndexOutOfBoundsException("Index out of bounds");
            } else { // All good
                array[startPointer+index] = value;
            }
        }

    }

    /**
     * Remove an element by index
     * @param index Index of the element to remove
     */
    public void remove(int index){

        int indexToAdjust = startPointer+index;
        if (indexToAdjust > endPointer || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Index out of bounds");
        } else {

            array[indexToAdjust] = null;
            reformat(false);
        }
    }

    /**
     * Gets a specific element from the array
     * @param index The index of the element 
     * @return The element
     */
    public E get (int index){

        // Adjust in case 0 is not the starting point
        int indexToAdjust = startPointer+index;

        if (indexToAdjust > endPointer || index < 0){ // If in unset positions
            throw new ArrayIndexOutOfBoundsException("Index out of bounds");
        } else { // All good
            return array[indexToAdjust];
        }

    }

    /*
     * OVERLOADED SORT() FUNCTION
     */

    /**
     * Sorts the array based on the field defaultComparator. Must be set
     * This will overwrite the current array with the sorted version
     * Use a comparator for a returnable type
     */
    public void sort(){

        if (defaultComparator != null){
            // Calculate the length of the array that contains terms
            int arrSize = endPointer - startPointer;

            // Generate a temporary array of that length
            E[] tempArr = (E[]) new Object[arrSize];

            // Add all terms over skipping nulls
            int addCount = 0;
            for (int i = 0; i < endPointer; i++){
                if (array[i] == null){
                    continue;
                } else {
                    tempArr[addCount] = array[i];
                    addCount++;
                }
            }

            try {

                // Sort the array
                Arrays.sort(tempArr, defaultComparator);

                Arrays.fill(array, null); // Null out original to maintain terms
                System.arraycopy(tempArr, 0, array, 0, endPointer-startPointer); // Copy over

            } catch (Exception e){ // Throw error for either not implementing Comparable or smth else
                System.err.println(e);
                throw new UnknownError("Comparator incorrect or not set");
            }
        } else {
            throw new IllegalStateException("Default comparator must be set to use this sort");
        }
    }

    /**
     * Sort the array and then enforce sort from then onwards
     */
    public void sortOnwards() {
        if (!(length() < 0) && !(length() == 1)){ // Only sort when we have something to not nothing or 1 item
            sort();
        }
        setEnforceSort(true);
    }

    /**
     * Sorts the array based on the comarator given and returns a new Copy.
     * This method does not overwrite the current array
     * 
     * @param comparator The comparator for comparing the types
     * @return A sorted Sequence
     */
    public Sequence<E> sortCopy(Comparator<E> comparator){

        // Calculate the length of the array that contains terms
        int arrSize = endPointer - startPointer;

        // Generate a temporary array of that length
        E[] tempArr = (E[]) new Object[arrSize];

        // Add all terms over skipping nulls
        int addCount = 0;
        for (int i = 0; i < endPointer; i++){
            if (array[i] == null){
                continue;
            } else {
                tempArr[addCount] = array[i];
                addCount++;
            }
        }

        try {
            Arrays.sort(tempArr, comparator);
            Sequence<E> temp = new Sequence<>();
            temp.setSubArray(startPointer, endPointer, tempArr);
            return temp; // Return a sequence
        } catch (Exception e){ // Throw error for either not implementing Comparable or smth else
            System.err.println(e);
            throw new UnknownError("Comparator either not valid or cannot be compared");
        }
        

    }

    /**
     * Stop sorting the array automatically
     */
    public void stopSorting() {
        setEnforceSort(false);
    }

    /*
     * END OF OVERLOADED SORT() FUNCTION
     */

    /**
      * Set the comparator to be used
      * @param comparator
      */
      public void setComparator (Comparator<E> comparator){
        defaultComparator = comparator;
    }

    /**
     * Set the boolean flag enforceSort
     * @param value Boolean value to set the field
     */
    private void setEnforceSort (boolean value){

        // If no comparator is set we cannot enforce a sort
        if (defaultComparator == null){
            throw new IllegalStateException("Cannot enforce sort without a comparator set");
        }

        enforceSort = value;
    }

    /**
     * Get the current value for the boolean flag enforceFunctionality
     * @return The value of enforceFunctionality
     */
    private boolean getEnforceSort(){
        return enforceSort;
    }

    /*
     * ======================================================
     *                  END ARRAY OPERATIONS
     * ======================================================
     */

    /*
     * ======================================================
     *                    QUEUE FUNCTIONS
     * ======================================================
     */

    /**
     * Is the queue empty
     * @return true if queue is empty, false if queue has terms
     */
    public boolean isEmpty(){

        if (length() == 0){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Dequeue an item
     * @return The item that has  been dequeued
     */
    public E dequeue() {

        if (length() > 0){
            E temp = array[startPointer];
            array[startPointer] = null;
            startPointer++;
            return temp;
        } else {
            throw new NullPointerException("No items to dequeue");
        }
    }

    /**
     * Enqueue an item at the end of the queue
     * Functionaly the same as append. There is no difference
     * This means that when sortOnwards then it will insert into place
     * 
     * @param item The item to enqueue
     */
    public void enqueue(E item){

        if (item != null){
            append(item);
        } else {
            throw new IllegalArgumentException("null not allowed for append");
        }

    }

    /*
     * ======================================================
     *                END OF QUEUE FUNCTIONS
     * ======================================================
     */

    /*
     * ======================================================
     *                   STACK FUNCTIONS
     * ======================================================
     */

    /**
     * Push an item onto the stack
     * Functionally the same as append. Will push into place if
     * sortOnwards is set
     * 
     * @param item The item to be pushed
     */
    public void push (E item) {
        
        if (item != null){
            append(item);
        } else {
            throw new IllegalArgumentException("null not allowed for append");
        }
        

    }

    /**
     * Pop an item off of the stack
     * @return The item popped off of the stack
     */
    public E pop () {

        if(endPointer != 0){
            E temp = array[endPointer-1];
            endPointer--;
            array[endPointer] = null;
            return temp;
        } else if (endPointer == 0){
            throw new ArrayIndexOutOfBoundsException("Nothing to pop");
        } else {
            throw new RuntimeException("Cannot pop, check code");
        }
    }

    /*
     * ======================================================
     *                END OF STACK FUNCTIONS
     * ======================================================
     */

    /*
     * ======================================================
     *              MULTI PURPOSE FUNCTIONS
     * ======================================================
     */

    /**
     * Peek at the next item in the queue or stack depending on the enum
     * @param acting Enum of type HowToFunction being either QUEUE or STACK
     * @return The next item in the queue
     */
    public E peek(HowToFunction acting) {

        if (length() > 0){
            switch (acting){
                case STACK:
                    return array[endPointer-1];
                case QUEUE: 
                    return array[startPointer];
                default:
                    throw new IllegalArgumentException("Not an enum allowed with peek. Choose STACK/QUEUE");
            } 
        }
        else {
            throw new NullPointerException("No item to peek");
        }
    }

    /*
     * ======================================================
     *            END OF MULTI PURPOSE FUNCTIONS
     * ======================================================
     */

    /*
     * ============================================
     *          GENERAL PURPOSE FUNCTIONS
     * ============================================
     */

    /**
     * Alias for length method.
     * Size typically used by stack
     * @return The size of the stack
     */
    public int size(){
        return length();
    }

    /**
     * Returns the length of a given sequence
     * 
     * @return Int length of sequence
     */
    public int length(){
    
        // Length can be worked out via the difference between the startIndex and endIndex with removed taken off to track non existant terms.
        return endPointer - startPointer - removed;
    }

    /**
     * Clears the array. New arrays size is initialSize
     */
    public void clear(){
        E[] newArray = (E[]) new Object[initialSize];
        array = newArray;
        startPointer = 0;
        endPointer = 0;
    }

    /**
     * Alias for clear method.
     * Empty typically used with stack
     */
    public void empty() {
        clear();
    }

    /**
     * Are these two sequences identical. Objects must be at the same index and same length
     * 
     * !! IMPORTANT
     *      Does not check the "settings" of the Sequence.
     *      Not Checked:
     *          - Comparator
     *          - EnforceSort
     *          - CustomGrowthRate
     *          - InitialSize
     *      Checked:
     *          - Same length
     *          - Same terms at each index
     * 
     * @return boolean true if same / false if not
     */
    @Override
    public boolean equals(Object seq){

        if (this == seq){ // If memory address equal they are the same
            return true;
        } else if (seq instanceof Sequence<?>){

            // Convert to correct type
            Sequence<E> comp = (Sequence<E>) seq;

            // Do they have the same length
            if (length() != comp.length()){
                return false;
            } else { 
                for (int i = 0; i < length(); i++){ // Check each term now
                    if (get(i).equals(comp.get(i))){
                        continue; // Equal so can continue
                    } else { // If one term incorrect then not the same
                        return false;
                    }
                }
            }

            
        } else {
            // If they aren't the same memory location or same type then false
            return false;
        }
        
        // If we make it through they are equal
        return true;
    }

    /**
     * Custom toString method for generics.
     * 
     * @return String form of the array [term1, term2, term3]
     */
    @Override
    public String toString(){

        StringBuilder sb = new StringBuilder();

        // Add the beginning bracket
        sb.append("[");

        // As long as more than one element
        if (length() != 0){
            // Add all terms to the StringBuilder
            for (int i = startPointer; i < endPointer; i++){
                if (array[i] != null){
                    sb.append(array[i] + ", ");
                }
            }
            // Find and remove last comma and space
            int lastComma = sb.lastIndexOf(",");
            sb.delete(lastComma, lastComma+2);
        }

        // Add the final bracket
        sb.append("]");

        // Return the string
        return sb.toString();
    }

    /**
     * Returns true if item is contained and false if not
     * 
     * !!! Objects must override equals to use this function properly
     *     otherwise objects will be compared on object referrence
     * 
     * @param value The value to check for
     * @return true if yes, false if no
     */
    public boolean contains(E value){

        if (!enforceSort){ // If the array is not sorted
            for (int i = startPointer; i < endPointer; i++){
                if (value.equals(array[i])){ // If same value then return true
                    return true;
                } else {
                    continue;
                }
            }
        } else if (enforceSort){ // If sorted then we can search quicker
            //TODO: Swap out for a different form of search
            int index = BinarySearch.findInsertionIndex(array, startPointer, endPointer, value, defaultComparator);

            // Now check if the term at that position is what we are expecting (This is due to the way findInsertionIndex works)
            if (array[index].equals(value)){
                return true;
            } else {
                return false;
            }
        }
        

        // If get here then value not contained
        return false;
    }

    /**
     * Returns the first index of a given value in the array
     * 
     * !!! Objects must override equals to use this function properly
     *     otherwise objects will be compared on object referrence
     * 
     * @param value The value to check for
     * @return The index if item is there or null if it isnt
     */
    public Integer firstIndexOf(E value){

        // Search over all terms and check their equivalence to ours
        for (int i = startPointer; i < endPointer; i++){
            if (value.equals(array[i])){ // If same value then return true
                return i;
            } else {
                continue;
            }
        }

        // If we get here no terms match
        return null;
    }

    /**
     * Returns all indexes of a given element
     * 
     * !!! Objects must override equals to use this function properly
     *     otherwise objects will be compared on object referrence
     * 
     * @param value The value to search for
     * @return A Integer[] containing all the indexes that the value occurs at
     */
    public int[] allIndexesOf(E value){

        // Create an array of length total
        int[] loopList = new int[length()];

        // Each time we add one we increment this
        int count = 0;

        // Search over all terms and check their equivalence to ours
        for (int i = startPointer; i < endPointer; i++){
            if (value.equals(array[i])){ // If same value then add to array and increment
                loopList[count] = i;
                count++;
            } else {
                continue;
            }
        }

        if (count > 0){
            // Create a new array being the size we have found
            int[] returnable = new int[count];
            // Copy over to the new array of correct size
            System.arraycopy(loopList, 0, returnable, 0, count);
            return returnable;
        }

        return null;
    }

    /*
     * ============================================
     *      END OF GENERAL PURPOSE FUNCTIONS
     * ============================================
     */

    /*
     * SYSTEM FUNCTIONS
     */

    /**
     * Add an item to the end of the array correctly
     * @param item
     */
    private void addToEnd(E item){
        if (endPointer == array.length){ // We must expand the array
            // Expand the array
            reformat(true);

            // Now add the new item
            array[endPointer] = item;
            endPointer++;

        } else { // No need to expand array
            array[endPointer] = item;
            endPointer++;
        }
    }

     /**
     * Create and return a blank array of new size
     * @return Array of new size
     */
    private E[] expand(){
        // Check for minimum growth requirement of 1
        int length = array.length;
        int newSize = (int) Math.ceil(length*growthRate);

        if (newSize <= length){
            newSize = newSize + minumumExpansion;
        }
        // New array is created by expanding by growthFactor
        int newLen = (int) Math.round(newSize);
        E[] newArray = (E[]) new Object[newLen];
        return newArray;
    }

    /**
     * Expand the array by the given growth factor.
     * Check for null items and remove
     * @param expand Boolean telling whether to expand or not
     */
    private void reformat(boolean  expand){

        E[] newArray;

        if (expand == true){
            // If we choose to expand run the function expand
            newArray = expand();
        } else {
            // If we dont want to expand the array but just re-format
            newArray = (E[]) new Object[array.length];
        }
        

        // Now loop over and add all items to the larger list
        int secondCount = 0; // This counts from index 0 for the new array
        for (int i = startPointer; i < endPointer-removed; i++){ // This counts from index startPointer for the old array

            // Get current term from the array
            E curTerm = array[i];

            if (curTerm == null){
                // Skip as term not needed
                continue;
            } else {
                newArray[secondCount] = curTerm;
                secondCount++;
            }
    
        }

        // Now set array to newArray
        array = newArray;
        endPointer = length(); // Set to current length after expansion
        startPointer = 0;
        removed = 0; // No items have been removed from list currently
    }

    /*
     * END OF SYSTEM FUNCTIONS
     */

    /*
     * ================================================
     *             EXPERT USER FUNCTIONS
     * ================================================
     */

    /**
     * Returns the length of the underlying array not where terms are
     * @return Int being the raw length of the array
     */
    public int rawLength(){
        return array.length;
    }

    /**
     * Returns the array including null positions
     * @return String containing full length of the array not just what is being worked on
     */
    public String rawString(){
        StringBuilder sb = new StringBuilder();

        // Add the beginning bracket
        sb.append("[");

        // Add all terms to the StringBuilder
        for (int i = 0; i < array.length; i++){
            sb.append(array[i] + ", ");
        }
        // Find and remove last comma and space
        int lastComma = sb.lastIndexOf(",");
        sb.delete(lastComma, lastComma+2);

        // Add the final bracket
        sb.append("]");

        // Return the string
        return sb.toString();
    }

    /**
     * Set a custom growth rate for the array
     * @param newRate The new growth rate
     */
    public void setGrowthRate(Double newRate){
        if (newRate >  1.0){
            this.growthRate = newRate;
        } else {
            throw new IllegalArgumentException("Cannot have a growth rate of 1 or less than 1");
        }
    }

    /**
     * Get the growth rate Sequence is using
     * @return Double growth rate
     */
    public double getGrowthRate(){
        return growthRate;
    }

    /**
     * Retrieve the sub array
     * @return
     */
    public E[] getSubArray(){
        return array;
    }

    /**
     * Allow the setting of the sub array through passing in parameters
     * @param startPointer
     * @param endPointer
     * @param array
     */
    public void setSubArray(int startPointer, int endPointer, E[] array){
        this.startPointer = startPointer;
        this.endPointer = endPointer;
        this.array = array;
    }

    /*
     * ================================================
     *          END OF EXPERT USER FUNCTIONS
     * ================================================
     */

    public Iterator<E> iterator() {
        return new SequenceIterator();
    }

    class SequenceIterator implements Iterator<E> {
            
            private int index = startPointer;
    
            @Override
            public boolean hasNext() {
                return index < endPointer;
            }
    
            @Override
            public E next() {
                if (!hasNext()) {
                    throw new IndexOutOfBoundsException("No more elements to iterate over");
                }
                return array[index++];
            }
    }

    // Enum to allow the user to specify functionality
    public enum HowToFunction {
        STACK, // Can only function as a stack
        QUEUE // Can only function as a queue
    }
} 