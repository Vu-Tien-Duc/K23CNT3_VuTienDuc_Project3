package k23cnt3.vtdLesson06.service;


import jakarta.transaction.Transactional;
import k23cnt3.vtdLesson06.dto.VtdCustomerDTO;
import k23cnt3.vtdLesson06.entity.VtdCustomer;
import k23cnt3.vtdLesson06.repository.VtdCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VtdCustomerService {

    private final VtdCustomerRepository repo;

    public VtdCustomerService(VtdCustomerRepository repo) {
        this.repo = repo;
    }

    public List<VtdCustomer> findAll() {
        return repo.findAll();
    }

    public Optional<VtdCustomerDTO> findById(Long id) {
        VtdCustomer c = repo.findById(id).orElse(null);
        if (c == null) return Optional.empty();

        VtdCustomerDTO dto = VtdCustomerDTO.builder()
                .id(c.getId())
                .username(c.getUsername())
                .password(c.getPassword())
                .fullName(c.getFullName())
                .address(c.getAddress())
                .phone(c.getPhone())
                .email(c.getEmail())
                .birthDay(c.getBirthDay())
                .active(c.isActive())
                .build();
        return Optional.of(dto);
    }

    public boolean save(VtdCustomerDTO dto) {
        VtdCustomer c = VtdCustomer.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .fullName(dto.getFullName())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .birthDay(dto.getBirthDay())
                .active(dto.isActive())
                .build();
        try {
            repo.save(c);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public VtdCustomer updateById(Long id, VtdCustomerDTO dto) {
        return repo.findById(id)
                .map(c -> {
                    c.setUsername(dto.getUsername());
                    if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
                        c.setPassword(dto.getPassword());
                    }
                    if (dto.getFullName() != null && !dto.getFullName().isEmpty()) c.setFullName(dto.getFullName());
                    if (dto.getAddress() != null && !dto.getAddress().isEmpty()) c.setAddress(dto.getAddress());
                    if (dto.getPhone() != null && !dto.getPhone().isEmpty()) c.setPhone(dto.getPhone());
                    if (dto.getEmail() != null && !dto.getEmail().isEmpty()) c.setEmail(dto.getEmail());
                    if (dto.getBirthDay() != null) c.setBirthDay(dto.getBirthDay());
                    c.setActive(dto.isActive());
                    return repo.save(c);
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID: " + id));
    }



    public void delete(Long id) {
        repo.deleteById(id);
    }
}
