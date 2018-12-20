import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/8.
 */
public class CustomObject implements Serializable{
    private String key;
    private Integer cnt;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public CustomObject() {
    }

    public CustomObject(Integer cnt){
        this.cnt = cnt;
    }
    public CustomObject(String key,Integer cnt){
        this.key = key;
        this.cnt = cnt;
    }


}
