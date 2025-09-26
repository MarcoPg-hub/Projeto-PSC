package view;

import controller.ControlaCadastro;
import model.cadastroMedico;

public class TestaCadastroMedico {

	public static void main(String[] args) {
		
		
		System.out.println("O cadastro do cliente foi inicializado!");
		
		cadastroMedico cm [] = new cadastroMedico[2];
		cm[0] = new cadastroMedico();
		cm[0].setNOME("Marco Tulio");
		cm[0].setCRM("189189");
		cm[0].setTELEFONE("(34) 9 9999-9999");
		cm[0].setENDERECO("rua netuno nº1089");
		cm[0].setESPECIALIZACAO("Cardiologista");
		cm[0].setDISPONIBILIDADE("De segunda a sexta das 09:00 hrs ás 12:00hrs");
		cm[0].setEmail("MedicoTeste@hotmail.com");
		
		cm[1] = new cadastroMedico("Marco Tulio", "189189" , "(34) 9 9999-9999" , "rua netuno nº1089" , "Cardiologista"  , null, null);
		System.out.println("\nNome:\t " + cm[1].getNOME());
		
		ControlaCadastro cadastro = new ControlaCadastro();
		cadastro.adicionarcadastroMedico(cm[0]);

	}

}
