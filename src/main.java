import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class main {
    public static void main(String[] args) throws Throwable {
        try {
            EricMarceloFile configFile = new EricMarceloFile("C:\\Teste\\Configuracao\\", "config.txt");

            String linha;
            String newPath;
            ArrayList<String> path = new ArrayList<>();


            while ((linha = configFile.ler()) != null ) {
                try{
                    newPath = linha.split("=") [1];
                    path.add(newPath);
                    configFile.criarDir(newPath);
                    System.out.println("Linha: "+linha);
                } catch (Exception excp){
                    throw new Exception("Arquivo incorreto");
                }

            }

            if (path.size() < 2){
                throw new Exception("Arquivo de config incompleto");
            }

            String basePath = "C:\\Teste\\";
            File dir = new File(basePath);
            File[] filesList = dir.listFiles();
            if (filesList != null) {
                for (File arq : filesList) {
                    if (arq.isFile()) {
                        try {
                            Graph graph = new Graph(basePath, arq.getName());
                            Thread.sleep(10000);
                            arq.renameTo(new File(path.get(0) + "\\" + arq.getName()));
                            System.out.println("Arquivo " + arq.getName() + " processado");
                        } catch (Exception excp){
                            arq.renameTo(new File(path.get(1) + "\\" + arq.getName()));
                            System.out.println("Arquivo " + arq.getName() + " não processado");
                            System.out.println(excp.getMessage());
                        }
                    }
                }
            }

        } catch (Exception excp) {
            System.out.println("Error: "+excp);
            System.exit(0);
        }


    }
}
