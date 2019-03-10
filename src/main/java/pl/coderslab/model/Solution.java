package pl.coderslab.model;

import pl.coderslab.DbUtil;


import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;
import java.util.List;

public class Solution {

    private int id;
    private Date created;
    private Date updated;
    private String description;
    private int exercise_id;
    private int users_id;

    public static List<Solution> loadAll(Integer limit){
        List<Solution> solutions = new ArrayList<>();
        try(Connection connection = DbUtil.getConn()){
            PreparedStatement st = connection.prepareStatement("SELECT * FROM solutions ORDER BY updated DESC LIMIT ?");
            st.setInt(1, limit);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Solution s = new Solution();
                s.id = rs.getInt("id");
                s.description = rs.getString("description");
                s.created = rs.getDate("created");
                s.updated = rs.getDate("updated");
                s.users_id = rs.getInt("user_id");
                s.exercise_id = rs.getInt("exercise_id");
//                System.out.println(s.id + ": " + s.description + ". Created: " + s.created + ". Updated: " + s.updated + ". USER ID: " + s.users_id + ". EXERCISE: " + s.exercise_id);
                solutions.add(s);

            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return solutions;
    }

    public Solution[] loadByUserId(int userID){
        Solution[] solutions = new Solution[0];
        try (Connection connection = DbUtil.getConn()){
            String getAllUserSolutions = "SELECT * FROM solution WHERE users_id = ?";
            PreparedStatement preSt = connection.prepareStatement(getAllUserSolutions);
            preSt.setInt(1, userID);
            ResultSet rs = preSt.executeQuery();
            if (rs.next()){
                Solution s = new Solution();
                s.id = rs.getInt("id");
                s.description = rs.getString("description");
                solutions = Arrays.copyOf(solutions, solutions.length +1);
                solutions[solutions.length-1] = s;
                System.out.println(s.id + ": " + s.description);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return solutions;

    }

    public Solution[] loadAllByExerciseId(int exerciseID){
        Solution[] solutions = new Solution[0];
        try (Connection connection = DbUtil.getConn()){
            String getAllExercise = "SELECT * FROM solution WHERE exercise_id = ?";
            PreparedStatement preSt = connection.prepareStatement(getAllExercise);
            preSt.setInt(1, exerciseID);
            ResultSet rs = preSt.executeQuery();
            if (rs.next()){
                Solution s = new Solution();
                s.id = rs.getInt("id");
                s.description = rs.getString("description");
                solutions = Arrays.copyOf(solutions, solutions.length +1);
                solutions[solutions.length-1] = s;
                System.out.println(s.id + ": " + s.description);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return solutions;
    }


    public void saveToDB(){
        if (this.id == 0){
            try (Connection connection = DbUtil.getConn()){
                String insertQuery = "Insert into solution (created, updated, description, exercise_id, users_id) values (NOW(), NOW(), ?, ?, ?)";
                PreparedStatement preSt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                preSt.setString(1, this.description);
                preSt.setInt(3, this.users_id);
                preSt.setInt(2, this.exercise_id);

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


    public Solution[] loadAll(){
        Solution[] solutions = new Solution[0];
        try(Connection connection = DbUtil.getConn()){
            String selectAll = "SELECT * FROM solution;";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(selectAll);
            while (rs.next()){
                Solution s = new Solution();
                s.id = rs.getInt("id");
                s.description = rs.getString("description");
                s.created = rs.getDate("created");
                s.updated = rs.getDate("updated");
                s.users_id = rs.getInt("users_id");
                s.exercise_id = rs.getInt("exercise_id");
                System.out.println(s.id + ": " + s.description + ". Created: " + s.created + ". Updated: " + s.updated + ". USER ID: " + s.users_id + ". EXERCISE: " + s.exercise_id);
                solutions = Arrays.copyOf(solutions, solutions.length +1);
                solutions[solutions.length-1] = s;

            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return solutions;
    }

    public void modifySolution(){
        if (this.id != 0) {
            try (Connection connection = DbUtil.getConn()){
                String modifySolution = "UPDATE solution SET updated = NOW(), description = ?, exercise_id = ?, users_id = ? where id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(modifySolution);
                preparedStatement.setString(1, this.description);
                preparedStatement.setInt(2, this.exercise_id);
                preparedStatement.setInt(3, this.users_id);
                preparedStatement.setInt(4, this.id);
                preparedStatement.executeUpdate();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }


    public static Solution loadByID (int id){
        Solution s = new Solution();
        try (Connection connection = DbUtil.getConn()){
            String selectByID = "SELECT * FROM solutions WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectByID);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                s.id = rs.getInt("id");
                s.description = rs.getString("description");
                s.created = rs.getDate("created");
                s.updated = rs.getDate("updated");
                s.exercise_id = rs.getInt("exercise_id");
                s.users_id = rs.getInt("user_id");
            }

            return s;

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return s;
    }


    public void deleteSolution(int solutionID) {
        if (solutionID != 0) {
            try (Connection connection = DbUtil.getConn()) {
                String deleteSolution = "DELETE FROM solution WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSolution);
                preparedStatement.setInt(1, solutionID);
                preparedStatement.executeUpdate();
                this.id = 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * Gettery & Settery
     */

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public int getId() {
        return id;
    }

    public void setID(int id){this.id = id;}

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public String getDescription() {
        return description;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public int getUsers_id() {
        return users_id;
    }
}
