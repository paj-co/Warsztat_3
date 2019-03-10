package pl.coderslab.model;

import pl.coderslab.DbUtil;

import java.sql.*;
import java.util.Arrays;

public class Exercise {

    private int id;
    private String title;
    private String description;

    public void saveToDB(){
        if (this.id == 0){
            try (Connection connection = DbUtil.getConn()){
                String insertQuery = "Insert into exercise (title, description) values (?, ?)";
                PreparedStatement preSt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                preSt.setString(1, this.title);
                preSt.setString(2, this.description);
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

    public Exercise[] loadAll(){
        Exercise[] exercise = new Exercise[0];
        try(Connection connection = DbUtil.getConn()){
            String selectAll = "SELECT * FROM exercise;";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(selectAll);
            while (rs.next()){
                Exercise e = new Exercise();
                e.id = rs.getInt("id");
                e.title = rs.getString("title");
                e.description = rs.getString("description");
                System.out.println(e.id + " " + e.title + " " + e.description);
                exercise = Arrays.copyOf(exercise, exercise.length +1);
                exercise[exercise.length-1] = e;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return exercise;
    }

    public Exercise loadByID (int id){
        Exercise e = new Exercise();
        try (Connection connection = DbUtil.getConn()){
            String selectByID = "SELECT * FROM exercise WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectByID);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                e.id = rs.getInt("id");
                e.title = rs.getString("title");
                e.description = rs.getString("description");
                System.out.println(e.title + ". ID: " + e.id + ". DESC: " + e.description);
            }

            return e;

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return e;
    }

    public Exercise loadByIDWithoutExercise (int id){
        Exercise e = new Exercise();
        try (Connection connection = DbUtil.getConn()){
            String selectByID = "SELECT * FROM exercise \n" +
                    "WHERE id NOT IN (SELECT exercise_id FROM solution JOIN exercise ON solution.exercise_id = exercise.id WHERE users_id = ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(selectByID);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                e.id = rs.getInt("id");
                e.title = rs.getString("title");
                e.description = rs.getString("description");
                System.out.println(e.title + ". ID: " + e.id + ". DESC: " + e.description);
            }

            return e;

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return e;
    }


    public void deleteExercise(){
        if (this.id != 0){
            try (Connection connection = DbUtil.getConn()){
                String deleteGroupSQL = "DELETE FROM exercise WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteGroupSQL);
                preparedStatement.setInt(1, this.id);
                preparedStatement.executeUpdate();
                this.id = 0;
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * Gettery & Settery
     */

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
