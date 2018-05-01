package Cars;

public abstract class AbstractCar {

    protected String model;
    protected String color;

    protected int serialNumber;
    protected double averageSpeed;

    public AbstractCar(String model, String color, int serialNumber, int type, double averageSpeed) {
        this.model = model;
        this.color = color;
        this.serialNumber = serialNumber;
        this.averageSpeed = averageSpeed;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public abstract void printInfo();
}
