package de.dhbw.erpbackend.repository;

import de.dhbw.erpbackend.domain.Log;
import de.dhbw.erpbackend.domain.User;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Insert;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;

import java.util.List;

@Repository
public interface LogRepository extends CrudRepository<Log, Long> {

    @Find
    List<Log> findByUser(User user);

    // Explicit INSERT (logs are always new) — same H2-MERGE avoidance as the other repos.
    @Insert
    Log insert(Log log);

    @Query("select count(l) from Log l")
    long countAll();

    @Query("""
            from Log l
            where l.description ilike concat('%', :q, '%')
               or l.user.username ilike concat('%', :q, '%')
            order by l.created desc
            """)
    Page<Log> search(String q, PageRequest pageRequest);

    @Query("""
            select count(l) from Log l
            where l.description ilike concat('%', :q, '%')
               or l.user.username ilike concat('%', :q, '%')
            """)
    long countSearch(String q);
}
