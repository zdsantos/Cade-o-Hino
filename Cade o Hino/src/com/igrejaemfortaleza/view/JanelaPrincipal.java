package com.igrejaemfortaleza.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utils.HintTextField;
import utils.PreferenciasException;


/**
 * @author Zedequias Santos
 *
 */
public class JanelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	private JMenuBar mnbBarra;
	public JMenu mnArquivo;
	public JMenuItem miSobre;
	public JMenuItem miAbir;
	public JMenuItem miSair;
	
	
	private JPanel pnPrincipal;
	public JTextField tfBusca;
	public JButton btBuscar;
	
	public static JLabel lbDirBusca;
	
	private ControladorTela controladorTela;
	
	public JanelaPrincipal() {
		
		controladorTela = ControladorTela.getInstance();
		
		setTitle("Cad� o Hino?");
		setBounds(250, 200, 500,125);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		preparaMenu();
		preparaPainelPrincipal();
		
		add(mnbBarra, BorderLayout.NORTH);
		add(pnPrincipal, BorderLayout.CENTER);
		
		JPanel pnExibirBusca = new JPanel();
		preparaPainelExibeBusca(pnExibirBusca);
		
		add(pnExibirBusca, BorderLayout.SOUTH);
		
		setResizable(false);
		setVisible(true);
	}

	private void preparaPainelExibeBusca(JPanel pnExibirBusca) {
		pnExibirBusca.setLayout(new BoxLayout(pnExibirBusca, BoxLayout.Y_AXIS));
		
		lbDirBusca = new JLabel();
		lbDirBusca.setForeground(Color.GRAY);
		
		JLabel lbAux = new JLabel("Diret�rio de busca:");
		lbAux.setForeground(Color.GRAY);
		
		controladorTela.exibeDirBusca();
		pnExibirBusca.add(lbAux);
		pnExibirBusca.add(lbDirBusca);
	}
	
	private void preparaPainelPrincipal() {
		tfBusca = new HintTextField("Ex: S100");
		tfBusca.setColumns(10);
		tfBusca.addKeyListener(controladorTela);
		btBuscar = new JButton("Buscar");
		btBuscar.addActionListener(controladorTela);
		
		pnPrincipal = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 3));
		
		pnPrincipal.add(new JLabel("Buscar:"));
		pnPrincipal.add(tfBusca);
		pnPrincipal.add(btBuscar);
	}

	private void preparaMenu() {
		
		miAbir = new JMenuItem("Abrir");
		miAbir.setMnemonic(KeyEvent.VK_A);
		miSair = new JMenuItem("Sair");
		miSobre = new JMenuItem("Sobre");
		
		miAbir.addActionListener(controladorTela);
		miSair.addActionListener(controladorTela);
		miSobre.addActionListener(controladorTela);
		
		mnArquivo = new JMenu("Arquivo");
		mnArquivo.add(miAbir);
		mnArquivo.add(miSair);

		
		mnbBarra = new JMenuBar();
		mnbBarra.add(mnArquivo);
	}
	
	@Override
	public void dispose() {
		try {
			controladorTela.deploy();
		} catch (PreferenciasException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Jesus � o Senhor!", JOptionPane.INFORMATION_MESSAGE);
		}
		super.dispose();
	}
}
