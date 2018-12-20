import jms.Connection;
import jms.Message;
import jms.TcpConnection;
import jms.TcpConnectionDelegate;

import java.lang.String;
import java.lang.reflect.Constructor;
import animal.Animal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URLClassLoader;
import java.sql.DriverManager;
import java.util.*;



public class Main {

    private final static List<Integer> a = Arrays.asList(1,2);
    private final static List<Integer> b = Arrays.asList(2,1);

    public static void main(String[] args) throws Exception {
        TcpConnectionDelegate tcpConnectionDelegate = new TcpConnectionDelegate(new TcpConnection());
        Connection tc = (Connection) tcpConnectionDelegate.bind();
        Message m = (Message) tcpConnectionDelegate.bind();
        tc.connect();
        final Class<?>[] constructorParams = { InvocationHandler.class };
        if(tc instanceof Proxy){
            System.out.println("true");
        }
        m.send();
        if(URLClassLoader.class.isAssignableFrom(ClassLoader.getSystemClassLoader().getClass())){
            System.out.println("extends urlclassloader");
        }

    }




    public static int test(int total, int max, int size){
        System.out.println((double)max);
        System.out.println((double) total/size);
        System.out.println(((double)max >= (double) total/size));
        return 0;
    }

    public static int getDiffInt(LeftMoneyPackage _leftMoneyPackage){
        int extra = _leftMoneyPackage.getRemainMoney() - _leftMoneyPackage.getRemainSize();
        return getRandom(1, extra < 10 ? extra : 10);
    }

    public static int[] getRandom(int total, int size, int max, int min){
        assert total > size;
        assert max > min;
        assert (double)max >= ((double)total)/size;
        int[] a = new int[size];
        for (int i = 0; i < size; i++) {
            a[i] = 1;
        }
        List<Integer> randomList = new ArrayList<>();
        int bound = max - min;
        int diff = total - size;
        for(int i = 1; i <= bound; i++){
            for(int j = 0; j < size; j++){
                randomList.add(j);
            }
        }
        Collections.shuffle(randomList);
        List<Integer> jList = randomList.subList(0, diff);
        for (Integer j : jList){
            a[j]++;
        }
        return a;
    }

    public static int getRandom(int min, int max){
        Random r = new Random();
        double result = Math.floor(r.nextDouble() * (max - min) + min);
        return (int)result;
    }
    public static double getRandomMoney(LeftMoneyPackage _leftMoneyPackage) {
        // remainSize 剩余的红包数量
        // remainMoney 剩余的钱
        if (_leftMoneyPackage.remainSize == 1) {
            _leftMoneyPackage.remainSize--;
            return (double) Math.round(_leftMoneyPackage.remainMoney * 100) / 100;
        }
        Random r     = new Random();
        double min   = 0.01; //
        double max   = _leftMoneyPackage.remainMoney / _leftMoneyPackage.remainSize * 2;
        double money = r.nextDouble() * max;
        money = money < min ? 0.01: money;
        money = Math.floor(money * 100) / 100;
        _leftMoneyPackage.remainSize--;
      //  _leftMoneyPackage.remainMoney -= money;
        return money;
    }


    public static int getRandomMoneyInt(LeftMoneyPackage _leftMoneyPackage, int max) {
        // remainSize 剩余的红包数量
        // remainMoney 剩余的钱
        if (_leftMoneyPackage.remainSize == 1) {
            _leftMoneyPackage.remainSize--;
            return Math.round(_leftMoneyPackage.remainMoney);
        }
        Random r     = new Random();
        double min   = 1.0;
        double max1   = (double)_leftMoneyPackage.remainMoney / _leftMoneyPackage.remainSize * 2;
        max1 = max1 < (double)max ? max1 : max;
        double money = r.nextDouble() * max1;
        money = money < min ? min : money;
        money = Math.floor(money);
        _leftMoneyPackage.remainSize--;
        _leftMoneyPackage.remainMoney -= (int)money;
        return (int)money;
    }

    public static void add(Boolean bool) {
        bool = !bool;
    }

    private static boolean test(Optional<Integer> a){
        return a.isPresent();
    }

    static class Person{
        String name;
        Integer age;
        Person(String name, Integer age){
            this.name = name;
            this.age = age;
        }
        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
//    private static String getOrderStatusTxt(Integer code){
//        if(null == code){
//            return  null;
//        }
//        String txt = "";
//        switch (code){
//            case 1:
//                txt = "待支付";
//                break;
//            case 7:
//                txt = "预支付";
//                break;
//            case 10:
//            case 20:
//                txt = "已支付";
//                break;
//            default:
//                break;
//        }
//        return txt;
//    }
//    public static Integer fun(CustomObject object){
//        if(object == null)
//            return null;
//        return object.getCnt();
//    }

    public void fun1(){
//        long targetValue = getLongValueByBitList(Arrays.asList(1,2,34));
//        long longValue = getLongValueByBitList(Arrays.asList(1,34));
//        System.out.println("targetValue:" + Long.valueOf(targetValue));
//        System.out.println("rst:" + Long.valueOf(longValue & targetValue).equals(targetValue));
//        List<Integer> numbers = Arrays.asList(null,null);
//        numbers.parallelStream().filter(a -> a != null)
//                .forEach(a ->System.out.println(a));
//        List<CustomObject> list = Arrays.asList(new CustomObject(), new CustomObject(), new CustomObject());
//
//        HashSet<Integer> fitlerSet = new HashSet<>();
//        fitlerSet.add(14002); fitlerSet.add(14003);
        //        List<CustomObject> list1 = list.stream().filter(item -> item != null)
//                .map(aa -> {
//                    CustomObject c = new CustomObject();
//                    c.setCnt(aa);
//                    //if(aa == 1) throw new RuntimeException("1");
//                    return c;
//                })
//                .collect(Collectors.toList());
    }

    public static long getLongValueByBitPos(int bit){
        //位的范围0-62
        if(bit > 62 || bit < 0){
            return 0L;
        }
        return 1L << bit;
    }

    public static long getLongValueByBitList(List<Integer> bits){
        long sum = 0L;
        for (Integer bit: bits) {
            sum += getLongValueByBitPos(bit);
        }
        return  sum;
    }
}
