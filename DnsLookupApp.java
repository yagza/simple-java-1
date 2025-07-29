import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;

public class DnsLookupApp {

    public static void main(String[] args) {
        Properties prop = new Properties();
        
        try (InputStream input = DnsLookupApp.class.getClassLoader().getResourceAsStream("DnsLookupApp.properties")) {
            if (input == null) {
                System.err.println("Файл DnsLookupApp.properties не найден!");
                return;
            }
            
            // Загружаем properties
            prop.load(input);
            
            // Получаем список хостов
            String hostsStr = prop.getProperty("hosts");
            if (hostsStr == null || hostsStr.isEmpty()) {
                System.err.println("В файле DnsLookupApp.properties не указаны хосты (hosts)");
                return;
            }
            
            String[] hosts = hostsStr.split(",");
            
            // Выполняем DNS-запросы
            for (String host : hosts) {
                host = host.trim();
                if (!host.isEmpty()) {
                    try {
                        InetAddress[] addresses = InetAddress.getAllByName(host);
                        System.out.println("DNS записи для " + host + ":");
                        for (InetAddress addr : addresses) {
                            System.out.println("  - " + addr.getHostAddress());
                        }
                    } catch (IOException e) {
                        System.err.println("Ошибка при DNS-запросе для " + host + ": " + e.getMessage());
                    }
                }
            }
            
        } catch (IOException e) {
            System.err.println("Ошибка при чтении config.properties: " + e.getMessage());
        }
    }
}
