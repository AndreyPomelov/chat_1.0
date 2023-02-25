package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static constants.Commands.EXIT;
import static constants.Constants.*;

/**
 * Клиентская часть сетевого чата.
 *
 * @author  Andrey Pomelov
 * @version 1.0
 */
public class Client {

    /**
     * Клиентский сокет.
     * Инициализируется путём запроса на серверный сокет сервера.
     */
    private static Socket socket;

    /**
     * Входящий поток данных для приёма сообщений от сервера.
     */
    private static DataInputStream in;

    /**
     * Точка старта приложения. Запускает соединение с сервером.
     */
    public static void main(String[] args) {
        try {
            connect();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                // Игнорируем ошибку закрытия сокета
            }
        }
    }

    /**
     * Метод подключается к серверной части, инициализирует потоки ввода-вывода
     * и запускает режимы отправки и приёма сообщений в разных потоках.
     *
     * @throws IOException ошибка ввода-вывода
     */
    private static void connect() throws IOException {
        socket = new Socket(IP_ADDRESS, PORT);
        in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        System.out.println("Соединение с сервером успешно установлено.");

        // Запускаем отправку и получение сообщений.
        new MessageSender(out);
        receive();
    }

    /**
     * Получение сообщений от сервера.
     *
     * @throws IOException ошибка ввода-вывода
     */
    private static void receive() throws IOException {
        while (true) {
            String message = in.readUTF();
            System.out.println(message);

            if (message.contains(EXIT)) {
                break;
            }
        }
    }
}