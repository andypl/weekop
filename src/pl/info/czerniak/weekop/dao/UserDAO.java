package pl.info.czerniak.weekop.dao;

import pl.info.czerniak.weekop.model.User;

import java.util.List;

public interface UserDAO extends GenericDAO<User, Long>{

    List<User> getAll();
    User getUserByUsername(String username);
}
