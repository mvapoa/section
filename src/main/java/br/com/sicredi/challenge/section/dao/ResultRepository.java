package br.com.sicredi.challenge.section.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sicredi.challenge.section.entity.Result;
import br.com.sicredi.challenge.section.entity.Session;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

	public Optional<Result> findBySession(Session session);

}
