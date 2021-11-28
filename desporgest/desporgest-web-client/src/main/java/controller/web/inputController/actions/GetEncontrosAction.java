package controller.web.inputController.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.dto.EncontroDTO;
import facade.exceptions.ApplicationException;
import facade.handlers.IEncontroServiceRemote;

@Stateless
public class GetEncontrosAction extends Action {
	
	@EJB
	private IEncontroServiceRemote associarHandler;

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String designacao = request.getParameter("prova");
		
		String objectToReturn = getEncontrosJSON(designacao);
		
		response.setContentType("application/json");     
		PrintWriter out = response.getWriter();  
		out.print(objectToReturn);
		out.flush();
	}

	private String getEncontrosJSON(String designacao) {
		try {
			Collection<EncontroDTO> encontros = associarHandler.encontrosComFaltaDeArbitros(designacao);
			
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			
			for (EncontroDTO e : encontros) {
				sb.append("{");
				sb.append("\"id\": \"" + e.getNumero() + "\", ");
				sb.append("\"data\": \"" + e.getDataRealizacao().toString() + "\", ");
				sb.append("\"part\": \"" + e.getPart1() + " vs " + e.getPart2() + "\"");
				sb.append("}, ");
			}
			if (encontros.size() > 0)
				sb.deleteCharAt(sb.length() - 2);
			
			sb.append("]");
			return sb.toString();
		} catch (ApplicationException e) {
			return "{}";
		}
	}
}
