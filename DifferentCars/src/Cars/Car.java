package Cars;

public class Car extends AbstractCar {

    public Car(String model, String color, int serialNumber, int type, double averageSpeed) {
        super(model, color, serialNumber, type, averageSpeed);
    }

    @Override
    public void printInfo() {

        System.out.println("Model - " + model);
        System.out.println("Color - " + color);
        System.out.println("Serial number - " + serialNumber);
        System.out.println("Average speed - " + averageSpeed);
    }
}
