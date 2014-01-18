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

    public MFArrayList(){
        super();
    }

    public MFArrayList(int capacity){
        super(capacity);
    }

    public MFArrayList(Collection<? extends E> collection) {
        super(collection);
    }

    public <T> MFArrayList<T> map(Mapper<E, T> mapper) {
        MFArrayList<T> result = new MFArrayList<T>();
        for(E item : this) {
            result.add(mapper.map(item));
        }
        return result;
    }

    public MFArrayList<E> filter(Filter<E> filter) {
        MFArrayList<E> result = new MFArrayList<E>();
        for(E item : this) {
            if(filter.filter(item)) {
                result.add(item);
            }
        }
        return result;
    }

    public static abstract class Mapper<E, T>{
        public abstract T map(E item);
    }

    public static abstract class Filter<E>{
        public abstract boolean filter(E item);
    }
}
