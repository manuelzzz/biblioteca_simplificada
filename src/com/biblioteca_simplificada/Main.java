package com.biblioteca_simplificada;

import java.util.Scanner;
import com.biblioteca_simplificada.domain.*;

public class Main {
	public static void main(String[] args) {
		Biblioteca biblioteca = new Biblioteca();
		boolean run = true;

		while (run) {
			biblioteca.mostrarOpcoes();

			run = biblioteca.executarOpcao();
		}
	}
}
