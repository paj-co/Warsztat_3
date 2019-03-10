package pl.coderslab.model;

import pl.coderslab.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserGroup {

    private int id;
    private String name;


    public void saveToDB(){
        if (this.id == 0){
            try (Connection connection = DbUtil.getConn()){
                String insertQuery = "Insert into user_group (name) values (?)";
                PreparedStatement preSt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                preSt.setString(1, this.name);
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

    //Zmienione
    public static List<UserGroup> loadAll(){
        List<UserGroup> groups = new ArrayList<>();
        try(Connection connection = DbUtil.getConn()){
            String selectAll = "SELECT * FROM users_group";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(selectAll);
            while (rs.next()){
                UserGroup group = new UserGroup();
                group.id = rs.getInt("id");
                group.name = rs.getString("name");
                groups.add(group);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return groups;
    }


    //oryginalne
//    public UserGroup[] loadAll(){
//        UserGroup[] groups = new UserGroup[0];
//        try(Connection connection = DbUtil.getConn()){
//            String selectAll = "SELECT * FROM user_group;";
//            Statement st = connection.createStatement();
//            ResultSet rs = st.executeQuery(selectAll);
//            while (rs.next()){
//                UserGroup group = new UserGroup();
//                group.id = rs.getInt("id");
//                group.name = rs.getString("name");
//                System.out.println(group.id + ": " + group.name);
//                groups = Arrays.copyOf(groups, groups.length + 1);
//                groups[groups.length -1] = group;
//            }
//        } catch (SQLException e){
//            e.printStackTrace();
//        }
//
//        return groups;
//    }

    public UserGroup loadUserByID (int id){
        UserGroup group = new UserGroup();
        try (Connection connection = DbUtil.getConn()){
            String selectByID = "SELECT * FROM user_group WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectByID);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                group.id = rs.getInt("id");
                group.name = rs.getString("name");
                System.out.println("Group " + group.name + " . ID: " + group.id);
            }

            return group;

        } catch (SQLException e){
            e.printStackTrace();
        }

        return group;
    }


    public void deleteGroup(int groupToDelete){
        if (groupToDelete != 0){
            try (Connection connection = DbUtil.getConn()){
                String deleteGroupSQL = "DELETE FROM user_group WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteGroupSQL);
                preparedStatement.setInt(1, groupToDelete);
                preparedStatement.executeUpdate();
                this.id = 0;
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void modifyGroup(){
        if (this.id != 0) {
            try (Connection connection = DbUtil.getConn()){
                String modifyGroup = "UPDATE user_group SET name = ? where id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(modifyGroup);
                preparedStatement.setString(1, this.name);
                preparedStatement.setInt(2, this.id);
                preparedStatement.executeUpdate();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }


    /**
     *
     * Gettery & Settery
     */

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
