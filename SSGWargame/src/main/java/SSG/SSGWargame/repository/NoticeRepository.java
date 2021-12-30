package SSG.SSGWargame.repository;

import SSG.SSGWargame.domain.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class NoticeRepository {

    @Autowired private EntityManager em;

    public void save(Notice notice){
        em.persist(notice);
    }

    public List<Notice> findAll(){
        return em.createQuery("select n from Notice n", Notice.class)
                .getResultList();
    }

    public void deleteById(Long id) {
        Notice notice = em.find(Notice.class, id);
        em.remove(notice);
    }

    //paging 적용해야 할 듯?

}
