package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final List<User> users = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public synchronized User save(User user) {
        if (user.getId() == 0) {
            user.setId(idGenerator.getAndIncrement());
        } else {
            users.removeIf(u -> u.getId() == user.getId());
        }
        users.add(user);
        return user;
    }

    @Override
    public synchronized User findById(int id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public synchronized User findByEmail(String email) {
        if (email == null) {
            return null;
        }
        return users.stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
    }
}

