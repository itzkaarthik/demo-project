package com.automation.ingestion;

import static java.nio.file.StandardWatchEventKinds.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FolderWatcher {
	static Logger logger = LoggerFactory.getLogger(FolderWatcher.class);
	public static void main(String[] args) {
		
		try {
			InputStream is =  FolderWatcher.class.getResourceAsStream("/application.properties");
			Properties prop = new Properties();
			if(is == null) {
				logger.error("Couldnt read application properties file");
				System.exit(1);
			}
			prop.load(is);
		
			String inputFolder  = prop.getProperty("input.folder");
			String OutputFolder  = prop.getProperty("output.folder");
			if(inputFolder ==  null || OutputFolder == null) {
				logger.error("Input and Output folder not set properly, exiting...");
				System.exit(1);
			}
			FolderMonitor folderMonitor = new FolderMonitor(Paths.get(inputFolder));
			Thread monThread =  new Thread(folderMonitor);
			monThread.start();
			
			RequestProcessor reqProcessor = new RequestProcessor(folderMonitor, OutputFolder);
			Thread requestThread = new Thread(reqProcessor);
			requestThread.start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

