package wizut.tpsi.ogloszenia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;
import wizut.tpsi.ogloszenia.jpa.*;
import wizut.tpsi.ogloszenia.services.OffersService;
import wizut.tpsi.ogloszenia.web.OfferFilter;
import wizut.tpsi.ogloszenia.web.OfferSorter;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private OffersService offersService;

    private int page;
    @RequestMapping("/")
    public String home(Model model, OfferFilter offerFilter,OfferSorter offerSorter){
        return "redirect:/offersList/1";
    }
    @GetMapping("/offersList/{page}")
    public String offerList(Model model, OfferFilter offerFilter, OfferSorter offerSorter, @PathVariable("page") Integer page) {
        List<CarManufacturer> carManufacturers = offersService.getCarManufacturers();
        List<CarModel> carModels = offersService.getCarModels();
        List<Offer> offers;
        if(offerSorter.getSortOffer()!=null)
        {
            offersService.setSortOffer(offerSorter.getSortOffer());
        }
        if(offerFilter.getManufacturerId()!=null) {
            offers = offersService.getOffersByManufacturer(offerFilter.getManufacturerId(),page);
        } else {
            offers = offersService.getOffers(page);
        }
        int pageCount=offersService.getNumberOfPages();
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("carManufacturers", carManufacturers);
        model.addAttribute("carModels", carModels);
        model.addAttribute("offers", offers);
        model.addAttribute("currentPage",page);
        return "offersList";
    }
    @GetMapping("/offer/{id}")
    public String offerDetails(Model model, @PathVariable("id") Integer id) {
        Offer offer = offersService.getOffer(id);
        model.addAttribute("offer", offer);
        return "offerDetails";
    }
    @GetMapping("/newoffer")
    public String newOfferForm(Model model, Offer offer, HttpSession session) {
        if(session.getAttribute("currentUser")!=null)
        {
                List<CarModel> carModels = offersService.getCarModels();
                List<BodyStyle> bodyStyles = offersService.getBodyStyles();
                List<FuelType> fuelTypes = offersService.getFuelTypes();
                model.addAttribute("header", "Nowe ogłoszenie");
                model.addAttribute("action", "/newoffer");
                model.addAttribute("carModels", carModels);
                model.addAttribute("bodyStyles", bodyStyles);
                model.addAttribute("fuelTypes", fuelTypes);
                return "offerForm";
        }
        return "redirect:/offersList/1";
    }
    @PostMapping("/newoffer")
    public String saveNewOffer(Model model, @Valid Offer offer, BindingResult binding) {
        if(binding.hasErrors()) {
            List<CarModel> carModels = offersService.getCarModels();
            List<BodyStyle> bodyStyles = offersService.getBodyStyles();
            List<FuelType> fuelTypes = offersService.getFuelTypes();
            model.addAttribute("header", "Nowe ogłoszenie");
            model.addAttribute("action", "/newoffer");
            model.addAttribute("carModels", carModels);
            model.addAttribute("bodyStyles", bodyStyles);
            model.addAttribute("fuelTypes", fuelTypes);
            return "offerForm";
        }
        offer.setDateTime(LocalDate.now());
        offer = offersService.createOffer(offer);
        return "redirect:/offer/" + offer.getId();
    }
    @RequestMapping("/deleteoffer/{id}")
    public String deleteOffer(Model model, @PathVariable("id") Integer id, HttpSession session) {
        if(session.getAttribute("currentUser")!=null)
        {
            User loggedUser=(User) session.getAttribute("currentUser");
            if(loggedUser.getId()==offersService.getOffer(id).getUserId()) {
                Offer offer = offersService.deleteOffer(id);
                model.addAttribute("offer", offer);
                return "deleteOffer";
            }
        }
        return "redirect:/offersList/1";
    }
    @GetMapping("/editoffer/{id}")
    public String editOffer(Model model, @PathVariable("id") Integer id, HttpSession session) {

        if(session.getAttribute("currentUser")!=null)
        {
            User loggedUser=(User) session.getAttribute("currentUser");
            if(loggedUser.getId()==offersService.getOffer(id).getUserId()) {
                List<CarModel> carModels = offersService.getCarModels();
                List<BodyStyle> bodyStyles = offersService.getBodyStyles();
                List<FuelType> fuelTypes = offersService.getFuelTypes();

                model.addAttribute("carModels", carModels);
                model.addAttribute("bodyStyles", bodyStyles);
                model.addAttribute("fuelTypes", fuelTypes);
                Offer offer = offersService.getOffer(id);
                model.addAttribute("offer", offer);
                model.addAttribute("header", "Edycja ogłoszenia");
                model.addAttribute("action", "/editoffer/" + id);
                return "offerForm";
            }
        }
        return "redirect:/offersList/1";
    }
    @PostMapping("/editoffer/{id}")
    public String saveEditedOffer(Model model, @PathVariable("id") Integer id, @Valid Offer offer, BindingResult binding , HttpSession session) {
        if(binding.hasErrors()) {
            model.addAttribute("header", "Edycja ogłoszenia");
            model.addAttribute("action", "/editoffer/" + id);

            List<CarModel> carModels = offersService.getCarModels();
            List<BodyStyle> bodyStyles = offersService.getBodyStyles();
            List<FuelType> fuelTypes = offersService.getFuelTypes();

            model.addAttribute("carModels", carModels);
            model.addAttribute("bodyStyles", bodyStyles);
            model.addAttribute("fuelTypes", fuelTypes);

            return "offerForm";
        }
        offer.setDateTime(LocalDate.now());
        offersService.saveOffer(offer);
        return "redirect:/offer/" + offer.getId();
    }
}