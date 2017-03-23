package com.mazhen.darkhorse.batch.tasklet;

import com.mazhen.darkhorse.domain.input.Transaction;
import com.mazhen.darkhorse.entity.TransProcessResult;
import com.mazhen.darkhorse.service.TransProcessService;
import com.mazhen.darkhorse.service.SinaStockRealTimeService;
import com.mazhen.darkhorse.util.Constants;
import com.mazhen.darkhorse.util.CsvUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.List;

/**
 * Created by smithma on 3/8/17.
 */
@Slf4j
@Component
public class TransProcessTasklet implements Tasklet {

	@Autowired
	private TransProcessService transactionProcessService;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {

		String transDate = (String) chunkContext.getStepContext().getJobParameters().get("transDate");
		String fileName = (String) chunkContext.getStepContext().getStepExecutionContext().get("fileName");
		String filePath = StringUtils.removeStart(fileName, Constants.FILE_SYSTEM_PREFIX);
		String code = FilenameUtils.getBaseName(fileName);

		log.info("Process data code: " + code);

		List<Transaction> transactions = CsvUtils.getTransactions(Paths.get(filePath), Transaction.class);

		TransProcessResult result = transactionProcessService.process(transactions);
		result.setCode(code);
		result.setTransDate(transDate);

		transactionProcessService.save(result);

		return RepeatStatus.FINISHED;
	}
}
