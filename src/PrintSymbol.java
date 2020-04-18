/**
 * Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок – ABСABСABС). Используйте wait/notify/notifyAll.
 */
public class PrintSymbol {
    // Создаем в классе объект для монитора потоков
    private final Object mon = new Object();
    private volatile char currentLetter = 'A';

    public static void main(String[] args) {
        PrintSymbol printSymbol = new PrintSymbol();
        //Поток для печати символа 'A'
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                //Вызываем метод печати символа 'A'
                printSymbol.printA();
            }
        });
        //Поток для печати символа 'B'
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                //Вызываем метод печати символа 'B'
                printSymbol.printB();
            }
        });
        //Поток для печати символа 'C'
        Thread threadC = new Thread(new Runnable() {
            @Override
            public void run() {
                //Вызываем метод печати символа 'C'
                printSymbol.printC();
            }
        });
        //Запускаем потоки
        threadA.start();
        threadB.start();
        threadC.start();
    }

    /**
     * Метод печати символа 'A'.
     * В роли монитора выступает объект "mon" класса PrintSymbol, в главном методе которого запущены потоки
     * Если текущий символ не 'A' - поток исполнения метода уступает монитор другому потоку и ждёт этот символ.
     * Как только текущий символ становится 'A' - печатаем его, меняем текущий символ на 'B'
     * и освобождаем все потоки методов, которые находятся в wait.
     */
    public void printA() {
        synchronized (mon) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'A') {
                        mon.wait();
                    }
                    System.out.print("A");
                    currentLetter = 'B';
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Метод печати символа 'B'.
     * В роли монитора выступает объект "mon" класса PrintSymbol, в главном методе которого запущены потоки
     * Если текущий символ не 'B' - поток исполнения метода уступает монитор другому потоку и ждёт этот символ.
     * Как только символ становится 'B' - печатаем его, меняем текущий символ на 'C'
     * и освобождаем все потоки методов, которые находятся в wait.
     */
    public void printB() {
        synchronized (mon) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'B') {
                        mon.wait();
                    }
                    System.out.print("B");
                    currentLetter = 'C';
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Метод печати символа 'C'.
     * В роли монитора выступает объект "mon" класса PrintSymbol, в главном методе которого запущены потоки
     * Если текущий символ не 'C' - поток исполнения метода уступает монитор другому потоку и ждёт этот символ.
     * Как только символ становится 'C' - печатаем его, меняем текущий символ на 'A'
     * и освобождаем все потоки методов, которые находятся в wait.
     */
    public void printC() {
        synchronized (mon) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'C') {
                        mon.wait();
                    }
                    System.out.print("C");
                    currentLetter = 'A';
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
