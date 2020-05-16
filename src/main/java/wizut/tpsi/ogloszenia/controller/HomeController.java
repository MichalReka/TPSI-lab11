package wizut.tpsi.ogloszenia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wizut.tpsi.ogloszenia.jpa.CarManufacturer;
import wizut.tpsi.ogloszenia.jpa.CarModel;
import wizut.tpsi.ogloszenia.services.OffersService;

import java.sql.SQLException;

@Controller
public class HomeController {
    @Autowired
    private OffersService offersService;
    @RequestMapping("/")
    public String home(Model model) throws SQLException {
        CarModel carModel = offersService.getModel(3);
        CarManufacturer carManufacturer = offersService.getCarManufacturer(2);
        model.addAttribute("carModel", carModel);
        model.addAttribute("carManufacturer",carManufacturer);
        return "index";
    }
}