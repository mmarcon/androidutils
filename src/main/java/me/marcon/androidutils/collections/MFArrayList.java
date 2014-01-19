package me.marcon.androidutils.collections;

import java.util.ArrayList;
import java.util.Collection;

/**
 * An extension of the {@link java.util.ArrayList ArrayList} class
 * that exposes the JavaScript-like methods <code>map</code> and <code>filter</code>.
 *
 * @author mmarcon
 * @version 1.0.0
 */
public class MFArrayList<E> extends ArrayList<E> {

    private static final long serialVersionUID = 7281673743494991943L;

    /**
     * @see java.util.ArrayList#ArrayList()
     */
    public MFArrayList(){
        super();
    }

    /**
     * @see java.util.ArrayList#ArrayList(int)
     */
    public MFArrayList(int capacity){
        super(capacity);
    }

    /**
     * @see java.util.ArrayList#ArrayList(java.util.Collection)
     */
    public MFArrayList(Collection<? extends E> collection) {
        super(collection);
    }

    /**
     * Returns a MFArrayList where the size is the same as
     * the instance of MFArrayList this method is called on
     * and the contained values correspond to the values in
     * the original list passed through a mapping function.
     *
     * @param mapper a class that implements the mapping function
     * @return a new MFArrayList that contains the mapped values.
     */
    public <T> MFArrayList<T> map(Mapper<E, T> mapper) {
        MFArrayList<T> result = new MFArrayList<T>();
        for(E item : this) {
            result.add(mapper.map(item));
        }
        return result;
    }

    /**
     * Returns a MFArrayList that only contains the values of the
     * original list that passes the test implemented in a filtering
     * function.
     *
     * @param filter a class that implements the filtering function,
     * @return a list that contains only the values that pass the test
     */
    public MFArrayList<E> filter(Filter<E> filter) {
        MFArrayList<E> result = new MFArrayList<E>();
        for(E item : this) {
            if(filter.filter(item)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Maps an object to another object.
     *
     * @param <E> original type
     * @param <T> resulting type
     */
    public static abstract class Mapper<E, T> {
        /**
         * {@link me.marcon.androidutils.collections.MFArrayList#map(me.marcon.androidutils.collections.MFArrayList.Mapper)}  MFArrayList.map(E)}
         * calls this method for each fo the elements in the list.
         *
         * @param item the item that has to be mapped
         * @return the result of the mapping
         */
        public abstract T map(E item);
    }

    /**
     * Filters a MFArrayList.
     *
     * @param <E> the type of objects that are in the MFArrayList
     */
    public static abstract class Filter<E>{
        /**
         * {@link me.marcon.androidutils.collections.MFArrayList#filter(me.marcon.androidutils.collections.MFArrayList.Filter)}  MFArrayList.filter(E)}
         * calls this method for each fo the elements in the list.
         *
         * @param item the item that has to be tested
         * @return true if the items should be in the resulting list, false otherwise.
         */
        public abstract boolean filter(E item);
    }
}
