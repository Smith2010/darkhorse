package com.mazhen.darkhorse.repository;

import com.mazhen.darkhorse.entity.TransProcessResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by smithma on 3/18/17.
 */
@Repository
public interface TransProcessResultRepository extends JpaRepository<TransProcessResult, Long> {
}
