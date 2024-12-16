import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

class Carro {
    private String nome;
    private int ano;
    private int portas;
    private double preco;

    public Carro(String nome, int ano, int portas, double preco) {
        this.nome = nome;
        this.ano = ano;
        this.portas = portas;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public int getAno() {
        return ano;
    }

    public int getPortas() {
        return portas;
    }

    public double getPreco() {
        return preco;
    }

    public String getDetalhes() {
        return "Carro: " + nome + "\nAno: " + ano + "\nPortas: " + portas + "\nPreço: R$" + preco;
    }
}

class LoginPanel extends JPanel {
    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnLogin;

    public LoginPanel(JFrame frame) {
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Usuário:"));
        txtUsuario = new JTextField();
        add(txtUsuario);

        add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        add(txtSenha);

        btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login(frame);
            }
        });
        add(btnLogin);
    }

    private void login(JFrame frame) {
        String usuario = txtUsuario.getText();
        String senha = new String(txtSenha.getPassword());


        if (usuario.equals("admin") && senha.equals("1234")) {
            frame.remove(this);
            frame.add(new HomePanel(frame));
            frame.revalidate();
            frame.repaint();
        } else {
            JOptionPane.showMessageDialog(frame, "Credenciais inválidas", "Erro de Login", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class HomePanel extends JPanel {
    private List<Carro> carrosDisponiveis;
    private JFrame frame;

    public HomePanel(JFrame frame) {
        this.frame = frame;
        carrosDisponiveis = new ArrayList<>();
        // Carros disponíveis iniciais
        carrosDisponiveis.add(new Carro("Fiat Uno", 1995, 2, 4500));
        carrosDisponiveis.add(new Carro("Gol 1.6", 2006, 2, 11000));
        carrosDisponiveis.add(new Carro("Omega 2.0", 2010, 4, 25000));

        setLayout(new GridLayout(5, 1));

        JButton btnCadastro = new JButton("Cadastrar Carro");
        btnCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCadastroCarro();
            }
        });

        JButton btnBuscar = new JButton("Buscar Carro");
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarBuscaCarro();
            }
        });

        JButton btnVenda = new JButton("Vender Carro");
        btnVenda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVendaCarro();
            }
        });

        JButton btnSelecao = new JButton("Selecionar Carro por Investimento");
        btnSelecao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarSelecaoCarro();
            }
        });

        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(btnCadastro);
        add(btnBuscar);
        add(btnVenda);
        add(btnSelecao);
        add(btnSair);
    }

    private void mostrarCadastroCarro() {
        frame.remove(this);
        frame.add(new CadastroCarroPanel(frame, carrosDisponiveis));
        frame.revalidate();
        frame.repaint();
    }

    private void mostrarBuscaCarro() {
        frame.remove(this);
        frame.add(new BuscaCarroPanel(frame, carrosDisponiveis));
        frame.revalidate();
        frame.repaint();
    }

    private void mostrarVendaCarro() {
        frame.remove(this);
        frame.add(new VendaCarroPanel(frame, carrosDisponiveis));
        frame.revalidate();
        frame.repaint();
    }

    private void mostrarSelecaoCarro() {
        frame.remove(this);
        frame.add(new SelecaoCarroPanel(frame, carrosDisponiveis));
        frame.revalidate();
        frame.repaint();
    }
}

class CadastroCarroPanel extends JPanel {
    private JTextField txtNome, txtAno, txtPortas, txtPreco;
    private List<Carro> carrosDisponiveis;

