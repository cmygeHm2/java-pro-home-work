import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class TestsRunner {

    public static void runTests(Class<?> c) throws ReflectiveOperationException {

        TestClassMethods testClassMethods = new TestClassMethods();

        for (Method method : c.getDeclaredMethods()) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                extractMethods(testClassMethods, method, annotation);
            }
        }

        if (testClassMethods.getBeforeSuite() != null) {
            testClassMethods.getBeforeSuite().invoke(null);
        }

        if (!testClassMethods.getPriorityMap().isEmpty()) {
            var instance = c.getDeclaredConstructor().newInstance();
            testClassMethods.getPriorityMap().forEach((key, methods) -> methods
                    .forEach(
                            method -> {
                                try {
                                    invokeMethods(testClassMethods.getBeforeTestMethods(), instance);
                                    if (testClassMethods.hasCsvSourceAnnotation(method)) {
                                        method.invoke(instance, testClassMethods.getCsvSourceArguments(method));
                                    } else {
                                        method.invoke(instance);
                                    }
                                    invokeMethods(testClassMethods.getAfterTestMethods(), instance);
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    ));
        }

        if (testClassMethods.getAfterSuite() != null) {
            testClassMethods.getAfterSuite().invoke(null);
        }
    }

    private static void invokeMethods(List<Method> methods, Object instance)
            throws IllegalAccessException, InvocationTargetException {
        for (Method method : methods) {
            method.invoke(instance);
        }
    }

    private static void extractMethods(TestClassMethods testClassMethods, Method method, Annotation annotation) {
        if (annotation.annotationType() == BeforeSuite.class) {
            testClassMethods.initBeforeSuite(method, annotation);
        } else if (annotation.annotationType() == AfterSuite.class) {
            testClassMethods.initAfterSuite(method, annotation);
        } else if (annotation.annotationType() == BeforeTest.class) {
            testClassMethods.extractBeforeTestMethods(method, annotation);
        } else if (annotation.annotationType() == AfterTest.class) {
            testClassMethods.extractAfterTestMethods(method, annotation);
        } else if (annotation.annotationType() == Test.class) {
            testClassMethods.extractTestMethods(method);
        }
    }

    public static void main(String[] args) throws ReflectiveOperationException {
        runTests(SomeTestClass.class);
    }
}
