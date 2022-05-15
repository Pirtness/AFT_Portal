package dataBase.repository.error;

import dataBase.entity.error.ErrorTemplates;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ErrorTemplatesRepository extends JpaRepository<ErrorTemplates, Long> {
    List<ErrorTemplates> findAllById(Long id);

    List<ErrorTemplates> findAll();

    @Query(value = " select et.id as id_template, " +
            " et.error_template, string_agg(rfttet.id_test\\:\\:text, ', ') as test_ids," +
            " " +
            " et.description " +
            " from portal.error_template et" +
            " join portal.report_failed_test_to_error_template rfttet " +
            " on et.id = rfttet.id_template" +
            " group by et.id, et.error_template, et.description " +
            " order by count(rfttet.id_test) desc", nativeQuery = true)
    ArrayList<Map<String, Object>> getErrorTemplateTestGroups();

}
