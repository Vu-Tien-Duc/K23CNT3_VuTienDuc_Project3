package k23cnt3.vtd.project3.movie.controller;

import k23cnt3.vtd.project3.movie.entity.Phim;
import k23cnt3.vtd.project3.movie.entity.TheLoai;
import k23cnt3.vtd.project3.movie.service.BinhLuanService;
import k23cnt3.vtd.project3.movie.service.PhimService;
import k23cnt3.vtd.project3.movie.service.TheLoaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PhimService phimService;
    private final TheLoaiService theLoaiService;
    private final BinhLuanService binhLuanService;

    @GetMapping("/")
    public String home(Model model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "8") int size,
                       @RequestParam(required = false) String keyword,
                       @RequestParam(required = false) Integer namPhatHanh,
                       @RequestParam(required = false) Double minRating) {

        Page<Phim> phimPage = phimService.searchPhim(
                keyword,
                null, // chưa lọc thể loại tạm thời
                namPhatHanh,
                minRating,
                PageRequest.of(page, size)
        );

        // Tính trung bình đánh giá cho từng phim
        phimPage.getContent().forEach(phim -> {
            Double avg = binhLuanService.getAverageRatingByPhim(phim.getId());
            phim.setAvgRating(avg != null ? avg : 0.0);
        });

        List<TheLoai> theLoais = theLoaiService.findAll();

        model.addAttribute("phimPage", phimPage);
        model.addAttribute("theLoais", theLoais);
        model.addAttribute("keyword", keyword != null ? keyword : "");
        model.addAttribute("namPhatHanh", namPhatHanh);
        model.addAttribute("minRating", minRating);

        return "home"; // template home.html
    }
}
