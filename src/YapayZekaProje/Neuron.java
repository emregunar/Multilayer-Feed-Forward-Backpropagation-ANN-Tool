package YapayZekaProje;

import java.util.List;

public class Neuron {
    double sum;
    double input;
    double output;
    double bias;
    int numOfNeuron;
    int placeOfNeuron;
    
    public Neuron(){
        this.sum = 0;
        this.output = 0;
        this.bias = 1;

    }
    
    public Neuron(List<Double> lines, double[][] firstWeights, double[] biasWeights,int placeOfNeuron, int numOfNeuron) {
        this.bias = 1;
        this.sum = 0;
        this.output = 0;
        this.numOfNeuron = numOfNeuron;
        this.placeOfNeuron = placeOfNeuron;
        sumFunction(lines,firstWeights,biasWeights);
        activationFunction();
    }
    
    private void sumFunction(List<Double> Lines, double[][] firstWeights, double[] bias_agirliklar) {
        
        for(int j = 0; j < Lines.size() - 1; j++ ){
            this.sum += Lines.get(j) * firstWeights[j][placeOfNeuron];
        }
        
        this.sum += bias_agirliklar[placeOfNeuron];
        
    }
    
    public void activationFunction() {
        this.output = 1 / (1 + Math.exp(-(this.sum)));
    }
    

    

    public double getSum() {
        return sum;
    }

    public void setSum(double toplam) {
        this.sum = toplam;
    }



    public double getOutput() {
        return output;
    }

    public void setOutput(double cikis) {
        this.output = cikis;
    }



  
    
    
    
}
