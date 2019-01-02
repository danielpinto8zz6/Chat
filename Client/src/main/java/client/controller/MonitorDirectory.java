package client.controller;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 * <p>
 * MonitorDirectory class.
 * </p>
 *
 * @author daniel
 * @version $Id: $Id
 */
public class MonitorDirectory {
	/**
	 * <p>
	 * watch.
	 * </p>
	 *
	 * @param path a {@link java.nio.file.Path} object.
	 * @param controller a {@link client.controller.ChatController} object.
	 * @throws java.io.IOException if any.
	 * @throws java.lang.InterruptedException if any.
	 */
	public static void watch(ChatController controller, Path path) throws IOException, InterruptedException {
		WatchService watchService = FileSystems.getDefault().newWatchService();

		path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
				StandardWatchEventKinds.ENTRY_MODIFY);

		WatchKey key;
		while ((key = watchService.take()) != null) {
			for (WatchEvent<?> event : key.pollEvents()) {
				System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
				controller.updateSharedFolder();
			}
			key.reset();
		}
	}
}
