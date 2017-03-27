package org.openmhealth.dsu.repository;

import org.openmhealth.schema.domain.omh.ParticipationMetaData;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * @author Anders Borch
 */
public interface MetaDataRepository extends Repository<ParticipationMetaData, String>, CustomMetaDataRepository {

    boolean exists(String id);

    Optional<ParticipationMetaData> findOne(String id);

    ParticipationMetaData save(ParticipationMetaData metaData);

    Iterable<ParticipationMetaData> save(Iterable<ParticipationMetaData> metaDatas);

    void delete(String id);
}
