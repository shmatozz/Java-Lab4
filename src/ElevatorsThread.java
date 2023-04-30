public class ElevatorsThread implements Runnable {
    private final ElevatorsManager manager;  // manager to send requests

    ElevatorsThread(ElevatorsManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        // move elevators while requests or tasks exists
        while (!ElevatorsManager.requestsOver || !(manager.first.targetFloors.isEmpty() && manager.second.targetFloors.isEmpty())) {
            manager.stepElevators();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
