package papka.pahan.testproject.data;

/**
 * Created by admin on 17.06.2017.
 */

public class DataXYZ {
    private String x;
    private String y;
    private String z;
    private String time;


    public DataXYZ() {

    }

    public DataXYZ(String x, String y, String z, String time) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.time = time;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getZ() {
        return z;
    }

    public String getTime() {
        return time;
    }
}
