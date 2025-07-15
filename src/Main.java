import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Main {
    private static Semaphore[] massive = new Semaphore[5];
    private static Semaphore waiter = new Semaphore(4);
    public static void main(String[] args) {
    //    Semaphore[] massive = new Semaphore[5];
        for (int i = 0; i < 5; i++) {
           massive[i] = new Semaphore(1, true);
        }
        for (int i = 0; i < 5; i++) {
            int number = i;
            new Thread(() -> {
                eat(number);
            }).start();
        }
    }
    public static void eat(int number){
        int index1 = number;
        int index2 = (number+1)%massive.length;
        int j = 0;
        while (true) {
            try {
                waiter.acquire();
                massive[index1].acquire();
                System.out.println("философ" + number + "взял вилку" + index1);
                massive[index2].acquire();
                System.out.println("философ " + number + "взял вилку" + index2);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(500);
                System.out.println("философ " + number + "освободил вилку" + index1);
                System.out.println("философ " + number + "освободил вилку" + index2);
                massive[index1].release();
                massive[index2].release();
                waiter.release();
                System.out.println("поток " + number + "поел");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

