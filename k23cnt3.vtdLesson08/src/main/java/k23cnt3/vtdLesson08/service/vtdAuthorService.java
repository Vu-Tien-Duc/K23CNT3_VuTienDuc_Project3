package k23cnt3.vtdLesson08.service;

import k23cnt3.vtdLesson08.entity.vtdAuthor;
import k23cnt3.vtdLesson08.repositoty.vtdAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class vtdAuthorService {
    @Autowired
    private vtdAuthorRepository authorRepository;
    public List<vtdAuthor> getAllAuthors() {
        return authorRepository.findAll();
    }
    public vtdAuthor saveAuthor(vtdAuthor author) {
        return authorRepository.save(author);
    }
    public vtdAuthor getAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
    public List<vtdAuthor> findAllById(List<Long> ids) {
        return authorRepository.findAllById(ids);
    }
}
