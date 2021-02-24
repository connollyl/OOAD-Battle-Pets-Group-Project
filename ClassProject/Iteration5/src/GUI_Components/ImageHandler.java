package GUI_Components;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.image.Image;

/**
 * This Class Handles all Images Pulled from the resource folder. It allows for
 * all files to be pulled on start and stored for easy access. The files pulled
 * are all images for pet generation. This also means that we don't have to
 * handle any errors regarding missing files during gameplay.
 */
public class ImageHandler {

	private ArrayList<File> fileList;
	private ArrayList<String> fileStringList;
	private String rootPath;
	private String rawFileName;
	private File folder;

	/**
	 * Constructor that sets root folder
	 * 
	 * @param root
	 */
	public ImageHandler(String root) {
		this.rawFileName = root;
		this.rootPath = root;
		try {
			checkRootFolderExists();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.folder = new File(this.rootPath);
		this.fileList = new ArrayList<File>();
		this.fileStringList = new ArrayList<String>();
		initFiles();
	}

	/*
	 * Pulls all files from root folder on start.
	 */
	private void initFiles() {
		File[] list = this.folder.listFiles();

		for (File item : list) {
			if (item.exists()) {
				this.fileList.add(item);
				this.fileStringList.add(item.toString().replace(this.rawFileName + "\\", ""));
			}
		}

	}

	/**
	 * Returns file based on given name if found.
	 * 
	 * @param fileName
	 * @return
	 */
	public File getFile(String fileName) {
		for (File file : this.fileList) {
			if (file.getName().equals(fileName)) {
				return file;
			}
		}
		return null;
	}

	/**
	 * Checks to see if given foleder Exists.
	 * 
	 * @throws IOException
	 */
	public void checkRootFolderExists() throws IOException {
		File root = new File(this.rootPath);
		if (root.exists()) {
			return;
		}
		// root.mkdir();
	}

	/**
	 * Checks to see if file exists.
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean fileExists(String fileName) {
		{
			File file = new File(this.rootPath + fileName);
			if (file.exists()) {
				return true;
			}
			return false;
		}

	}

	/**
	 * Gets file and returns it as a JavaFx Image.
	 * 
	 * @param fileName
	 * @return
	 */
	public Image getImage(String fileName) {
		return new Image(getFile(fileName).toURI().toString());
	}

}



