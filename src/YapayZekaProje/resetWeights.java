package YapayZekaProje;

import java.util.List;
import java.util.Random;

public class resetWeights {
    
    Random r= new Random();
    
    public resetWeights(int numOfNeuron, double[] secondWeightsLastVariables, List<Integer> depAndIndepValues, double[][] firstWeightsLastValues, 
    double[][] firstWeights, double[] biasWeights,double[] secondWeights, double outputBiasWeight){
        
        resetFirstWeights(numOfNeuron,depAndIndepValues,firstWeights);
        resetSecondWeights(numOfNeuron, secondWeights);
        resetSecondWeightsLastValues(numOfNeuron, secondWeightsLastVariables);
        resetFirstWeightsLastValues(depAndIndepValues, numOfNeuron, firstWeightsLastValues);
        resetBiasWeights(numOfNeuron, biasWeights);
        outputBiasWeight= r.nextDouble();
        
    }
    
    public void resetFirstWeightsLastValues(List<Integer> depAndIndepValues, int numOfNeuron, double [][] firstWeightsLastValues){
        
        for(int i =0; i < depAndIndepValues.get(0); i++){
            for(int j = 0; j< numOfNeuron ; j++){
                double temp = r.nextDouble() * 100;
                temp= Math.round(temp);
                temp/= 100;
                firstWeightsLastValues[i][j]= temp;
            }
        }
    }
    
    public void resetSecondWeightsLastValues(int numOfNeuron, double[] secondWeightsLastValues){
        
        for(int i=0; i<numOfNeuron; i++)
            secondWeightsLastValues[i]=0;
        
    }
    

    
    public void resetBiasWeights(int numOfNeuron, double[] biasWeights){

        for(int j = 0; j<numOfNeuron ; j++){
            double temp = r.nextDouble() * 100;
            temp= Math.round(temp);
            temp/= 100;
            biasWeights[j]= temp;
        }

    }
    
    public void resetFirstWeights(int numOfNeuron, List<Integer> depAndIndepValues, double[][] firstWeights){
        for(int i =0; i < depAndIndepValues.get(0) ; i++){
            for(int j = 0; j<numOfNeuron ; j++){
                double temp = r.nextDouble() * 100;
                temp= Math.round(temp);
                temp/= 100;
                firstWeights[i][j]= temp;
            }
        }
    }
    
    public void resetSecondWeights(int numOfNeuron, double[] secondWeights){
        for (int i = 0; i < numOfNeuron; i++) {          
            double temp = r.nextDouble() * 100;
            temp = Math.round(temp);
            temp /= 100;
            secondWeights[i] = temp;
        }
    }
    
}
