import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Labirinto {
    private File arquivo;
    private int linhas, colunas; //dimensao labirinto
    private String[][] matriz;  //representa o labirinto
    private boolean[][] visitados; //verifica se ja visitou uma celula
    private List<Integer> regioesTamanhos; // Tamanhos das regiões

    // Construtor da classe labirinto, verifica se existe
    public Labirinto(String nomeArquivo) {
        try {
            this.arquivo = new File(nomeArquivo);
            Scanner in = new Scanner(arquivo);
            linhas = Integer.parseInt(in.next());
            colunas = Integer.parseInt(in.next());

            matriz = new String[linhas][colunas];
            visitados = new boolean[linhas][colunas];
            regioesTamanhos = new ArrayList<>();

            preencheLabirinto(in);
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + nomeArquivo);
        }

    }

    // Preenche a matriz do labirinto lendo o arquivo
    private void preencheLabirinto(Scanner in) {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (in.hasNext()) {
                    matriz[i][j] = in.next();
                }
            }
        }
    }

    // Conta as regiões isoladas no labirinto
    public int contarRegioes() {
        int regioes = 0;

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (!visitados[i][j]) {
                    int tamanhoRegiao = exploraRegiao(i, j);
                    regioesTamanhos.add(tamanhoRegiao);
                    regioes++;
                }
            }
        }

        return regioes;
    }

    // Explora uma região usando BFS e retorna seu tamanho
    private int exploraRegiao(int x, int y) {
        if (!isValido(x, y) || visitados[x][y]) {
            return 0;
        }

        visitados[x][y] = true;
        int tamanho = 1;

        String paredes = converteHexParaBinario(matriz[x][y]);
        int[][] direcoes = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        for (int d = 0; d < 4; d++) {
            int novoX = x + direcoes[d][0];
            int novoY = y + direcoes[d][1];
            if (isValido(novoX, novoY) && !visitados[novoX][novoY] && paredes.charAt(d) == '0') {
                tamanho += exploraRegiao(novoX, novoY);
            }
        }

        return tamanho;
    }


    // Encontra o ser mais frequente no labirinto
    public String serMaisFrequente() {
        List<String> elementos = new ArrayList<>(); // Lista para armazenar elementos distintos
        List<Integer> frequencias = new ArrayList<>(); // Lista para armazenar suas contagens

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                String conteudo = matriz[i][j];
                if (conteudo != null && !conteudo.isEmpty() && Character.isUpperCase(conteudo.charAt(0))) {
                    int indice = elementos.indexOf(conteudo);
                    if (indice == -1) {
                        elementos.add(conteudo);
                        frequencias.add(1);
                    } else {
                        frequencias.set(indice, frequencias.get(indice) + 1);
                    }
                }
            }
        }

        // Determinar o elemento mais frequente
        String maisFrequente = "Nenhum";
        int maiorFrequencia = 0;

        for (int i = 0; i < elementos.size(); i++) {
            if (frequencias.get(i) > maiorFrequencia) {
                maiorFrequencia = frequencias.get(i);
                maisFrequente = elementos.get(i);
            }
        }

        return maisFrequente;
    }


    // Converte hexadecimal para binário
    private String converteHexParaBinario(String hexadecimal) {
        int valor = Integer.parseInt(hexadecimal, 16);
        String binario = Integer.toBinaryString(valor);

        while (binario.length() < 4) {
            binario = "0" + binario;
        }

        return binario;
    }


    // Verifica se a célula é válida
    private boolean isValido(int x, int y) {
        return x >= 0 && x < linhas && y >= 0 && y < colunas;
    }

    // Imprime o labirinto
    public void imprimeLabirinto() {
        System.out.println("Labirinto:");
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }
}
