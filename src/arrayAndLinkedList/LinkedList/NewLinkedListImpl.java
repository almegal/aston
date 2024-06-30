package arrayAndLinkedList.LinkedList;

import arrayAndLinkedList.NewList;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 * Реализация двусвязного списка, поддерживающего основные операции
 * добавления, удаления, получения элементов, а также очистки и сортировки.
 *
 * @param <T> тип элементов, которые будут храниться в списке
 */
public class NewLinkedListImpl<T> implements NewList<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    /**
     * Конструктор, инициализирующий пустой список.
     */
    public NewLinkedListImpl() {
        this.size = 0;
    }

    /**
     * Добавляет элемент в конец списка.
     *
     * @param element элемент, который нужно добавить, не может быть {@code null}
     * @return {@code true}, если элемент был успешно добавлен
     * @throws IllegalArgumentException если {@code element} равен {@code null}
     */
    @Override
    public boolean add(@NotNull T element) {
        addLast(element);
        return true;
    }

    /**
     * Добавляет элемент по указанному индексу. Если элемент добавляется не в конец списка,
     * все последующие элементы сдвигаются вправо.
     *
     * @param index   индекс, по которому будет добавлен элемент
     * @param element элемент, который нужно добавить, не может быть {@code null}
     * @throws IndexOutOfBoundsException если индекс находится за пределами списка
     * @throws IllegalArgumentException  если {@code element} равен {@code null}
     */
    @Override
    public void add(int index, @NotNull T element) {
        checkIndex(index);

        // Создание нового узла с указанным элементом
        Node<T> newNode = new Node<>(element, null, null);

        // Если добавить вначало то вызовем метод addFirst
        if (index == 0) {
            addFirst(element);
            // Если в конец, то стандартный метод добавление элементов
        } else if (index == size) {
            addLast(element);
            // иначе
        } else {
            // Плучим текущую ноду и ноду перед ней
            Node<T> current = getNodeByIndex(index);
            Node<T> previous = current.prev;

            // В новом узле записываем ссылки на предыдущий и следующий узел
            newNode.prev = previous;
            newNode.next = current;

            // перезапишем ссылки узлом на новый узел
            previous.next = newNode;
            current.prev = newNode;
            size++;
        }
    }

    /**
     * Возвращает элемент, находящийся по указанному индексу.
     *
     * @param index индекс элемента, который нужно получить
     * @return элемент, находящийся по указанному индексу
     * @throws IndexOutOfBoundsException если индекс находится за пределами списка
     */
    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) toArray()[index];
    }

    /**
     * Удаляет первое вхождение указанного элемента из списка, если такой элемент существует.
     * Все последующие элементы сдвигаются влево.
     *
     * @param element элемент, который нужно удалить, не может быть {@code null}
     * @return {@code true}, если элемент был успешно удалён
     * @throws IllegalArgumentException если {@code element} равен {@code null}
     */
    @Override
    public boolean remove(@NotNull T element) {
        // Задаем текущую ноду
        Node<T> current = first;
        // пока текущая нода существует
        while (current != null) {
            // если елементы равны
            if (current.element.equals(element)) {
                // И узел не является первым
                if (current.prev != null) {
                    // Перезаписываем ссылку предыдущий ноды на следующую
                    current.prev.next = current.next;
                    // Иначе, нода перва и можно просто удалить ссылку на текущую ноду
                } else {
                    // Удаление первого элемента
                    first = current.next;
                }
                // Если следующий элемент не последний
                if (current.next != null) {
                    // Перезаписываем у следующего элемента ссылку на предыдущий элемент
                    current.next.prev = current.prev;
                    // иначе последний и можно затереть последнюю ссылку
                } else {
                    // Удаление последнего элемента
                    last = current.prev;
                }

                size--;
                return true;
            }
            current = current.next;
        }
        // Элемент не найден
        return false;
    }

    /**
     * Очищает список, удаляя все элементы.
     */
    @Override
    public void clear() {
        first = last = null;
        size = 0;
    }

    /**
     * Сортирует элементы списка с использованием указанного компаратора.
     *
     * @param comparator компаратор для сортировки элементов, может быть {@code null}
     */
    @Override
    public void sort(Comparator<T> comparator) {
        // Реализация сортировки потребуется
    }

    /**
     * Возвращает количество элементов в списке.
     *
     * @return количество элементов в списке
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Возвращает массив, содержащий все элементы списка.
     *
     * @return массив, содержащий все элементы списка
     */
    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int index = 0;
        Node<T> current = first;
        while (current != null) {
            result[index++] = current.element;
            current = current.next;
        }
        return result;
    }

    /**
     * Возвращает первый элемент списка.
     *
     * @return первый элемент списка или {@code null}, если список пуст
     */
    public T getFirst() {
        return first != null ? first.element : null;
    }

    /**
     * Возвращает последний элемент списка.
     *
     * @return последний элемент списка или {@code null}, если список пуст
     */
    public T getLast() {
        return last != null ? last.element : null;
    }

    private void addFirst(T element) {
        Node<T> newNode = new Node<>(element, null, first);
        if (first != null) {
            first.prev = newNode;
        }
        first = newNode;
        if (last == null) {
            last = newNode;
        }
        size++;
    }

    private void addLast(T element) {
        Node<T> newNode = new Node<>(element, last, null);
        if (last != null) {
            last.next = newNode;
        }
        last = newNode;
        if (first == null) {
            first = newNode;
        }
        size++;
    }

    /**
     * Проверяет, находится ли индекс в допустимом диапазоне.
     *
     * @param index индекс для проверки
     * @throws IndexOutOfBoundsException если индекс находится за пределами допустимого диапазона
     */
    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Индекс вне листа");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    /**
     * Внутренний класс для представления узла в двусвязном списке.
     *
     * @param <T> тип элемента, который хранится в узле
     */
    private static class Node<T> {
        T element;
        Node<T> prev;
        Node<T> next;

        /**
         * Конструктор для создания нового узла.
         *
         * @param element элемент, который хранится в узле
         * @param prev    предыдущий узел
         * @param next    следующий узел
         */
        public Node(T element, Node<T> prev, Node<T> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }
}
