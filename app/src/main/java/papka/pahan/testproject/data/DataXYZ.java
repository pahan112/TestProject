package papka.pahan.testproject.data;

/**
 * Created by admin on 17.06.2017.
 */

public class DataXYZ {

    private String x;
    private String y;
    private String z;
    private transient String time;

    public DataXYZ() {
    }

    public DataXYZ(String x, String y, String z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setX(String x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
    }

    public void setZ(String z) {
        this.z = z;
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

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "DataXYZ{" +
                "x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", z='" + z + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
