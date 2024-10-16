/**
 * Tests the stack functionality of sequence
 * @author Rhys Walker
 */
package com.rwalker;

// Junit tings
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class StackTest {
    
    /**
     * Test pushing items
     * @throws NoSuchMethodException
     */
    @Test
    public void testPush() throws NoSuchMethodException{
        Sequence<Integer> test = new Sequence<Integer>();
        test.push(10);
        test.push(20);
        assertEquals("[10, 20]", test.toString());
    }

    /**
     * Test poping items
     * @throws NoSuchMethodException
     */
    @Test
    public void testPop() throws NoSuchMethodException{
        Sequence<Integer> test = new Sequence<Integer>();
        test.push(10);
        test.push(20);
        assertEquals(20, (int) test.pop());
        assertEquals(10, (int) test.pop());
    }

    /**
     * Test that IllegalStateException is thrown upon enforcing sort
     * @throws NoSuchMethodException
     */
    @Test(expected = IllegalStateException.class)
    public void testEnforceSortPush() throws NoSuchMethodException{
        Sequence<Integer> test = new Sequence<Integer>();
        test.setEnforce(true);
        test.setFunctionality(HowToFunction.SORTED);
        test.push(10);
    }

    /**
     * Test that IllegalStateException is thrown upon enforcing sort
     * @throws NoSuchMethodException
     */
    @Test(expected = IllegalStateException.class)
    public void testEnforceSortPop() throws NoSuchMethodException{
        Sequence<Integer> test = new Sequence<Integer>();
        test.push(10);
        test.setEnforce(true);
        test.setFunctionality(HowToFunction.SORTED);
        test.pop();
    }

    /**
     * Test pop on an empty list throws error
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testPopEmptyList() {
        Sequence<Integer> test = new Sequence<Integer>();
        test.pop();
    }

    /**
     * Test peek on the stack
     */
    @Test
    public void testPeek() throws NoSuchMethodException{
        Sequence<Integer> test = new Sequence<Integer>();
        test.push(10);
        test.push(20);
        assertEquals(20, (int) test.peek(HowToFunction.STACK));
    }

    /**
     * Test peek on an empty list
     */
    @Test(expected = NullPointerException.class)
    public void testPeekEmpty() {
        Sequence<Integer> test = new Sequence<Integer>();
        test.peek(HowToFunction.STACK);
    }

    /**
     * Test the empty method for stack
     * @throws NoSuchMethodException
     */
    @Test
    public void testEmpty() throws NoSuchMethodException{
        Sequence<Integer> test = new Sequence<Integer>();
        test.push(10);
        test.push(20);
        test.empty();
        assertEquals("[]", test.toString());
    }

    /**
     * Test the size method for stack
     * @throws NoSuchMethodException
     */
    @Test
    public void testSize() throws NoSuchMethodException{
        Sequence<Integer> test = new Sequence<Integer>();
        test.push(10);
        test.push(20);
        test.size();
        assertEquals(2, test.size());
    }
}
