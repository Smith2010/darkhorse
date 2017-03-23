package com.mazhen.darkhorse.util;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Created by smithma on 3/19/17.
 */
public class ZipUtils {

	public static void decompress(File zipFile, File directory) throws IOException {

		try (InputStream inputStream = new FileInputStream(zipFile);
			ZipArchiveInputStream in = new ZipArchiveInputStream(inputStream)) {
			ZipArchiveEntry entry = in.getNextZipEntry();

			while (entry != null) {
				if (entry.isDirectory()) {
					entry = in.getNextZipEntry();
					continue;
				}

				String fileName = FilenameUtils.getName(entry.getName());
				File currentFile = new File(directory, fileName);
				File parent = currentFile.getParentFile();
				if (!parent.exists()) {
					parent.mkdirs();
				}

				try (OutputStream out = new FileOutputStream(currentFile)) {
					IOUtils.copy(in, out);
				}

				entry = in.getNextZipEntry();
			}
		}
	}
}
