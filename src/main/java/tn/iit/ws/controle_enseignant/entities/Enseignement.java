package tn.iit.ws.controle_enseignant.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class Enseignement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idBase")
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "COD_NIVEAU")
	private Niveau niveau;
	@ManyToOne
	@JoinColumn(name = "COD_dep")
	private Departement departement;
	@Column(name = "semestre1")
	private Integer semestre;
	@Column(name = "annee1")
	private Integer annee;
	@ManyToOne
	@JoinColumn(name = "COD_enseig")
	private Enseignant enseignant;
	@Column
	private Integer cours;
	@Column(name = "par15")
	private Integer parQuinzaine;
	@Column
	private Integer module;
	@ManyToOne
	@JoinColumn(name = "COD_salle")
	private Salle salle;
	@ManyToOne
	@JoinColumn(name = "cod_jour")
	private Jour jour;
	@ManyToOne
	@JoinColumn(name = "COD_senace")
	private Seance seance;
	@ManyToOne
	@JoinColumn(name = "COD_mat")
	private Matiere matiere;

	public Integer getId() {
		return id;
	}

	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

	public Enseignant getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}

	public Integer getCours() {
		return cours;
	}

	public void setCours(Integer cours) {
		this.cours = cours;
	}

	public Integer getModule() {
		return module;
	}

	public void setModule(Integer module) {
		this.module = module;
	}

	public Salle getSalle() {
		return salle;
	}

	public void setSalle(Salle salle) {
		this.salle = salle;
	}

	public Jour getJour() {
		return jour;
	}

	public void setJour(Jour jour) {
		this.jour = jour;
	}

	public Seance getSeance() {
		return seance;
	}

	public void setSeance(Seance seance) {
		this.seance = seance;
	}

	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public Integer getSemestre() {
		return semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}

	public Integer getAnnee() {
		return annee;
	}

	public void setAnnee(Integer annee) {
		this.annee = annee;
	}

	public Integer getParQuinzaine() {
		return parQuinzaine;
	}

	public void setParQuinzaine(Integer parQuinzaine) {
		this.parQuinzaine = parQuinzaine;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Enseignement other = (Enseignement) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
