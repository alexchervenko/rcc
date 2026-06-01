package io.student.rcc.config;

public class ClientFactory {
    
    private static ClientFactory INSTANCE;
    
    public static ClientFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClientFactory();
        }
        return INSTANCE;
    }
    
    // Заглушка для UsersClient - в реальном приложении здесь будет логика
    // для создания или получения конкретной реализации
    public Object getUsersClient() {
        // Возвращаем заглушку - в реальном приложении здесь будет:
        // 1. Проверка конфигурации
        // 2. Создание соответствующего клиента (API, мок и т.д.)
        return new Object();
    }
}