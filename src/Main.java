import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Default elevator time per floor is 1000ms = 1s");
        Scanner scanner = new Scanner(System.in);

        // input settings of elevators
        System.out.print("Input floors count: ");
        var floorsCount = scanner.nextInt();
        System.out.print("Input requests count: ");
        var requestsCount = scanner.nextInt();
        System.out.print("Input requests interval (ms): ");
        var requestsInterval = scanner.nextInt();

        // init elevators manager, requests and elevators threads
        ElevatorsManager manager = new ElevatorsManager(floorsCount);
        Thread requests = new Thread(new RequestThread(manager, requestsCount, requestsInterval));
        Thread elevators = new Thread(new ElevatorsThread(manager));
        requests.start();
        elevators.start();
        try {
            requests.join();
            elevators.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\nCURRENT BUILDING INFO: ");
        for (int i = manager.floorsCount; i >= 0; i--) {
            System.out.println("Floor " + i + " " + manager.floors.get(i).toString());
        }
        System.out.println("First elevator " + manager.first.passengers);
        System.out.println("Second elevator " + manager.second.passengers);
        System.out.println("Requests queue " + ElevatorsManager.requests);
    }
}