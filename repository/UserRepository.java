package repository;

import domain.User;

public interface UserRepository {

    User save(User user);

    User findById(int id);

    User findByEmail(String email);
}

