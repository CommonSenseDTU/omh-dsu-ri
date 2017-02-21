package org.openmhealth.dsu.service;

import org.openmhealth.dsu.domain.PrivateSearchCriteria;
import org.openmhealth.schema.domain.ork.Signature;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author Anders Borch
 */
public interface PrivateService {

    boolean exists(String id);

    Optional<Signature> findOne(String id);

    Iterable<Signature> findBySearchCriteria(PrivateSearchCriteria searchCriteria, @Nullable Integer offset,
                                             @Nullable Integer limit);

    Signature save(Signature signature);

    Iterable<Signature> save(Iterable<Signature> signatures);

    void delete(String id);

    Long deleteByIdAndUserId(String id, String userId);
}
