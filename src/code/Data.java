package code;
import java.util.HashMap;
import java.util.Map;

public class Data {
    private Map<String, String> dataUser;


    public Data() {
        dataUser = new HashMap<>();
    }


    public void addUser(String username, String password) {
        dataUser.put(username, password);
    }

    // Method untuk memeriksa apakah username dan password cocok
    public boolean isMember(String username, String password) {
        String storedPassword = dataUser.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }
}
