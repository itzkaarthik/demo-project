package com.automation.ingestion;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class FolderMonitor implements Runnable {

	private WatchService watcher;
	private Map<WatchKey, Path> keys;
	private Queue<Path> q;

	/**
	 * Creates a WatchService and registers the given directory
	 */
	FolderMonitor(Path dir) throws IOException {
		this.watcher = FileSystems.getDefault().newWatchService();
		this.keys = new HashMap<WatchKey, Path>();
		this.q = new LinkedList<>();
		registerDirectory(dir);
	}

	/**
	 * Register the given directory with the WatchService; This function will be
	 * called by FileVisitor
	 */
	private void registerDirectory(Path dir) throws IOException {
		WatchKey key = dir.register(watcher, ENTRY_CREATE);
		keys.put(key, dir);
	}

	/**
	 * Returns the first element from the Queue.
	 * 
	 * @return
	 */
	public Path getElement() {

		Path returnValue = q.poll();
		return returnValue;
	}

	public void setElement(Path elem) {
		q.add(elem);

	}

	/**
	 * Process all events for keys queued to the watcher
	 */
	void processEvents() {
		for (;;) {

			// wait for key to be signalled
			WatchKey key;
			try {
				key = watcher.take();
			} catch (InterruptedException x) {
				return;
			}

			Path dir = keys.get(key);
			if (dir == null) {
				System.err.println("WatchKey not recognized!!");
				continue;
			}

			for (WatchEvent<?> event : key.pollEvents()) {
				@SuppressWarnings("rawtypes")
				WatchEvent.Kind kind = event.kind();

				// Context for directory entry event is the file name of entry
				@SuppressWarnings("unchecked")
				Path name = ((WatchEvent<Path>) event).context();
				Path child = dir.resolve(name);

				// print out event
				System.out.format("%s: %s\n", event.kind().name(), child);

				// if directory is created, and watching recursively, then register it and its
				// sub-directories
				if (kind == ENTRY_CREATE) {
					q.add(child);

				}
			}

			// reset key and remove from set if directory no longer accessible
			boolean valid = key.reset();
			if (!valid) {
				keys.remove(key);

				// all directories are inaccessible
				if (keys.isEmpty()) {
					break;
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Path dir = Paths.get("c:/temp");
		new FolderMonitor(dir).processEvents();
	}

	@Override
	public void run() {
		processEvents();

	}
}