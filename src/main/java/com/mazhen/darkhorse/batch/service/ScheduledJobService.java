package com.mazhen.darkhorse.batch.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by smithma on 3/13/17.
 */
@Service
public class ScheduledJobService {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job transAnalysisJob;

	@Scheduled(fixedRate = 500000000)
	public void executeTransAnalysisJob() throws Exception {
		executeTransAnalysisJob("2017-01-26");
	}

	private void executeTransAnalysisJob(String transDate) throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
			.addString("transDate", transDate)
			.addDate("executeTime", new Date())
			.toJobParameters();

		jobLauncher.run(transAnalysisJob, jobParameters);
	}
}
