package data;

import java.sql.*;
import java.util.ArrayList;

public class UserDB {
    private static String url = "jdbc:postgresql://localhost:5432/unitTest";
    private static String username = "postgres";
    private static String password = "root";

    public ArrayList<User> select() {
        ArrayList<User> users = new ArrayList<User>();
        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try(Connection conn = DriverManager.getConnection(url, username, password)) {
                Statement statement = conn.createStatement();
                ResultSet set = statement.executeQuery("SELECT * FROM users");
                while (set.next()){
                    int id = set.getInt(1);
                    String name = set.getString(2);
                    String login = set.getString(3);
                    String pass = set.getString(4);
                    User user = new User(id, name, login, pass);
                    users.add(user);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return users;
    }

    public static User selectOne(String log, String pass) {

        User user = null;
        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                String sql = "SELECT * FROM users WHERE login=?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setString(1, log);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()){

                        int user_id = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        String login = resultSet.getString(3);
                        String password = resultSet.getString(4);
                        if(password.equals(pass)){
                            user = new User(user_id, name, login,password);
                        }
                        else{
                            user = null;
                        }
                    }
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return user;
    }

    public int insert(User user) {

        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                String sql = "INSERT INTO users (name, login, pass) Values (?, ?, ?)";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setString(1, user.getName());
                    preparedStatement.setString(2, user.getLogin());
                    preparedStatement.setString(3, user.getPassword());

                    return  preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }
}
