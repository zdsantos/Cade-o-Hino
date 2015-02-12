package com.igrejaemfortaleza;
import java.awt.Desktop;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import utils.PreferenciasException;

/**
 * Classe que guarda as "regras de negócio" para busca de arquivos e atualização do diretório de busca 
 * 
 * @author Zedequias Santos
 */
public class Controlador {

	private static final int SELECT_FILE = 0;
	private static final int SELECT_DIRECTORY = 1;
	
	private static Controlador instance;
	private static Preferencias preferencias;
	
	private Controlador() throws PreferenciasException {
		preferencias = Preferencias.getInstance();
	}
	
	public static Controlador getInstance() throws PreferenciasException {
		if(instance == null) {
			instance = new Controlador();
		}
		return instance;
	}
	
	/**
	 * Mostra ao usuário uma janela para escolhe de um diretório ou um arquivo
	 * 
	 * @param selectionMode @see SELECT_FILE e SELECT_DIRECTORY
	 * @return path selecionado pelo usuário
	 * @throws PreferenciasException
	 */
	private static String escolherDiretorio(final int selectionMode) {
		
		JFileChooser chooser;
		try {
			File currentDirectory = preferencias.getDiretorioBusca();
			chooser = new JFileChooser(currentDirectory);
		} catch (PreferenciasException e) {
			chooser = new JFileChooser();
		}
		
		if(selectionMode == SELECT_FILE) {
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		} else if(selectionMode == SELECT_DIRECTORY) {
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		} else {
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		}
		
		chooser.setFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				return "";
			}
			
			@Override
			public boolean accept(File f) {
				String fName = f.getName().toLowerCase();
				if(!fName.isEmpty()) {
					if(selectionMode == SELECT_FILE && f.isDirectory()) {
						return false;
					} else if(selectionMode == SELECT_DIRECTORY && f.isFile()) {
						return false;
					}
					return true;
				}
				return false;
			}
		});
		
		int result = chooser.showOpenDialog(null);
		String path = null; 
		switch(result) {
		case JFileChooser.APPROVE_OPTION:
			path = chooser.getSelectedFile().getAbsolutePath();
			System.out.println(path);
			break;
		case JFileChooser.CANCEL_OPTION: case JFileChooser.ERROR_OPTION : break;
		}
		
		return path;
	}
	
	public void setDiretorioBusca() {
		String path = escolherDiretorio(SELECT_DIRECTORY);
		File dir = new File(path);
		
		preferencias.setDiretorioBusca(dir);
	}
	
	public String getDiretorioBusca() throws PreferenciasException {
		return preferencias.getDiretorioBusca().getPath();
	}

	/**
	 * Busca o arquivo pelo nome na pasta setada nas preferências
	 * 
	 * @param filtro parâmetro de busca pelo nome do arquivo
	 * @return
	 * @throws PreferenciasException
	 */
	public boolean buscarSlide(String filtro) throws PreferenciasException {
	
		/* verificação se está no formato X-000 */
		String regex = "[a-zA-Z]{1}[0-9]+";
		if(!filtro.matches(regex)) {
			throw new IllegalArgumentException("Busque usando como padrão <tipo><num>\n ex: S100");
		}
		
		/* tratamento para entrada com letra minúscula */
		filtro = filtro.substring(0, 1).toUpperCase() + filtro.substring(1);
		filtro = filtro.trim();
		
		/* tratamento para entrada tipo C1 ser considerada C01 */
		if(filtro.length() < 3) {
			filtro = filtro.substring(0, 1) + "0" + filtro.substring(1);
		}
		System.out.println("filtro: " + filtro);
		
		File dir = getDiretorioEspecifico(filtro, preferencias.getDiretorioBusca());
		
		if(dir == null) {
			throw new IllegalArgumentException("Não achei a pasta do seu hino! Você pediu pra eu buscar no lugar certo?");
		}
		
		File arquivos[] = dir.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.contains("-")
						&& (name.contains(".ppt") || name.contains(".pptx") );
			}
		});
		
		for(File f: arquivos) {
			String codFile = f.getName();
			codFile = (codFile.split("-")[0]).trim();
			
			System.out.println("codFile: " + codFile);
			if(codFile.equals(filtro)) {
				abrirArquivo(f);
				return true;
			}
		}
		return false;
	}

	private File getDiretorioEspecifico(String filtro, File diretorioBusca) {
		String tipo = filtro.substring(0, 1);
		
		// lista todas as subpastas do diretório de buscar que estão no padrão <num> - <nome>. ex: 01 - HINOS
		File pastas[] = diretorioBusca.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				String regex = "\\d{2} [A-Z]+";
				if(!name.matches(regex)) {
					return false;
				}
				return true;
			}
		});
		
		String codPasta = PastasEnum.getPastasEnum(tipo).getValor();
		
		System.out.println("procurando por: "+codPasta);
		
		for (File p : pastas) {
			System.out.println("testando: " + p.getName() + " como: "+ p.getName().substring(1, 2));
			if(p.getName().substring(0, 2).equals(codPasta)) {
				return p;
			}
		}
		
		return null;
	}
	
	public void abrirArquivo() {
		String path = escolherDiretorio(JFileChooser.FILES_AND_DIRECTORIES);
		File file = new File(path);
		abrirArquivo(file);
	}

	private void abrirArquivo(File f) {
		try {
			Desktop.getDesktop().open(f);
		} catch (IOException e) {
			throw new IllegalArgumentException("Não foi possível abrir o arquivo.");
		}
	}

	public void deploy() throws PreferenciasException {
		preferencias.deploy();
	}
	
	/**
	 * Enum usado para converter a string que representa o hino para o código que representa a pasta
	 * 
	 * @author Zedequias
	 */
	private static enum PastasEnum {
		HINOS("H", "01"),
		CANTICOS("C", "02"),
		SUPLEMENTOS("S", "03"),
		EXTRAS("E", "04"),
		ALTERNATIVOS("A", "05");
		
		public String valor;
		public String tipo;
		
		private PastasEnum(String tipo, String valor) {
			this.valor = valor;
			this.tipo = tipo;
		}
		
		public String getValor() {return this.valor;}
		public static PastasEnum getPastasEnum(String tipo) {
			for (PastasEnum pastaenum : PastasEnum.values()) {
				if(pastaenum.tipo.equals(tipo)) {
					return pastaenum;
				}
			}
			return null;
		}
	}
}
