package dal;

import model.Products;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Products;
import model.User;
import org.mindrot.jbcrypt.BCrypt;

public class DAO extends DBContext {

    // Lấy danh sách tất cả sản phẩm
    public List<Products> getAllProducts() {
        List<Products> list = new ArrayList<>();
        String sql = "SELECT * FROM Products";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Products p = new Products(
                        rs.getString("ProductName"),
                        rs.getString("Category"),
                        rs.getString("Images"),
                        rs.getString("Description"),
                        rs.getDouble("price"),
                        rs.getInt("stockQuantity"),
                        rs.getInt("ProductID")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error in getAllProducts: " + e.getMessage());
        }
        return list;
    }

    // Lấy danh mục sản phẩm (không trùng lặp)
    public List<String> getCategory() {
        List<String> listC = new ArrayList<>();
        String sql = "SELECT DISTINCT Category FROM Products";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                listC.add(rs.getString("Category"));
            }
        } catch (SQLException e) {
            System.out.println("Error in getCategory: " + e.getMessage());
        }
        return listC;
    }

    // Lấy danh sách sản phẩm theo danh mục
    public List<Products> getProductByCategory(String category) {
        List<Products> list = new ArrayList<>();
        String sql = "SELECT * FROM Products WHERE Category = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, category);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Products p = new Products(
                        rs.getString("ProductName"),
                        rs.getString("Category"),
                        rs.getString("Images"),
                        rs.getString("Description"),
                        rs.getDouble("price"),
                        rs.getInt("stockQuantity"),
                        rs.getInt("ProductID")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error in getProductByCategory: " + e.getMessage());
        }
        return list;
    }

    // Lấy thông tin chi tiết của một sản phẩm theo ID
    public Products getProductById(int id) {
        String sql = "SELECT * FROM Products WHERE ProductID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Products(
                        rs.getString("ProductName"),
                        rs.getString("Category"),
                        rs.getString("Images"),
                        rs.getString("Description"),
                        rs.getDouble("price"),
                        rs.getInt("stockQuantity"),
                        rs.getInt("ProductID")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error in getProductById: " + e.getMessage());
        }
        return null; // Trả về null nếu không tìm thấy sản phẩm
    }

    // Thêm một sản phẩm mới vào database
    public void addProduct(Products product) {
        String sql = "INSERT INTO Products (ProductName, Category, Images, Description, Price, StockQuantity) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, product.getProductName());
            st.setString(2, product.getCategory());
            st.setString(3, product.getImages());
            st.setString(4, product.getDescription());
            st.setDouble(5, product.getPrice());
            st.setInt(6, product.getStockQuantity());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in addProduct: " + e.getMessage());
        }
    }

    public List<Products> searchByKey(String key) {
        List<Products> pList = new ArrayList<>();
        String sql = "select DISTINCT p.ProductID,p.ProductName,p.Category,p.Price,p.images,p.Description,p.StockQuantity\n"
                + "from  Products p \n"
                + "where 1=1";
        if (key != null) {
            sql += " and p.ProductName LIKE ?";
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "%" + key + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Products p = new Products(
                        rs.getString("ProductName"),
                        rs.getString("Category"),
                        rs.getString("Images"),
                        rs.getString("Description"),
                        rs.getDouble("price"),
                        rs.getInt("stockQuantity"),
                        rs.getInt("ProductID")
                );
                System.out.println("Number of results found: " + pList.size());
                pList.add(p);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return pList;
    }

    // Phương thức kiểm tra đăng nhập
    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");
                if (BCrypt.checkpw(password, hashedPassword)) {
                    return new User(
                            resultSet.getInt("user_id"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            resultSet.getString("role")
                    );
                }
            }
            return null; // Tài khoản không tồn tại hoặc mật khẩu không đúng
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }

    }

    // Phương thức đăng ký người dùng
    public void register(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt())); // Mã hóa mật khẩu
            statement.setString(3, user.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Phương thức lấy tất cả người dùng
    

    // Phương thức xóa người dùng
    public void deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Phương thức cập nhật thông tin người dùng
    public void updateUser(String email, String phone, String address, int id, String name) {
        String sql = "UPDATE users SET email = ?, phone = ?, address = ?, username = ? WHERE user_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, phone);
            statement.setString(3, address);
            statement.setString(4, name);

            statement.setInt(5, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE user_id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("role")
                );
            }

        } catch (SQLException e) {
        }
        return null;
    }

    public User getUserByName(String name) {
        String sql = "SELECT * FROM users WHERE username=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("role")
                );
            }

        } catch (SQLException e) {
        }
        return null;
    }

    public void updateRole(int userId, String newRole) {
        String query = "UPDATE users SET role = ? WHERE user_id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, newRole);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void resetPassword(int userId, String newPassword) {
        // Giả sử bạn đã sử dụng Bcrypt để mã hóa mật khẩu
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        String query = "UPDATE users SET password = ? WHERE user_id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, hashedPassword);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void delete(String name) {
        String sql = "delete from products where ProductName= ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void insertNew(Products p) {
        String sql = "insert into products values(?,?,?,?,?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, p.getProductName());
            st.setString(2, p.getCategory());
            st.setDouble(3, p.getPrice());
            st.setInt(4, p.getStockQuantity());
            st.setString(5, p.getImages());
            st.setString(6, p.getDescription());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    
    // Phương thức lấy tất cả người dùng
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("role")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return users;
    }

   
    
    public void update(Products p) {
        String sql = "UPDATE Products SET ProductName=?, Category=?, Price=?, StockQuantity=?, images=?, Description=? WHERE ProductID=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, p.getProductName());
            st.setString(2, p.getCategory());
            st.setDouble(3, p.getPrice());
            st.setInt(4, p.getStockQuantity());
            st.setString(5, p.getImages());
            st.setString(6, p.getDescription());
            st.setInt(7, p.getProductID());

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in update: " + e.getMessage());
        }
    }

//     public static void main(String[] args) {
//        DAO d = new DAO();
//        List<Products> listC = d.getProductByCategory("lithium");
//        for(Products p :listC){
//             System.out.println(p.getCategory());
//        }
//    }
}
