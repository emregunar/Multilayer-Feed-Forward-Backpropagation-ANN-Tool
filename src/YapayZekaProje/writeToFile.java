package YapayZekaProje;
import java.io.File;  
import java.io.IOException;  
import java.io.FileWriter;

public class writeToFile {
    
    File txt;
    
    public writeToFile(double [][][] allFirstWeights, double [][] allSecondWeights, int numOfNeuron) throws IOException{
        
        
        
        FileWriter myWriter = new FileWriter("giriş ve ara katmanı arasındaki ağ ağırlıkları.txt");
        myWriter.write("Y1 çıkışlı verisetindeki giriş ve ara katman arasındaki ağırlıklar:\n");
                
        for(int i=0;i<allFirstWeights.length;i++){
            
            for(int j=0;j<allFirstWeights[i].length;j++){
                myWriter.write(j+1+". nöron ağırlıkları:");
                
                for(int k=0; k<allFirstWeights[i][j].length;k++)
                    myWriter.write(Double.toString(allFirstWeights[i][j][k])+" ");
                
                myWriter.write("\n");
            }
            myWriter.write("\n");
            if(i==0)
            myWriter.write("Y2 çıkışlı verisetindeki giriş ve ara katman arasındaki ağırlıklar:\n");
        }
        
        myWriter.close();
    
    
        FileWriter myWriter2 = new FileWriter("ara ve çıkış katmanı arasındaki ağ ağırlıkları.txt");
        
        myWriter2.write("Y1 çıkışlı verisetindeki ara ve çıkış katmanları arasındaki ağırlıklar:\n");
                
        for(int i=0;i<allSecondWeights.length;i++){
            
            for(int j=0;j<allSecondWeights[i].length;j++){
                myWriter2.write(j+1+". nöron ağırlıkları:");
                myWriter2.write(Double.toString(allSecondWeights[i][j])+" \n");
            }
            
            myWriter2.write("\n");
            if(i==0)
            myWriter2.write("Y2 çıkışlı verisetindeki ara ve çıkış katmanları arasındaki ağırlıklar:\n");
        }
        
        myWriter2.close();
        //hocam word'de ağ parametreleri olarak neyi yazdırmam gerektiğini anlayamadım. Ondan dolayı yazdırmadım. 
    }
}