package com.igrejaemfortaleza;

import com.igrejaemfortaleza.view.ControladorTela;
import com.igrejaemfortaleza.view.JanelaPrincipal;

public class Main {

	public static void main(String[] args) {
		ControladorTela controladorTela = ControladorTela.getInstance();
		
		controladorTela.setTela(new JanelaPrincipal());
	}
}
