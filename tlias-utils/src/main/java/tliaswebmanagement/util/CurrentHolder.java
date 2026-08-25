package tliaswebmanagement.util;


//操作线程局部变量的工具类: 线程局部变量ThreadLocal，每个线程都拥有自己的变量副本，线程之间互不干扰,可以在里面存储当前项目的上下文信息
public class CurrentHolder {
                                   //表示当前线程局部变量存储integer类型的值
    private static final ThreadLocal<Integer> CURRENT_LOCAL = new ThreadLocal<>();

    public static void setCurrentId(Integer employeeId) {
        CURRENT_LOCAL.set(employeeId);
    }

    public static Integer getCurrentId() {
        return CURRENT_LOCAL.get();
    }

    public static void remove() {
        CURRENT_LOCAL.remove();
    }
}
