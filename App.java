import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("\n--------------------------------");
        System.out.println("O Labirinto do Horror");
        System.out.println("\n--------------------------------");

        System.out.println("Digite o nome do labirinto que deseja ler: ");
        System.out.println("""
                Opções:\tcaso40_a.txt
                        \tcaso80_a.txt
                        \tcaso100_a.txt
                        \tcaso120_a.txt
                        \tcaso150_a.txt
                        \tcaso180_a.txt
                        \tcaso200_a.txt
                        \tcaso250_a.txt
                """);

        String arquivo = in.nextLine();
        String caminhoArquivo = "testes-t10/" + arquivo;

        try {
            Labirinto labirinto = new Labirinto(caminhoArquivo);

            labirinto.imprimeLabirinto();

            int regioes = labirinto.contarRegioes();
            String serFrequente = labirinto.serMaisFrequente();

            System.out.println("\n--------------------------------");
            System.out.println("Resultados para o arquivo: " + arquivo);
            System.out.println("Número de regiões isoladas: " + regioes);
            System.out.println("Ser mais frequente: " + serFrequente);
            System.out.println("--------------------------------");

        } catch (Exception e) {
            System.err.println("Erro ao processar o arquivo: " + arquivo);
            System.err.println("Detalhes do erro: " + e.getMessage());
        }

        in.close();
    }
}
