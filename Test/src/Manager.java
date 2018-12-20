/**
 * Created by qingyang on 2017/5/24.
 */
public class Manager {
    private static  Manager instance;
    private Manager(){

    }
    private static Manager newInstance(){
        if(null == instance){
            synchronized (Manager.class){
                if(null == instance){
                    return new Manager();
                }
            }
        }
        return instance;
    }
}
