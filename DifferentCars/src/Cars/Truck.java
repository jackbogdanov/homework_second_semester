package Cars;

public class Truck extends Car {

    protected int capacity;

    public Truck(String model, String color, int serialNumber, int type, double averageSpeed, int capacity) {
        super(model, color, serialNumber, type, averageSpeed);

        this.capacity = capacity;
    }

    @Override
    public void printInfo() {
        System.out.println("------- Truck info -------");
        super.printInfo();
        System.out.println("Capacity - " + capacity);
    }
}
