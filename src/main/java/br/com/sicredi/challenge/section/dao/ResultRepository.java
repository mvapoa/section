package br.com.sicredi.challenge.section.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sicredi.challenge.section.entity.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

}
