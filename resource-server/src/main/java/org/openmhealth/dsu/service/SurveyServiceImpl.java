package org.openmhealth.dsu.service;

import org.openmhealth.dsu.domain.SurveySearchCriteria;
import org.openmhealth.dsu.domain.StepSearchCriteria;
import org.openmhealth.dsu.repository.MetaDataRepository;
import org.openmhealth.dsu.repository.SurveyRepository;
import org.openmhealth.schema.domain.omh.ParticipationMetaData;
import org.openmhealth.schema.domain.ork.Step;
import org.openmhealth.schema.domain.ork.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Anders Borch
 */
@Service
public class SurveyServiceImpl implements SurveyService {
    @Autowired
    private SurveyRepository repository;

    @Autowired
    private MetaDataRepository metaDataRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {

        checkNotNull(id);
        checkArgument(!id.isEmpty());

        return repository.exists(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Survey> findOne(String id) {

        checkNotNull(id);
        checkArgument(!id.isEmpty());

        return repository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Survey findOneWithoutParticipantId(String id, String participantId) {

        checkNotNull(id);
        checkArgument(!id.isEmpty());
        checkNotNull(participantId);
        checkArgument(!participantId.isEmpty());

        return repository.findOneWithoutParticipantId(id, participantId);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Survey> findBySearchCriteria(SurveySearchCriteria searchCriteria, @Nullable Integer offset,
                                                    @Nullable Integer limit) {

        checkNotNull(searchCriteria);
        checkArgument(offset == null || offset >= 0);
        checkArgument(limit == null || limit >= 0);

        return repository.findBySearchCriteria(searchCriteria, offset, limit);
    }

    public Optional<Step> findBySearchCriteria(StepSearchCriteria searchCriteria) {
        checkNotNull(searchCriteria);

        return repository.findBySearchCriteria(searchCriteria);
    }

    @Override
    @Transactional
    public Survey save(Survey survey) {

        checkNotNull(survey);

        return repository.save(survey);
    }

    @Override
    @Transactional
    public Iterable<Survey> save(Iterable<Survey> surveys) {

        checkNotNull(surveys);

        return repository.save(surveys);
    }

    @Override
    @Transactional
    public void delete(String id) {

        checkNotNull(id);
        checkArgument(!id.isEmpty());

        repository.delete(id);
    }

    @Override
    public Long deleteByIdAndUserId(String id, String userId) {
        checkNotNull(id);
        checkArgument(!id.isEmpty());
        checkNotNull(userId);
        checkArgument(!userId.isEmpty());

        return repository.deleteByIdAndUserId(id, userId);
    }

    @Override
    public void updateMetaData(ParticipationMetaData metaData, String endUserId) {
        checkNotNull(metaData);
        checkNotNull(endUserId);
        checkNotNull(metaData.getUserId());
        checkNotNull(metaData.getData());
        checkNotNull(metaData.getSurveyId());
        checkArgument(!metaData.getUserId().isEmpty());
        checkArgument(!metaData.getSurveyId().isEmpty());

        if (!endUserId.equals(metaData.getUserId())) {
            Optional<Survey> survey = repository.findOne(metaData.getSurveyId());
            checkArgument(survey.isPresent());
            checkArgument(survey.get().getUserId().equals(endUserId));
        }

        metaDataRepository.save(metaData);
    }
}
