package arrayAndLinkedList;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 * Интерфейс для создания списка, поддерживающего основные операции добавления,
 * удаления, получения элементов, а также очистки и сортировки.
 *
 * @param <T> тип элементов, которые будут храниться в списке
 */
public interface NewList<T> {

    /**
     * Добавляет элемент в конец списка.
     *
     * @param element элемент, который нужно добавить, не может быть {@code null}
     * @return {@code true}, если элемент был успешно добавлен
     * @throws IllegalArgumentException если {@code element} равен {@code null}
     */
    boolean add(@NotNull T element);

    /**
     * Добавляет элемент по указанному индексу. Если элемент добавляется не в конец списка,
     * все последующие элементы сдвигаются вправо.
     *
     * @param index   индекс, по которому будет добавлен элемент
     * @param element элемент, который нужно добавить, не может быть {@code null}
     * @throws IndexOutOfBoundsException если индекс находится за пределами списка
     * @throws IllegalArgumentException  если {@code element} равен {@code null}
     */
    void add(int index, @NotNull T element);

    /**
     * Возвращает элемент, находящийся по указанному индексу.
     *
     * @param index индекс элемента, который нужно получить
     * @return элемент, находящийся по указанному индексу
     * @throws IndexOutOfBoundsException если индекс находится за пределами списка
     */
    T get(int index);

    /**
     * Удаляет первое вхождение указанного элемента из списка, если такой элемент существует.
     * Все последующие элементы сдвигаются влево.
     *
     * @param element элемент, который нужно удалить, не может быть {@code null}
     * @return {@code true}, если элемент был успешно удалён
     * @throws IllegalArgumentException если {@code element} равен {@code null}
     */
    boolean remove(@NotNull T element);

    /**
     * Очищает список, удаляя все элементы.
     */
    void clear();

    /**
     * Сортирует элементы списка с использованием указанного компаратора.
     *
     * @param comparator компаратор для сортировки элементов, может быть {@code null}
     */
    void sort(Comparator<T> comparator);

    /**
     * Возвращает количество элементов в списке.
     *
     * @return количество элементов в списке
     */
    int size();

    /**
     * Возвращает массив, содержащий все элементы списка.
     *
     * @return массив, содержащий все элементы списка
     */
    Object[] toArray();
}
