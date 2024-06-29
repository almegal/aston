package arrayAndLinkedList.arrayList;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Реализация динамического списка, поддерживающая добавление, удаление,
 * получение элементов, очистку и сортировку. Класс управляет собственным
 * хранилищем и автоматически увеличивает его размер по мере необходимости.
 *
 * @param <T> тип элементов, хранящихся в этом списке
 */
public class NewArrayListImpl<T> implements NewArrayList<T> {

    /**
     * Начальная ёмкость списка по умолчанию.
     */
    private final int DEFAULT_CAPACITY = 10;

    /**
     * Текущее количество элементов в списке.
     */
    private int size;

    /**
     * Массив для хранения элементов списка.
     */
    private T[] storage;

    /**
     * Текущая ёмкость массива хранения.
     */
    private int currentCapacity;

    /**
     * Создаёт пустой список с начальной ёмкостью 10.
     */
    @SuppressWarnings("unchecked")
    public NewArrayListImpl() {
        this.size = 0;
        this.currentCapacity = DEFAULT_CAPACITY;
        this.storage = (T[]) new Object[DEFAULT_CAPACITY];
    }

    /**
     * Создаёт пустой список с указанной начальной ёмкостью.
     *
     * @param capacity начальная ёмкость списка
     */
    @SuppressWarnings("unchecked")
    public NewArrayListImpl(int capacity) {
        this.size = 0;
        this.currentCapacity = capacity;
        this.storage = (T[]) new Object[capacity];
    }

    /**
     * Возвращает количество элементов в этом списке.
     *
     * @return количество элементов в этом списке
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Добавляет указанный элемент в конец этого списка. Если список
     * заполнен, увеличивает его ёмкость.
     *
     * @param element элемент, который нужно добавить в список
     * @return {@code true}, если элемент успешно добавлен
     */
    @Override
    public boolean add(@NotNull T element) {
        if (needGrow()) {
            growSizeStorage();
        }
        storage[size++] = element;
        return true;
    }

    /**
     * Вставляет указанный элемент на указанную позицию в этом списке.
     * Сдвигает текущий элемент и любые последующие элементы вправо
     * (добавляет один к их индексам).
     *
     * @param index   индекс, по которому должен быть вставлен элемент
     * @param element элемент, который нужно вставить
     * @throws IndexOutOfBoundsException если индекс выходит за пределы
     */
    @Override
    public void add(int index, @NotNull T element) {
        // проверяем что индекс допустимый
        checkIndexInRange(index);
        // если надо увеличить массив - увеличиваем
        if (needGrow()) {
            growSizeStorage();
        }
        // добавляем елемент в массив на индекс
        if (index == size) {
            add(element);
        } else {
            offsetStorage(index, element);
        }
    }

    /**
     * Возвращает элемент, находящийся на указанной позиции в этом списке.
     *
     * @param index индекс возвращаемого элемента
     * @return элемент на указанной позиции в этом списке
     * @throws IndexOutOfBoundsException если индекс выходит за пределы
     */
    @Override
    public T get(int index) {
        checkIndexInRange(index);
        return storage[index];
    }

    /**
     * Удаляет первое вхождение указанного элемента из этого списка, если оно присутствует.
     * Сдвигает любые последующие элементы влево (вычитает один из их индексов).
     *
     * @param element элемент, который нужно удалить из этого списка, если он присутствует
     * @return {@code true}, если этот список содержал указанный элемент
     */
    @Override
    public boolean remove(T element) {
        int index = getIndexByElement(element);
        if (index >= 0) {
            offsetStorage(index);
            return true;
        }
        return false;
    }

    /**
     * Удаляет все элементы из этого списка. Список будет пустым после вызова этого метода.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        size = 0;
        currentCapacity = DEFAULT_CAPACITY;
        storage = (T[]) new Object[DEFAULT_CAPACITY];
    }

    /**
     * Сортирует этот список в соответствии с порядком, навязанным указанным компаратором.
     * Все элементы в списке должны быть взаимно сравнимы указанным компаратором.
     *
     * @param comparator компаратор, определяющий порядок списка
     */
    @Override
    public void sort(Comparator<T> comparator) {
        storage = toArray();
        Arrays.sort(storage, comparator);
        storage = Arrays.copyOfRange(storage, 0, currentCapacity);
    }

    /**
     * Возвращает массив, содержащий все элементы этого списка в правильной последовательности (от первого до последнего элемента).
     *
     * @return массив, содержащий все элементы этого списка в правильной последовательности
     */
    @Override
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        return Arrays.stream(storage)
                .filter(Objects::nonNull)
                .toArray(size -> (T[]) new Object[size]);
    }

    /**
     * Увеличивает ёмкость массива хранения.
     *
     * @return новый массив хранения с увеличенной ёмкостью
     */
    private void growSizeStorage() {
        currentCapacity = storage.length * 2;
        storage = Arrays.copyOf(storage, currentCapacity);
    }

    /**
     * Проверяет, нужно ли увеличить ёмкость массива хранения, исходя из текущего размера.
     *
     * @return {@code true}, если необходимо увеличить ёмкость массива хранения
     */
    private boolean needGrow() {
        return size >= storage.length;
    }

    /**
     * Проверяет, находится ли указанный индекс в пределах допустимого диапазона (от 0 до размера списка не включительно).
     *
     * @param index индекс для проверки
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона
     */
    private void checkIndexInRange(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс вне диапазона: " + index);
        }
    }

    /**
     * Сдвигает элементы вправо, начиная с указанного индекса, и вставляет указанный элемент.
     *
     * @param index   индекс, по которому должен быть вставлен элемент
     * @param element элемент, который нужно вставить
     */
    private void offsetStorage(int index, T element) {
        // свдигаем массив на одну позицию вправо
        System.arraycopy(storage, index, storage, index + 1, size - index);
        // записываем значение в нужный индекс
        storage[index] = element;
        size++;
    }

    /**
     * Сдвигает элементы влево, начиная с указанного индекса, чтобы удалить элемент на этой позиции.
     *
     * @param index индекс элемента, который нужно удалить
     */
    private void offsetStorage(int index) {
        // проверяем что не выходим за массив
        if (size - 1 - index >= 0) {
            // свдигаем массив влево
            System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
        }
        size--;
        // записываем последний елемент как null
        storage[size] = null;
    }

    /**
     * Возвращает индекс первого вхождения указанного элемента в этом списке,
     * или -1, если этот список не содержит элемент.
     *
     * @param element элемент для поиска
     * @return индекс первого вхождения указанного элемента, или -1, если не найден
     */
    private int getIndexByElement(T element) {
        return IntStream.range(0, toArray().length)
                .filter(i -> element.equals(storage[i]))
                .findFirst()
                .orElse(-1);
    }
}
