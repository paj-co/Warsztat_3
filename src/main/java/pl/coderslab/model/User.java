package pl.coderslab.model;

import pl.coderslab.DbUtil;


import java.sql.*;
import java.util.Arrays;

public class User {

    private int id;
    private String firstName;
    private String password;
    private String email;
    private int user_group_id;

    public User(String firstName, String password, String email) {
        this.firstName = firstName;
        this.email = email;
        this.setPassword(password);
    }

    public User() {

    }

    /**
     *
     * GETTERY & SETTERY
     *
     */

    public void setID(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUser_group_id() {
        return user_group_id;
    }

    public void setUser_group_id(int user_group_id) {
        this.user_group_id = user_group_id;
    }

    /**
     *
     * Operacje bazodanowe dla ActiveRecord
     *
     */

    public void saveToDB(){
        if (this.id == 0){
            try (Connection connection = DbUtil.getConn()){
                String insertQuery = "Insert into user (email, password, firstname, user_group_id) values (?, ?, ?, ?)";
                PreparedStatement preSt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                preSt.setString(1, this.email);
                preSt.setString(2, this.password);
                preSt.setString(3, this.firstName);
                preSt.setInt(4, this.user_group_id);

                preSt.executeUpdate();
                ResultSet rs = preSt.getGeneratedKeys();
                if (rs.next()){
                    this.id = rs.getInt(1);
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public static User[] loadAllbyGroupID(int user_group_id){
        User[] users = new User[0];
        try (Connection c = DbUtil.getConn()){
            String selectAllByGroupID = "SELECT * FROM user WHERE user_group_id = ?";
            PreparedStatement preSt = c.prepareStatement(selectAllByGroupID);
            preSt.setInt(1, user_group_id);
            ResultSet rs = preSt.executeQuery();
            if(rs.next()){
                User user = new User();
                user.id = rs.getInt("id");
                user.email = rs.getString("email");
                user.firstName = rs.getString("firstName");
                user.user_group_id = rs.getInt("user_group_id");
                users = Arrays.copyOf(users, users.length+1);
                users[users.length-1] =  user;

            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return users;
    }

    public User[] loadAllUsers(){
        User[] allUsers = new User[0];
        try(Connection connection = DbUtil.getConn()){
            String selectAll = "SELECT * FROM user;";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(selectAll);
            while (rs.next()){
                User user = new User();
                user.id = rs.getInt("id");
                user.email = rs.getString("email");
                user.firstName = rs.getString("firstName");
                System.out.println("ID: " + user.id + ". E-mail: " + user.email + ". Imię: " + user.firstName);
                allUsers = Arrays.copyOf(allUsers, allUsers.length + 1);
                allUsers[allUsers.length -1] = user;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return allUsers;
    }

    public User loadUserByID (int id){
        User user = new User();
        try (Connection connection = DbUtil.getConn()){
            String selectByID = "SELECT * FROM user WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectByID);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                user.id = rs.getInt("id");
                user.firstName = rs.getString("firstname");
                user.email = rs.getString("email");
                user.password = rs.getString("password");
                System.out.println("user" + this.id + " - " + this.firstName + " " + this.email + " " + this.password);
            }

            return user;

        } catch (SQLException e){
            e.printStackTrace();
        }

        return user;
    }

    public void modifyUser(){
        if (this.id != 0) {
            try (Connection connection = DbUtil.getConn()){
                String modifyUser = "UPDATE user SET firstname = ?, email = ?, password = ?, user_group_id = ? where id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(modifyUser);
                preparedStatement.setString(1, this.firstName);
                preparedStatement.setString(2, this.email);
                preparedStatement.setString(3, this.password);
                preparedStatement.setInt(4, this.user_group_id);
                preparedStatement.setInt(5, this.id);
                preparedStatement.executeUpdate();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void deleteUser(int toDelete){
        if (toDelete != 0){
            try (Connection connection = DbUtil.getConn()){
                String deleteUserSQL = "DELETE FROM user WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteUserSQL);
                preparedStatement.setInt(1, toDelete);
                preparedStatement.executeUpdate();
                this.id = 0;
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
