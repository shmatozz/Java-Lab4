public class ElevatorsThread implements Runnable {
    private final ElevatorsManager manager;
    ElevatorsThread(ElevatorsManager manager) {
        this.manager = manager;
    }
    @Override
    public void run() {
        while (!ElevatorsManager.requestsOver || !(manager.first.targetFloors.isEmpty() && manager.second.targetFloors.isEmpty())) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            manager.stepElevators();
        }
        System.out.println("\nCURRENT BUILDING INFO: ");
        for (int i = manager.floorsCount; i >= 0; i--) {
            System.out.println("Floor " + i + " " + manager.floors.get(i).toString());
        }
        System.out.println("First elevator " + manager.first.passengers);
        System.out.println("Second elevator " + manager.second.passengers);
    }
}
