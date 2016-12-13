package org.openmhealth.dsu.repository;

import org.openmhealth.schema.domain.ork.Survey;
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
public interface SurveyRepository extends Repository<Survey, String>, CustomSurveyRepository {

    boolean exists(String id);

    Optional<Survey> findOne(String id);

    Survey findOneWithoutParticipantId(String id, String participantId);

    Survey save(Survey survey);

    Iterable<Survey> save(Iterable<Survey> surveys);

    void delete(String id);

    Long deleteByIdAndUserId(String id, String userId);
}
