package YapayZekaProje;

import java.util.List;

public class outputLayerNeuron {

    double sum;
    double output;
    double input;
    double bias;
    int numOfNeuron;
    

    public outputLayerNeuron(List<Double> outputNeuron,double[]secondWeights,int numOfNeuron,double outputBiasWeights){
        this.sum=0;
        this.output=0;
        this.bias=1;
        this.numOfNeuron= numOfNeuron;
        sumFunction(outputNeuron,numOfNeuron,secondWeights,outputBiasWeights);
        activationFunction();
    }

    public void sumFunction(List<Double> outputNeuron, int numOfNeuron, double[] secondWeights, double outputBiasWeights) {
        
    for(int i=0; i < numOfNeuron ; i++){
        this.sum = outputNeuron.get(i) * secondWeights[i];
    }
    this.sum += outputBiasWeights;
    
    }

    public void activationFunction() {
    this.output = 1/ (1 + Math.exp(-(this.sum)));
    
    }
    
    

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getOutput() {
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public double getInput() {
        return input;
    }

    public void setInput(double input) {
        this.input = input;
    }

    public int getNumOfNeuron() {
        return numOfNeuron;
    }

    public void setNumOfNeuron(int numOfNeuron) {
        this.numOfNeuron = numOfNeuron;
    }
    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }


    
}
