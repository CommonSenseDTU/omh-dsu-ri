package org.openmhealth.dsu.service;

import org.openmhealth.dsu.domain.SurveySearchCriteria;
import org.openmhealth.schema.domain.ork.Survey;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * A service that manages surveys.
 *
 * @author Anders Borch
 */
public interface SurveyService {

    boolean exists(String id);

    Optional<Survey> findOne(String id);

    Survey findOneWithoutParticipantId(String id, String participantId);

    Iterable<Survey> findBySearchCriteria(SurveySearchCriteria searchCriteria, @Nullable Integer offset,
                                          @Nullable Integer limit);

    Survey save(Survey survey);

    Iterable<Survey> save(Iterable<Survey> surveys);

    void delete(String id);

    Long deleteByIdAndUserId(String id, String userId);
}
