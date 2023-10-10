import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    static AtomicInteger length3 = new AtomicInteger(0);
    static AtomicInteger length4 = new AtomicInteger(0);
    static AtomicInteger length5 = new AtomicInteger(0);

    static boolean isPalindrom(String word) {
        if (word.length() % 2 != 0) return false;
        int i1 = 0;
        int i2 = word.length() - 1;
        while (i2 > i1) {
            if (word.charAt(i1) != word.charAt(i2)) {
                return false;
            }
            i1++;
            i2--;
        }
        return true;
    }

    static boolean isSimilar(String word) {
        char[] arr = word.toCharArray();
        for (char c : arr) {
            if (c != word.charAt(0)) {
                return false;
            }
        }
        return true;
    }

    static boolean isAscending(String word) {
        for (int i = 1; i < word.length(); i++) {
            if (word.charAt(i) == word.charAt(i - 1)
                    || word.charAt(i) - word.charAt(i - 1) == 1) {

            } else {
                return false;
            }
        }
        return true;
    }

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
                if (texts[i].length() == 3) {
                    if (isAscending(texts[i]) || isPalindrom(texts[i]) || isSimilar(texts[i])) {
                        length3.addAndGet(1);
                    }
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (texts[i].length() == 4) {
                    if (isAscending(texts[i]) || isPalindrom(texts[i]) || isSimilar(texts[i])) {
                        length4.addAndGet(1);
                    }
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (texts[i].length() == 5) {
                    if (isAscending(texts[i]) || isPalindrom(texts[i]) || isSimilar(texts[i])) {
                        length5.addAndGet(1);
                    }
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