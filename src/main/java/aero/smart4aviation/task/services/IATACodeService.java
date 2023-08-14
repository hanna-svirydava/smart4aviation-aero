package aero.smart4aviation.task.services;

import aero.smart4aviation.task.repositories.IATACodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IATACodeService {
    private final IATACodeRepository repository;

    /**
     * Upload list of codes.
     *
     * @param codeList is a list of codes
     */
    public void upload(List<String> codeList) {
        repository.save(codeList);
    }
}
