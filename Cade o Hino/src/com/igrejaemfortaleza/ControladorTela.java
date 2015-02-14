package com.igrejaemfortaleza;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import utils.PreferenciasException;

public class ControladorTela implements ActionListener, KeyListener {

	private static ControladorTela instance;
	
	Controlador controlador;

	private JanelaPrincipal tela;
	
	private ControladorTela() {
		try {
			controlador = Controlador.getInstance();
		} catch (PreferenciasException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Deu erro!" , JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static ControladorTela getInstance() {
		if(instance == null) {
			instance = new ControladorTela();
		}
		return instance;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == tela.btBuscar) {				// A��O BUSCAR
			String filtro = tela.tfBusca.getText();
			
			try {
				boolean result = false;
				if(tela.rbTipoBuscaCod.isSelected()) {
					result = controlador.buscarSlidePorCodigo(filtro);
				} else if(tela.rbTipoBuscaNome.isSelected()) {
					result = controlador.buscarSlidePorNome(filtro);
				} else if(tela.rbTipoBuscaTexto.isSelected()) {
					result = controlador.buscarSlidePorTexto(filtro);
				}
				
				if(!result) {
					JOptionPane.showMessageDialog(null, "Hino n�o encontrado.", "Arquivo n�o encontrado", JOptionPane.ERROR_MESSAGE);
				}
			} catch (IllegalArgumentException exc) {
				JOptionPane.showMessageDialog(null, exc.getMessage(), "Formato inv�lido", JOptionPane.ERROR_MESSAGE);
			} catch (PreferenciasException exc) {
				JOptionPane.showMessageDialog(null, exc.getMessage(), "Erro ao buscar!", JOptionPane.ERROR_MESSAGE);
			}
			
		} else if(e.getSource() == tela.miAbir) {			// A��O ABRIR
			controlador.abrirArquivo();
			exibeDirBusca();
		} else if(e.getSource() == tela.miSair) {			// A��O SAIR
			tela.dispose();
		} else if(e.getSource() == tela.miSobre) {			// A��O SOBRE
			// TODO: criar janela "Sobre" que ir� tem um manual e informa��es do sistema
			System.out.println("Sobre");
		} else if (e.getSource() == tela.rbTipoBuscaCod) {
			tela.tfBusca.setHint("Ex: S100");
			tela.tfBusca.requestFocus();
			tela.rbTipoBuscaCod.requestFocus();
		} else if (e.getSource() == tela.rbTipoBuscaNome) {
			tela.tfBusca.setHint("Ex: Jardim De Deus");
			tela.tfBusca.requestFocus();
			tela.rbTipoBuscaNome.requestFocus();
		} else if (e.getSource() == tela.rbTipoBuscaTexto) {
			tela.tfBusca.setHint("Ex: eu de fato nunca consegui");
			tela.tfBusca.requestFocus();
			tela.rbTipoBuscaTexto.requestFocus();
		}
	}
	
	void exibeDirBusca() {
		try {
			String dirPath = controlador.getDiretorioBusca();
			
			JanelaPrincipal.lbDirBusca.setText(dirPath);
		} catch (PreferenciasException e) {
			e.printStackTrace();
		}
	}
	
	public void mudarEstilo() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getSource() == tela.tfBusca) {
			if(e.getKeyChar() == '\n') {
				tela.btBuscar.doClick();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public void setTela(JanelaPrincipal janelaPrincipal) {
		this.tela = janelaPrincipal;
	}
	
}
