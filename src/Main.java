import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    static AtomicInteger length3 = new AtomicInteger(0);
    static AtomicInteger length4 = new AtomicInteger(0);
    static AtomicInteger length5 = new AtomicInteger(0);

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (texts[i].equals("aaa")) {
                    length3.addAndGet(1);
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (texts[i].equals("abba")) {
                    length4.addAndGet(1);
                }
            }
        });
        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (texts[i].equals("aaccc")) {
                    length5.addAndGet(1);
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("Красивых слов  длиной 3: " + length3);
        System.out.println("Красивых слов  длиной 4: " + length4);
        System.out.println("Красивых слов  длиной 5: " + length5);


    }
}