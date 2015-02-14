package com.igrejaemfortaleza;
import java.io.File;

import utils.PreferenciasException;


/**
 * Classe que gerencia os diretórios do sistema
 * 
 * @author Zedequias Santos
 *
 */
public class Preferencias {

	private static Preferencias instance;
	
	private File diretorioBusca;
	private File diretorioBase;
	
	private Preferencias() {
		String userHome = System.getProperty("user.home");
		String nomePasta = "Cade o Hino";
		String path = userHome + File.separator + "Documents" +  File.separator + nomePasta;
		
		diretorioBase = new File(path);
		if(!diretorioBase.exists()) {
			diretorioBase.mkdir();
		}

		diretorioBusca = new File(diretorioBase, "Hinos");
		if(!diretorioBusca.exists()) {
			diretorioBusca.mkdir();
		}
		
		String result = criarEstruturaSubPastas(diretorioBusca);
		
		if(!"Estas pastas já existem:\n".equals(result)) {
			System.out.println(result);
		}
	}
	
	public static Preferencias getInstance() {
		if(instance == null) {
			instance = new Preferencias();
		}
		return instance;
	}
	
	public String criarEstruturaSubPastas(File raiz) {
		File subPastas[] = new File[5];
		StringBuilder log = new StringBuilder("Estas pastas já existem:\n");
		
		subPastas[0] = new File(raiz, "01 HINOS");
		subPastas[1] = new File(raiz, "02 CANTICOS");
		subPastas[2] = new File(raiz, "03 SUPLEMENTOS");
		subPastas[3] = new File(raiz, "04 EXTRAS");
		subPastas[4] = new File(raiz, "05 ALTERNATIVOS");
		
		for (File file : subPastas) {
			if(!file.exists()) {
				file.mkdir();
			} else {
				log.append("\t"+ file.getName() +"\n");
			}
		}
		return log.toString();
	}

	public File getDiretorioBusca() throws PreferenciasException {
		
		if(diretorioBusca == null) {
			throw new PreferenciasException("Calma rapaz, você nem me disse ainda onde procurar! Vá em Arquivo>Preferências, por favor");
		}
		
		if(!diretorioBusca.exists() || !diretorioBusca.isDirectory() || diretorioBusca.isHidden()) {
			throw new PreferenciasException("Eu não consigo acessar o diretório! :(");
		}
		return diretorioBusca;
	}

	public void setDiretorioBusca(String path) {
		this.diretorioBusca = new File(path);
	}
	
	public void setDiretorioBusca(File diretorio) {
		this.diretorioBusca = diretorio;
	}
}
