package hu.alkfejl.dao;

import hu.alkfejl.model.MultiPlayer;

import java.util.List;

public interface MultiPlayerDAO {
    List<MultiPlayer> findAll();
    MultiPlayer save(MultiPlayer multiPlayer);
    void delete(MultiPlayer multiPlayer);
    MultiPlayer find(int id);
}
