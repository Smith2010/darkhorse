package com.mazhen.darkhorse.batch.listener;

import com.mazhen.darkhorse.util.Constants;
import com.mazhen.darkhorse.util.ZipUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Created by smithma on 3/23/17.
 */
@Slf4j
@Component
public class TransProcessStepListener implements StepExecutionListener {

	@Value("${darkhorse.datapath.input.transaction}")
	private String transInputPath;

	@Value("${darkhorse.datapath.process.transaction}")
	private String transProcessPath;

	@Value("${darkhorse.datapath.complete.transaction}")
	private String transCompletePath;

	@Override
	public void beforeStep(StepExecution stepExecution) {

		String date = stepExecution.getJobParameters().getString("transDate");
		String zipFileName = date + Constants.ZIP_FILE_SUFFIX;

		File inputPath = FileUtils.getFile(transInputPath);
		File processPath = FileUtils.getFile(transProcessPath);
		File zipFile = FileUtils.getFile(inputPath, zipFileName);

		try {
			FileUtils.cleanDirectory(processPath);
			ZipUtils.decompress(zipFile, processPath);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {

		String date = stepExecution.getJobParameters().getString("transDate");
		String zipFilePath= transInputPath + date + Constants.ZIP_FILE_SUFFIX;

		File processPath = FileUtils.getFile(transProcessPath);
		File completePath = FileUtils.getFile(transCompletePath);
		File zipFile = FileUtils.getFile(zipFilePath);

//		try {
//			FileUtils.moveFileToDirectory(zipFile, completePath, true);
//			FileUtils.cleanDirectory(processPath);
//		} catch (IOException e) {
//			log.error(e.getMessage(), e);
//			return ExitStatus.FAILED;
//		}

		return ExitStatus.COMPLETED;
	}
}
