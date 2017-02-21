package org.openmhealth.dsu.repository;

import org.openmhealth.schema.domain.ork.Signature;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * A repository of data points.
 *
 * @see org.springframework.data.repository.CrudRepository
 * @author Anders Borch
 */
@NoRepositoryBean
public interface PrivateRepository extends Repository<Signature, String>, CustomPrivateRepository {

    boolean exists(String id);

    Optional<Signature> findOne(String id);

    Signature save(Signature dataPoint);

    Iterable<Signature> save(Iterable<Signature> dataPoints);

    void delete(String id);
}
