package k23cnt3.vtd.project3.movie.service;

import k23cnt3.vtd.project3.movie.entity.TheLoai;
import k23cnt3.vtd.project3.movie.repository.TheLoaiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TheLoaiService {

    private final TheLoaiRepository theLoaiRepository;

    public TheLoai save(TheLoai theLoai) {
        return theLoaiRepository.save(theLoai);
    }

    public List<TheLoai> findAll() {
        return theLoaiRepository.findAll();
    }

    public Optional<TheLoai> findById(Long id) {
        return theLoaiRepository.findById(id);
    }

    public void delete(Long id) {
        theLoaiRepository.deleteById(id);
    }

    public Optional<TheLoai> findByTen(String ten) {
        return theLoaiRepository.findByTen(ten);
    }
}
