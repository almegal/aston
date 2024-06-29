package arrayAndLinkedList.ArrayList;

import arrayAndLinkedList.ArrayList.NewArrayListImpl;
import arrayAndLinkedList.NewList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/*  Кейсы для тестирования
 * - добавление елементов / если нет места / если null
 * - доабвить по индексу /добавить в первый-последний инедкс коллекции/ если нет места / индекс вне коллекции
 * - удалить елемент /удалить первый-последний елемент коллекции/ если нет елемента
 * */
public class NewArrayListImplTest {
    // константы используемые в тестах
    private final int INITIAL_SIZE = 4;
    private NewList<Integer> DEFAULT_ARRAY_LIST;

    // аргументы для тестов типа int element, int index
    static public Stream<Arguments> argsProviderForShouldAddElementByIndex() {
        return Stream.of(
                Arguments.of(100, 0),
                Arguments.of(10, 9),
                Arguments.of(40, 39),
                Arguments.of(100, 5),
                Arguments.of(2000, 126),
                Arguments.of(10, 3),
                Arguments.of(1, 0),
                Arguments.of(54341, 313));
    }

    // заполним дефолтный лист елементами
    @BeforeEach
    public void init() {
        DEFAULT_ARRAY_LIST = new NewArrayListImpl<>();
        // добавим пару елементов в массив
        IntStream.range(0, INITIAL_SIZE).forEach(DEFAULT_ARRAY_LIST::add);
    }

    // добавления елемента
    @Test
    @DisplayName("Добавить елемент в коллекцию")
    public void shouldAddAndReturnElement() {
        // подготовка ожидаемого значения
        Integer[] expectedArray = new Integer[]{0, 1, 2, 3, 7};
        int expectedSize = INITIAL_SIZE + 1;
        // актуальное значение
        boolean isAdded = DEFAULT_ARRAY_LIST.add(7);
        int actualSize = DEFAULT_ARRAY_LIST.size();
        // почему тут невозможен каст в Integer
        Object[] actualArray = DEFAULT_ARRAY_LIST.toArray();
        // тест
        assertTrue(isAdded);
        assertEquals(expectedSize, actualSize);
        assertArrayEquals(expectedArray, actualArray);
    }

    // добавления елементов если нет места - массив полон
    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 88, 1005, 100_000})
    @DisplayName("Добавить елемент если нет места (resize)")
    public void shouldResizeAndAddElement(int sizeStream) {
        // подготовка данных
        // создадим массив для проверки и тестируемый
        Integer[] arrayList = new Integer[sizeStream];
        NewList<Integer> array = new NewArrayListImpl<>();
        // заполним нативный список и наш лист данными
        IntStream.range(0, sizeStream).forEach(e -> {
            arrayList[e] = e;
            array.add(e);
        });
        // подготовка ожидаемого значения
        Object[] expectedArray = Arrays.stream(arrayList).toArray();
        // подготовка актуального результата
        int actualSize = array.size();
        Object[] actualArray = array.toArray();
        // тест
        assertEquals(sizeStream, actualSize);
        assertArrayEquals(expectedArray, actualArray);
    }

    //
    @Test
    @DisplayName("При добавление null - выдает ошибку")
    public void shouldReturnNullPointerIfPassedNullInAdd() {
        assertThrows(IllegalArgumentException.class, () -> {
            DEFAULT_ARRAY_LIST.add(null);
        });
    }

    // добавить елементы по индексу
    @ParameterizedTest
    @MethodSource("argsProviderForShouldAddElementByIndex")
    @DisplayName("Добавить элемент по индексу")
    public void shouldAddElementByIndex(int sizeStream, int index) {
        // подготовка данных
        // создадим элемент
        int element = index * 2;
        // создадим реализованный лист и наш
        List<Integer> arrayForExpected = new ArrayList<>();
        NewList<Integer> arrayForActual = new NewArrayListImpl<>();
        // заполним данными
        IntStream.range(0, sizeStream).forEach(e -> {
            arrayForExpected.add(e);
            arrayForActual.add(e);
        });

        // подготовка ожидаемого значения
        arrayForExpected.add(index, element);
        Object[] expectedArray = arrayForExpected.toArray();
        // подготовка актуального резульата
        arrayForActual.add(index, element);
        Object[] actualArray = arrayForActual.toArray();
        // тестируем что наша реализация
        // работает как и реализации ArrayList
        assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    @DisplayName("Должен выбрасить исключение если индекса больше массива")
    public void shouldThrowIfIndexOut() {
        int index = 100;
        //
        IndexOutOfBoundsException thrown = assertThrows(IndexOutOfBoundsException.class, () -> {
            DEFAULT_ARRAY_LIST.add(index, 10);
        });
        assertEquals("Индекс вне диапазона: " + index, thrown.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    @DisplayName("Удалить елемент")
    public void shouldRemoveElement(Integer element) {
        // создадим список из стандартной реализации
        List<Integer> arrayForExpected = new ArrayList<>(Arrays.asList(0, 1, 2, 3)) {
        };
        // подгтовка ожидаемого значения
        arrayForExpected.remove(element);
        Object[] expected = arrayForExpected.toArray();
        // подготовка актуального значения
        boolean isRemoved = DEFAULT_ARRAY_LIST.remove(element);
        Object[] actual = DEFAULT_ARRAY_LIST.toArray();
        // тест
        assertTrue(isRemoved);
        assertArrayEquals(expected, actual);
    }
}
