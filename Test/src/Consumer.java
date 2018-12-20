import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/4/17.
 */
public class Consumer{
    private Integer id;
    private Integer count;
    private ReentrantLock lock = new ReentrantLock();

    public Consumer(Integer id, Integer count){
        this.count = count;
        this.id =id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
