package arrayAndLinkedList.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/*  Кейсы для тестирования
 * - добавление елементов / если null
 * - получить елемент по индкексу / если индекс вне массива
 * - доабвить по индексу /добавить в первый-последний индекс коллекции/ индекс вне коллекции
 * - удалить елемент /удалить первый-последний елемент коллекции / если нет елемента
 * */

public class NewLinkedListImplTest {

    private final List<String> UTILS_STRING_ARRAY = new ArrayList<>(
            Arrays.asList("Hello", "Amazing", "Beautiful", "World"));
    private NewLinkedListImpl<String> DEFAULT_LINKED_LIST;

    private int DEFAULT_SIZE;

    @BeforeEach
    public void init() {
        // Добавим в тестируемый список элементы
        DEFAULT_LINKED_LIST = new NewLinkedListImpl<>();
        for (String s : UTILS_STRING_ARRAY) {
            DEFAULT_LINKED_LIST.add(s);
        }

        DEFAULT_SIZE = DEFAULT_LINKED_LIST.size();
    }

    @Test
    @DisplayName("Добавить элемент в пустой NewLinkedListImpl")
    public void addElementWhenListEmpty() {
        // ожидаемое значение
        String expected = "Hello";

        // актуальное значение
        String actual = DEFAULT_LINKED_LIST.getFirst();
        int actualSize = DEFAULT_LINKED_LIST.size();

        // test
        assertEquals(expected, actual);
        assertEquals(DEFAULT_SIZE, actualSize);
    }

    @Test
    @DisplayName("Добавить элемент не в пустой NewLinkedListImpl")
    public void addElementWhenListNotEmpty() {
        // подготовка ожидаемого результат
        String expectedFirst = "Hello";
        String expectedLast = "World";
        int expectedSize = DEFAULT_LINKED_LIST.size();

        // подготовка актуального результата
        String actualFirst = DEFAULT_LINKED_LIST.getFirst();
        String actualLast = DEFAULT_LINKED_LIST.getLast();
        int actualSize = DEFAULT_LINKED_LIST.size();

        // тест
        assertEquals(expectedFirst, actualFirst);
        assertEquals(expectedLast, actualLast);
        assertEquals(expectedSize, actualSize);
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Выбросить IllegalArgumentException если элемент null")
    public void shouldThrownWhenNullAsArgument(String input) {
        assertThrows(IllegalArgumentException.class,
                () -> DEFAULT_LINKED_LIST.add(input));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    @DisplayName("Получить элемент по индексу")
    public void shouldReturnElementByIndex(int index) {
        // подготовка ожидаемого результата
        String expected = UTILS_STRING_ARRAY.get(index);
        // подготовка актуального значения
        String actual = DEFAULT_LINKED_LIST.get(index);
        // тест
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Если индекс вне массива")
    public void shouldReturnIndexOutExceptionWhenTruGetByIncorrectIndex() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> DEFAULT_LINKED_LIST.get(10000));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello", "Amazing", "World", "Beautiful"})
    @DisplayName("Удаляет элемент и возращает true")
    public void shouldRemoveAndReturnTrueIfRemoved(String element) {
        // подготовка результата
        boolean isRemoved = DEFAULT_LINKED_LIST.remove(element);
        // тест
        assertTrue(isRemoved);
        assertEquals(DEFAULT_SIZE - 1, DEFAULT_LINKED_LIST.size());
    }

    @Test
    @DisplayName("Если нет элемента то возращает false")
    public void shouldReturnFalseIfElementNotExistsInList() {
        // подготовка результат
        boolean isRemoved = DEFAULT_LINKED_LIST.remove("abc");

        // тест
        assertFalse(isRemoved);
        assertEquals(DEFAULT_SIZE, DEFAULT_LINKED_LIST.size());
    }

    @Test
    @DisplayName("Добавляет элемент на позицию первого индекса")
    public void shouldAddCorrectElementInFirstIndex() {
        // подготовка ожидаемого результата
        String expectedElement = "Whoops";
        UTILS_STRING_ARRAY.add(0, expectedElement);
        Object[] expectedArray = UTILS_STRING_ARRAY.toArray();

        // вызова тетсируемого метода
        DEFAULT_LINKED_LIST.add(0, expectedElement);

        // подготовка актуального результата
        String actual = DEFAULT_LINKED_LIST.getFirst();
        Object[] actualArray = DEFAULT_LINKED_LIST.toArray();

        // тест
        assertEquals(expectedElement, actual);
        assertArrayEquals(expectedArray, actualArray);
        assertEquals(UTILS_STRING_ARRAY.size(), DEFAULT_LINKED_LIST.size());
    }

    @Test
    @DisplayName("Добавляет элемент на позицию последнего индекса")
    public void shouldAddCorrectElementInLastIndex() {
        // подготовка ожидаемого результата
        String expectedElement = "Whoops";
        UTILS_STRING_ARRAY.add(DEFAULT_SIZE, expectedElement);
        Object[] expectedArray = UTILS_STRING_ARRAY.toArray();

        // вызова тетсируемого метода
        DEFAULT_LINKED_LIST.add(DEFAULT_SIZE, expectedElement);

        // подготовка актуального результата
        String actual = DEFAULT_LINKED_LIST.getLast();
        Object[] actualArray = DEFAULT_LINKED_LIST.toArray();

        // тест
        assertEquals(expectedElement, actual);
        assertArrayEquals(expectedArray, actualArray);
        assertEquals(UTILS_STRING_ARRAY.size(), DEFAULT_LINKED_LIST.size());
    }
}
