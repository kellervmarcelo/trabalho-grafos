import java.io.*;

public class EricMarceloFile {
    private final String caminhoArquivo;
    private final String nomeArquivo;
    private File file;
    private BufferedReader br;

    public EricMarceloFile(final String caminhoArquivo, final String nomeArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        this.nomeArquivo = nomeArquivo;
    }

    public String ler() throws Throwable {
        String linha = null;
        this.file = new File(this.caminhoArquivo+this.nomeArquivo);

        if (this.file.exists()) {
            if(br == null) {
                FileInputStream inputStream = new FileInputStream(this.file);
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                br = new BufferedReader(streamReader);
            }
        }
        else {
            throw new FileNotFoundException("Arquivo: " + this.caminhoArquivo+this.nomeArquivo+" não encontrado.");
        }

        linha = br.readLine();
        if (linha == null) {
            finalizar();
        }

        return linha;
    }

    public void criarDir(String path) throws Throwable {
        File f = null;

        f = new File(path);
        f.mkdirs();
    }

    protected void finalizar() throws Throwable {
        if (br != null) {
            br.close();
        }
    }
}
