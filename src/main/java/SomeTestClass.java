public class SomeTestClass {

    @BeforeSuite
    public static void beforeSuite() {
        System.out.println("BeforeSuite");
    }

    @AfterSuite
    public static void afterSuite() {
        System.out.println("AfterSuite");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("BeforeTest");
    }

    @BeforeTest
    public void beforeTest2() {
        System.out.println("BeforeTest 2");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("AfterTest");
    }

    @Test(priority = 2)
    public void testWithPriority2() {
        System.out.println("priority 2");
    }

    @Test()
    public void testWithPriority5() {
        System.out.println("priority 5");
    }

    @Test
    @CsvSource("10, Java, 20, true")
    public void csvSourceTestMethod(Integer a, String b, Integer c, Boolean d) {
        System.out.printf("CsvSourceTestMethod: %s, %s, %s, %s\n", a, b, c, d);
    }

    @Test(priority = 4)
    public void testWithPriority4() {
        System.out.println("priority 4");
    }

    @Test(priority = 10)
    public void testWithPriority10() {
        System.out.println("priority 10");
    }

    @Test(priority = 1)
    public void testWithPriority1() {
        System.out.println("priority 1");
    }
}
