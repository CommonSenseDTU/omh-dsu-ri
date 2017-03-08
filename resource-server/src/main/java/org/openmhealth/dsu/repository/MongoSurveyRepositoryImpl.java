package org.openmhealth.dsu.repository;

import org.openmhealth.dsu.domain.SurveySearchCriteria;
import org.openmhealth.dsu.domain.StepSearchCriteria;
import org.openmhealth.schema.domain.ork.Step;
import org.openmhealth.schema.domain.ork.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Nullable;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * @author Anders Borch
 */
public class MongoSurveyRepositoryImpl implements CustomSurveyRepository {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public Iterable<Survey> findBySearchCriteria(SurveySearchCriteria searchCriteria, @Nullable Integer offset, @Nullable Integer limit) {
        checkNotNull(searchCriteria);
        checkArgument(offset == null || offset >= 0);
        checkArgument(limit == null || limit >= 0);

        Query query = newQuery(searchCriteria);

        if (offset != null) {
            query.skip(offset);
        }

        if (limit != null) {
            query.limit(limit);
        }

        return mongoOperations.find(query, Survey.class);

    }

    @Override
    public Optional<Step> findBySearchCriteria(StepSearchCriteria searchCriteria) {
        checkNotNull(searchCriteria);
        checkArgument(searchCriteria.getId() != null);
        checkArgument(searchCriteria.getSurveyId() != null);

        Survey survey = mongoOperations.findById(searchCriteria.getSurveyId(), Survey.class);
        for (Step step : survey.getTask().getSteps()) {
            if (step.getId().equals(searchCriteria.getId())) {
                return Optional.of(step);
            }
        }
        return Optional.empty();
    }

    @Override
    public Survey findOneWithoutParticipantId(String id, String participantId) {

        Query query = new Query();
        query.addCriteria(where("id").is(id));
        query.addCriteria(where("participant_ids").not().is(participantId));

        return mongoOperations.findOne(query, Survey.class);
    }

    private Query newQuery(SurveySearchCriteria searchCriteria) {

        Query query = new Query();

        if (searchCriteria.getUserId() != null) {
            query.addCriteria(where("user_id").is(searchCriteria.getUserId()));
        }

        return query;
    }

    private Query newQuery(StepSearchCriteria searchCriteria) {
        Query query = new Query();

        if (searchCriteria.getSurveyId() != null) {
            query.addCriteria(where("_id").is(searchCriteria.getSurveyId()));
        }

        return query;
    }

}
