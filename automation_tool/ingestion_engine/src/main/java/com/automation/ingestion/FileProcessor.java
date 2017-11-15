package com.automation.ingestion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FilenameUtils;

public class FileProcessor {

	String destination = null;

	public FileProcessor(String destPath) {
		destination = destPath;
	}

	public void process(Path path, String destPath) {
		validateAndProcessFiles(path);
	}

	private void validateAndProcessFiles(Path path) {
		List<String> files;
		try {
			if (Files.isReadable(path)) {

				if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
					files = processDirectory(path);
				} else if (Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS)) {
					files = processFiles(path);
				} else {

					// TODO
				}

			} else {

				// TODO
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private List<String> processFiles(Path path) throws IOException {
		String extn = FilenameUtils.getExtension(path.getFileName().toString().toLowerCase());
		List<String> files = new ArrayList<String>();
		switch (extn) {
		case "zip": {

			files = processZip(path);
			break;
		}
		case "iso": {
			files = processZip(path);
			break;
		}
		case "tif": {

		}
		}
		return files;
	}

	private List<String> processZip(Path path) throws IOException {
		byte[] buffer = new byte[1024];
		ZipInputStream zis = new ZipInputStream(new FileInputStream(path.toFile()));
		ZipEntry zipEntry = zis.getNextEntry();
		while (zipEntry != null) {
			String fileName = zipEntry.getName();
			File newFile = new File(destination + File.separator + FilenameUtils.getBaseName(path.getFileName().toString())+File.separator+fileName);
			System.out.println("Unzipping to " + newFile.getAbsolutePath());
			// create directories for sub directories in zip

			new File(newFile.getParent()).mkdirs();
			if (zipEntry.isDirectory()) {
				newFile.mkdirs();
			} else {
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
			}
			// close this ZipEntry
			zis.closeEntry();
			zipEntry = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();
		return null;
	}

	private List<String> processDirectory(Path path) {
		// TODO Auto-generated method stub
		return null;
	}

}
