package pl.info.czerniak.weekop.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import pl.info.czerniak.weekop.model.User;
import pl.info.czerniak.weekop.util.ConnectionProvider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO{

    private final static String CREATE_USER = "INSERT INTO users(username, email, password, is_active) VALUES(:username, :email, :password, :active)";
    private final static String READ_USER = "SELECT user_id, username, email, password, is_active FROM users WHERE user_id = :id;";
    private static final String READ_USER_BY_USERNAME = "SELECT user_id, username, email, password, is_active FROM users WHERE username = :username;";
    private NamedParameterJdbcTemplate template;

    public UserDAOImpl() {
        this.template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());

    }

    @Override
    public User create(User user) {
        User resultUser = new User(user);
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        int update = template.update(CREATE_USER, parameterSource, holder, new String[] {"user_id"});
        if(update > 0){
            resultUser.setId(holder.getKey().longValue());
            setPrivileges(resultUser);
        }
        return resultUser;
    }

    private void setPrivileges(User user) {
        final String userRoleQuery = "INSERT INTO user_role(username, role_name) VALUES(:username, 'user')";
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        template.update(userRoleQuery, parameterSource);
        System.out.println(parameterSource.getValue("username"));
    }

    @Override
    public User read(Long primaryKey) {

        User resultUser = null;
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", primaryKey);
        resultUser = template.queryForObject(READ_USER, parameterSource, new UserRowMapper());
        return resultUser;
    }

    @Override
    public boolean update(User updateObject) {
        return false;
    }

    @Override
    public boolean delete(Long key) {
        return false;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        User resultUser = null;
        SqlParameterSource parameterSource = new MapSqlParameterSource("username", username);
        resultUser = template.queryForObject(READ_USER_BY_USERNAME, parameterSource, new UserRowMapper());
        return resultUser;
    }

    private class UserRowMapper implements RowMapper<User>{

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setUsername(resultSet.getString("username"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            return user;
        }
    }
}
