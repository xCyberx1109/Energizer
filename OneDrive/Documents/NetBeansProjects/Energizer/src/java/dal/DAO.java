package dal;

import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Products;

public class DAO extends DBContext {

    //Get products
    public List<Products> getAllProducts() {
        List<Products> list = new ArrayList<>();
        String sql = "select * from Products";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Products p = new Products(rs.getString("productName"), rs.getString("category"),
                        rs.getString("images"), rs.getString("description"), rs.getDouble("price"),
                        rs.getInt("StockQuantity"));
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<String> getCategory() {
        List<String> listC = new ArrayList<>();
        String sql = "select DISTINCT Category from Products";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                listC.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listC;
    }

    public List<Products> searchByKey(String key) {
        List<Products> pList = new ArrayList<>();
        String sql = "select i.InventoryID,i.ProductID,i.SupplierID,i.StockLevel,\n"
                + "	p.ProductName,p.Category,p.Price,p.images,p.Description\n"
                + "from Inventory i join Products p on i.ProductID=p.ProductID";
        if(key!=null){
            sql+=" and p.ProductName LIKE '%"+key+"%'";
        }
        try {
            PreparedStatement st=connection.prepareStatement(sql);
            ResultSet rs=st.executeQuery();
            while(rs.next()){
                Products p=new Products(rs.getString("productName"),rs.getString("category"),
                        rs.getString("images"),
                        rs.getString("description"),rs.getDouble("price"),
                        rs.getInt("StockQuantity"));
                pList.add(p);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
       return pList;
    }

    public List<Products> getProductByCategory(String category) {
        List<Products> list = new ArrayList<>();
        String sql = "select [productName],[category],[images],[description],[price],[StockQuantity]\nfrom Products\nwhere category=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, category);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Products p = new Products(rs.getString("productName"), rs.getString("category"),
                        rs.getString("images"), rs.getString("description"), rs.getDouble("price"),
                        rs.getInt("StockQuantity"));
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

//     public static void main(String[] args) {
//        DAO d = new DAO();
//        List<Products> listC = d.getProductByCategory();
//        for(Products p :listC){
//             System.out.println(p);
//        }
//
//
//    }
}
