import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Elevator {
    public int number;
    public int capacity;
    public int currentFloor;
    public ArrayDeque<Integer> maxTargetFloor;
    public int status; // 1 = up, -1 = down, 0 = waiting;
    public List<Integer> passengers;
    public int passengerStatus; // 1 = request up, 0 = none, -1 request down;`

    Elevator(int number) {
        this.number = number;
        capacity = 12;
        currentFloor = 0;
        maxTargetFloor = new ArrayDeque<>();
        status = 0;
        passengers = new ArrayList<>();
        passengerStatus = 0;
    }

    public int getMax() {
        int max = -1;
        for (Integer integer : maxTargetFloor) {
            if (integer > max) {
                max = integer;
            }
        }
        return max;
    }
    public int getMin() {
        int min = 21;
        for (Integer integer : maxTargetFloor) {
            if (integer > min) {
                min = integer;
            }
        }
        return min;
    }
}
