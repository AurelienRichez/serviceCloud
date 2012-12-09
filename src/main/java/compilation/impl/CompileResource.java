package compilation.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import compilation.GlobalResult;
import compilation.Result;

@Path("/compile")
public class CompileResource {
	@POST
	@Produces(MediaType.TEXT_XML)
	public GlobalResult getTestMessage(	@FormParam("fileName") String fileName,
									@FormParam("sourceCode") String sourceCode) {
		String className = fileName.replace(".java", "");
		
		GlobalResult globalResult = new GlobalResult();
		InMemoryJavaFile file = new InMemoryJavaFile(className, sourceCode);List<InMemoryJavaFile> files = new ArrayList<InMemoryJavaFile>();
		files.add(file);

		CustomJavaCompiler compiler = new CustomJavaCompiler();
		globalResult.getCompilation().setError(compiler.compile(files));
		String[] params = new String[0];
		globalResult.getExecution().setOutput(compiler.execute(className, "main", new Object[] {params}));
		
		return globalResult;
	}
}
