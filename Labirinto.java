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
        Queue<int[]> fila = new LinkedList<>();
        fila.add(new int[]{x, y});
        visitados[x][y] = true;
        int tamanho = 0;

        while (!fila.isEmpty()) {
            int[] atual = fila.poll();
            int i = atual[0], j = atual[1];
            tamanho++;

            String paredes = converteHexParaBinario(matriz[i][j]);
            int[][] direcoes = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

            for (int d = 0; d < 4; d++) {
                int novoX = i + direcoes[d][0];
                int novoY = j + direcoes[d][1];
                if (isValido(novoX, novoY) && !visitados[novoX][novoY] && paredes.charAt(d) == '0') {
                    fila.add(new int[]{novoX, novoY});
                    visitados[novoX][novoY] = true;
                }
            }
        }

        return tamanho;
    }

    // Encontra o ser mais frequente no labirinto
    public String serMaisFrequente() {
        Map<String, Integer> frequencias = new HashMap<>();

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                String conteudo = matriz[i][j];
                if (Character.isUpperCase(conteudo.charAt(0))) {
                    frequencias.put(conteudo, frequencias.getOrDefault(conteudo, 0) + 1);
                }
            }
        }

        return frequencias.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Nenhum");
    }

    // Converte hexadecimal para binário
    private String converteHexParaBinario(String hex) {
        int valor = Integer.parseInt(hex, 16);
        return String.format("%4s", Integer.toBinaryString(valor)).replace(' ', '0');
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
