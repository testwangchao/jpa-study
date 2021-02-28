package com.example.jpa.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * CrudService interface contains some common methods.
 *
 * @param <D> domain type
 * @param <I> id type
 */
public interface CrudService<D, I> {
    /**
     * list all
     * @return List<D>
     */
    List<D> listAll();

    /**
     * List all by sort
     *
     * @param sort sort
     * @return List
     */
    @NonNull
    List<D> listAll(@NonNull Sort sort);

    /**
     * List all by pageable
     *
     * @param pageable pageable
     * @return Page
     */
    @NonNull
    Page<D> listAll(@NonNull Pageable pageable);
}
