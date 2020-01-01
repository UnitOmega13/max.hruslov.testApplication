package max.hruslov.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.sun.xml.internal.fastinfoset.util.CharArray;
import lombok.Value;
import max.hruslov.Application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class DBService {
    Properties prop = new Properties();

    public JsonObject getProducts(String arguments){
        JsonObject jsonResponse = new JsonObject();
        try {
            prop.load(Application.class.getClassLoader().getResourceAsStream("application.properties"));
            String DB_URL = prop.getProperty("spring.datasource.url");
            Connection connection = DriverManager.getConnection(DB_URL,"","");
            Statement statement = connection.createStatement();
            ResultSet resultSet;
            String argumentFilter = arguments.replaceAll("\\W", "");
            resultSet = statement.executeQuery("SELECT * FROM products");
            JsonArray data = new JsonArray();
            if (argumentFilter.matches("A-Z")){
                if(!resultSet.getString("name").startsWith(argumentFilter)) {
                    while (resultSet.next()) {
                        JsonArray row = new JsonArray();
                        row.add(new JsonPrimitive(resultSet.getLong("id")));
                        row.add(new JsonPrimitive(resultSet.getString("name")));
                        row.add(new JsonPrimitive(resultSet.getString("description")));
                        data.add(row);
                    }
                }
            }else{
                CharSequence charSequence = argumentFilter;
                if(!resultSet.getString("name").contains(charSequence)) {
                    while (resultSet.next()) {
                        JsonArray row = new JsonArray();
                        row.add(new JsonPrimitive(resultSet.getLong("id")));
                        row.add(new JsonPrimitive(resultSet.getString("name")));
                        row.add(new JsonPrimitive(resultSet.getString("description")));
                        data.add(row);
                    }
                }
            }
            jsonResponse.add("products", data);
            connection.close();
        } catch (Exception e) {
            System.err.println("Error! ");
            System.err.println(e.getMessage());
        }
        return jsonResponse;
    }
}
