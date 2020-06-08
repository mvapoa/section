package br.com.sicredi.challenge.section.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sicredi.challenge.section.entity.Associate;

@Repository
public interface AssociateRepository extends JpaRepository<Associate, Long> {

	public Optional<Associate> findByCpf(String cpf);

}
