package org.openmhealth.dsu.repository;

import org.openmhealth.dsu.domain.SurveySearchCriteria;
import org.openmhealth.dsu.domain.StepSearchCriteria;
import org.openmhealth.schema.domain.ork.Step;
import org.openmhealth.schema.domain.ork.Survey;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * A set of data point repository methods not automatically implemented by Spring Data repositories.
 *
 * @author Anders Borch
 */
public interface CustomSurveyRepository {

    Iterable<Survey> findBySearchCriteria(SurveySearchCriteria searchCriteria, @Nullable Integer offset,
                                          @Nullable Integer limit);

    Optional<Step> findBySearchCriteria(StepSearchCriteria searchCriteria);

    Survey findOneWithoutParticipantId(String id, String participantId);
}
