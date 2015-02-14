package com.igrejaemfortaleza;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import utils.HintTextField;


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
	public HintTextField tfBusca;
	public JButton btBuscar;
	public ButtonGroup rbTipoBusca;
	public JRadioButton rbTipoBuscaCod;
	public JRadioButton rbTipoBuscaNome;
	public JRadioButton rbTipoBuscaTexto;
	
	public static JLabel lbDirBusca;
	
	private ControladorTela controladorTela;
	
	public JanelaPrincipal() {
		
		controladorTela = ControladorTela.getInstance();
		
		setTitle("Cadê o Hino?");
		setBounds(250, 200, 550, 200);
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
		
		JLabel lbAux = new JLabel("Diretório de busca:");
		lbAux.setForeground(Color.GRAY);
		
		controladorTela.exibeDirBusca();
		pnExibirBusca.add(lbAux);
		pnExibirBusca.add(lbDirBusca);
	}
	
	private void preparaPainelPrincipal() {
		tfBusca = new HintTextField("Ex: S100");
		tfBusca.setColumns(20);
		tfBusca.addKeyListener(controladorTela);
		btBuscar = new JButton("Buscar");
		btBuscar.addActionListener(controladorTela);
		rbTipoBusca = new ButtonGroup();
		rbTipoBuscaCod = new JRadioButton("Código", true);
		rbTipoBuscaNome = new JRadioButton("Nome");
		rbTipoBuscaTexto = new JRadioButton("Texto");
		
		rbTipoBusca.add(rbTipoBuscaCod);
		rbTipoBusca.add(rbTipoBuscaNome);
		rbTipoBusca.add(rbTipoBuscaTexto);
		
		rbTipoBuscaCod.addActionListener(controladorTela);
		rbTipoBuscaNome.addActionListener(controladorTela);
		rbTipoBuscaTexto.addActionListener(controladorTela);
		
		JPanel pnTipo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnTipo.setBorder(BorderFactory.createTitledBorder("Buscar por:"));
		
		pnTipo.add(rbTipoBuscaCod);
		pnTipo.add(rbTipoBuscaNome);
		pnTipo.add(rbTipoBuscaTexto);
		
		pnPrincipal = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 3));

		pnPrincipal.add(pnTipo);
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
		JOptionPane.showMessageDialog(null, "Jesus é o Senhor!", "", JOptionPane.INFORMATION_MESSAGE);
		super.dispose();
	}
}
