package main;
import javax.swing.*;

import sistema.Sistema;
import telas.TelaInicial;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        
        Sistema sistema;
        File arquivoDados = new File("dados_restaurante.bin");

        // --- 1. LÓGICA DE RECUPERAÇÃO DE DADOS ---
        if (arquivoDados.exists()) {
            int resposta = JOptionPane.showConfirmDialog(null, 
                    "Encontrei dados salvos de uma sessão anterior.\nDeseja carregar esses dados?", 
                    "Carregar Dados", 
                    JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
                // Carrega o arquivo existente
                sistema = Sistema.carregarDados();
                // Verifica se carregou null (erro) e corrige
                if (sistema == null) sistema = new Sistema(); 
                else JOptionPane.showMessageDialog(null, "Dados carregados com sucesso!");
            } else {
                // Começa do zero e APAGA o arquivo velho para não confundir
                sistema = new Sistema();
                if (arquivoDados.delete()) {
                    System.out.println("Arquivo antigo deletado para iniciar nova sessão.");
                }
            }
        } else {
            // Arquivo não existe, cria novo
            sistema = new Sistema();
        }

        // Variável auxiliar para ser usada dentro do "Shutdown Hook"
        Sistema sistemaFinal = sistema;

        // --- 2. O SEGURO DE VIDA (SHUTDOWN HOOK) ---
        // Este bloco roda automaticamente sempre que o programa fecha,
        // não importa se foi pela tela de Login, Dono, Cliente ou Inicial.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Sistema.salvarDados(sistemaFinal);
            System.out.println("Sistema salvo automaticamente no encerramento.");
        }));

        // --- 3. INICIA A TELA ---
        SwingUtilities.invokeLater(() -> {
            TelaInicial tela = new TelaInicial(sistemaFinal);
            // Não precisamos mais do WindowListener aqui, o Hook cuida de tudo
        });
    }
}