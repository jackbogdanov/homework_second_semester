package Exceptions;

public class OutOfFiledException extends Exception {

    @Override
    public void printStackTrace() {
        System.out.println("OUT OF FIELD!");
        super.printStackTrace();
    }
}
