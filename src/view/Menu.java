package view;

import java.awt.Component;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import connection.ConexaoMySQL;
import controller.ControlaCadastro;
import model.cadastroMedico;
import model.cadastroPaciente;

public class Menu {

	public static void main(String[] args) {
        fazerLoginEExibirMenuPrincipal();
    }

    private static void fazerLoginEExibirMenuPrincipal() {
        UIManager.put("OptionPane.background", new java.awt.Color(135, 206, 235));
        UIManager.put("Panel.background", new java.awt.Color(135, 206, 235));
        JOptionPane.showMessageDialog(null, "Bem-vindo à Clínica Saude e Bem-Estar!", "Olá!!!", JOptionPane.PLAIN_MESSAGE);
        fazerLogin();
    }

    private static void fazerLogin() {
        String usuario;
        String senha;
        boolean loginSucesso = false;

        final String USUARIO_PADRAO = "admin";
        final String SENHA_PADRAO = "admin";

        do {
            usuario = JOptionPane.showInputDialog(null, "Digite o usuário:", "Login:", JOptionPane.PLAIN_MESSAGE);
            senha = CustomPasswordField.showInputDialog("Digite a senha:");

            if (usuario != null && senha != null && usuario.equals(USUARIO_PADRAO) && senha.equals(SENHA_PADRAO)) {
                loginSucesso = true;
                JOptionPane.showMessageDialog(null, "Login bem-sucedido!", "Iniciando o programa...", JOptionPane.PLAIN_MESSAGE);
                exibirMenuPrincipal();
            } else {
                JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos. Tente novamente.");
            }
        } while (!loginSucesso);
    }

    public static class CustomPasswordField {
        private static JPasswordField jpf;

        public static String showInputDialog(final Object message) {
            jpf = new JPasswordField();
            final JTextField jtf = new JTextField();
            jtf.setEditable(false);
            jtf.setBackground(javax.swing.UIManager.getColor("Panel.background"));
            jpf.addAncestorListener(new javax.swing.event.AncestorListener() {
                @Override
                public void ancestorAdded(javax.swing.event.AncestorEvent ancestorEvent) {
                    jpf.requestFocusInWindow();
                    jpf.removeAncestorListener(this);
                }

                @Override
                public void ancestorMoved(javax.swing.event.AncestorEvent ancestorEvent) {
                }

                @Override
                public void ancestorRemoved(javax.swing.event.AncestorEvent ancestorEvent) {
                }
            });

            Object[] ob = {message, jpf};
            int result = JOptionPane.showOptionDialog(null, ob, "Senha:",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, null, null);

            if (result == JOptionPane.OK_OPTION) {
                return new String(jpf.getPassword());
            } else {
                return null;
            }
        }
    }

    private static void exibirMenuPrincipal() {
        ControlaCadastro controle = new ControlaCadastro();
        int escolha;

        do {
            try {
                UIManager.put("OptionPane.background", new java.awt.Color(135, 206, 235));
                UIManager.put("Panel.background", new java.awt.Color(135, 206, 235));

                escolha = Integer.parseInt(JOptionPane.showInputDialog(null,
                        "Escolha uma operação:"
                                + "\n1 - Cadastro" + "\n2 - Atualizar cadastro Total"
                                + "\n3 - Remover cadastro" + "\n4 - Alteração Celular(Paciente/Medico)"
                                + "\n5 - Listar todos ordenados pelo nome"
                                + "\n6 - Busca e Lista todos por parte do nome" + "\n7 - Relatório"
                                + "\n8 - Fechar / Sair do programa",
                        "Gerenciamento de Médicos e Pacientes", JOptionPane.PLAIN_MESSAGE));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, insira um número válido.");
                escolha = 0;
            }

            switch (escolha) {
                case 1:
                    cadastrarPacienteOuMedico(controle);
                    break;
                case 2:
                    atualizarCadastroTotal(controle);
                    break;
                case 3:
                    removerPacienteOuMedico(controle);
                    break;
                case 4:
                    buscarAlterarCelular(controle);
                    break;
                case 5:
                	exibirOrdemAlfabetica();
                    break;
                case 6:
                	exibirParteNome();
                    break;
                case 7:
                    escolherRelatorio();
                    break;
                case 8:
                    System.out.println("Saindo...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, escolha uma opção válida.");
            }

        } while (escolha != 8);
    }

    private static void cadastrarPacienteOuMedico(ControlaCadastro controle) {
        boolean realizarNovoCadastro = true;

        while (realizarNovoCadastro) {
            int escolhaCadastro = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "1 - Cadastro de Paciente" + "\n2 - Cadastro de Médico",
                    "Qual cadastro deseja efetuar?", JOptionPane.PLAIN_MESSAGE));

