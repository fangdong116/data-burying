/**
 * Created by qingyang on 2017/9/4.
 */
public class LeftMoneyPackage {

    public Integer remainSize;

    public Integer remainMoney;

    public LeftMoneyPackage(Integer remainSize, Integer remainMoney) {
        this.remainSize = remainSize;
        this.remainMoney = remainMoney;
    }

    public Integer getRemainSize() {
        return remainSize;
    }

    public void setRemainSize(Integer remainSize) {
        this.remainSize = remainSize;
    }

    public Integer getRemainMoney() {
        return remainMoney;
    }

    public void setRemainMoney(int remainMoney) {
        this.remainMoney = remainMoney;
    }
}
