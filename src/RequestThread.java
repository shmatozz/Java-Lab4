import java.util.Random;

public class RequestThread implements Runnable {
    private final ElevatorsManager manager;

    RequestThread(ElevatorsManager manager) {
        this.manager = manager;
    }
    @Override
    public void run() {
        Random random = new Random(0);
        for (int i = 0; i < 15; i++) {
            var start = random.nextInt(0, 15 + 1);
            var end = random.nextInt(0, 15 + 1);
            while (start == end) {
                end = random.nextInt(0, 15 + 1);
            }
            var direction = start > end ? -1 : 1;
            this.manager.addWaiters(new Request(start, end, direction));
            System.out.println("\uD83D\uDCE9 New request on " + start + " floor, " + "direction " + (direction == 1 ? "up" : "down"));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for (int i = 0; i <= 15; i++) {
            System.out.println(manager.floors.get(i).toString());
        }
        System.out.println(manager.first.passengers);
        System.out.println(manager.second.passengers);
    }
}