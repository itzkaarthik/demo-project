package com.automation.ingestion;

import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestProcessor implements Runnable{
	static Logger logger = LoggerFactory.getLogger(RequestProcessor.class);
	FolderMonitor folderMonitor;
	FileProcessor fileProcessor;
	String destPath;

	public RequestProcessor(FolderMonitor monitor, String destPath) {
		this.folderMonitor = monitor;
		this.destPath = destPath;
		fileProcessor = new FileProcessor(destPath);
	}
	@Override
	public void run() {
		logger.debug("Request Processor Thread started");
		
		do {
			
			Path path = folderMonitor.getElement();
			if(path==null) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO: handle exception
				}
				
			}else {
				if(path!=null && !Files.isReadable(path)) {
					
					folderMonitor.setElement(path);
					continue;
				}else {
					logger.debug(String.format("Request processor path : %s Readable Status %s ", path,Files.isReadable(path)));
					fileProcessor.process(path, destPath);
					
				}
				
			}
		}
		while(true);
		
	}

}
