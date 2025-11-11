package k23cnt3.vtdLesson06.controller;


import k23cnt3.vtdLesson06.dto.VtdCustomerDTO;
import k23cnt3.vtdLesson06.service.VtdCustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/customers")
public class VtdCustomerController {

    private final VtdCustomerService service;

    public VtdCustomerController(VtdCustomerService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("customers", service.findAll());
        return "customers/list";
    }

    @GetMapping("/add")
    public String formAdd(Model model) {
        model.addAttribute("customer", new VtdCustomerDTO());
        return "customers/add";
    }

    @PostMapping("/add")
    public String saveAdd(@Valid @ModelAttribute("customer") VtdCustomerDTO dto, BindingResult result) {
        if (result.hasErrors()) return "customers/add";
        service.save(dto);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String formEdit(@PathVariable Long id, Model model) {
        VtdCustomerDTO dto = service.findById(id).orElse(new VtdCustomerDTO());
        model.addAttribute("customer", dto);
        return "customers/edit";
    }

    @PostMapping("/edit")
    public String saveEdit(@ModelAttribute("customer") VtdCustomerDTO dto, BindingResult result) {

        // Nếu password trống, bỏ qua validation
        if (dto.getPassword() != null && dto.getPassword().isEmpty()) {
            dto.setPassword(null); // đánh dấu không update password
        }

        // Thực hiện update
        service.updateById(dto.getId(), dto);
        return "redirect:/customers";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/customers";
    }
}
