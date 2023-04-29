public class ElevatorsThread implements Runnable {
    private final ElevatorsManager manager;
    ElevatorsThread(ElevatorsManager manager) {
        this.manager = manager;
    }
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            manager.stepElevators();
        }
    }
}
