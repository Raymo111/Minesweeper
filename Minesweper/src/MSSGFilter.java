import java.io.File;

import javax.swing.filechooser.FileFilter;

//Minesweeper Save Game File filter class
public class MSSGFilter extends FileFilter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	// Accepts only .mssg files when selecting files in save loader
	public boolean accept(File f) {

		// Enable all directories to be opened
		if (f.isDirectory()) {
			return true;
		}

		// Gets the extension of the selected file
		String extension = getExtension(f);

		// Checks if file has an extension first
		if (extension != null) {

			// Only returns true if the file extension is mssg
			if (extension.equals("mssg")) {
				return true;
			}
		}

		// Returns false if all other conditions fail
		return false;
	}

	/**
	 * The description of this filter to be put into the file selection window
	 */
	public String getDescription() {
		return "Minesweeper Savegames (.mssg files)";
	}

	/**
	 * Gets extension of selected file
	 * 
	 * @param f
	 *            The file the user has selected
	 * @return The extension of the selected file
	 */
	public static String getExtension(File file) {
		String extension = null;
		String fileName = file.getName();
		int index = fileName.lastIndexOf('.');

		if (index > 0 && index < fileName.length() - 1) {
			extension = fileName.substring(index + 1).toLowerCase();
		}
		return extension;
	}

}