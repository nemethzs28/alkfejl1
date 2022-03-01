package hu.alkfejl.dao;

import hu.alkfejl.model.MultiPlayer;
import hu.alkfejl.model.SinglePlayer;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MultiPlayerDAOImpl implements MultiPlayerDAO{
    private static final String SELECT_ALL_MULTIPLAYERS = "SELECT * FROM MULTIPLAYER";
    private static final String DELETE_MULTIPLAYER = "DELETE FROM MULTIPLAYER WHERE id=?";
    private static final String UPDATE_MUTLIPLAYER = "UPDATE MULTIPLAYER SET name1=?, score1=?, score2=?, name2=? WHERE id=?";
    private static final String INSERT_MULTIPLAYER = "INSERT INTO MULTIPLAYER (name1, score1, score2, name2) VALUES (?,?,?,?)";
    private static final String SELECT = "SELECT * FROM MULTIPLAYER WHERE id=?";
    private Properties props = new Properties();
    private String connectionURL;
    public MultiPlayerDAOImpl() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        try {
            props.load(getClass().getResourceAsStream("/application.properties"));
            connectionURL = props.getProperty("db.url");
        } catch (IOException e) {
            //TODO:logging
            e.printStackTrace();
        }
    }
    @Override
    public List<MultiPlayer> findAll() {
        List<MultiPlayer> result = new ArrayList<>();
        try(Connection c = DriverManager.getConnection(connectionURL);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ALL_MULTIPLAYERS);
        ){
            while(rs.next()) {
                MultiPlayer multiPlayer = new MultiPlayer();
                multiPlayer.setId(rs.getInt("id"));
                multiPlayer.setName1(rs.getString("name1"));
                multiPlayer.setName2(rs.getString("name2"));
                multiPlayer.setScore1(rs.getInt("score1"));
                multiPlayer.setScore2(rs.getInt("score2"));

                result.add(multiPlayer);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public MultiPlayer save(MultiPlayer multiPlayer) {
        try(Connection c = DriverManager.getConnection(connectionURL);
            PreparedStatement stmt = multiPlayer.getId() <= 0 ? c.prepareStatement(INSERT_MULTIPLAYER, Statement.RETURN_GENERATED_KEYS) : c.prepareStatement(UPDATE_MUTLIPLAYER)
        ){
            if(multiPlayer.getId() > 0) {
                stmt.setInt(5, multiPlayer.getId());
            }
            stmt.setString(1, multiPlayer.getName1());
            stmt.setInt(2, multiPlayer.getScore1());
            stmt.setInt(3, multiPlayer.getScore2());
            stmt.setString(4, multiPlayer.getName2());

            int affectedRows = stmt.executeUpdate();
            if(affectedRows == 0) {
                return null;
            }
            if(multiPlayer.getId() == 0) {
                ResultSet genKeys = stmt.getGeneratedKeys();
                if(genKeys.next()) {
                    multiPlayer.setId(genKeys.getInt(1));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

        return multiPlayer;
    }

    @Override
    public void delete(MultiPlayer multiPlayer) {
        try(Connection c = DriverManager.getConnection(connectionURL);
            PreparedStatement stmt = c.prepareStatement(DELETE_MULTIPLAYER);
        ){
            stmt.setInt(1, multiPlayer.getId());
            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public MultiPlayer find(int id) {
        MultiPlayer player = null;
        try(Connection c = DriverManager.getConnection(connectionURL);
            PreparedStatement stmt = c.prepareStatement(SELECT);

        ){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                MultiPlayer multiPlayer = new MultiPlayer();
                multiPlayer.setId(rs.getInt("id"));
                multiPlayer.setName1(rs.getString("name1"));
                multiPlayer.setScore1(rs.getInt("score1"));
                multiPlayer.setScore2(rs.getInt("score2"));
                multiPlayer.setName2(rs.getString("name2"));
                player = multiPlayer;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return player;
    }
}
