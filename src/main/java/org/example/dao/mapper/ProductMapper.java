package org.example.dao.mapper;

import com.google.gson.Gson;
import org.example.model.Info;
import org.example.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {

        Product product = new Product(
                rs.getString("name"),
                rs.getString("product_url"),
                rs.getDouble("price"),
                rs.getInt("quantity"),
                List.of(new Gson().fromJson(rs.getString("info"), Info[].class)),
                rs.getInt("category_id"),
                rs.getDouble("discount")
        );
        product.setId(rs.getInt("id"));
        return product;
    }
}
