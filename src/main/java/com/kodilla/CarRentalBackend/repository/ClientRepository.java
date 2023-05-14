package com.kodilla.CarRentalBackend.repository;

import com.kodilla.CarRentalBackend.domain.Client;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ClientRepository extends CrudRepository<Client, Long> {

    List<Client> findAll();
    Optional<Client> findById(Long id);
}
