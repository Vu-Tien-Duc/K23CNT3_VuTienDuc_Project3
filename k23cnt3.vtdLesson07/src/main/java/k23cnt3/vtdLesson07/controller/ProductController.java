package k23cnt3.vtdLesson07.controller;

import k23cnt3.vtdLesson07.entity.Product;
import k23cnt3.vtdLesson07.service.ProductService;
import k23cnt3.vtdLesson07.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // Hiển thị danh sách sản phẩm
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product/product-list"; // file Thymeleaf
    }

    // Hiển thị form tạo mới sản phẩm
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/product-form";
    }

    // Hiển thị form chỉnh sửa sản phẩm
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id).orElse(null);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/product-form";
    }

    // Lưu hoặc cập nhật sản phẩm
    @PostMapping("/create")
    public String saveProduct(@ModelAttribute Product product,
                              @RequestParam(value = "imageFile", required = false) MultipartFile file) throws IOException {

        if (file != null && !file.isEmpty()) {
            // Lưu file vào folder /uploads
            String uploadDir = "uploads/";
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) uploadFolder.mkdirs();

            String fileName = file.getOriginalFilename();
            file.transferTo(new File(uploadDir + fileName));

            // Lưu đường dẫn ảnh vào product.imageUrl
            product.setImageUrl("/" + uploadDir + fileName);
        }

        productService.saveProduct(product);
        return "redirect:/products";
    }

    // Cập nhật sản phẩm
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id,
                                @ModelAttribute Product product,
                                @RequestParam(value = "imageFile", required = false) MultipartFile file) throws IOException {

        product.setId(id);

        if (file != null && !file.isEmpty()) {
            String uploadDir = "uploads/";
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) uploadFolder.mkdirs();

            String fileName = file.getOriginalFilename();
            file.transferTo(new File(uploadDir + fileName));
            product.setImageUrl("/" + uploadDir + fileName);
        }

        productService.saveProduct(product);
        return "redirect:/products";
    }

    // Xóa sản phẩm
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
