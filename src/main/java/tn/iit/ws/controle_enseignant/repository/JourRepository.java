package tn.iit.ws.controle_enseignant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.iit.ws.controle_enseignant.entities.Jour;
@Repository
public interface JourRepository extends JpaRepository<Jour, Integer> {

}