    public CadastroCarroPanel(JFrame frame, List<Carro> carrosDisponiveis) {
        this.carrosDisponiveis = carrosDisponiveis;
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Nome do Carro:"));
        txtNome = new JTextField();
        add(txtNome);

        add(new JLabel("Ano do Carro:"));
        txtAno = new JTextField();
        add(txtAno);

        add(new JLabel("Portas:"));
        txtPortas = new JTextField();
        add(txtPortas);

        add(new JLabel("Preço:"));
        txtPreco = new JTextField();
        add(txtPreco);

        JButton btnCadastrar = new JButton("Cadastrar Carro");
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCarro();
            }
        });
        add(btnCadastrar);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltarHome(frame);
            }
        });
        add(btnVoltar);
    }

    private void cadastrarCarro() {
        try {
            String nome = txtNome.getText();
            int ano = Integer.parseInt(txtAno.getText());
            int portas = Integer.parseInt(txtPortas.getText());
            double preco = Double.parseDouble(txtPreco.getText());

            if (nome.isEmpty() || preco <= 0 || ano <= 0 || portas <= 0) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Carro novoCarro = new Carro(nome, ano, portas, preco);
            carrosDisponiveis.add(novoCarro);
            JOptionPane.showMessageDialog(this, "Carro cadastrado com sucesso!", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
            limparCamposCadastro();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira valores válidos para ano, portas e preço.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCamposCadastro() {
        txtNome.setText("");
        txtAno.setText("");
        txtPortas.setText("");
        txtPreco.setText("");
    }

    private void voltarHome(JFrame frame) {
        frame.remove(this);
        frame.add(new HomePanel(frame));
        frame.revalidate();
        frame.repaint();
    }
}

class BuscaCarroPanel extends JPanel {
    private JTextField txtBusca;
    private JTextArea txtResultado;
    private List<Carro> carrosDisponiveis;

    public BuscaCarroPanel(JFrame frame, List<Carro> carrosDisponiveis) {
        this.carrosDisponiveis = carrosDisponiveis;
        setLayout(new BorderLayout());

        txtBusca = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCarro();
            }
        });

        JPanel panelBusca = new JPanel();
        panelBusca.add(new JLabel("Buscar Carro por Nome:"));
        panelBusca.add(txtBusca);
        panelBusca.add(btnBuscar);

        txtResultado = new JTextArea(10, 40);
        txtResultado.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtResultado);

        add(panelBusca, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltarHome(frame);
            }
        });
        add(btnVoltar, BorderLayout.SOUTH);
    }

    private void buscarCarro() {
        String nomeBusca = txtBusca.getText().toLowerCase();
        boolean encontrou = false;

        txtResultado.setText(""); // Limpar área de texto

        for (Carro carro : carrosDisponiveis) {
            if (carro.getNome().toLowerCase().contains(nomeBusca)) {
                txtResultado.append(carro.getDetalhes() + "\n\n");
                encontrou = true;
            }
        }

        if (!encontrou) {
            txtResultado.setText("Nenhum carro encontrado com o nome: " + nomeBusca);
        }
    }

    private void voltarHome(JFrame frame) {
        frame.remove(this);
        frame.add(new HomePanel(frame));
        frame.revalidate();
        frame.repaint();
    }
}

class VendaCarroPanel extends JPanel {
    private JTextField txtVenda;
    private List<Carro> carrosDisponiveis;

    public VendaCarroPanel(JFrame frame, List<Carro> carrosDisponiveis) {
        this.carrosDisponiveis = carrosDisponiveis;
        setLayout(new GridLayout(3, 1));

        txtVenda = new JTextField(20);
        JButton btnVender = new JButton("Vender Carro");
        btnVender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                venderCarro();
            }
        });

        add(new JLabel("Nome do Carro para Vender:"));
        add(txtVenda);
        add(btnVender);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltarHome(frame);
            }
        });
        add(btnVoltar);
    }

    private void venderCarro() {
        String nomeVenda = txtVenda.getText().toLowerCase();
        boolean encontrado = false;

        for (int i = 0; i < carrosDisponiveis.size(); i++) {
            Carro carro = carrosDisponiveis.get(i);
            if (carro.getNome().toLowerCase().equals(nomeVenda)) {
                carrosDisponiveis.remove(i);
                JOptionPane.showMessageDialog(this, "Carro vendido com sucesso: " + carro.getDetalhes(), "Venda", JOptionPane.INFORMATION_MESSAGE);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            JOptionPane.showMessageDialog(this, "Carro não encontrado para venda.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void voltarHome(JFrame frame) {
        frame.remove(this);
        frame.add(new HomePanel(frame));
        frame.revalidate();
        frame.repaint();
    }
}

class SelecaoCarroPanel extends JPanel {
    private JTextField txtValor;
    private JTextArea txtResultado;
    private List<Carro> carrosDisponiveis;

    public SelecaoCarroPanel(JFrame frame, List<Carro> carrosDisponiveis) {
        this.carrosDisponiveis = carrosDisponiveis;
        setLayout(new BorderLayout());

        txtValor = new JTextField(20);
        JButton btnSelecao = new JButton("Selecionar Carro");
        btnSelecao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selecionarCarro();
            }
        });

        JPanel panelSelecao = new JPanel();
        panelSelecao.add(new JLabel("Valor disponível para investimento:"));
        panelSelecao.add(txtValor);
        panelSelecao.add(btnSelecao);

        txtResultado = new JTextArea(10, 40);
        txtResultado.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtResultado);

        add(panelSelecao, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltarHome(frame);
            }
        });
        add(btnVoltar, BorderLayout.SOUTH);
    }

    private void selecionarCarro() {
        try {
            double valorInvestido = Double.parseDouble(txtValor.getText());

            if (valorInvestido < 0) {
                JOptionPane.showMessageDialog(this, "O valor não pode ser negativo.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean encontrou = false;
            txtResultado.setText(""); // Limpar a área de resultados

            for (Carro carro : carrosDisponiveis) {
                if (valorInvestido >= carro.getPreco()) {
                    txtResultado.append(carro.getDetalhes() + "\n\n");
                    encontrou = true;
                }
            }

            if (!encontrou) {
                txtResultado.setText("Não temos oferta para este valor. Tente um valor maior.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um valor numérico válido.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void voltarHome(JFrame frame) {
        frame.remove(this);
        frame.add(new HomePanel(frame));
        frame.revalidate();
        frame.repaint();
    }
}

public class SistemaGestaoCarros {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Top Car - Lucas Vitor");
        frame.setSize(800, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(new LoginPanel(frame));
        frame.setVisible(true);
    }
}
