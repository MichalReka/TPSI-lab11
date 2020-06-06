package wizut.tpsi.ogloszenia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wizut.tpsi.ogloszenia.jpa.*;
import wizut.tpsi.ogloszenia.services.OffersService;
import wizut.tpsi.ogloszenia.services.UsersService;
import wizut.tpsi.ogloszenia.web.OfferFilter;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UsersService usersService;
    private User currentUser;
    @GetMapping("/login")
    public String loginForm(Model model,User user){
        return "loginForm";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        currentUser=null;
        session.removeAttribute("currentUser");
        return "redirect:/offersList/1";
    }
    @PostMapping("/login")
    public String userLogin(Model model, User user, HttpSession session) throws SQLException {
        currentUser=usersService.loginUser(user);
        if(currentUser!=null)
        {
            session.setAttribute("currentUser",currentUser);
            return "redirect:/offersList/1";
        }
        return "loginForm";
    }


}
