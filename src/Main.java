public class Main {
    public static void main(String[] args) {
        ElevatorsManager manager = new ElevatorsManager();
        Thread requests = new Thread(new RequestThread(manager));
        Thread elevators = new Thread(new ElevatorsThread(manager));
        requests.start();
        elevators.start();
    }
}