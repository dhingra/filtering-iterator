package com.pimco.poc.iterator;

import function.IObjectTest;

import java.util.*;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * @author rohitdhingra
 */
public class FilteringIteratorTest {
    private IObjectTest<Integer> evenPredicate;
    private Iterator<Integer> evenFilteringIterator ;
    private List<Integer> evenList;

    @Before
    public void setUp(){
        evenPredicate = (i) -> i%2 == 0 ;
        evenList = Arrays.asList(0,1,24,33,42,5,64,27,83,29,50);
    }


    @Test
    public void filterEvenNumbers(){
        int arr[] = {0,24,42,64,50};
        int k = 0;
        evenFilteringIterator = new FilteringIterator<>(evenList.iterator(),evenPredicate);
        while(evenFilteringIterator.hasNext() ) {
            assertEquals(Integer.valueOf(arr[k]), evenFilteringIterator.next());
            k++;
        }
    }



    @Test(expected=NoSuchElementException.class)
    public void filteringIteratorThrowsExceptionWhenIteratorHasNoMoreElements(){
        evenFilteringIterator = new FilteringIterator<>(evenList.iterator(),evenPredicate);
        while(evenFilteringIterator.hasNext() ) {
            evenFilteringIterator.next();
        }
        evenFilteringIterator.next();
    }

    @Test
    public void filteringIteratorReturnsFalseWhenIteratorHasNoMoreElements(){
        evenFilteringIterator = new FilteringIterator<>(evenList.iterator(),evenPredicate);
        while(evenFilteringIterator.hasNext() ) {
            evenFilteringIterator.next();
        }
        assertEquals(false,evenFilteringIterator.hasNext());
    }

    @Test
    public void nextInvokedWithoutHasNext(){
        final List<Integer> list = Arrays.asList(5,1,24,33,42,5,64,27,83,29,50);
        final Iterator<Integer> it = new FilteringIterator<>(list.iterator(),evenPredicate);
        assertEquals(new Integer(24), it.next());
    }

    /**
     *
     */
    @Test(expected=NoSuchElementException.class)
    public void filteringIteratorThrowsExceptionWhenIteratorNeverHadElements(){
        final List<Integer> list = new ArrayList<>();
        final Iterator<Integer> it = new FilteringIterator<>(list.iterator(),evenPredicate);
        it.next();
    }

    @Test
    public void testRemoveRemovesElementFromUnderlyingCollection(){
    	
    	int elementToRemove=36;
        final List<Integer> list = new ArrayList<>(Arrays.asList(5,1,24,elementToRemove,42,5,64,27,83,29,50));
        
        assertEquals(true,list.contains(elementToRemove));
        
        int initialSize=list.size();
        final Iterator<Integer> it = new FilteringIterator<>(list.listIterator(),evenPredicate);
        while(it.hasNext() ) {
            int k = it.next();
            if (elementToRemove == k){ 
                it.remove();
            }
        }
        
        assertEquals(initialSize-1, list.size());
        assertFalse(list.contains(elementToRemove));
    }

    @Test
    public void testRemoveRemovesAllInstancesOfElementFromUnderlyingCollection(){
    	
    	int elementToRemove=36;
        final List<Integer> list = new ArrayList<>(Arrays.asList(5,1,24,elementToRemove,42,5,64,27,elementToRemove,83,29,elementToRemove,50,elementToRemove));
        
        assertEquals(true,list.contains(elementToRemove));
        
        int initialSize=list.size();
        final Iterator<Integer> it = new FilteringIterator<>(list.listIterator(),evenPredicate);
        while(it.hasNext() ) {
            int k = it.next();
            if (elementToRemove == k){ 
                it.remove();
            }
        }
        
        assertEquals(initialSize-4, list.size());
        assertFalse(list.contains(elementToRemove));
    }

    @Test
    public void testRemoveRetainsOtherInstancesIfOnlyFirstIsRemoved(){
    	
    	int elementToRemove=36;
        final List<Integer> list = new ArrayList<>(Arrays.asList(5,1,24,elementToRemove,42,5,64,27,elementToRemove,83,29,elementToRemove,50,elementToRemove));
        final List<Integer> expectedList = new ArrayList<>(Arrays.asList(5,1,24,42,5,64,27,elementToRemove,83,29,elementToRemove,50,elementToRemove)); //expected 

        assertEquals(true,list.contains(elementToRemove));
        
        int initialSize=list.size();
        final Iterator<Integer> it = new FilteringIterator<>(list.listIterator(),evenPredicate);
        while(it.hasNext() ) {
            int k = it.next();
            if (elementToRemove == k){ 
                it.remove();
                break;
            }
        }
        
        assertEquals(initialSize-1, list.size());
        assertEquals(true,list.contains(elementToRemove));
        assertEquals(expectedList,list);

    }

}
