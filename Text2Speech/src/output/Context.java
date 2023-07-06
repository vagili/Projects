package output;

public class Context {
	
	private DocumentWriter docWriter;
	
	public void configureContext(DocumentWriter docWriter) {
		this.docWriter = docWriter;
	}
	
	public void doWork() {
		
		docWriter.save(null);
		
	}
	
	
}
