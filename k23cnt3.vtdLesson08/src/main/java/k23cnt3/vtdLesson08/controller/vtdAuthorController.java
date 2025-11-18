package k23cnt3.vtdLesson08.controller;

import k23cnt3.vtdLesson08.entity.vtdAuthor;
import k23cnt3.vtdLesson08.service.vtdAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
public class vtdAuthorController {

    @Autowired
    private vtdAuthorService authorService;

    // Hiển thị danh sách Author
    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        return "authors/author-list";
    }

    // Form tạo mới
    @GetMapping("/create")
    public String createAuthorForm(Model model) {
        model.addAttribute("author", new vtdAuthor());
        return "authors/author-form";
    }

    // Form cập nhật
    @GetMapping("/edit/{id}")
    public String editAuthorForm(@PathVariable Long id, Model model) {
        vtdAuthor author = authorService.getAuthorById(id);
        if (author == null) {
            return "redirect:/authors";
        }
        model.addAttribute("author", author);
        return "authors/author-form";
    }

    // Lưu dữ liệu (Thêm mới + Cập nhật)
    @PostMapping("/save")
    public String saveAuthor(@ModelAttribute("author") vtdAuthor author) {
        authorService.saveAuthor(author);
        return "redirect:/authors";
    }

    // Xóa Author
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }
}
