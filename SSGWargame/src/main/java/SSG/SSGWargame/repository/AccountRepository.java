package SSG.SSGWargame.repository;

import SSG.SSGWargame.domain.Account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountRepository {
    @Autowired private EntityManager em;

    public void save(Account account){ // /account/ post
        em.persist(account);
        //idx 자동으로 채워짐.
    }

    public Optional<Account> findOne(Long idx){ // /account?idx={idx} get
        return Optional.ofNullable(em.find(Account.class, idx));
    }

    public List<Account> findAll(){ // /account get
        return em.createQuery("select a from Account a", Account.class)
                .getResultList();
    }

    public Optional<Account> findById(String Id) { // account/get?id={id}
        return em.createQuery("select a from Account a where a.username = :Id", Account.class)
                .setParameter("Id", Id)
                .getResultList()
                .stream().findAny();
        //findAny vs findFirst : parallel 연산(멀티스레드)에서 차이가 존재한다.
        //자바의 멀티스레드에 대해서는 추후에 알아보자.
        //https://codechacha.com/ko/java8-stream-difference-findany-findfirst/
    }

    public void deleteByIdx(Long idx){ // account/{id}
        Optional<Account> account = findOne(idx);
        if(account.isPresent()) {
            em.remove(account.get());
        }
    }

    //우선, id로 delete하는 것은 안 만든다. 어디에서 에러가 났는지 확인이 어렵다.
/*    public void deleteById(String Id){
        Optional<Account> account = findById(Id);
        em.remove(account); //영속성 컨텍스트에서 제거됨.
    }*/


}
