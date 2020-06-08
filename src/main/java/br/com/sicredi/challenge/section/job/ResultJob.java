package br.com.sicredi.challenge.section.job;

import org.springframework.scheduling.annotation.Scheduled;

public class ResultJob {

	@Scheduled(cron = "${cron.result.job}")
	public void teste() {

		
		
	}

}
