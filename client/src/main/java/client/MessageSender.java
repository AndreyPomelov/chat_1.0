package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Класс для отправки сообщений на сервер.
 */
public class MessageSender extends Thread {

    /**
     * Исходящий поток данных для отправки сообщений на сервер.
     */
    private final DataOutputStream OUT;

    /**
     * Конструктор.
     * При создании объекта он автоматически стартует в новом потоке
     * и назначает себя демоном, для того чтобы данный поток автоматически
     * завершал свою работу при завершении работы приложения.
     *
     * @param out исходящий поток данных для отправки сообщений на сервер.
     */
    public MessageSender(DataOutputStream out) {
        this.OUT = out;
        this.setDaemon(true);
        this.start();
    }

    /**
     * Отправка сообщений на сервер.
     */
    @Override
    public void run() {
        System.out.println("Запущен режим отправки сообщений. Вводите Ваши сообщения в консоль.");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                OUT.writeUTF(scanner.nextLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}