package org.openmhealth.dsu.repository;

import org.openmhealth.dsu.domain.PrivateSearchCriteria;
import org.openmhealth.schema.domain.ork.Signature;

import javax.annotation.Nullable;

/**
 * A set of data point repository methods not automatically implemented by Spring Data repositories.
 *
 * @author Anders Borch
 */
public interface CustomPrivateRepository {

    Iterable<Signature> findBySearchCriteria(PrivateSearchCriteria searchCriteria, @Nullable Integer offset,
                                             @Nullable Integer limit);
}
