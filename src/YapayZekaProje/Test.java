package YapayZekaProje;

import java.util.ArrayList;
import java.util.List;

public class Test {
    
double sumOfTestMAPE;
double testMAPE;
Neuron[] neurons;


public Test(List<List<List<Double>>> testDataSet,int placeOfDataSet, int numOfNeuron,double [][][] allFirstWeights,double[] biasWeights, double[][] allSecondWeights, double outputBiasWeights ){
    this.testMAPE= testMAPE;
    this.sumOfTestMAPE= 0;
    
    neurons = new Neuron[numOfNeuron];       

    for(int numOfLines=0 ; numOfLines < testDataSet.get(placeOfDataSet).size() ; numOfLines++){
        List<Double> neuronOutputs= new ArrayList<>();
        
        for(int i=0; i<numOfNeuron ; i++){
            neurons[i] = new Neuron(testDataSet.get(placeOfDataSet).get(numOfLines), allFirstWeights[placeOfDataSet], biasWeights,i,numOfNeuron);
            neuronOutputs.add(neurons[i].output);
        }
        
        outputLayerNeuron output = new outputLayerNeuron(neuronOutputs,allSecondWeights[placeOfDataSet],numOfNeuron,outputBiasWeights);
        
        if(testDataSet.get(placeOfDataSet).get(numOfLines).get(8) != 0){
              sumOfTestMAPE += Math.abs(testDataSet.get(placeOfDataSet).get(numOfLines).get(8) - output.output) / testDataSet.get(placeOfDataSet).get(numOfLines).get(8);
        }
        
        testMAPE= sumOfTestMAPE / (testDataSet.get(placeOfDataSet).size()-4)*100;
        
    }
    System.out.println("Test MAPE değeri="+testMAPE);
}
}
