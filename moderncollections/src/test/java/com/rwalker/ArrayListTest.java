package com.rwalker;

/**
 * Tests for the ArrayList functionality of Sequence
 * 
 * @author Rhys Walker
 */

// Junit tings
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Unit test for ArrayList functionality of Sequence
 */
public class ArrayListTest {

    /**
     * Test that adding the 10 items has worked and length function
     */
    @Test
    public void testPopulation() throws NoSuchMethodException{
        Sequence<Integer> testing = TestUtils.addTenInts(TestUtils.generateEmptySequence());
        assertEquals(10, testing.length());
    }

    /**
     * Test removal of two items from the list
     */
    @Test
    public void testRandomRemoval() {
        Sequence<Integer> testing = TestUtils.addTenInts(TestUtils.generateEmptySequence());
        testing.remove(4);
        testing.remove(7);
        assertEquals("[100, 52, 250, 5, 1052, 9, 100, 52]", testing.toString());
    }

    /**
     * Test getting the 1st integer
     */
    @Test
    public void testGet() {
        Sequence<Integer> testing = TestUtils.generateFullSmallSequence();
        int out = testing.get(1);
        assertEquals(2, out);
    }

    /**
     * Test get with negative index
     */
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testGetNegative() {
        Sequence<Integer> testing = TestUtils.generateFullSmallSequence();
        testing.get(-1);
    }

    /**
     * Test get with out of bounds index
     */
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testGetOutOfBounds() {
        Sequence<Integer> testing = TestUtils.generateFullSmallSequence();
        testing.get(100);
    }

    /**
     * Test clearing the array
     */
    @Test
    public void testClear(){
        Sequence<Integer> testing = TestUtils.addTenInts(TestUtils.generateEmptySequence());
        testing.clear();
        assertEquals("[]", testing.toString());
    }

    /**
     * Test insertion at multiple points in the array
     * @throws NoSuchMethodException
     */
    @Test
    public void testInsert() throws NoSuchMethodException{
        Sequence<Integer> testing = new Sequence<Integer>();
        testing.append(5);
        testing.append(10);
        testing.append(15);
        testing.append(20);
        testing.insert(0, 100);
        assertEquals("[100, 5, 10, 15, 20]", testing.toString());
        testing.insert(2, 50);
        assertEquals("[100, 5, 50, 10, 15, 20]", testing.toString());
        testing.insert(6, 50);
        assertEquals("[100, 5, 50, 10, 15, 20, 50]", testing.toString());
    }

    /**
     * Test insertion into the array where it is out of bounds
     * @throws NoSuchMethodException
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testOutOfBoundsInsertion() throws NoSuchMethodException{
        Sequence<Integer> testing = new Sequence<Integer>();
        testing.append(5);
        testing.append(10);
        testing.append(15);
        testing.append(20);
        testing.insert(10, 100);
    }

    /**
     * Test that the sub-array expands when inserting into a full array
     * @throws NoSuchMethodException
     */
    @Test
    public void testArrayExpansionWithInsert() throws NoSuchMethodException {
        Sequence<Integer> testing = new Sequence<Integer>(4);
        testing.append(5);
        testing.append(10);
        testing.append(15);
        testing.append(20);
        assertEquals(4, testing.rawLength());
        testing.insert(2, 100);
        assertEquals(6, testing.rawLength());
        assertEquals("[5, 10, 100, 15, 20]", testing.toString());
    }

    /**
     * Test that we can insert into position 0 of the array
     */
    @Test
    public void testInsertingIntoZeroEmptyArray(){
        Sequence<Integer> testing = new Sequence<Integer>(4);
        testing.insert(0, 10);
        assertEquals("[10]", testing.toString());
    }

    /**
     * Test attempting to insert into a negative index
     */
    @Test(expected=IndexOutOfBoundsException.class)
    public void testInsertingIntoNegativePosition(){
        Sequence<Integer> testing = new Sequence<Integer>();
        testing.insert(-1, 100);
    }

    /**
     * Test that the replace method works
     */
    @Test
    public void testReplace(){
        Sequence<Integer> testing = new Sequence<>();
        testing.append(10);
        testing.append(20);
        testing.replace(0, 50);
        assertEquals("[50, 20]", testing.toString());
    }

    /**
     * Test that inserting negative throws an error
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testReplaceNegativeIndex(){
        Sequence<Integer> testing = new Sequence<>();
        testing.replace(-1, 10);
    }

    /**
     * Test that inserting out of bounds throws an error
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testReplaceOutOdBounds(){
        Sequence<Integer> testing = new Sequence<>();
        testing.replace(10, 10);
    }

    /**
     * Test remove function normally
     */
    @Test
    public void testRemove(){
        Sequence<Integer> testing = new Sequence<>();
        testing.append(10);
        testing.append(20);
        testing.remove(0);
    }

    /**
     * Test remove function with negative index
     */
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testRemoveNegative(){
        Sequence<Integer> testing = new Sequence<>();
        testing.append(10);
        testing.append(20);
        testing.remove(-1);
    }

    /**
     * Test remove function with out of bounds index
     */
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testRemoveOutOfBounds(){
        Sequence<Integer> testing = new Sequence<>();
        testing.append(10);
        testing.append(20);
        testing.remove(10);
    }

    /**
     * Test the append all method
     */
    @Test
    public void testAppendAll() {
        Sequence<Integer> testing = new Sequence<>();
        testing.append(10);
        testing.append(20);
        testing.append(30);
        Sequence<Integer> toAdd = new Sequence<>();
        toAdd.append(40);
        toAdd.append(50);
        toAdd.append(60);
        testing.appendAll(toAdd);
        assertEquals("[10, 20, 30, 40, 50, 60]", testing.toString());
    }
}
