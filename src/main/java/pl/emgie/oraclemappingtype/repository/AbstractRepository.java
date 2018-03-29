package pl.emgie.oraclemappingtype.repository;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
public class AbstractRepository<S> {

    @PersistenceContext(unitName = "oracleTypes")
    EntityManager entityManager;

    public S save(S entity) {
        entityManager.persist(entity);
        return entity;
    }
}
