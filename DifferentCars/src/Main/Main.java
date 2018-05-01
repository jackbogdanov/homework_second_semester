package Main;

import Cars.Bus;
import Cars.Truck;

public class Main {

    public static void main(String[] args) {

        Truck truck = new Truck("BMV", "RED", 222, 0, 60.557, 300);
        Bus bus = new Bus("BMV", "BLUE", 004, 24, 85.235, 35);

        bus.printInfo();
        truck.printInfo();
    }
}
