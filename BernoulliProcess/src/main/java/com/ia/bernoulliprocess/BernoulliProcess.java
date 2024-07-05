package com.ia.bernoulliprocess;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.ia.bernoulliprocess.DecisionTree;

/**
 *
 * @author marib
 */

public class BernoulliProcess {
    public static void main(String[] args) {
        // métodos para gerar dados e construir a árvore de decisão
        List<Map<String, Boolean>> data = generateData(100); // Exemplo: 100 registros de dados
        DecisionTree tree = buildDecisionTree(data);
        
        // Previsão para novos dados
        Map<String, Boolean> newData = generateSingleData(); // Gerar novo dado para previsão
        boolean prediction = tree.predict(newData);
        System.out.println("Resultado previsto: " + prediction);
    }
    
    // Métodos para gerar dados de exemplo
    private static List<Map<String, Boolean>> generateData(int numSamples) {
        List<Map<String, Boolean>> data = new ArrayList<>();
        Random random = new Random();
        
        for (int i = 0; i < numSamples; i++) {
            Map<String, Boolean> sample = new HashMap<>();
            // gerando dados booleanos aleatórios para dois atributos
            sample.put("atributo1", random.nextBoolean());
            sample.put("atributo2", random.nextBoolean());
            data.add(sample);
        }
        
        return data;
    }
    
    private static Map<String, Boolean> generateSingleData() {
        Map<String, Boolean> newData = new HashMap<>();
        Random random = new Random();
        
        // gerando novo dado para previsão
        newData.put("atributo1", random.nextBoolean());
        newData.put("atributo2", random.nextBoolean());
        
        return newData;
    }

    private static DecisionTree buildDecisionTree(List<Map<String, Boolean>> data) {
    // construção simples da árvore de decisão
    // Aqui,criei um nó de decisão para um atributo específico
    return new DecisionTree(new AttributeDecisionNode("atributo1", true));
}
}
