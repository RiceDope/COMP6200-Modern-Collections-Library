package com.rwalker;

// Junit tings
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class QueueTest {
    
    /**
     * Test adding 10 items to a queue
     * order = [5, 10, 15, 20, 25, 30, 35, 40, 45, 50]
     */
    @Test
    public void testEnqueue(){
        Sequence<Integer> testing = TestUtils.generateEnqueueTenItems();
        assertEquals("[5, 10, 15, 20, 25, 30, 35, 40, 45, 50]", testing.toString());
    }

    /**
     * Test dequeuing and check the output
     */
    @Test
    public void testDequeue(){
        Sequence<Integer> testing = TestUtils.generateEnqueueTenItems();
        int out = testing.dequeue();
        assertEquals(5, out);
        out = testing.dequeue();
        assertEquals(10, out);
    }

    /**
     * Test dequeuing on an empty queue
     */
    @Test
    public void testEmptyDequeue(){
        Sequence<Integer> testing = TestUtils.generateEmptySequence();
        assertEquals(null, testing.dequeue());
    }

    /**
     * Test peek twice in order to check it doesnt remove and stays consistent
     */
    @Test
    public void testPeek(){
        Sequence<Integer> testing = TestUtils.generateEnqueueTenItems();
        int out = testing.peek();
        assertEquals(5, out);
        out = testing.peek();
        assertEquals(5, out);
    }
}