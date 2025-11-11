package k23cnt3.vtdLesson05.controller;

import k23cnt3.vtdLesson05.entity.Info;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    @GetMapping
    public String index(){
        return "index";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        List<Info> profile = new ArrayList<>();

        profile.add(new Info("Vũ Tiến Đức",
                "master",
                "vduc@masterz.edu.vn",
                "https://masterz.edu.vn"));

        model.addAttribute("vtdProfile", profile);
        return "profile";
    }
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("title", "Master::Trang chủ");
        return "index";
    }
    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }
    @GetMapping("/contact")
    public String contact(Model model) {
        return "contact";
    }
    @GetMapping("/services")
    public String services(Model model) {
        return "services";
    }

}
