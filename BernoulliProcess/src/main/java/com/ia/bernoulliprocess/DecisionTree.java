package com.ia.bernoulliprocess;

import java.util.List;
import java.util.Map;


/**
 *
 * @author marib
 */
class DecisionTree {
   private DecisionNode root;

    // Construtor da árvore de decisão
    public DecisionTree(DecisionNode root) {
        this.root = root;
    }

    // Método para prever o resultado para novos dados
    public boolean predict(Map<String, Boolean> data) {
        return root.predict(data);
    }
}

// Classe para representar os nós da árvore de decisão
abstract class DecisionNode {
    // Método abstrato para prever o resultado
    public abstract boolean predict(Map<String, Boolean> data);
}

// nó da árvore de decisão: nó de decisão baseado em um atributo
class AttributeDecisionNode extends DecisionNode {
    private String attribute;
    private boolean decision;

    public AttributeDecisionNode(String attribute, boolean decision) {
        this.attribute = attribute;
        this.decision = decision;
    }

    @Override
    public boolean predict(Map<String, Boolean> data) {
        // Realizei a decisão com base no valor do atributo
        return data.getOrDefault(attribute, false) == decision;
    }
  
}