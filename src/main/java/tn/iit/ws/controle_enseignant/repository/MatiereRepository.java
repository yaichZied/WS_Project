package tn.iit.ws.controle_enseignant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.iit.ws.controle_enseignant.entities.Matiere;
@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Integer> {

}
