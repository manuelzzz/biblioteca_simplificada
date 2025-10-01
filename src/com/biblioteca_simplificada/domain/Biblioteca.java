package com.biblioteca_simplificada.domain;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class Biblioteca {
	private int opcao = 0;
	private Scanner scanner;
	private List<Usuario> usuarios;
	private List<Livro> livros;
	private List<Emprestimo> emprestimos;

	public Biblioteca() {
		scanner = new Scanner(System.in);
		usuarios = new ArrayList<>();
		livros = new ArrayList<>();
		emprestimos = new ArrayList<>();
	}

	public void mostrarOpcoes() {
		System.out.println("--------------------------------------------------");
		System.out.println("Sistema de gerenciamento de empréstimos");
		System.out.println("--------------------------------------------------");
		System.out.println("1 - Visualizar os empréstimos");
		System.out.println("2 - Cadastrar Livro");
		System.out.println("3 - Cadastrar Usuário");
		System.out.println("4 - Realizar um novo empréstimo");
		System.out.println("5 - Sair");
	}

	public boolean executarOpcao() {
		opcao = scanner.nextInt();

		switch (opcao) {
		case 1:
			listarEmprestimosEmAndamento();
			break;
		case 2:
			cadastrarLivro();
			break;
		case 3:
			cadastrarUsuario();
			break;
		case 4:
			realizarEmprestimo();
			break;
		case 5:
			System.out.println("Saindo do sistema...");
			return false;
		default:
			System.out.println("Por favor, escolha uma opção válida dentre as anteriores!");
		}

		return true;
	}

	private void listarEmprestimosEmAndamento() {
		System.out.println("Empréstimos em andamento:");
		for (Emprestimo e : emprestimos) {
			if (e.getDataDevolucao() == null) {
				System.out.println("Usuário: " + e.getUsuario().getNome() + " | Livro: " + e.getLivro().getTitulo()
						+ " | Data prevista: " + e.getDataPrevistaDevolucao());
			}
		}
	}

	private void cadastrarLivro() {
		System.out.print("Título: ");
		String titulo = scanner.next();

		System.out.print("Autor: ");
		String autor = scanner.next();

		System.out.print("Ano: ");
		int ano = scanner.nextInt();

		System.out.print("Quantidade disponível: ");
		int quantidade = scanner.nextInt();

		Livro livro = new Livro();
		livro.setTitulo(titulo);
		livro.setAutor(autor);
		livro.setAno(ano);
		livro.setQuantidade(quantidade);

		livros.add(livro);
		System.out.println("Livro cadastrado com sucesso!");
	}

	private void cadastrarUsuario() {
		System.out.print("Nome: ");
		String nome = scanner.next();

		System.out.print("Matrícula: ");
		String matricula = scanner.next();

		System.out.print("Curso: ");
		String curso = scanner.next();

		System.out.print("Tipo (1-Aluno, 2-Professor): ");
		int tipo = scanner.nextInt();

		Usuario usuario;
		if (tipo == 1) {
			usuario = new Aluno();
		} else {
			usuario = new Professor();
		}

		usuario.setNome(nome);
		usuario.setMatricula(matricula);
		usuario.setCurso(curso);

		usuarios.add(usuario);
		System.out.println("Usuário cadastrado com sucesso!");
	}

	private void realizarEmprestimo() {
		if (usuarios.isEmpty() || livros.isEmpty()) {
			System.out.println("Necessário ter usuários e livros cadastrados!");
			return;
		}

		System.out.println("Escolha um usuário:");
		for (int i = 0; i < usuarios.size(); i++) {
			System.out.println(i + " - " + usuarios.get(i).getNome());
		}
		int idxUsuario = scanner.nextInt();
		Usuario usuario = usuarios.get(idxUsuario);

		System.out.println("Escolha um livro disponível:");
		for (int i = 0; i < livros.size(); i++) {
			if (livros.get(i).getQuantidade() > 0) {
				System.out.println(i + " - " + livros.get(i).getTitulo());
			}
		}
		int idxLivro = scanner.nextInt();
		Livro livro = livros.get(idxLivro);

		Emprestimo emprestimo = new Emprestimo();
		emprestimo.setUsuario(usuario);
		emprestimo.setLivro(livro);
		emprestimo.setDataEmprestimo(new Date());

		long prazoMs = usuario.getPrazoDias() * 24L * 60 * 60 * 1000;
		emprestimo.setDataPrevistaDevolucao(new Date(System.currentTimeMillis() + prazoMs));

		emprestimos.add(emprestimo);
		livro.setQuantidade(livro.getQuantidade() - 1);

		System.out.println("Empréstimo realizado com sucesso!");
	}
}
