package dal;

import model.Products;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("Category"),
                        rs.getString("Images"),
                        rs.getString("Description"),
                        rs.getDouble("price"),
                        rs.getInt("stockQuantity")
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
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("Category"),
                        rs.getString("Images"),
                        rs.getString("Description"),
                        rs.getDouble("price"),
                        rs.getInt("stockQuantity")
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
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("Category"),
                        rs.getString("Images"),
                        rs.getString("Description"),
                        rs.getDouble("price"),
                        rs.getInt("stockQuantity")
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
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("Category"),
                        rs.getString("Images"),
                        rs.getString("Description"),
                        rs.getDouble("price"),
                        rs.getInt("stockQuantity")
                );
                System.out.println("Number of results found: " + pList.size());
                pList.add(p);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return pList;
    }
}
