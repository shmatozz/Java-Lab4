import java.util.*;

public class ElevatorsManager {
    public static Queue<Request> requests = new ArrayDeque<>();
    public ArrayList<Map<Integer, ArrayDeque<Integer>>> floors;
    Elevator first;
    Elevator second;
    ElevatorsManager() {
        first = new Elevator(1);
        second = new Elevator(2);
        floors = new ArrayList<>(16);
        for (int i = 0; i <= 15; i++) {
            Map<Integer, ArrayDeque<Integer>> floorMap = new HashMap<>();
            floorMap.put(-1, new ArrayDeque<>());
            floorMap.put(1, new ArrayDeque<>());
            floors.add(floorMap);
        }
    }

    public synchronized void stepElevators() {
        checkFloor(first);
        checkFloor(second);
        changeElevatorStatus(first);
        changeElevatorStatus(second);
    }

    public int getDist(Request rq, Elevator elevator) {
        int dist = 0;
        if (elevator.status == 0) {
            dist = Math.abs(elevator.currentFloor - rq.start);
        } else if (elevator.status == rq.direction && elevator.status == 1) {

        }
        return dist;
    }

    public void addWaiters(Request rq) {
        floors.get(rq.start).get(rq.direction).add(rq.end);
        if (first.maxTargetFloor.isEmpty() || (first.passengerStatus == rq.direction &&
                ((rq.direction == 1 && first.currentFloor <= rq.start) || (rq.direction == -1 && first.currentFloor > rq.start)))) {
            first.maxTargetFloor.add(rq.start);
            first.passengerStatus = rq.direction;
        } else if (second.maxTargetFloor.isEmpty() || (second.passengerStatus == rq.direction &&
                ((rq.direction == 1 && second.currentFloor <= rq.start) || (rq.direction == -1 && second.currentFloor > rq.start)))) {
            second.maxTargetFloor.add(rq.start);
            second.passengerStatus = rq.direction;
        } else {
            requests.add(rq);
        }
        System.out.println(first.maxTargetFloor);
        System.out.println(second.maxTargetFloor);
    }

    public void checkFloor(Elevator elevator) {
        if (elevator.passengerStatus != 0) {
            while (elevator.passengers.contains(elevator.currentFloor)) {
                elevator.passengers.removeAll(List.of(elevator.currentFloor));
                elevator.maxTargetFloor.removeAll(List.of(elevator.currentFloor));
                System.out.println("❌ Passenger left " + elevator.number + " on floor " + elevator.currentFloor);
                System.out.println(elevator.currentFloor + " " + elevator.maxTargetFloor);
            }

            ArrayDeque<Integer> floorQueue = floors.get(elevator.currentFloor).get(elevator.passengerStatus);
            while (!floorQueue.isEmpty()) {
                System.out.println("✅ Passenger enter " + elevator.number + " on floor " + elevator.currentFloor);
                int currentPassenger = floorQueue.poll();
                elevator.maxTargetFloor.add(currentPassenger);
                elevator.passengers.add(currentPassenger);
                elevator.status = elevator.passengerStatus;
            }
        } else {
            ArrayDeque<Integer> floorQueueUp = floors.get(elevator.currentFloor).get(1);
            ArrayDeque<Integer> floorQueueDown = floors.get(elevator.currentFloor).get(-1);
            ArrayDeque<Integer> floorQueue;
            if (floorQueueUp.size() >= floorQueueDown.size()) {
                floorQueue = floorQueueUp;
                elevator.status = 1;
                elevator.passengerStatus = 1;
            } else {
                floorQueue = floorQueueDown;
                elevator.status = -1;
                elevator.passengerStatus = -1;
            }
            while (!floorQueue.isEmpty()) {
                int currentPassenger = floorQueue.poll();
                elevator.maxTargetFloor.add(currentPassenger);
                elevator.passengers.add(currentPassenger);
                System.out.println("✅ Passenger enter " + elevator.number + " on floor " + elevator.currentFloor);
                System.out.println(elevator.currentFloor + " " + elevator.maxTargetFloor);
            }
        }
    }

    public void changeElevatorStatus(Elevator elevator) {
        if (elevator.maxTargetFloor.isEmpty()) {
            if (requests.isEmpty()) {
                elevator.status = 0;
                elevator.passengerStatus = 0;
            } else {
                Request rq = requests.poll();
                elevator.maxTargetFloor.add(rq.start);
                elevator.passengerStatus = rq.direction;
                elevator.status = elevator.currentFloor < rq.start ? 1 : -1;
                System.out.println("Elevator " + elevator.number + " take req on floor " + rq.start);
            }
        } else if (elevator.maxTargetFloor.peek() == elevator.currentFloor) {
            elevator.maxTargetFloor.removeAll(List.of(elevator.currentFloor));
        } else if (elevator.maxTargetFloor.peek() <= elevator.currentFloor) {
            elevator.status = -1;
        } else {
            elevator.status = 1;
        }
        if (!elevator.maxTargetFloor.isEmpty() && elevator.status == 0) {
            elevator.status = elevator.maxTargetFloor.peek() >= elevator.currentFloor ? 1 : -1;
            System.out.println("\uD83D\uDD04 Elevator " + elevator.number + " get passenger on floor " + elevator.currentFloor);
        } else if (elevator.status == 0) {
            System.out.println("\uD83D\uDCA4 Elevator " + elevator.number + " waiting on floor " + elevator.currentFloor);
        } else if (elevator.status == -1) {
            elevator.currentFloor--;
            System.out.println("\uD83D\uDD3D Elevator " + elevator.number + " going ↓. Current floor = " + elevator.currentFloor);
        } else if (elevator.status == 1 ){
            elevator.currentFloor++;
            System.out.println("\uD83D\uDD3C Elevator " + elevator.number + " going ↑. Current floor = " + elevator.currentFloor);
        }
    }
}
