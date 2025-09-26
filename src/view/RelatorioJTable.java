package view;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.cadastroMedico;
import model.cadastroPaciente;

public class RelatorioJTable {

	    public void criarRelatorioJTable(List<cadastroPaciente> listaPacientes, List<cadastroMedico> listaMedicos) {

	        String[] colunasPacientes = {"ID", "Nome", "Endereço", "CPF", "Profissão", "Nascimento", "Sexo", "Telefone", "Celular", "Email"};
	        DefaultTableModel modeloPacientes = new DefaultTableModel(colunasPacientes, 0);
	        
	        for (cadastroPaciente paciente : listaPacientes) {
	            Object[] linha = {paciente.getId(), paciente.getNomePaciente(), paciente.getEnderecoPaciente(), paciente.getCpf(), 
	                    paciente.getProfissao(), paciente.getNascimento(), paciente.getSexo(), paciente.getTelefonePaciente(), 
	                    paciente.getCelular(), paciente.getEmailPaciente()};
	            modeloPacientes.addRow(linha);
	        }
	        
	        JTable tabelaPacientes = new JTable(modeloPacientes);
	        JScrollPane painelPacientes = new JScrollPane(tabelaPacientes);
	        
	        String[] colunasMedicos = {"ID", "Nome", "CRM", "Telefone", "Endereço", "Especialização", "Disponibilidade", "Email"};
	        DefaultTableModel modeloMedicos = new DefaultTableModel(colunasMedicos, 0);
	        
	        for (cadastroMedico medico : listaMedicos) {
	            Object[] linha = {medico.getId_medico(), medico.getNOME(), medico.getCRM(), medico.getTELEFONE(), medico.getENDERECO(), 
	                    medico.getESPECIALIZACAO(), medico.getDISPONIBILIDADE(), medico.getEmail()};
	            modeloMedicos.addRow(linha);
	        }
	        
	        JTable tabelaMedicos = new JTable(modeloMedicos);
	        JScrollPane painelMedicos = new JScrollPane(tabelaMedicos);
	        	     
	        JFrame frame = new JFrame("Relatório");
	        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
	        frame.add(new JLabel("Relatório de Pacientes"));
	        frame.add(painelPacientes);
	        frame.add(new JLabel("Relatório de Médicos"));
	        frame.add(painelMedicos);
	        frame.pack();
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setVisible(true);
	    }
	}