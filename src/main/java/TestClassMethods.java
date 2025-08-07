import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class TestClassMethods {
    private SortedMap<Integer, List<Method>> priorityMap = new TreeMap<>(Comparator.reverseOrder());
    private Method beforeSuite = null;
    private Method afterSuite = null;
    private final List<Method> beforeTestMethods = new ArrayList<>();
    private final List<Method> afterTestMethods = new ArrayList<>();
    private final Map<Method, CsvSourceArgument> csvSourceMethods = new HashMap<>();

    public void initBeforeSuite(Method method, Annotation annotation) {
        if (!Modifier.isStatic(method.getModifiers())) {
            throw new RuntimeException(getMessageForStaticOnlyMethods(annotation));
        }
        if (beforeSuite == null) {
            beforeSuite = method;
        } else {
            throw new RuntimeException("Duplicate before suite method");
        }
    }

    public void initAfterSuite(Method method, Annotation annotation) {
        if (!Modifier.isStatic(method.getModifiers())) {
            throw new RuntimeException(getMessageForStaticOnlyMethods(annotation));
        }
        if (afterSuite == null) {
            afterSuite = method;
        } else {
            throw new RuntimeException("Duplicate after suite method");
        }
    }

    public void extractBeforeTestMethods(Method method, Annotation annotation) {
        if (Modifier.isStatic(method.getModifiers())) {
            throw new RuntimeException(getMessageForNonStaticMethods(annotation));
        }
        beforeTestMethods.add(method);
    }

    public void extractAfterTestMethods(Method method, Annotation annotation) {
        if (Modifier.isStatic(method.getModifiers())) {
            throw new RuntimeException(getMessageForNonStaticMethods(annotation));
        }
        afterTestMethods.add(method);
    }

    public void extractTestMethods(Method method) {
        Test annotationTest = method.getAnnotation(Test.class);
        if (Modifier.isStatic(method.getModifiers())) {
            throw new RuntimeException("@Test annotation can annotate only non-static methods");
        }
        int priority = annotationTest.priority();
        checkHasCsvSourceAnnotation(method);
        priorityMap.computeIfAbsent(priority, k -> new ArrayList<>()).add(method);
    }

    private void checkHasCsvSourceAnnotation(Method method) {
        Annotation[] annotations = method.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == CsvSource.class) {
                Object[] args = parseArgs(method.getAnnotation(CsvSource.class));
                csvSourceMethods.put(method, new CsvSourceArgument(args));
            }
        }
    }

    private Object[] parseArgs(CsvSource annotation) {
        String[] args = annotation.value().split("\\s*,\\s*");
        Object[] typedArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            typedArgs[i] = parseStringArg(args[i]);
        }
        return typedArgs;
    }

    public static Object parseStringArg(String arg) {
        if (arg == null) return null;

        if (arg.equalsIgnoreCase("true") || arg.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(arg);
        }

        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            // Не integer, продолжаем проверки
        }

        try {
            return Double.parseDouble(arg);
        } catch (NumberFormatException e) {
            // Не double, возвращаем как строку
        }

        return arg;
    }

    public Method getBeforeSuite() {
        return beforeSuite;
    }

    public Method getAfterSuite() {
        return afterSuite;
    }

    public List<Method> getBeforeTestMethods() {
        return beforeTestMethods;
    }

    public List<Method> getAfterTestMethods() {
        return afterTestMethods;
    }

    public SortedMap<Integer, List<Method>> getPriorityMap() {
        return priorityMap;
    }

    public boolean hasCsvSourceAnnotation(Method method) {
        return csvSourceMethods.containsKey(method);
    }

    public Object[] getCsvSourceArguments(Method method) {
        return csvSourceMethods.get(method).getArgs();
    }

    private String getMessageForStaticOnlyMethods(Annotation annotation) {
        return "@" + annotation.getClass().getSimpleName() + "annotation can annotate only static methods";
    }

    private String getMessageForNonStaticMethods(Annotation annotation) {
        return "@" + annotation.getClass().getSimpleName() + "annotation can annotate only non-static methods";
    }
}
