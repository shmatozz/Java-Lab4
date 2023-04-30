## Java-Lab4
This lab includes working with threads and simulation of working two elevators system.

---

To start elevators system you should input parameter of simulation (floors count, requests count and requests period).

```java
System.out.print("Input floors count: ");
var floorsCount = scanner.nextInt();
System.out.print("Input requests count: ");
var requestsCount = scanner.nextInt();
System.out.print("Input requests interval (ms): ");
var requestsInterval = scanner.nextInt();
```

Initializing elevators manager and start requests and elevators threads.

```java
ElevatorsManager manager = new ElevatorsManager(floorsCount);
Thread requests = new Thread(new RequestThread(manager, requestsCount, requestsInterval));
Thread elevators = new Thread(new ElevatorsThread(manager));

requests.start();
elevators.start();
```
Working logs will be output in the console.

---
Working example:

```
Default elevator time per floor is 1000ms = 1s
> Input floors count: 5
> Input requests count: 2
> Input requests interval (ms): 1000
💤 Elevator 1 waiting on floor 0
💤 Elevator 2 waiting on floor 0
📩 New request on 4 floor, direction down
📩 New request on 1 floor, direction up
🔼 Elevator 1 going ↑. Current floor = 1
💤 Elevator 2 waiting on floor 0
🔼 Elevator 1 going ↑. Current floor = 2
🔼 Elevator 2 going ↑. Current floor = 1
✅ Passenger enter elevator 2 on floor 1
🔼 Elevator 1 going ↑. Current floor = 3
🔼 Elevator 2 going ↑. Current floor = 2
🔼 Elevator 1 going ↑. Current floor = 4
🔼 Elevator 2 going ↑. Current floor = 3
✅ Passenger enter elevator 1 on floor 4
🔽 Elevator 1 going ↓. Current floor = 3
🔼 Elevator 2 going ↑. Current floor = 4
🔽 Elevator 1 going ↓. Current floor = 2
🔼 Elevator 2 going ↑. Current floor = 5
❌ 1 Passenger(s) left elevator 1 on floor 2
❌ 1 Passenger(s) left elevator 2 on floor 5
💤 Elevator 1 waiting on floor 2
💤 Elevator 2 waiting on floor 5

[SOME INFO TO CHECK IF ALL FLOORS AND ELEVATORS ARE EMPTY...]

Process finished with exit code 0
```