            switch (escolhaCadastro) {
                case 1:
                    cadastrarPaciente(controle);
                    break;
                case 2:
                    cadastrarMedico(controle);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, escolha uma opção válida.");
            }

            int novaOperacao = JOptionPane.showOptionDialog(null,
                    "Deseja realizar uma nova operação?",
                    "Continuar?", JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.PLAIN_MESSAGE, null, new String[]{"Novo Cadastro", "Voltar ao Menu", "Finalizar"}, "Novo Cadastro");
            if (novaOperacao == JOptionPane.CANCEL_OPTION) {
            		JOptionPane.showMessageDialog(null, "Obrigado por utilizar o nosso sistema." , " Até mais!!!", JOptionPane.PLAIN_MESSAGE);
                System.exit(0);
                
            } else if (novaOperacao == JOptionPane.NO_OPTION) {
                realizarNovoCadastro = false;
            }
        }
    }

    private static void cadastrarPaciente(ControlaCadastro controle) {
        JPanel panel = new JPanel(new GridLayout(0, 2));

        JTextField nomeField = new JTextField(20);
        JTextField enderecoField = new JTextField(20);
        JTextField cpfField = new JTextField(20);
        JTextField profissaoField = new JTextField(20);
        JTextField nascimentoField = new JTextField(20);
        JTextField sexoField = new JTextField(20);
        JTextField telefoneField = new JTextField(20);
        JTextField celularField = new JTextField(20);
        JTextField emailField = new JTextField(20);

        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Endereço:"));
        panel.add(enderecoField);
        panel.add(new JLabel("CPF:"));
        panel.add(cpfField);
        panel.add(new JLabel("Profissão:"));
        panel.add(profissaoField);
        panel.add(new JLabel("Data de Nascimento (AAAA-MM-DD):"));
        panel.add(nascimentoField);
        panel.add(new JLabel("Sexo (M/F):"));
        panel.add(sexoField);
        panel.add(new JLabel("Telefone (XX) XXXX-XXXX:"));
        panel.add(telefoneField);
        panel.add(new JLabel("Celular:"));
        panel.add(celularField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        int option = JOptionPane.showConfirmDialog(null, panel, "Cadastro de Paciente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String nomePaciente = nomeField.getText();
            String enderecoPaciente = enderecoField.getText();
            String cpf = cpfField.getText();
            String profissao = profissaoField.getText();
            String nascimento = nascimentoField.getText();
            String sexo = sexoField.getText();
            String telefonePaciente = telefoneField.getText();
            String celular = celularField.getText();
            String emailPaciente = emailField.getText();

            cadastroPaciente paciente = new cadastroPaciente(nomePaciente, enderecoPaciente, cpf, profissao, nascimento, sexo, telefonePaciente, celular, emailPaciente);
            controle.adicionarcadastroPaciente(paciente);
        }
    }

    private static void cadastrarMedico(ControlaCadastro controle) {
        JPanel panel = new JPanel(new GridLayout(0, 2));

        JTextField nomeField = new JTextField(20);
        JTextField enderecoField = new JTextField(20);
        JTextField crmField = new JTextField(20);
        JTextField especializacaoField = new JTextField(20);
        JTextField telefoneField = new JTextField(20);
        JTextField disponibilidadeField = new JTextField(20);
        JTextField emailField = new JTextField(20);

        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Endereço:"));
        panel.add(enderecoField);
        panel.add(new JLabel("CRM:"));
        panel.add(crmField);
        panel.add(new JLabel("Especialidade:"));
        panel.add(especializacaoField);
        panel.add(new JLabel("Telefone (XX) XXXX-XXXX:"));
        panel.add(telefoneField);
        panel.add(new JLabel("Disponibilidade:"));
        panel.add(disponibilidadeField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        int option = JOptionPane.showConfirmDialog(null, panel, "Cadastro de Médico", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String nomeMedico = nomeField.getText();
            String enderecoMedico = enderecoField.getText();
            String crm = crmField.getText();
            String especializacao = especializacaoField.getText();
            String telefoneMedico = telefoneField.getText();
            String disponibilidade = disponibilidadeField.getText();
            String emailMedico = emailField.getText();

            cadastroMedico medico = new cadastroMedico(nomeMedico, enderecoMedico, crm, especializacao, telefoneMedico, disponibilidade, emailMedico);
            controle.adicionarcadastroMedico(medico);
        }
    }

    private static void atualizarCadastroTotal(ControlaCadastro controle) {
        int escolhaAtualizacao = Integer.parseInt(JOptionPane.showInputDialog(null,
                  "1 - Atualização total de Paciente"
                        + "\n2 - Atualização total de Médico" , "Selecione o campo a alterar:" , JOptionPane.PLAIN_MESSAGE));

        switch (escolhaAtualizacao) {
            case 1:
                atualizarPacienteTotal(controle);
                break;
            case 2:
                atualizarMedicoTotal(controle);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, escolha uma opção válida.");
        }
    }

    private static void atualizarPacienteTotal(ControlaCadastro controle) {
        String busca = JOptionPane.showInputDialog(null , "Digite o CPF ou nome do paciente para buscar:","Informe :" , JOptionPane.PLAIN_MESSAGE);
        controle.buscarAtualizarPaciente(busca);
    }

    private static void atualizarMedicoTotal(ControlaCadastro controle) {
        String busca = JOptionPane.showInputDialog(null , "Digite o CRM ou nome do médico para buscar:","Informe :" , JOptionPane.PLAIN_MESSAGE);
        controle.buscarAtualizarMedico(busca);
    }

    private static void removerPacienteOuMedico(ControlaCadastro controle) {
		int tipoRemocao = Integer.parseInt(JOptionPane.showInputDialog(null, "1 - Paciente\n2 - Médico" ,
				" Selecione a opção para remoção :" , JOptionPane.PLAIN_MESSAGE));
		if (tipoRemocao == 1) {
			controle.removerRegistro("paciente");
		} else if (tipoRemocao == 2) {
			controle.removerRegistro("medico");
		} else {
			JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, escolha uma opção válida.");
		}
	}

	private static void buscarAlterarCelular(ControlaCadastro controle) {
	    int escolhaTipoCadastro = Integer.parseInt(JOptionPane.showInputDialog(null,
	             "1 - Paciente" + "\n2 - Médico" , "Selecione a opção para alteração :" , JOptionPane.PLAIN_MESSAGE));

	    switch (escolhaTipoCadastro) {
	        case 1:
	            buscarAlterarCelularPaciente(controle);
	            break;
	        case 2:
	            buscarAlterarCelularMedico(controle);
	            break;
	        default:
	            JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, escolha uma opção válida.");
	    }
	}

	private static void buscarAlterarCelularPaciente(ControlaCadastro controle) {
	    String busca = JOptionPane.showInputDialog(null , "Digite o CPF ou nome do paciente para buscar:" , 
	    		"Paciente" , JOptionPane.PLAIN_MESSAGE);
	    controle.buscarAlterarCelularPaciente(busca);
	}

	private static void buscarAlterarCelularMedico(ControlaCadastro controle) {
	    String busca = JOptionPane.showInputDialog(null , "Digite o CRM ou nome do médico para buscar:", 
	    		"Médico" , JOptionPane.PLAIN_MESSAGE);
	    controle.buscarAlterarCelularMedico(busca);
	}

	private static ControlaCadastro controle = new ControlaCadastro();
	private static void escolherRelatorio() {
        int escolhaRelatorio = Integer.parseInt(JOptionPane.showInputDialog(null,"1 - Relatório de Pacientes" 
        		+ "\n2 - Relatório de Médicos" + "\n3 - Relatório de Especialização dos médicos"
        		+"\n4 - Relatório de busca por endereço do paciente", "Selecione uma opção :" ,
        		JOptionPane.PLAIN_MESSAGE));

        switch (escolhaRelatorio) {
            case 1:
                exibirRelatorioPacientes();
                break;
            case 2:
                exibirRelatorioMedicos();
                break;
            case 3:
            	String especializacao = JOptionPane.showInputDialog(null, "Digite a especialização:", "Especialização", JOptionPane.PLAIN_MESSAGE);
            	if (especializacao != null) {
                    if (especializacaoExiste(especializacao)) {
                        exibirRelatorioEspecializacao(especializacao);
                    } else {
                        JOptionPane.showMessageDialog(null, "Especialização não encontrada no cadastro.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Operação cancelada.", "Cancelado", JOptionPane.WARNING_MESSAGE);
                } 
            	break;
            case 4:            	
            	buscarParteEnderecoCliente(controle);            	
            	break;
            default:
                JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, escolha uma opção válida.");
        }
    }
	 private static boolean especializacaoExiste(String especializacao) {
	        Connection conn = ConexaoMySQL.getInstance();
	        try {
	            String sql = "SELECT COUNT(*) FROM medico WHERE especializacao = ?";
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setString(1, especializacao);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                int count = rs.getInt(1);
	                return count > 0;
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        } finally {
	            try {
	                conn.close();
	            } catch (SQLException e) {
	                throw new RuntimeException(e);
	            }
	        }
	        return false;
	    }

	private static void exibirRelatorioPacientes() {
	    Connection conn = ConexaoMySQL.getInstance();
	    List<cadastroPaciente> listaPacientes = new ArrayList<>();

	    try {
	        String sqlPacientes = "SELECT * FROM pacientes";
	        PreparedStatement stmtPacientes = conn.prepareStatement(sqlPacientes);
	        ResultSet resultadosPacientes = stmtPacientes.executeQuery();
	        while (resultadosPacientes.next()) {
	            cadastroPaciente paciente = new cadastroPaciente(resultadosPacientes.getString("nomePaciente"),
	                    resultadosPacientes.getString("enderecoPaciente"), resultadosPacientes.getString("cpf"),
	                    resultadosPacientes.getString("profissao"), resultadosPacientes.getString("nascimento"),
	                    resultadosPacientes.getString("sexo"), resultadosPacientes.getString("telefonePaciente"),
	                    resultadosPacientes.getString("celular"), resultadosPacientes.getString("emailPaciente"));
	            paciente.setId(resultadosPacientes.getInt("id"));
	            listaPacientes.add(paciente);
	        }
	        stmtPacientes.close();
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }

	    String[] colunas = {"ID", "Nome", "Endereço", "CPF", "Profissão", "Nascimento", "Sexo", "Telefone", "Celular", "Email"};
	    DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
	    for (cadastroPaciente paciente : listaPacientes) {
	        Object[] linha = {paciente.getId(), paciente.getNomePaciente(), paciente.getEnderecoPaciente(), paciente.getCpf(),
	                paciente.getProfissao(), paciente.getNascimento(), paciente.getSexo(), paciente.getTelefonePaciente(),
	                paciente.getCelular(), paciente.getEmailPaciente()};
	        modelo.addRow(linha);
	    }

	    JTable tabela = new JTable(modelo);
	    tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	    for (int column = 0; column < tabela.getColumnCount(); column++) {
	        TableColumn tableColumn = tabela.getColumnModel().getColumn(column);
	        int preferredWidth = tableColumn.getMinWidth();
	        int maxWidth = tableColumn.getMaxWidth();

	        for (int row = 0; row < tabela.getRowCount(); row++) {
	            TableCellRenderer cellRenderer = tabela.getCellRenderer(row, column);
	            Component c = tabela.prepareRenderer(cellRenderer, row, column);
	            int width = c.getPreferredSize().width + tabela.getIntercellSpacing().width;
	            preferredWidth = Math.max(preferredWidth, width);

	            if (preferredWidth >= maxWidth) {
	                preferredWidth = maxWidth;
	                break;
	            }
	        }
	        tableColumn.setPreferredWidth(preferredWidth);
	    }

	    JFrame frame = new JFrame("Relatório de Pacientes");
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.add(new JScrollPane(tabela));
	    frame.pack();
	    frame.setLocationRelativeTo(null); 
	    frame.setVisible(true);
	}
	private static void exibirRelatorioMedicos() {
	    Connection conn = ConexaoMySQL.getInstance();
	    List<cadastroMedico> listaMedicos = new ArrayList<>();

	    try {
	        String sqlMedicos = "SELECT * FROM medico";
	        PreparedStatement stmtMedicos = conn.prepareStatement(sqlMedicos);
	        ResultSet resultadosMedicos = stmtMedicos.executeQuery();
	        while (resultadosMedicos.next()) {
	            cadastroMedico medico = new cadastroMedico(resultadosMedicos.getString("nome"),
	                    resultadosMedicos.getString("crm"), resultadosMedicos.getString("telefone"),
	                    resultadosMedicos.getString("endereco"), resultadosMedicos.getString("especializacao"),
	                    resultadosMedicos.getString("disponibilidade"), resultadosMedicos.getString("email"));
	            medico.setId_medico(resultadosMedicos.getInt("id_medico"));
	            listaMedicos.add(medico);
	        }
	        stmtMedicos.close();
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }

	    String[] colunas = {"ID", "Nome", "CRM", "Telefone", "Endereço", "Especialização", "Disponibilidade", "Email"};
	    DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
	    for (cadastroMedico medico : listaMedicos) {
	        Object[] linha = {medico.getId_medico(), medico.getNOME(), medico.getCRM(), medico.getTELEFONE(), medico.getENDERECO(),
	                medico.getESPECIALIZACAO(), medico.getDISPONIBILIDADE(), medico.getEmail()};
	        modelo.addRow(linha);
	    }

	    JTable tabela = new JTable(modelo);
	    tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	    for (int column = 0; column < tabela.getColumnCount(); column++) {
	        TableColumn tableColumn = tabela.getColumnModel().getColumn(column);
	        int preferredWidth = tableColumn.getMinWidth();
	        int maxWidth = tableColumn.getMaxWidth();

	        for (int row = 0; row < tabela.getRowCount(); row++) {
	            TableCellRenderer cellRenderer = tabela.getCellRenderer(row, column);
	            Component c = tabela.prepareRenderer(cellRenderer, row, column);
	            int width = c.getPreferredSize().width + tabela.getIntercellSpacing().width;
	            preferredWidth = Math.max(preferredWidth, width);

	            if (preferredWidth >= maxWidth) {
	                preferredWidth = maxWidth;
	                break;
	            }
	        }
	        tableColumn.setPreferredWidth(preferredWidth);
	    }

	    JFrame frame = new JFrame("Relatório de Médicos");
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.add(new JScrollPane(tabela));
	    frame.pack();
	    frame.setLocationRelativeTo(null); 
	    frame.setVisible(true);
	}
	private static void buscarParteEnderecoCliente(ControlaCadastro controle) {
	    String parteEndereco = JOptionPane.showInputDialog(null, "Digite a parte do endereço para iniciar a busca:", 
	                                                       "Busca por parte do Endereço", JOptionPane.PLAIN_MESSAGE);
	    
	    List<cadastroPaciente> resultadosBusca = controle.buscarPorParteEnderecoPaciente(parteEndereco);
	    if (resultadosBusca.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Paciente não encontrado no cadastro." , "Erro" , JOptionPane.ERROR_MESSAGE);
        } else {
	    exibirResultadosBusca(resultadosBusca);
	}
}
	private static void exibirResultadosBusca(List<cadastroPaciente> listaPacientes) {
	    String[] colunas = {"ID", "Nome", "Endereço", "CPF", "Profissão", "Nascimento", "Sexo", "Telefone", "Celular", "Email"};
	    DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

	    for (cadastroPaciente paciente : listaPacientes) {
	        Object[] linha = {paciente.getId(), paciente.getNomePaciente(), paciente.getEnderecoPaciente(), paciente.getCpf(),
	                paciente.getProfissao(), paciente.getNascimento(), paciente.getSexo(), paciente.getTelefonePaciente(),
	                paciente.getCelular(), paciente.getEmailPaciente()};
	        modelo.addRow(linha);
	    }

	    JTable tabela = new JTable(modelo);
	    tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

	    for (int column = 0; column < tabela.getColumnCount(); column++) {
	        TableColumn tableColumn = tabela.getColumnModel().getColumn(column);
	        int preferredWidth = tableColumn.getMinWidth();
	        int maxWidth = tableColumn.getMaxWidth();

	        for (int row = 0; row < tabela.getRowCount(); row++) {
	            TableCellRenderer cellRenderer = tabela.getCellRenderer(row, column);
	            Component c = tabela.prepareRenderer(cellRenderer, row, column);
	            int width = c.getPreferredSize().width + tabela.getIntercellSpacing().width;
	            preferredWidth = Math.max(preferredWidth, width);

	            if (preferredWidth >= maxWidth) {
	                preferredWidth = maxWidth;
	                break;
	            }
	        }
	        tableColumn.setPreferredWidth(preferredWidth);
	    }

	    JFrame frame = new JFrame("Resultado da Busca por Endereço de Pacientes");
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.add(new JScrollPane(tabela));
	    frame.pack();
	    frame.setLocationRelativeTo(null); 
	    frame.setVisible(true);
	}
	 private static void exibirRelatorioEspecializacao(String especializacao) {
		 Connection conn = ConexaoMySQL.getInstance();
	        List<cadastroMedico> listaMedicos = new ArrayList<>();

	        try {
	            String sqlMedicos = "SELECT * FROM medico WHERE especializacao = ?";
	            PreparedStatement stmtMedicos = conn.prepareStatement(sqlMedicos);
	            stmtMedicos.setString(1, especializacao);
	            ResultSet resultadosMedicos = stmtMedicos.executeQuery();
	            while (resultadosMedicos.next()) {
	                cadastroMedico medico = new cadastroMedico(resultadosMedicos.getString("nome"),
	                        resultadosMedicos.getString("crm"), resultadosMedicos.getString("telefone"),
	                        resultadosMedicos.getString("endereco"), resultadosMedicos.getString("especializacao"),
	                        resultadosMedicos.getString("disponibilidade"), resultadosMedicos.getString("email"));
	                medico.setId_medico(resultadosMedicos.getInt("id_medico"));
	                listaMedicos.add(medico);
	            }
	            stmtMedicos.close();
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }

	        String[] colunas = {"ID", "Nome", "CRM", "Telefone", "Endereço", "Especialização", "Disponibilidade", "Email"};
	        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
	        for (cadastroMedico medico : listaMedicos) {
	            Object[] linha = {medico.getId_medico(), medico.getNOME(), medico.getCRM(), medico.getTELEFONE(), medico.getENDERECO(),
	                    medico.getESPECIALIZACAO(), medico.getDISPONIBILIDADE(), medico.getEmail()};
	            modelo.addRow(linha);
	        }

	        JTable tabela = new JTable(modelo);
	        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	        for (int column = 0; column < tabela.getColumnCount(); column++) {
	            TableColumn tableColumn = tabela.getColumnModel().getColumn(column);
	            int preferredWidth = tableColumn.getMinWidth();
	            int maxWidth = tableColumn.getMaxWidth();

	            for (int row = 0; row < tabela.getRowCount(); row++) {
	                TableCellRenderer cellRenderer = tabela.getCellRenderer(row, column);
	                Component c = tabela.prepareRenderer(cellRenderer, row, column);
	                int width = c.getPreferredSize().width + tabela.getIntercellSpacing().width;
	                preferredWidth = Math.max(preferredWidth, width);

	                if (preferredWidth >= maxWidth) {
	                    preferredWidth = maxWidth;
	                    break;
	                }
	            }
	            tableColumn.setPreferredWidth(preferredWidth);
	        }

	        JFrame frame = new JFrame("Relatório de Médicos");
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        frame.add(new JScrollPane(tabela));
	        frame.pack();
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	    }
	
private static void exibirPorOrdemAlfabeticaMedico() {
    Connection conn = ConexaoMySQL.getInstance();
    List<cadastroMedico> listaMedicos = new ArrayList<>();

    try {
        String sqlMedicos = "SELECT * FROM medico ORDER BY nome ASC";
        PreparedStatement stmtMedicos = conn.prepareStatement(sqlMedicos);
        ResultSet resultadosMedicos = stmtMedicos.executeQuery();
        while (resultadosMedicos.next()) {
            cadastroMedico medico = new cadastroMedico(resultadosMedicos.getString("nome"),
                    resultadosMedicos.getString("crm"), resultadosMedicos.getString("telefone"),
                    resultadosMedicos.getString("endereco"), resultadosMedicos.getString("especializacao"),
                    resultadosMedicos.getString("disponibilidade"), resultadosMedicos.getString("email"));
            medico.setId_medico(resultadosMedicos.getInt("id_medico"));
            listaMedicos.add(medico);
        }
        stmtMedicos.close();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    String[] colunas = {"ID", "Nome", "CRM", "Telefone", "Endereço", "Especialização", "Disponibilidade", "Email"};
    DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
    for (cadastroMedico medico : listaMedicos) {
        Object[] linha = {medico.getId_medico(), medico.getNOME(), medico.getCRM(), medico.getTELEFONE(), medico.getENDERECO(),
                medico.getESPECIALIZACAO(), medico.getDISPONIBILIDADE(), medico.getEmail()};
        modelo.addRow(linha);
    }

    JTable tabela = new JTable(modelo);
    tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    for (int column = 0; column < tabela.getColumnCount(); column++) {
        TableColumn tableColumn = tabela.getColumnModel().getColumn(column);
        int preferredWidth = tableColumn.getMinWidth();
        int maxWidth = tableColumn.getMaxWidth();

        for (int row = 0; row < tabela.getRowCount(); row++) {
            TableCellRenderer cellRenderer = tabela.getCellRenderer(row, column);
            Component c = tabela.prepareRenderer(cellRenderer, row, column);
            int width = c.getPreferredSize().width + tabela.getIntercellSpacing().width;
            preferredWidth = Math.max(preferredWidth, width);

            if (preferredWidth >= maxWidth) {
                preferredWidth = maxWidth;
                break;
            }
        }
        tableColumn.setPreferredWidth(preferredWidth);
    }

    JFrame frame = new JFrame("Relatório de Médicos");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.add(new JScrollPane(tabela));
    frame.pack();
    frame.setLocationRelativeTo(null); 
    frame.setVisible(true);
}
private static void exibirOrdemAlfabeticaPaciente() {
    Connection conn = ConexaoMySQL.getInstance();
    List<cadastroPaciente> listaPacientes = new ArrayList<>();

    try {
        String sqlPacientes = "SELECT * FROM pacientes ORDER BY nomePaciente ASC";
        PreparedStatement stmtPacientes = conn.prepareStatement(sqlPacientes);
        ResultSet resultadosPacientes = stmtPacientes.executeQuery();
        while (resultadosPacientes.next()) {
            cadastroPaciente paciente = new cadastroPaciente(resultadosPacientes.getString("nomePaciente"),
                    resultadosPacientes.getString("enderecoPaciente"), resultadosPacientes.getString("cpf"),
                    resultadosPacientes.getString("profissao"), resultadosPacientes.getString("nascimento"),
                    resultadosPacientes.getString("sexo"), resultadosPacientes.getString("telefonePaciente"),
                    resultadosPacientes.getString("celular"), resultadosPacientes.getString("emailPaciente"));
            paciente.setId(resultadosPacientes.getInt("id"));
            listaPacientes.add(paciente);
        }
        stmtPacientes.close();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    String[] colunas = {"ID", "Nome", "Endereço", "CPF", "Profissão", "Nascimento", "Sexo", "Telefone", "Celular", "Email"};
    DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
    for (cadastroPaciente paciente : listaPacientes) {
        Object[] linha = {paciente.getId(), paciente.getNomePaciente(), paciente.getEnderecoPaciente(), paciente.getCpf(),
                paciente.getProfissao(), paciente.getNascimento(), paciente.getSexo(), paciente.getTelefonePaciente(),
                paciente.getCelular(), paciente.getEmailPaciente()};
        modelo.addRow(linha);
    }

    JTable tabela = new JTable(modelo);
    tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    for (int column = 0; column < tabela.getColumnCount(); column++) {
        TableColumn tableColumn = tabela.getColumnModel().getColumn(column);
        int preferredWidth = tableColumn.getMinWidth();
        int maxWidth = tableColumn.getMaxWidth();

        for (int row = 0; row < tabela.getRowCount(); row++) {
            TableCellRenderer cellRenderer = tabela.getCellRenderer(row, column);
            Component c = tabela.prepareRenderer(cellRenderer, row, column);
            int width = c.getPreferredSize().width + tabela.getIntercellSpacing().width;
            preferredWidth = Math.max(preferredWidth, width);

            if (preferredWidth >= maxWidth) {
                preferredWidth = maxWidth;
                break;
            }
        }
        tableColumn.setPreferredWidth(preferredWidth);
    }

    JFrame frame = new JFrame("Relatório de Pacientes");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.add(new JScrollPane(tabela));
    frame.pack();
    frame.setLocationRelativeTo(null); 
    frame.setVisible(true);
}

	private static void exibirOrdemAlfabetica() {
    int escolhaRelatorio = Integer.parseInt(JOptionPane.showInputDialog(null,"1 - Relatório de Pacientes por ordem alfabética" 
    		+ "\n2 - Relatório de Médicos por ordem alfabética" , "Selecione uma opção :" ,
    		JOptionPane.PLAIN_MESSAGE));

    switch (escolhaRelatorio) {
        case 1:
        	exibirOrdemAlfabeticaPaciente();
            break;
        case 2:
        	exibirPorOrdemAlfabeticaMedico();
            break;
        default:
            JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, escolha uma opção válida.");
    	}
	}
	
	private static void buscarParteNomeMedico(String nome) {
	    Connection conn = ConexaoMySQL.getInstance();
	    List<cadastroMedico> listaMedicos = new ArrayList<>();

	    try {
	        String sqlMedicos = "SELECT * FROM medico WHERE nome LIKE ?";
	        PreparedStatement stmtMedicos = conn.prepareStatement(sqlMedicos);
	        stmtMedicos.setString(1, "%" + nome + "%");
	        ResultSet resultadosMedicos = stmtMedicos.executeQuery();
	        while (resultadosMedicos.next()) {
	            cadastroMedico medico = new cadastroMedico(resultadosMedicos.getString("nome"),
	                    resultadosMedicos.getString("crm"), resultadosMedicos.getString("telefone"),
	                    resultadosMedicos.getString("endereco"), resultadosMedicos.getString("especializacao"),
	                    resultadosMedicos.getString("disponibilidade"), resultadosMedicos.getString("email"));
	            medico.setId_medico(resultadosMedicos.getInt("id_medico"));
	            listaMedicos.add(medico);
	        }
	        stmtMedicos.close();
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }

	    String[] colunas = {"ID", "Nome", "CRM", "Telefone", "Endereço", "Especialização", "Disponibilidade", "Email"};
	    DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
	    for (cadastroMedico medico : listaMedicos) {
	        Object[] linha = {medico.getId_medico(), medico.getNOME(), medico.getCRM(), medico.getTELEFONE(), medico.getENDERECO(),
	                medico.getESPECIALIZACAO(), medico.getDISPONIBILIDADE(), medico.getEmail()};
	        modelo.addRow(linha);
	    }

	    JTable tabela = new JTable(modelo);
	    tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	    for (int column = 0; column < tabela.getColumnCount(); column++) {
	        TableColumn tableColumn = tabela.getColumnModel().getColumn(column);
	        int preferredWidth = tableColumn.getMinWidth();
	        int maxWidth = tableColumn.getMaxWidth();

	        for (int row = 0; row < tabela.getRowCount(); row++) {
	            TableCellRenderer cellRenderer = tabela.getCellRenderer(row, column);
	            Component c = tabela.prepareRenderer(cellRenderer, row, column);
	            int width = c.getPreferredSize().width + tabela.getIntercellSpacing().width;
	            preferredWidth = Math.max(preferredWidth, width);

	            if (preferredWidth >= maxWidth) {
	                preferredWidth = maxWidth;
	                break;
	            }
	        }
	        tableColumn.setPreferredWidth(preferredWidth);
	    }

	    JFrame frame = new JFrame("Relatório de Médicos");
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.add(new JScrollPane(tabela));
	    frame.pack();
	    frame.setLocationRelativeTo(null); 
	    frame.setVisible(true);
	}
	private static void buscarParteNomePaciente(String nomePaciente) {
	    Connection conn = ConexaoMySQL.getInstance();
	    List<cadastroPaciente> listaPacientes = new ArrayList<>();

	    try {
	        String sqlPacientes = "SELECT * FROM pacientes WHERE nomePaciente LIKE ?";
	        PreparedStatement stmtPacientes = conn.prepareStatement(sqlPacientes);
	        stmtPacientes.setString(1, "%" + nomePaciente + "%");
	        ResultSet resultadosPacientes = stmtPacientes.executeQuery();
	        while (resultadosPacientes.next()) {
	            cadastroPaciente paciente = new cadastroPaciente(resultadosPacientes.getString("nomePaciente"),
	                    resultadosPacientes.getString("enderecoPaciente"), resultadosPacientes.getString("cpf"),
	                    resultadosPacientes.getString("profissao"), resultadosPacientes.getString("nascimento"),
	                    resultadosPacientes.getString("sexo"), resultadosPacientes.getString("telefonePaciente"),
	                    resultadosPacientes.getString("celular"), resultadosPacientes.getString("emailPaciente"));
	            paciente.setId(resultadosPacientes.getInt("id"));
	            listaPacientes.add(paciente);
	        }
	        stmtPacientes.close();
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }

	    String[] colunas = {"ID", "Nome", "Endereço", "CPF", "Profissão", "Nascimento", "Sexo", "Telefone", "Celular", "Email"};
	    DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
	    for (cadastroPaciente paciente : listaPacientes) {
	        Object[] linha = {paciente.getId(), paciente.getNomePaciente(), paciente.getEnderecoPaciente(), paciente.getCpf(),
	                paciente.getProfissao(), paciente.getNascimento(), paciente.getSexo(), paciente.getTelefonePaciente(),
	                paciente.getCelular(), paciente.getEmailPaciente()};
	        modelo.addRow(linha);
	    }

	    JTable tabela = new JTable(modelo);
	    tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	    for (int column = 0; column < tabela.getColumnCount(); column++) {
	        TableColumn tableColumn = tabela.getColumnModel().getColumn(column);
	        int preferredWidth = tableColumn.getMinWidth();
	        int maxWidth = tableColumn.getMaxWidth();

	        for (int row = 0; row < tabela.getRowCount(); row++) {
	            TableCellRenderer cellRenderer = tabela.getCellRenderer(row, column);
	            Component c = tabela.prepareRenderer(cellRenderer, row, column);
	            int width = c.getPreferredSize().width + tabela.getIntercellSpacing().width;
	            preferredWidth = Math.max(preferredWidth, width);

	            if (preferredWidth >= maxWidth) {
	                preferredWidth = maxWidth;
	                break;
	            }
	        }
	        tableColumn.setPreferredWidth(preferredWidth);
	    }

	    JFrame frame = new JFrame("Relatório de Pacientes");
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.add(new JScrollPane(tabela));
	    frame.pack();
	    frame.setLocationRelativeTo(null); 
	    frame.setVisible(true);
	}
	private static void exibirParteNome() {
	    int escolhaRelatorio = Integer.parseInt(JOptionPane.showInputDialog(null,"1 - Pacientes :" 
	    		+ "\n2 - Médicos : " , "Selecione uma opção :" ,
	    		JOptionPane.PLAIN_MESSAGE));

	    switch (escolhaRelatorio) {
	        case 1:	        	
	        	String nomePaciente = JOptionPane.showInputDialog(null, "Digite a parte do nome do paciente:", 
	        			"Pacientes Encontrados : ", JOptionPane.PLAIN_MESSAGE);
	        	buscarParteNomePaciente(nomePaciente);
	            break;
	        case 2:
	        	String nome = JOptionPane.showInputDialog(null, "Digite a parte do nome do médico:", 
	        			"Médicos Encontrados : ", JOptionPane.PLAIN_MESSAGE);
	        	buscarParteNomeMedico(nome);
	            break;
	        default:
	            JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, escolha uma opção válida.");
	    	}
		}
}
