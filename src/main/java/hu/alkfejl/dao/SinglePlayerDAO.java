package hu.alkfejl.dao;

import hu.alkfejl.model.SinglePlayer;

import java.util.List;

public interface SinglePlayerDAO {
    List<SinglePlayer> findAll();
    SinglePlayer save(SinglePlayer singlePlayer);
    void delete(SinglePlayer singlePlayer);
    SinglePlayer find(int id);
}
