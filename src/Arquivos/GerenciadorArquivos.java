package src.Arquivos;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class GerenciadorArquivos {

    public static File criaPasta(String caminho) throws IOException {
        File dir = new File(caminho);
        if (dir.exists()) {
            return dir;
        }
        if (dir.mkdirs()) {
            return dir;
        }
        throw new IOException("Falhou ao criar a pasta '" + dir.getAbsolutePath());
    }

    public static String[] listarArquivosNaPasta(String caminho) {
        return Arrays.stream(Objects.requireNonNull(new File(caminho).listFiles())).map(File::getName).toArray(String[]::new);
    }

    public static void geraArquivo(String fileName, String[] content) {
        try {
            criaPasta("pesos");
            FileWriter writer = new FileWriter("pesos/" + fileName + ".txt", false);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (String s : content) {
                bufferedWriter.write(s);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String[] leArquivo(String fileName) {
        ArrayList<String> strings = new ArrayList<>();
        try (FileReader reader = new FileReader("pesos/" + fileName.replace(".txt", "") + ".txt")) {
            BufferedReader buffer = new BufferedReader(reader);
            String line = buffer.readLine();
            while (line != null) {
                strings.add(line);
                line = buffer.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings.toArray(new String[0]);
    }
}
