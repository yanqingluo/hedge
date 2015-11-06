package com.diorsunion.hedge.common;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author harley-dog on 2015/4/8.
 */
public interface BaseRepository<E, ID extends Serializable> {
    void insert(E entity);

    void delete(E id);

    void update(E entity);

    E get(ID id);

    List<E> find(E query);

    List<E> findByIds(List<ID> ids);

    Set<E> findToSet(E query);

    Set<E> findToSetByIds(List<ID> ids);

    int count(E query);
}
