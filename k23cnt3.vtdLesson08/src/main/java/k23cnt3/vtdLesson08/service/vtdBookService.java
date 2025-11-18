package k23cnt3.vtdLesson08.service;

import k23cnt3.vtdLesson08.entity.vtdBook;
import k23cnt3.vtdLesson08.repositoty.vtdBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class vtdBookService {
    @Autowired
    private vtdBookRepository bookRepository;
    public List<vtdBook> getAllBooks() {
        return bookRepository.findAll();
    }
    public vtdBook saveBook(vtdBook book) {
        return bookRepository.save(book);
    }
    public vtdBook getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
