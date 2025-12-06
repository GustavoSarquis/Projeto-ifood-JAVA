package telas;

import javax.swing.*;
import itens.Produto;
import java.awt.*;
import java.util.List;

public class TelaCarrinho extends JFrame {
    private List<Produto> carrinho;
    private DefaultListModel<Produto> modeloLista;
    private JLabel lblTotal;

    public TelaCarrinho(List<Produto> carrinho, JFrame telaAnterior) {
        this.carrinho = carrinho;
        
        setTitle("Meu Carrinho");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha só essa janela
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Cabeçalho ---
        JLabel lblTitulo = new JLabel("Gerenciar Itens do Carrinho", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);

        // --- Lista Visual ---
        modeloLista = new DefaultListModel<>();
        atualizarListaVisual(); // Preenche a lista ao abrir

        JList<Produto> listaVisual = new JList<>(modeloLista);
        listaVisual.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Formatação bonita para a lista
        listaVisual.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Produto p = (Produto) value;
                String texto = p.getNome() + " - R$" + String.format("%.2f", p.getPreco());
                return super.getListCellRendererComponent(list, texto, index, isSelected, cellHasFocus);
            }
        });

        JScrollPane scroll = new JScrollPane(listaVisual);
        add(scroll, BorderLayout.CENTER);

        // --- Painel Inferior (Total e Botões) ---
        JPanel painelInferior = new JPanel(new BorderLayout());
        
        // Rótulo do Total em Tempo Real
        lblTotal = new JLabel("Total: R$ 0,00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        atualizarPrecoTotal();

        // Botões de Ação
        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnRemover = new JButton("Remover Item");
        JButton btnAumentar = new JButton("Adicionar Mais (+1)");
        JButton btnPagar = new JButton("Ir para Pagamento");
        JButton btnVoltar = new JButton("Voltar ao Cardápio");

        // --- AÇÃO: REMOVER ITEM ---
        btnRemover.addActionListener(e -> {
            Produto selecionado = listaVisual.getSelectedValue();
            if (selecionado != null) {
                carrinho.remove(selecionado); // Remove da lista real
                atualizarListaVisual();       // Atualiza a tela
                atualizarPrecoTotal();        // Atualiza o preço
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um item para remover.");
            }
        });

        // --- AÇÃO: AUMENTAR QUANTIDADE ---
        btnAumentar.addActionListener(e -> {
            Produto selecionado = listaVisual.getSelectedValue();
            if (selecionado != null) {
                carrinho.add(selecionado); // Adiciona o mesmo objeto novamente (duplica)
                atualizarListaVisual();
                atualizarPrecoTotal();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um item para adicionar mais um igual.");
            }
        });

        // --- AÇÃO: PAGAR (CONFIRMAÇÃO) ---
        btnPagar.addActionListener(e -> {
            if (carrinho.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seu carrinho está vazio!");
                return;
            }
            abrirTelaConfirmacao();
        });

        btnVoltar.addActionListener(e -> dispose()); // Fecha a janela atual

        painelBotoes.add(btnRemover);
        painelBotoes.add(btnAumentar);
        painelBotoes.add(btnPagar);
        painelBotoes.add(btnVoltar);

        painelInferior.add(lblTotal, BorderLayout.NORTH);
        painelInferior.add(painelBotoes, BorderLayout.SOUTH);

        add(painelInferior, BorderLayout.SOUTH);
        setVisible(true);
    }

    // Método auxiliar para redesenhar a lista
    private void atualizarListaVisual() {
        modeloLista.clear();
        for (Produto p : carrinho) {
            modeloLista.addElement(p);
        }
    }

    // Método auxiliar para calcular total
    private void atualizarPrecoTotal() {
        double total = 0;
        for (Produto p : carrinho) {
            total += p.getPreco();
        }
        lblTotal.setText("Total Carrinho: R$ " + String.format("%.2f", total));
    }

    // --- TELA DE PAGAMENTO/CONFIRMAÇÃO ---
    private void abrirTelaConfirmacao() {
        double total = 0;
        int tempoMax = 0;

        for (Produto p : carrinho) {
            total += p.getPreco();
            if (p.getTempoPreparo() > tempoMax) {
                tempoMax = p.getTempoPreparo();
            }
        }

        // Mensagem formatada
        String mensagem = "<html><h2>Confirmação de Pedido</h2>" +
                          "<br><b>Valor Total:</b> R$ " + String.format("%.2f", total) +
                          "<br><b>Tempo Estimado:</b> " + tempoMax + " minutos" +
                          "<br><br>Deseja confirmar a compra?</html>";

        // Opções dos botões
        Object[] opcoes = {"Confirmar Compra", "Voltar pro Carrinho"};

        int escolha = JOptionPane.showOptionDialog(
            this,
            mensagem,
            "Pagamento",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opcoes,
            opcoes[0] // Seleção padrão
        );

        // Se escolheu "Confirmar Compra" (Índice 0)
        if (escolha == 0) {
            carrinho.clear(); // Limpa a lista real
            JOptionPane.showMessageDialog(this, "Compra realizada com sucesso!\nO pedido está sendo preparado.");
            dispose(); // Fecha a tela do carrinho
        }
    }
}