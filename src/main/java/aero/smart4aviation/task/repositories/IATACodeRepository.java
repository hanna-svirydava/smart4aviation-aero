package aero.smart4aviation.task.repositories;

import java.util.List;

public interface IATACodeRepository {
    /**
     * Save list of IATA codes.
     *
     * @param codeList is a list of code objects
     */
    void save(List<String> codeList);

    /**
     * Check if such IATA code exists.
     *
     * @param code is IATA code
     * @return true if code exists, otherwise false
     */
    boolean existByCode(String code);
}
