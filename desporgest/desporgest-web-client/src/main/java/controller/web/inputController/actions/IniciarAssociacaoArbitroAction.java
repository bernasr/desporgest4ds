package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.handlers.IEncontroServiceRemote;
import presentation.web.model.AssociarArbitroModel;

@Stateless
public class IniciarAssociacaoArbitroAction extends Action {
	
	@EJB
	private IEncontroServiceRemote encontroHandler;

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		AssociarArbitroModel model = new AssociarArbitroModel();
		model.setEncontroHandler(encontroHandler);
		request.setAttribute("model", model);
		request.getRequestDispatcher("/associarArbitro/associarArbitro.jsp").forward(request, response);
	}
	
}
