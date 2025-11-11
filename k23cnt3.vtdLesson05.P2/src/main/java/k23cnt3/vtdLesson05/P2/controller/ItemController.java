package k23cnt3.vtdLesson05.P2.controller;

import k23cnt3.vtdLesson05.P2.entity.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class ItemController {

    private List<Item> itemList = new ArrayList<>();

    public ItemController() {
        itemList.add(new Item("I001", "Laptop Dell", 1500.0));
        itemList.add(new Item("I002", "Chuột Logitech", 25.0));
        itemList.add(new Item("I003", "Bàn phím Mechanical", 70.0));
    }

    // Trang chủ index.html
    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(Model model) {
        return "index"; // message sẽ hiển thị từ application.properties
    }

    // Trang danh sách home.html
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("list", itemList);
        return "home";
    }

    // --- Thêm ---
    @GetMapping("/admin/them")
    public String formThem(Model model) {
        model.addAttribute("item", new Item());
        return "them";
    }

    @PostMapping("/admin/them")
    public String saveThem(@ModelAttribute Item item) {
        itemList.add(item);
        return "redirect:/home";
    }

    // --- Sửa ---
    @GetMapping("/admin/sua/{maItem}")
    public String formSua(@PathVariable String maItem, Model model) {
        Item item = itemList.stream()
                .filter(i -> i.getMaItem().equals(maItem))
                .findFirst()
                .orElse(new Item());
        model.addAttribute("item", item);
        return "sua";
    }

    @PostMapping("/admin/sua")
    public String saveSua(@ModelAttribute Item item) {
        itemList.stream()
                .filter(i -> i.getMaItem().equals(item.getMaItem()))
                .forEach(i -> {
                    i.setNameItem(item.getNameItem());
                    i.setPrice(item.getPrice());
                });
        return "redirect:/home";
    }

    // --- Xóa ---
    @GetMapping("/admin/xoa/{maItem}")
    public String xoa(@PathVariable String maItem) {
        itemList.removeIf(i -> i.getMaItem().equals(maItem));
        return "redirect:/home";
    }
}
