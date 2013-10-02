package infoprivacy;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

/**
 * Reads a delimited file line by line, acting as an iterator.
 * 
 * @author Joseph Lewis <joehms22@gmail.com>
 *
 */
public class DelimitedFileReader implements Iterator<String[]>, Iterable<String[]>
{
	BufferedReader m_reader;
	String[] nextLine;
	boolean m_hasAnother = true;
	private final String m_delimiter;
	

	/**
	 * Constructs a delimited file reader that will read the file line by line
	 * and yield the lines after being split by the given delimiter. 
	 * 
	 * @param file - the path to the file that will be read
	 * @param delimiter - the delimiter to use.
	 * @throws IOException - If the file can't be found, etc.
	 */
	public DelimitedFileReader(Path file, String delimiter) throws IOException
	{
		this(file, delimiter, 0);
	}
	
	/**
	 * Constructs a delimited file reader that will read the file line by line
	 * and yield the lines after being split by the given delimiter. 
	 * 
	 * @param file - the path to the file that will be read
	 * @param delimiter - the delimiter to use.
	 * @param skipLines - the number of lines to skip
	 * @throws IOException - If the file can't be found, etc.
	 */
	public DelimitedFileReader(Path file, String delimiter, int skipLines) throws IOException
	{
		m_reader = Files.newBufferedReader(file, Charset.defaultCharset());
		m_delimiter = delimiter;
		
		prepNextLine(); // prep initial line
		
		// Skip the skiplines
		for(int i = 0; i < skipLines; i++)
		{
			prepNextLine();
		}
	}
	
	/**
	 * Prepares the next line for being returned.
	 */
	private void prepNextLine()
	{
		try {
			nextLine = m_reader.readLine().split(m_delimiter);
		} catch (Exception e) {
			m_hasAnother = false;
		}
	}

	@Override
	public boolean hasNext() {
		return m_hasAnother;
	}

	/**
	 * Returns the next line already broken up by the given delimiter
	 */
	@Override
	public String[] next() {
		String[] next = nextLine;
		prepNextLine();
		return next;
	}

	@Override
	public void remove() {
		// does nothing
	}

	@Override
	public Iterator<String[]> iterator() {
		return this;
	}
	
}
