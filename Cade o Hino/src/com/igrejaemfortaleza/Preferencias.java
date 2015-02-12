package com.igrejaemfortaleza;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import utils.PreferenciasException;


/**
 * Classe que gerencia os diret�rios do sistema
 * 
 * @author Zedequias Santos
 *
 */
public class Preferencias {

	private static Preferencias instance;
	
	private File diretorioBusca;
	private File diretorioBase;
	private File diretorioHinos;
	private File prefs;
	
	private Preferencias() throws PreferenciasException {
		String userHome = System.getProperty("user.home");
		String nomePasta = "Cade o Hino";
		String path = userHome + File.separator + "Documents" +  File.separator + nomePasta;
		
		diretorioBase = new File(path);
		if(!diretorioBase.exists()) {
			diretorioBase.mkdir();
		}

		diretorioHinos = new File(diretorioBase, "Hinos");
		if(!diretorioHinos.exists()) {
			diretorioHinos.mkdir();
		}
		
		prefs = new File(diretorioBase, "Preferencias.dat");
		diretorioBusca = diretorioHinos;
		
		String result = criarEstruturaSubPastas(diretorioBusca);
		
		if(!"Estas pastas j� existem:\n".equals(result)) {
			System.out.println(result);
		}
		
		// se o arquivo poder ser usado, tentamos ler qual o �ltimo diret�rio de busca usado
//		if (prefs.exists() && prefs.length() > 0) {
//			try {
//				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(prefs));
//				
//				File readObject = (File) ois.readObject();
//				
//				diretorioBusca = readObject;
//				ois.close();
//			} catch (IOException | ClassNotFoundException e) {
//				throw new PreferenciasException("N�o consegui l� onde era a diret�rio para eu buscar os hinos.\nPor favor, informe de novo em Arquivo>Prefer�ncias");
//			}
//		} else {	// sen�o, criamos o arquivo
//			try {
//				prefs.createNewFile();
//			} catch (IOException e) {
//				throw new PreferenciasException("N�o consegui criar minha pasta para guardar minhas coisas.\nTentei criar aqui: "+ prefs.getAbsolutePath());
//			}
//		}
	}
	
	public static Preferencias getInstance() throws PreferenciasException {
		if(instance == null) {
			instance = new Preferencias();
		}
		return instance;
	}
	
	public String criarEstruturaSubPastas(File raiz) {
		File subPastas[] = new File[5];
		StringBuilder log = new StringBuilder("Estas pastas j� existem:\n");
		
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
			throw new PreferenciasException("Calma rapaz, voc� nem me disse ainda onde procurar! V� em Arquivo>Prefer�ncias, por favor");
		}
		
		if(!diretorioBusca.exists() || !diretorioBusca.isDirectory() || diretorioBusca.isHidden()) {
			throw new PreferenciasException("Eu n�o consigo acessar o diret�rio que voc� me disse pra procurar! :(");
		}
		return diretorioBusca;
	}

	public void setDiretorioBusca(String path) {
		this.diretorioBusca = new File(path);
	}
	
	public void setDiretorioBusca(File diretorio) {
		this.diretorioBusca = diretorio;
	}
	
	/**
	 * Salva o �ltimo diret�rio de busca usado
	 * 
	 * @throws PreferenciasException
	 */
	public void deploy() throws PreferenciasException {
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(prefs));
			oos.writeObject(diretorioBusca);
			oos.close();
		} catch (IOException e) {
			throw new PreferenciasException("N�o consegui salvar minhas coisas.\nDepois voc� vai ter que dizer onde ficam os hinos de novo, Ok?");
		}
		
	}
}
