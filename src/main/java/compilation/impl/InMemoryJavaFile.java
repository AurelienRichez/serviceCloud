package compilation.impl;

import java.net.URI;

import javax.tools.SimpleJavaFileObject;
import compilation.SourceFile;

public class InMemoryJavaFile extends SimpleJavaFileObject implements
		SourceFile {

	private CharSequence content;
	
	/**
	    * This constructor will store the source code in the
	    * internal "content" variable and register it as a
	    * source code, using a URI containing the class full name
	    * It will appear to the compiler as a class written on disk.
	    * @param className
	    *            name of the public class in the source code
	    * @param content
	    *            source code to compile
	    */
	protected InMemoryJavaFile(String className, CharSequence content) {
		super(URI.create("file:///" + className.replace('.', '/')
				+ Kind.SOURCE.extension), Kind.SOURCE);
		this.content = content;
	}

	/* (non-Javadoc)
	 * @see javax.tools.SimpleJavaFileObject#getCharContent(boolean)
	 */
	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) {
		return content;
	}

	@Override
	public String getContent() {
		return getCharContent(true).toString();
	}

}
