package aero.smart4aviation.task.repositories.inmemory;

import aero.smart4aviation.task.repositories.IATACodeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class IATACodeImMemoryRepository implements IATACodeRepository {
    private final Set<String> codes = ConcurrentHashMap.newKeySet();

    public void save(List<String> codeList) {
        codes.addAll(codeList);
    }

    public boolean existByCode(String code) {
        return codes.contains(code);
    }
}
