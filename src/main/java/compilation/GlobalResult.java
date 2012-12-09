package compilation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class GlobalResult {
	
	@XmlElement
	private Result compilation = new Result();
	@XmlElement
	private Result execution = new Result();
	
	public Result getCompilation() {
		return compilation;
	}
	public void setCompilation(Result compilation) {
		this.compilation = compilation;
	}
	public Result getExecution() {
		return execution;
	}
	public void setExecution(Result execution) {
		this.execution = execution;
	}
	
}
