package org.openmhealth.dsu.service;

import org.openmhealth.dsu.domain.SurveySearchCriteria;
import org.openmhealth.dsu.domain.StepSearchCriteria;
import org.openmhealth.schema.domain.omh.ParticipationMetaData;
import org.openmhealth.schema.domain.ork.Step;
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

    Optional<Step> findBySearchCriteria(StepSearchCriteria searchCriteria);

    Survey save(Survey survey);

    Iterable<Survey> save(Iterable<Survey> surveys);

    void delete(String id);

    Long deleteByIdAndUserId(String id, String userId);

    void updateMetaData(ParticipationMetaData metaData, String endUserId);
}
