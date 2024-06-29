package arrayAndLinkedList;

import java.util.Comparator;

/*
 *  Методы – добавить элемент, добавить элемент по индексу,
 * получить элемент, удалить элемент, очистить всю коллекцию, отсортировать,
 * остальное по желанию
 * */
public interface NewList<T> {
    public boolean add(T element);

    public void add(int index, T element);

    public T get(int index);

    public boolean remove(T element);

    public void clear();

    public void sort(Comparator<T> comparator);

    public int size();

    public Object[] toArray();
}
