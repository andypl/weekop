package pl.info.czerniak.weekop.service;

import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.SHA256Digest;
import pl.info.czerniak.weekop.dao.DAOFactory;
import pl.info.czerniak.weekop.dao.UserDAO;
import pl.info.czerniak.weekop.model.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserService {

    public void addUser(String username, String email, String password){
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        String md5Password = encryptPassword(password);
        user.setPassword(md5Password);
        user.setActive(true);
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDAO = factory.getUserDAO();
        userDAO.create(user);
    }

    public User getUserById(Long userId){
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDAO = factory.getUserDAO();
        User user = userDAO.read(userId);
        return user;
    }

    public User getUserByUsername(String username){
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDAO = factory.getUserDAO();
        User user = userDAO.getUserByUsername(username);
        return user;
    }

    private String encryptPassword(String password){
        MessageDigest messageDigest = null;
        try{
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(password.getBytes());
        String md5Password = new BigInteger(1,messageDigest.digest()).toString(16);
        return md5Password;
    }
}
