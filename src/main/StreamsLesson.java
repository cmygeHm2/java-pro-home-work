import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsLesson {
    public static void main(String[] args) {
        StreamsLesson streamsLesson = new StreamsLesson();
        System.out.println("----------------task 1-------------");
        streamsLesson.task1();
        System.out.println("----------------task 2-------------");
        streamsLesson.task2();
        System.out.println("----------------task 3-------------");
        streamsLesson.task3();
        System.out.println("----------------task 4-------------");
        streamsLesson.task4();
        System.out.println("----------------task 5-------------");
        streamsLesson.task5();
        System.out.println("----------------task 6-------------");
        streamsLesson.task6();
        System.out.println("----------------task 7-------------");
        streamsLesson.task7();
        System.out.println("----------------task 8-------------");
        streamsLesson.task8();
    }

    /**
     * Найдите в списке целых чисел 3-е наибольшее число (пример: 5 2 10 9 4 3 10 1 13 => 10)
     */
    private void task1() {
        Stream.of(6, 7, 5, 2, 10, 9, 4, 3, 10, 1, 13)
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .skip(2)
                .findFirst()
                .ifPresent(System.out::println);
    }

    /**
     * Найдите в списке целых чисел 3-е наибольшее «уникальное» число (пример: 5 2 10 9 4 3 10 1 13 => 9,
     * в отличие от прошлой задачи здесь разные 10 считает за одно число)
     */
    private void task2() {
        Stream.of(5, 2, 10, 9, 4, 3, 10, 1, 13)
                .sorted(Comparator.reverseOrder())
                .distinct()
                .limit(3)
                .skip(2)
                .findFirst()
                .ifPresent(System.out::println);
    }

    /**
     * Имеется список объектов типа Сотрудник (имя, возраст, должность),
     * необходимо получить список имен 3 самых старших сотрудников с должностью «Инженер», в порядке убывания возраста
     */
    private void task3() {
        var a = new Employee("a", 25, "programmer");
        var b = new Employee("b", 54, "programmer");
        var c = new Employee("c", 35, "programmer");
        var d = new Employee("d", 74, "engineer");
        var e = new Employee("e", 24, "engineer");
        var f = new Employee("f", 62, "engineer");
        var g = new Employee("g", 34, "engineer");
        var h = new Employee("h", 45, "toster");
        var i = new Employee("i", 32, "engineer");
        var j = new Employee("j", 21, "manager");
        var k = new Employee("k", 46, "engineer");
        var l = new Employee("l", 43, "manager");
        var m = new Employee("m", 55, "out");
        var n = new Employee("n", 44, "out");
        Stream.of(a, b, c, d, e, f, g, h, i, j, k, l, m, n)
                .filter(employee -> employee.getProfession().equals("engineer"))
                .sorted(Comparator.comparing(Employee::getAge).reversed())
                .limit(3)
                .map(Employee::getName)
                .forEach(System.out::println);

    }

    /**
     * Имеется список объектов типа Сотрудник (имя, возраст, должность),
     * посчитайте средний возраст сотрудников с должностью «Инженер»
     */
    private void task4() {
        var a = new Employee("a", 25, "programmer");
        var b = new Employee("b", 54, "programmer");
        var c = new Employee("c", 35, "programmer");
        var d = new Employee("d", 74, "engineer");
        var e = new Employee("e", 24, "engineer");
        var f = new Employee("f", 62, "engineer");
        var g = new Employee("g", 34, "engineer");
        var h = new Employee("h", 45, "toster");
        var i = new Employee("i", 32, "engineer");
        var j = new Employee("j", 21, "manager");
        var k = new Employee("k", 46, "engineer");
        var l = new Employee("l", 43, "manager");
        var m = new Employee("m", 55, "out");
        var n = new Employee("n", 44, "out");
        Double averageAge = Stream.of(a, b, c, d, e, f, g, h, i, j, k, l, m, n)
                .filter(employee -> employee.getProfession().equals("engineer"))
                .collect(Collectors.averagingInt(Employee::getAge));
        System.out.println(averageAge);
    }

    /**
     * Найдите в списке слов самое длинное
     */
    private void task5() {
        Stream.of("Найдите", "в", "списке", "этоСамоеДлинное", "слов", "самое", "длинное")
                .sorted(Comparator.reverseOrder())
                .limit(1)
                .findFirst()
                .ifPresent(System.out::println);
    }

    /**
     * Имеется строка с набором слов в нижнем регистре, разделенных пробелом.
     * Постройте хеш-мапы, в которой будут храниться пары: слово - сколько раз оно встречается во входной строке
     */
    private void task6() {
        Stream.of("найдите", "в", "слов", "списке", "слов", "найдите", "в", "слов", "самое", "длинное", "слов", "самое")
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .forEach(System.out::println);
    }

    /**
     * Отпечатайте в консоль строки из списка в порядке увеличения длины слова,
     * если слова имеют одинаковую длины, то должен быть сохранен алфавитный порядок
     */
    private void task7() {
        Stream.of("книг", "а", "в", "ббб", "aaa", "слов", "зззз", "в", "слов", "дддд", "длинное", "слов", "самое")
                .sorted(Comparator.comparingInt(String::length).thenComparing(Function.identity()))
                .forEach(System.out::println);
    }

    /**
     * Имеется массив строк, в каждой из которых лежит набор из 5 слов, разделенных пробелом.
     * Найдите среди всех слов самое длинное, если таких слов несколько, получите любое из них
     */
    private void task8() {
        Stream.of(
                "строка слово оченьдлинноеслово книга гоу",
                "привет мир в котором есть только пять слов",
                "хочу знать java но черепная коробка")
                .flatMap(s -> Stream.of(s.split(" ")))
                .sorted(Comparator.comparing(String::length).reversed())
                .limit(1)
                .forEach(System.out::println);
    }

    public static class Employee {
        private final String name;
        private final int age;
        private final String profession;
        public Employee(String name, int age, String profession) {
            this.name = name;
            this.age = age;
            this.profession = profession;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String getProfession() {
            return profession;
        }
    }
}

