	package view;
	
	import controller.ControlaCadastro;
	import model.cadastroPaciente;
	
	public class TestaCadastroCliente {
	
	    public static void main(String[] args) {
	
	        System.out.println("O cadastro do cliente foi inicializado!");
	
	        cadastroPaciente[] c = new cadastroPaciente[2];
	        c[0] = new cadastroPaciente(null, null, null, null, null, null, null, null, null);
	        c[0].setNomePaciente("Marco Aurélio");
	        c[0].setEnderecoPaciente("Rua estrela dalva nº 1100");
	        c[0].setCpf("018.868.616-19");
	        c[0].setProfissao("Professor");
	        c[0].setNascimento("1989-05-28");
	        c[0].setSexo("M");
	        c[0].setTelefonePaciente("(34) 3232-9999");
	        c[0].setCelular("(34) 93232-9999");
	        c[0].setEmailPaciente("ProjetoA3@hotmail.com");
	
	        c[1] = new cadastroPaciente("Marco Aurélio", "(34) 99991-3199", "(34) 99991-0000", "junior@teste.com", null, null, null, null , null);
	        System.out.println("\nNome:\t " + c[1].getNomePaciente());
	
	        ControlaCadastro cadastro = new ControlaCadastro();
	        cadastro.adicionarcadastroPaciente(c[0]);
	
	    }
	
	}
