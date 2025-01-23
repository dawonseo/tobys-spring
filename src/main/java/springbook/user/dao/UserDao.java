package springbook.user.dao;

import springbook.user.domain.User;

import java.sql.*;

public class UserDao {
    // 인터페이스를 통해 오브젝트를 접근하므로 구체적인 클래스 정보를 알 필요 없다
    private ConnectionMaker connectionMaker;

    // 외부에서 주입 (생성자 주입)
    public UserDao(ConnectionMaker connectionMaker) {
        connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        // 인터페이스에 정의된 메소드 사용하므로 클래스 바뀐다 해도 메소드 이름 변경될 걱정 없다
        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement(
                "insert into users(id, name, password) value(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement(
                "select * from users where id = ?"
        );
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }
}

