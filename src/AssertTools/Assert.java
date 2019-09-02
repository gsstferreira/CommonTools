package AssertTools;

public abstract class Assert {

    public static void _assert(boolean condition, String exceptionMessage) throws Exception {
        if(!condition) {
            throw new Exception(exceptionMessage);
        }
    }

    public static void _assert(boolean condition) throws Exception {
        if(!condition) {
            throw new Exception();
        }
    }
}
