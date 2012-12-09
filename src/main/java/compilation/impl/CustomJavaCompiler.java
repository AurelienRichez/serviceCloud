package compilation.impl;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;

import org.eclipse.jdt.internal.compiler.tool.EclipseCompiler;

import compilation.Compiler;

public class CustomJavaCompiler implements Compiler<InMemoryJavaFile> {
	
	

	private ClassFileManager fileManager;
	

	@Override
	public String compile(List<InMemoryJavaFile> sourceFiles) {
		
		JavaCompiler compiler = new EclipseCompiler();
		this.fileManager = new compilation.impl.ClassFileManager(
				compiler.getStandardFileManager(null, null, null));
		
		StringWriter writer = new StringWriter();
		CompilationTask cTask =  compiler.getTask(writer, fileManager, null, null, null, sourceFiles);
		cTask.call();
		
		return writer.toString();
	}

	@Override
	public String execute(String className, String functionName, Object[] args) {
		
		ClassLoader classLoader = fileManager.getClassLoader(null);
		PrintStream stdOut = System.out;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			Method method = classLoader.loadClass(className).getMethod("main", String[].class);
			
		    PrintStream ps = new PrintStream(baos);
		    
		    System.setOut(ps);			
			method.invoke(null, args);
			System.out.flush();
			
			
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}finally {
			System.setOut(stdOut);
		}
		return baos.toString();
	}
	
	public static void main(String[] args) {
		StringBuilder src = new StringBuilder();
		src.append("public class DynaClass {\n");
		src.append("    public static void main(String[] args) {\n");
		src.append("        System.out.println(\"Hello, I am \" + ");
		src.append(				" \"a class\");\n");
		src.append("    }\n");
		src.append("}\n");
		
		InMemoryJavaFile file = new InMemoryJavaFile("DynaClass", src);
		List<InMemoryJavaFile> files = new ArrayList<InMemoryJavaFile>();
		files.add(file);
		
		CustomJavaCompiler compiler = new CustomJavaCompiler();
		System.out.println(compiler.compile(files));
		String[] params = new String[0];
		System.out.println( " execution : " + compiler.execute("DynaClass", "main",new Object[] {params}));
		
	}

}
