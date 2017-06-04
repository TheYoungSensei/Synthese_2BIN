import java.io.IOException;
import java.util.Map;

public abstract class Test {
	private String a;
	Double b;
	protected boolean c;
	static double monAttributStatic;

	private Integer unBelAttribut;

	private Map<Integer, StringBuffer> uneCollection;

	public String getA() throws IOException {
		if (a == "")
			throw new IOException();
		return a;
	}

	protected Double getB() {
		unBelAttribut = 5;
		return b;
	}

	boolean getC() {
		return c;
	}

	static Test getTest() {
		return null;
	}

	abstract void rien();

	public void uneMéthode(String t) {

	}

	void uneMéthode(String t, Test h) {

	}

	public Test(String a, Double b, boolean c) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public Test() {
		// TODO Auto-generated constructor stub
	}

}
