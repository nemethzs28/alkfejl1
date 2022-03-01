package hu.alkfejl.dao;

import hu.alkfejl.model.SinglePlayer;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SinglePlayerDAOImpl implements SinglePlayerDAO{
    private static final String SELECT_ALL_SINGLEPLAYERS = "SELECT * FROM SINGLEPLAYER";
    private static final String INSERT_SINGLEPLAYER = "INSERT INTO SINGLEPLAYER (name, score) VALUES (?,?)";
    private static final String UPDATE_SINGLEPLAYER = "UPDATE SINGLEPLAYER SET name=?, score=? WHERE id=?";
    private static final String DELETE_SINGLEPLAYER = "DELETE FROM SINGLEPLAYER WHERE id=?";
    private static final String SELECT = "SELECT * FROM SINGLEPLAYER WHERE id=?";
    private Properties props = new Properties();
    private String connectionURL;

    public SinglePlayerDAOImpl() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        try {
            props.load(getClass().getResourceAsStream("/application.properties"));
            connectionURL = props.getProperty("db.url");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<SinglePlayer> findAll() {
        List<SinglePlayer> result = new ArrayList<>();
        try(Connection c = DriverManager.getConnection(connectionURL);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ALL_SINGLEPLAYERS);
        ){
            while(rs.next()) {
                SinglePlayer singlePlayer = new SinglePlayer();
                singlePlayer.setId(rs.getInt("id"));
                singlePlayer.setName(rs.getString("name"));
                singlePlayer.setScore(rs.getInt("score"));

                result.add(singlePlayer);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public SinglePlayer save(SinglePlayer singlePlayer) {
        try(Connection c = DriverManager.getConnection(connectionURL);
            PreparedStatement stmt = singlePlayer.getId() <= 0 ? c.prepareStatement(INSERT_SINGLEPLAYER, Statement.RETURN_GENERATED_KEYS) : c.prepareStatement(UPDATE_SINGLEPLAYER)
        ){
            if(singlePlayer.getId() > 0) {
                stmt.setInt(3, singlePlayer.getId());
            }
            stmt.setString(1, singlePlayer.getName());
            stmt.setInt(2, singlePlayer.getScore());

            int affectedRows = stmt.executeUpdate();
            if(affectedRows == 0) {
                return null;
            }
            if(singlePlayer.getId() == 0) {
                ResultSet genKeys = stmt.getGeneratedKeys();
                if(genKeys.next()) {
                    singlePlayer.setId(genKeys.getInt(1));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

        return singlePlayer;
    }

    @Override
    public void delete(SinglePlayer singlePlayer) {
        try(Connection c = DriverManager.getConnection(connectionURL);
            PreparedStatement stmt = c.prepareStatement(DELETE_SINGLEPLAYER);
        ){
            stmt.setInt(1, singlePlayer.getId());
            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public SinglePlayer find(int id) {
        SinglePlayer player = null;
        try(Connection c = DriverManager.getConnection(connectionURL);
            PreparedStatement stmt = c.prepareStatement(SELECT);

        ){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                SinglePlayer singlePlayer = new SinglePlayer();
                singlePlayer.setId(rs.getInt("id"));
                singlePlayer.setName(rs.getString("name"));
                singlePlayer.setScore(rs.getInt("score"));
                player = singlePlayer;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return player;
    }
}
