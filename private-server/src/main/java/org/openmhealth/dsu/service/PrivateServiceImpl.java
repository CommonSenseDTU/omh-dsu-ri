package org.openmhealth.dsu.service;

import org.openmhealth.dsu.domain.PrivateSearchCriteria;
import org.openmhealth.dsu.repository.PrivateRepository;
import org.openmhealth.schema.domain.ork.Signature;
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
public class PrivateServiceImpl implements PrivateService {

    @Autowired
    private PrivateRepository repository;

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {

        checkNotNull(id);
        checkArgument(!id.isEmpty());

        return repository.exists(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Signature> findOne(String id) {

        checkNotNull(id);
        checkArgument(!id.isEmpty());

        return repository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Signature> findBySearchCriteria(PrivateSearchCriteria searchCriteria, @Nullable Integer offset, @Nullable Integer limit) {
        checkNotNull(searchCriteria);
        checkArgument(offset == null || offset >= 0);
        checkArgument(limit == null || limit >= 0);

        return repository.findBySearchCriteria(searchCriteria, offset, limit);
    }

    @Override
    @Transactional
    public Signature save(Signature signature) {

        checkNotNull(signature);

        return repository.save(signature);
    }

    @Override
    @Transactional
    public Iterable<Signature> save(Iterable<Signature> signatures) {

        checkNotNull(signatures);

        return repository.save(signatures);
    }

    @Override
    @Transactional
    public void delete(String id) {

        checkNotNull(id);
        checkArgument(!id.isEmpty());

        repository.delete(id);
    }

    @Override
    @Transactional
    public Long deleteByIdAndUserId(String id, String userId) {

        checkNotNull(id);
        checkArgument(!id.isEmpty());
        checkNotNull(userId);
        checkArgument(!userId.isEmpty());

        return null;// TODO: repository.deleteByIdAndHeaderUserId(id, userId);
    }}
