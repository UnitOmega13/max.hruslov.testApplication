package max.hruslov.controller;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import max.hruslov.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class Controller {

    private DBService dbService;

    @Autowired
    public void ProductRepositoryDI(DBService dbService){
        this.dbService = dbService;
    }

    @RequestMapping("/product?nameFilter={arguments}")
    public JsonObject getProducts(@PathVariable("arguments") String arguments){
        return dbService.getProducts(arguments);
    }
}
