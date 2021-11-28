package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.exceptions.ApplicationException;
import facade.handlers.IEncontroServiceRemote;
import presentation.web.model.AssociarArbitroModel;

@Stateless
public class AssociarArbitroAction extends Action {

	@EJB
	private IEncontroServiceRemote associarHandler;

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		AssociarArbitroModel model = createModel(request);
		request.setAttribute("model", model);
		model.setEncontroHandler(associarHandler);
		if (inputValido(model)) {
			try {
				associarHandler.associarArbitroAoEncontro(intValue(model.getNumEncontro()), intValue(model.getNumFederativo()));
				model.clear();
				model.addMessage("Árbitro associado com sucesso");
			} catch (ApplicationException e) {
				model.addMessage("Erro ao associar árbitro ao encontro: " + e.getMessage());
			}
		}
		else {
			model.addMessage("Erro ao validar dados da associação");
		}

		request.getRequestDispatcher("/associarArbitro/associarArbitro.jsp").forward(request, response);
	}

	private boolean inputValido(AssociarArbitroModel model) {
		boolean result = isFilled(model, model.getProva(), "Tem que indicar a designação da prova.");
		result &= isFilled(model, model.getNumEncontro(), "Tem que selecionar uma encontro");
		result &= isFilled(model, model.getNumFederativo(), "Tem que selecionar um árbitro");
		return result;
	}

	private AssociarArbitroModel createModel(HttpServletRequest request) {
		AssociarArbitroModel model = new AssociarArbitroModel();
		String prova = request.getParameter("designacao");
		String encontro = request.getParameter("encontro");
		String numFederativo = request.getParameter("numFederativo");
		model.setProva(prova);
		model.setNumEncontro(encontro);
		model.setNumFederativo(numFederativo);
		return model;
	}

}
