# Спецкурс по языку Java
## Лабораторная работа №6
### Задание на лабораторную работу

В процессе написания тестовых заданий ознакомиться с механизмом образцов проектирования «Итератор», «Декоратор» и упрощенной версией «Фабричного метода».

#### Задание 1
Модифицировать ваш интерфейс таким образом, чтобы он реализовывал интерфейс `java.util.Iterable<>`.
Реализовать метод `java.util.Iterator<> iterator()`, унаследованный из интерфейса, в двух ваших классах. Для этого, естественно, следует описать некий дополнительный класс итератора с соответствующими методами итератора (из стандартного интерфейса `java.util.Iterator<>`). Объект итератора и возвращается из метода `iterator()` ваших классов. Итератор должен позволять обойти все элементы поля-массива.

#### Задание 2
Проверить работу итератора (например, с использованием улучшенного цикла for).

### Задание 3
Добавить в класс со статическими методами описание еще одного статического метода `<Интерфейс> unmodifiable<Интерфейс>(<Интерфейс> о)`, возвращающего ссылку на экземпляр неизменяемой оболочки указанного объекта. Для этого нужно написать новый класс-декоратор для типа `<Интерфейс>`, который содержит объект типа `<Интерфейс>`, сам реализует `<Интерфейс>`, а все методы интерфейса делегирует внутреннему объекту, кроме методов изменения объекта. Декоратор выбрасывает исключение `UnsupportedOperationException` в случае попытки изменения состояния внутреннего объекта.

#### Задание 4
Описать новый интерфейс `<Интерфейс>Factory`, содержащий единственный метод `createInstance()` для создания экземпляра по умолчанию (опционально в фабрику можно добавить параметризованные методы создания объектов).

#### Задание 5
В классе со статическими методами создать приватное статическое поле factory типа `<Интерфейс>Factory` и соответствующий ему публичный метод `set<Интерфейс>Factory`, позволяющие, соответственно, хранить ссылку и устанавливать ссылку на текущую фабрику объектов. Для каждого типа объекта из вашей иерархии нужно описать класс соответствующей фабрики (реализующей метод `createInstance()`). По умолчанию поле должно ссылаться на объект некоторого (одного из двух) класса `Factory`, порождающего экземпляры одного из ваших классов.

#### Задание 6
В классе со статическими методами описать метод `public static createInstance()`, с помощью текущей фабрики создающий новый экземпляр объекта. В остальных методах заменить прямое создание экземпляров на вызов этого метода.

#### Контрольные вопросы
1. Понятие каркаса
2. Понятие образца проектирования
3. Базовые паттерны проектирования
4. Порождающий паттерн Singleton
5. Порождающий паттерн Factory Method
6. Порождающий паттерн Abstract Factory
7. Структурный паттерн Adapter
8. Структурный паттерн Decorator
9. Структурный паттерн Proxy
10. Образец поведения Iterator
11. Образец поведения Observer