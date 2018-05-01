package Cars;

public class Bus extends Car {

    private int numOfPassengers;

    public Bus(String model, String color, int serialNumber, int type, double averageSpeed, int numOfPassengers) {
        super(model, color, serialNumber, type, averageSpeed);

        this.numOfPassengers = numOfPassengers;
    }

    @Override
    public void printInfo() {
        System.out.println("-------- Bus info --------");
        super.printInfo();

        System.out.println("Num of passengers - " + numOfPassengers);
    }
}
