package compilation;

import java.util.List;

public interface Compiler<T extends SourceFile> {
	/**
	 * Compile the given source files and return the output of the compiler
	 * @param sourceFiles source files to compile
	 * @return error messages if any.
	 */
	String compile(List<T> sourceFiles);
	
	/**
	 * @param className
	 * @param functionName
	 * @return
	 */
	String run(String className, String functionName, Object[] args);
	
}
