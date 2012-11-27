package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

import org.eclipse.jdt.internal.compiler.tool.EclipseCompiler;

import compilation.CharSequenceJavaFileObject;

/**
 * Servlet implementation class TestCompile
 */
@WebServlet("/TestCompile")
public class TestCompile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestCompile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Full name of the class that will be compiled.
		// If class should be in some package,
		// fullName should contain it too
		// (ex. "testpackage.DynaClass")
		String fullName = "DynaClass";

		// Here we specify the source code of the class to be compiled
		StringBuilder src = new StringBuilder();
		src.append("public class DynaClass {\n");
		src.append("    public String toString() {\n");
		src.append("        return \"Hello, I am \" + ");
		src.append("this.getClass().getSimpleName();\n");
		src.append("    }\n");
		src.append("}\n");

		// We get an instance of JavaCompiler. Then
		// we create a file manager
		// (our custom implementation of it)
		JavaCompiler compiler = new EclipseCompiler();
		JavaFileManager fileManager = new compilation.ClassFileManager(
				compiler.getStandardFileManager(null, null, null));

		// Dynamic compiling requires specifying
		// a list of "files" to compile. In our case
		// this is a list containing one "file" which is in our case
		// our own implementation (see details below)
		List<JavaFileObject> jfiles = new ArrayList<JavaFileObject>();
		jfiles.add(new CharSequenceJavaFileObject(fullName, src));

		// We specify a task to the compiler. Compiler should use our file
		// manager and our list of "files".
		// Then we run the compilation with call()

		try {
			compiler.getTask(null, fileManager, null, null, null, jfiles)
					.call();

			// Creating an instance of our compiled class and
			// running its toString() method
			ClassLoader classLoader = fileManager.getClassLoader(null);

			Object instance = classLoader.loadClass(fullName).newInstance();
			response.getWriter().append(instance.toString());
			// response.getWriter().append(instance.toString());
		} catch (RuntimeErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Full name of the class that will be compiled.
		// If class should be in some package,
		// fullName should contain it too
		// (ex. "testpackage.DynaClass")
		String fullName = request.getParameter("fullName");

		// Here we specify the source code of the class to be compiled
		String src = request.getParameter("source");

		response.getWriter().append(src + "\n");

		// We get an instance of JavaCompiler. Then
		// we create a file manager
		// (our custom implementation of it)
		JavaCompiler compiler = new EclipseCompiler();
		JavaFileManager fileManager = new compilation.ClassFileManager(
				compiler.getStandardFileManager(null, null, null));

		// Dynamic compiling requires specifying
		// a list of "files" to compile. In our case
		// this is a list containing one "file" which is in our case
		// our own implementation (see details below)
		List<JavaFileObject> jfiles = new ArrayList<JavaFileObject>();
		jfiles.add(new CharSequenceJavaFileObject(fullName, src));

		// We specify a task to the compiler. Compiler should use our file
		// manager and our list of "files".
		// Then we run the compilation with call()

		try {
			compiler.getTask(null, fileManager, null, null, null, jfiles)
					.call();

			// Creating an instance of our compiled class and
			// running its toString() method
			ClassLoader classLoader = fileManager.getClassLoader(null);

			Object instance = classLoader.loadClass(fullName).newInstance();
			response.getWriter().append(instance.toString());
			// response.getWriter().append(instance.toString());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			response.getWriter().append(e.getStackTrace().toString());
		}
	}

}
