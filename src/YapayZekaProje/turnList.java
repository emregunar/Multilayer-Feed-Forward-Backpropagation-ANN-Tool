package YapayZekaProje;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

public class turnList {
    
    HSSFSheet readExcel(String konum) throws FileNotFoundException, IOException{
            FileInputStream fis=new FileInputStream(new File(konum));  
            HSSFWorkbook wb=new HSSFWorkbook(fis);   
            HSSFSheet sheet=wb.getSheetAt(0);  
            FormulaEvaluator formulaEvaluator=wb.getCreationHelper().createFormulaEvaluator();  
            
            return sheet;
    }
    
    List<List<List<Double>>> clusterByDepenValues(HSSFSheet sheet, int numOfIndepen, int numOfDepen) throws FileNotFoundException, IOException{
        
            List<List<List<Double>>> dataSet = new ArrayList<>();

             
            for(int i = 0; i < numOfDepen; i++){         
                 List<List<Double>> list = new ArrayList<>();
                 dataSet.add(list);
            }

            boolean firstLine = false;
            boolean isEmpty = false;
            
            for(Row row: sheet)             
            {  
                
                if(firstLine){          
                       
                    if(isEmpty == true)      
                        break;
                    
                    List<List<Double>> temps = new ArrayList<>();           
                    
                    for(int i = 0; i < numOfDepen ; i++){                   
                        List<Double> temp = new ArrayList<>();
                        temps.add(new ArrayList<>(temp));
                    }
                    
                    int count = 0;                                      

                    for(Cell cell: row)    
                    {      
                        if(count == 0 & cell.getNumericCellValue() == 0){                
                            isEmpty = true;
                            break;
                        }

                        if(count == numOfDepen + numOfIndepen)
                            break;



                        if(count >= numOfIndepen)
                            temps.get(count % numOfIndepen).add(cell.getNumericCellValue());

                        else                                           
                            for(int i = 0; i < numOfDepen; i++)
                                temps.get(i).add(cell.getNumericCellValue());    


                        count++;
                    } 


                    for(int i = 0; i < numOfDepen ; i++)                   
                       dataSet.get(i).add(new ArrayList<>(temps.get(i)));
                        
                }
                
            firstLine=true;
            }   
            
        return dataSet;
    }
        

		
    List<List<Double>> findMaxAndMinValues(List<List<Double>> dataSet){
        int line = dataSet.size();
        int column = dataSet.get(0).size();
        
        
        List<List<Double>> maxAndMinValues = new ArrayList<>();
        List<Double> maxColumnValues = new ArrayList<>();
        List<Double> minColumnValues = new ArrayList<>();
        
        for(int i = 0; i < column ; i++){
            maxColumnValues.add(dataSet.get(0).get(i));
            minColumnValues.add(dataSet.get(0).get(i));
        }
        
        
        for(int i = 0; i < line - 1; i++){
            for(int j = 0 ; j < column ; j++){
                
                try {
                    if( dataSet.get(i).get(j) > maxColumnValues.get(j) ) 
                        maxColumnValues.set(j,dataSet.get(i).get(j));
                    else if( dataSet.get(i).get(j) < minColumnValues.get(j) )
                        minColumnValues.set(j,dataSet.get(i).get(j));
                    
                } catch (Exception e) {
                    System.out.println(i + " " + j);            
                }
            }
        }
        
        maxAndMinValues.add(maxColumnValues);
        maxAndMinValues.add(minColumnValues);
        
        return maxAndMinValues;
       
    }
    
    List<Integer> findDepenAndIndepenValues(HSSFSheet sheet){     
         List<Integer> list = new ArrayList<>();
         int i=0;
         int numOfIndepenValues = 0;
         int numOfDepenValues = 0;
         
         while(true){
             try {
                 char letter = sheet.getRow(0).getCell(i).getStringCellValue().charAt(0);
                 if(letter == 'X')
                     numOfIndepenValues++;
                 else if(letter == 'Y')
                     numOfDepenValues++;
                 
                 i++;
             } catch (Exception e) {
                 break;
             }
             
         }
         list.add(numOfIndepenValues);
         list.add(numOfDepenValues);
         return list;
    }
    
    List<List<List<Double>>> normalization(List<List<List<Double>>> dataSet, List<List<Double>> maxAndMinList){    
        
        for(int turn = 0; turn < dataSet.size(); turn++){
            for(int line = 0; line < dataSet.get(turn).size(); line++){              
                for(int column = 0; column < maxAndMinList.get(0).size(); column++){     
                    
                    try {
                        double cellSubsMin = dataSet.get(turn).get(line).get(column) - maxAndMinList.get(1).get(column);       

                        double maxSubsMin = maxAndMinList.get(0).get(column) - maxAndMinList.get(1).get(column);

                        double norm = cellSubsMin / maxSubsMin;
                    
                        dataSet.get(turn).get(line).set(column, norm);
                    } catch (Exception e) {
                        break;
                    }
                 
                    
                }
            }

            dataSet.get(0).remove(dataSet.get(0).size() - 1);
            dataSet.get(1).remove(dataSet.get(1).size() - 1);
                
        }
        return dataSet;
    }
    
    List<List<List<List<Double>>>> seperateForEducationAndTest( List<List<List<Double>>> dataSet){
        
         Random r=new Random(); 
         int num = (int)(dataSet.get(0).size() * 0.3);

         List<List<List<Double>>> testDataSet = new ArrayList<>();
         
         for(int numOfDataSet = 0; numOfDataSet < dataSet.size(); numOfDataSet++){
             List<List<Double>> temp = new ArrayList<>();
             testDataSet.add(new ArrayList<>(temp));
         }
         

        while(testDataSet.get(0).size() < num){                     
             
                int random = r.nextInt(dataSet.get(0).size());
     
                for(int turn = 0; turn < dataSet.size() ; turn++){         
                      testDataSet.get(turn).add(dataSet.get(turn).get(random));
                      dataSet.get(turn).remove(random);
                }
        }
		
        List<List<List<List<Double>>>> testAndEducation = new ArrayList<>();
        testAndEducation.add(dataSet);
        testAndEducation.add(testDataSet);
        return testAndEducation;
         
    }
    
 
}
