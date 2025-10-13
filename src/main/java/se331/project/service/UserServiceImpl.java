package se331.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se331.project.dao.UserDao;
import se331.project.entity.User;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final UserDao userDao;

    @Override
    public Optional<User> findById(Long id){
        return userDao.findById(id);
    }

    @Override
    public List<User> findAllUsers(){
        return userDao.findAllUsers();
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    @Transactional
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    @Transactional
    public User save(User user) {
        return userDao.save(user);
    }
}