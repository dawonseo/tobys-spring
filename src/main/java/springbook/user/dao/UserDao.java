package springbook.user.dao;

import springbook.user.domain.User;

import java.sql.*;

public class UserDao {
    // 인터페이스를 통해 오브젝트를 접근하므로 구체적인 클래스 정보를 알 필요 없다
    private ConnectionMaker connectionMaker;

    // 그런데 여기서 클래스 이름 필요
    public UserDao() {
        connectionMaker = new DConnectionMaker();
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        UserDao dao = new UserDao();

        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + " 조회 성공");
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

