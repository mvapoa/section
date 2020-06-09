package br.com.sicredi.challenge.section.service;

import br.com.sicredi.challenge.section.entity.Result;
import br.com.sicredi.challenge.section.entity.Session;

public interface ResultService {

	public Result findResultByIdSession(Long idSession);

	public Result findResult(Session session);

	public void closeSessions();

	public Result closeSession(Session session);

}
