package YapayZekaProje;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.swing.SwingUtilities;
import static jdk.nashorn.internal.objects.NativeMath.round;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class Education {

    List<Integer> depAndIndepVariables;            
    List<List<List<Double>>> dataSet;                               
    List<List<Double>> maxAndMinList;                             
    List<List<List<List<Double>>>> educationAndTestList;
    List<Double> neuronOutputs;
    
    
    double[][][] allFirstWeights;
    double[][]   allSecondWeights;
    double[][] firstWeights;
    double[][] firstWeightsLastValues;
    double[][] errorMargins;
    double[] biasWeights;
    double[] secondWeights;
    double[] secondWeightsLastValues;
    
    
    LineChartEx2 graph = new LineChartEx2();
    Neuron[] Neurons;
    outputLayerNeuron outputNeuron;
    resetWeights reset;
    

    static double sigmoM;
    double learningParameter = 0.01;
    double momentumParameter = 0.02;
    double sumMAPE = 0;
    double errorMarginOfMSE = 0;
    double MAPE=0;
    double errorMargin;
    double outputBiasWeight;
    
    int numOfNeuron;    
    int epoch = 1;
    int y1OutputZero = 0;

    Random r = new Random();
    Scanner scan = new Scanner(System.in);


    
    

    public Education() throws IOException {

        turnList object = new turnList();

        HSSFSheet sheet = object.readExcel("veriseti.xls");        

        depAndIndepVariables = object.findDepenAndIndepenValues(sheet);
        
        dataSet = object.clusterByDepenValues(sheet, depAndIndepVariables.get(0), depAndIndepVariables.get(1));      
        
        maxAndMinList = object.findMaxAndMinValues(dataSet.get(0));

        dataSet = object.normalization(dataSet, maxAndMinList);
        
        educationAndTestList = object.seperateForEducationAndTest(dataSet);
        
        

        
        System.out.print("Nöron Sayısını Giriniz= ");
        numOfNeuron = scan.nextInt();
        
        while(numOfNeuron < 2 || numOfNeuron > 20){
            System.out.print("Hatalı Nöron Sayısı Girdiniz. Yeni Nöron Sayısı =");
            numOfNeuron = scan.nextInt();
        }
     
        secondWeightsLastValues = new double[numOfNeuron];
        firstWeightsLastValues = new double[depAndIndepVariables.get(0)][numOfNeuron];

        firstWeights = new double[depAndIndepVariables.get(0)][numOfNeuron];
        secondWeights = new double[numOfNeuron];
        biasWeights = new double[numOfNeuron];
        allFirstWeights = new double[educationAndTestList.get(0).size()][depAndIndepVariables.get(0)][numOfNeuron];
        allSecondWeights = new double[educationAndTestList.get(0).size()][numOfNeuron];

        reset= new resetWeights(numOfNeuron,secondWeightsLastValues,depAndIndepVariables,firstWeightsLastValues,firstWeights,biasWeights,secondWeights,outputBiasWeight);
        
        Neurons = new Neuron[numOfNeuron];        

        errorMargins = new double[dataSet.size()][dataSet.get(0).size()];
        
        

        for (int whichDataSet = 0; whichDataSet < educationAndTestList.get(0).size(); whichDataSet++) {           
            
            do{
                for (int numberOfLines = 0; numberOfLines < educationAndTestList.get(0).get(whichDataSet).size() - 1; numberOfLines++) {   
                       
                    neuronOutputs= new ArrayList<>();

                    for (int i = 0; i < numOfNeuron; i++) {                     
                        Neurons[i] = new Neuron(educationAndTestList.get(0).get(whichDataSet).get(numberOfLines), firstWeights, biasWeights, i, numOfNeuron); 
                        neuronOutputs.add(Neurons[i].output);
                    }   

                    outputNeuron = new outputLayerNeuron(neuronOutputs,secondWeights,numOfNeuron,outputBiasWeight);            


                    updateSecondWeights(whichDataSet, numberOfLines, outputNeuron.output);
                    updateFirstWeights(whichDataSet, numberOfLines);

                    if(educationAndTestList.get(0).get(whichDataSet).get(numberOfLines).get(8) != 0){
                        sumMAPE += Math.abs(educationAndTestList.get(0).get(whichDataSet).get(numberOfLines).get(8) - outputNeuron.output) / educationAndTestList.get(0).get(whichDataSet).get(numberOfLines).get(8); 
                    }else{
                        y1OutputZero++;
                    }
                    errorMargins[whichDataSet][numberOfLines] = educationAndTestList.get(0).get(whichDataSet).get(numberOfLines).get(8) - outputNeuron.output;

                    errorMarginOfMSE += errorMargins[whichDataSet][numberOfLines] * errorMargins[whichDataSet][numberOfLines];

         
                }
                MAPE = sumMAPE / (educationAndTestList.get(0).get(whichDataSet).size() - y1OutputZero) * 100;
                errorMarginOfMSE /= educationAndTestList.get(0).get(whichDataSet).size();

                epoch++;

                /*if(whichDataSet==0)
                    graph.series1.add(epoch,errorMarginOfMSE);

                if(whichDataSet==1)
                    graph.series2.add(epoch,errorMarginOfMSE);

                if(epoch % 50 == 0)
                    graph.setVisible(true);*/
                if(epoch%100 == 0)
                    System.out.println(epoch+" "+MAPE);
                

                y1OutputZero = 0;
                sumMAPE = 0;
            } while(epoch < 10000);
            
            allFirstWeights[whichDataSet] = firstWeights;
            allSecondWeights[whichDataSet] = secondWeights;

            System.out.println("Epoch "+epoch+" değerine ulaştığı için sonlandı.");

            epoch = 1;
            errorMarginOfMSE = 0;
            sumMAPE = 0;
            
            Test test= new Test(educationAndTestList.get(1), whichDataSet ,numOfNeuron ,allFirstWeights ,biasWeights ,secondWeights ,outputBiasWeight );

            Results(firstWeights, secondWeights, biasWeights,outputBiasWeight,whichDataSet);
            
            reset= new resetWeights(numOfNeuron,secondWeightsLastValues,depAndIndepVariables,firstWeightsLastValues,firstWeights,biasWeights,secondWeights,outputBiasWeight);
            if(whichDataSet==0)
            System.out.println("\nY1 çıkışlı veriseti bitti. Y2 çıkışlı veriseti sonuçları: \n");
     
        }       
        
        
    }

    private void updateFirstWeights(int whichDataSet, int numOfLines) {

        for (int input = 0; input < depAndIndepVariables.get(0); input++) {
            
            for (int placeOfNeuron = 0; placeOfNeuron < numOfNeuron; placeOfNeuron++) {
                
                double sigmo_j = (Neurons[placeOfNeuron].getOutput() * (1 - Neurons[placeOfNeuron].getOutput()) * sigmoM * secondWeights[placeOfNeuron]);  
                
                double weightChangeAmount = (learningParameter * sigmo_j * educationAndTestList.get(0).get(whichDataSet).get(numOfLines).get(input)) + (momentumParameter * firstWeightsLastValues[input][placeOfNeuron]); 
                
                firstWeightsLastValues[input][placeOfNeuron] = weightChangeAmount;   
                
                firstWeights[input][placeOfNeuron] = weightChangeAmount + firstWeights[input][placeOfNeuron];
            }
        }

    }

    void updateSecondWeights(int whichDataSet, int numOfLines, double output) {
        sigmoM = output * (1 - output) * errorMargins[whichDataSet][numOfLines];
            
            for (int i = 0; i < numOfNeuron; i++) {
                
                double weightChangeAmount = (learningParameter * sigmoM * Neurons[i].getOutput()) + (momentumParameter * secondWeightsLastValues[i]); 
                
                secondWeightsLastValues[i] = weightChangeAmount;
                
                secondWeights[i] = weightChangeAmount + secondWeights[i];  
            }
    }
    private void Results(double[][] ilk_agirliklar, double[] ikinci_agirliklar, double[] bias_agirliklar, double cikis_bias_agirlik, int veriSetiSirasi) {
                        
                        neuronOutputs= new ArrayList<>();
                            
                        
                        for (int i = 0; i < numOfNeuron; i++) {                     
                            Neurons[i] = new Neuron(educationAndTestList.get(0).get(veriSetiSirasi).get(95), ilk_agirliklar, bias_agirliklar, i, numOfNeuron);
                            neuronOutputs.add(Neurons[i].output);
                        }

                        outputNeuron = new outputLayerNeuron(neuronOutputs,ikinci_agirliklar,numOfNeuron,cikis_bias_agirlik);
                       
                        for (int i = 0; i < 9; i++) {
                            System.out.println(educationAndTestList.get(0).get(veriSetiSirasi).get(95).get(i));
                        }
                        
                        System.out.println("-----------------------");
                        System.out.println("ÇIKIŞ : " + outputNeuron.output);
                        
    }

}
