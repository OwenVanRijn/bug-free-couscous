package io.swagger.repositories;

import io.swagger.model.Address;
import io.swagger.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Integer>  {
}
