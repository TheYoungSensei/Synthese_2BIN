import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class CalcServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Expression e = new ExpressionBuilder(req.getParameter("expr")).build();
		double result = e.evaluate();
		resp.setStatus(200);
		resp.setContentType("text/html");
		String msg = "<html><body><p>" + result + "</p></body></html>";
		resp.setContentLength(msg.getBytes().length);
		resp.setCharacterEncoding("utf-8");
		resp.getOutputStream().write(msg.getBytes());
	}
}
