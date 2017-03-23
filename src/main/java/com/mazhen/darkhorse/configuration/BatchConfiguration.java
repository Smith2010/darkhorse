package com.mazhen.darkhorse.configuration;

import com.mazhen.darkhorse.util.Constants;
import org.apache.commons.io.FilenameUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;

/**
 * Created by smithma on 3/1/17.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Value("${darkhorse.datapath.process.transaction}")
	private String transProcessPath;

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	@Bean
	public Job transAnalysisJob(@Qualifier("jobRepository") JobRepository jobRepository,
		@Qualifier("transProcessStep") Step transProcessStep) {
		return jobs.get("transAnalysisJob")
			.repository(jobRepository)
			.start(transProcessStep)
			.build();
	}

	@Bean
	public Step transProcessStep(@Qualifier("transProcessPartitionStep") Step transProcessPartitionStep,
		@Qualifier("transProcessStepListener") StepExecutionListener transProcessStepListener,
		@Qualifier("filePartitioner") Partitioner filePartitioner,
		@Qualifier("taskExecutor") TaskExecutor taskExecutor) {
		return steps.get("transProcessStep")
			.listener(transProcessStepListener)
			.partitioner(transProcessPartitionStep)
			.partitioner("transProcessPartitionStep", filePartitioner)
			.taskExecutor(taskExecutor)
			.gridSize(2)
			.build();
	}

	@Bean
	public Step transProcessPartitionStep(@Qualifier("transProcessTasklet") Tasklet transProcessTasklet) {
		return steps.get("transProcessPartitionStep").tasklet(transProcessTasklet).build();
	}

	@Bean
	public Partitioner filePartitioner() {
		MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

		try {
			String locationPattern = Constants.FILE_SYSTEM_PREFIX + FilenameUtils.concat(transProcessPath, Constants.ALL_CSV_FILES);
			Resource[] resources = resourcePatternResolver.getResources(locationPattern);
			partitioner.setResources(resources);
		} catch (IOException e) {
			throw new RuntimeException("I/O problems when resolving the input file pattern.", e);
		}

		return partitioner;
	}

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(15);
		taskExecutor.afterPropertiesSet();
		return taskExecutor;
	}

}
