package org.ingerencia.beerapp.dao.repository;

import org.ingerencia.beerapp.dao.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Integer> {
}
