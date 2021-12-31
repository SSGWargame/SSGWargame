package SSG.SSGWargame.repository;

import SSG.SSGWargame.domain.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class NoticeRepository {

    @Autowired private EntityManager em;

    public void save(Notice notice){
        em.persist(notice);
    }

    public Optional<Notice> findOne(Long id) {
        return Optional.ofNullable(em.find(Notice.class, id));
    }

    //paging 적용해야 할 듯? -> 나중에.
    public List<Notice> findAll(){
        return em.createQuery("select n from Notice n", Notice.class)
                .getResultList();
    }

    public void deleteById(Long id) {
        Notice notice = findOne(id).orElseThrow(IllegalAccessError::new);
        em.remove(notice);
    }

}
