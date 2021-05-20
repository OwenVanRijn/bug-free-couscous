package io.swagger.repositories;

import io.swagger.model.Limit;
import org.springframework.data.repository.CrudRepository;

public interface LimitRepository extends CrudRepository<Limit, Integer> {
}
