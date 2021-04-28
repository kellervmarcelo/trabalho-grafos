import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    private int nodeNumber;
    private int weightSum;
    private int connectionList;
    private int weightList;
    private ArrayList<String> lines = new ArrayList<>();
    private ArrayList<String[]> connections = new ArrayList<>();
    private HashMap<String, Integer> weights = new HashMap<String, Integer>();
    private ArrayList<String> nodes = new ArrayList<String>();


    public Graph(String dirPath, String filePath) throws Throwable {
        readFile(dirPath, filePath);
        readHeader();
        readBody();
        readTrailer();
        validate();
    }

    private void readFile(String dirPath, String filePath ) throws Throwable {
        EricMarceloFile emf = new EricMarceloFile(dirPath, filePath);
        String line;

        while ((line = emf.ler()) != null) {
            lines.add(line);
        }

        emf.finalizar();
    }

    private void readHeader() throws Exception {
        String header = lines.get(0);

        if(header.length() > 6 ){
            throw new Exception("Header com mais caracteres que o permitido!");
        }

        if((!header.substring(0, 2).equals("00"))) {
            throw new Exception("Header inválido!");
        }

        try {
            nodeNumber = Integer.parseInt(header.substring(2, 4));
            weightSum = Integer.parseInt(header.substring(4));
        } catch (Exception excp) {
            throw new Exception("Valores do header inválidos");
        }
    }

    private void readBody() throws Exception {
        for (int i = 1; i < this.lines.size() - 1; i++) {
            switch (this.lines.get(i).substring(0, 2)) {
                case "01":
                    processLink(this.lines.get(i).substring(2));
                    break;
                case "02":
                    processWeight(this.lines.get(i).substring(2));
                    break;
                default:
                    throw new Exception();
            }
        }
    }

    private void processLink(String param) throws Exception {

            String s[] = param.split("=");
            if (s.length != 2) {
                throw new Exception("Resumo de conexões inválidas");
            }

            connections.add(s);

    }

    private void processWeight(String param) throws Exception{
        String s[] = param.split("=");
        if (s.length > 2){
            throw new Exception();
        }

        try {
            int i = Integer.parseInt(s[1]);
            weights.put(s[0],i);
        } catch (Exception excep){
            throw new Exception("Valor dos pesos inválido");
        }
    }

    private void readTrailer() throws Exception {
        String trailer = this.lines.get(this.lines.size() - 1);

        if ((!trailer.substring(0, 2).equals("09"))) {
            throw new Exception("Trailer inválido");
        }
        String[] s = trailer.substring(2).split(";");

        connectionList = Integer.parseInt(s[0]);
        weightList = Integer.parseInt(s[1]);

    }

    private void validate() throws Exception {
        for (String[] n : connections) {
            if (!nodes.contains(n[0])) {
                nodes.add(n[0]);
            }
            if (!nodes.contains(n[1])) {
                nodes.add(n[1]);
            }
        }


        if (nodes.size() != nodeNumber) {
            throw new Exception("Número total de nós inválido");
        }

        int accum = 0;
        for (Map.Entry<String, Integer> entry : weights.entrySet()) {
            accum += entry.getValue();
        }

        if (accum != weightSum) {
            throw new Exception("Soma dos pesos do header difere");
        }

        if (connections.size() != connectionList) {
            throw new Exception("Número de conexões do trailer difere do número de conexões real");
        }

        if (weights.size() != weightList) {
            throw new Exception("Número de pesos do trailer difere do número de pesos real");
        }
    }
}
