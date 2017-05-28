package localalignment;

import static java.lang.Integer.max;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kuba
 */
public class SmithWaterman {
    
    String first;
    String second;
    int score[][];
    int[][] dynProg;
    int iPredecessor[][];
    int jPredecessor[][];
    
    int indel = -8;     // insertion/deletion
    
    private int index(char c){
        
        int i = 0;
        
        switch(c)
        {
            case 'A':
                i = 0;
                break;
            case 'T':
                i = 1;
                break;
            case 'G':
                i = 2;
                break;
            case 'C':
                i = 3;
                break;
        }
        return i;
    }
    
    
    SmithWaterman(String _first, String _second, int[][] _score){
        
        dynProg = new int[_first.length() + 1][_second.length() + 1];
        iPredecessor = new int[_first.length() + 1][_second.length() + 1];
        jPredecessor = new int[_first.length() + 1][_second.length() + 1];
        first = _first;
        second = _second;
        score = _score;
    }
    
    void align(){
        
        for(int i = 1; i < first.length() + 1; i++){
            
            dynProg[i][0] = i * indel;
            iPredecessor[i][0] = i - 1;
        }
        for(int j = 1; j < second.length() + 1; j++){
        
            dynProg[0][j] = j * indel;
            iPredecessor[0][j] = j - 1;
        }
        
        /*for(int i = 0; i < first.length() + 1; i++){
            
            for(int j = 0; j < second.length() + 1; j++)
                System.out.printf("%d ", dynProg[i][j]);
            
            System.out.printf("\n");
        }*/
        
        System.out.printf("\n");
        
        for(int i = 1; i < first.length() + 1; i++){
            
            for(int j = 1; j < second.length() + 1; j++){
                
                dynProg[i][j] = dynProg[i-1][j] + indel;
                iPredecessor[i][j] = i - 1;
                jPredecessor[i][j] = j;
                
                if(dynProg[i][j] < dynProg[i][j-1] + indel){

                    dynProg[i][j] = dynProg[i][j-1] + indel;
                    iPredecessor[i][j] = i;
                    jPredecessor[i][j] = j - 1;
                }
                if(dynProg[i][j] < dynProg[i-1][j-1] + score[index(first.charAt(i-1))][index(second.charAt(j-1))]){
                    
                    dynProg[i][j] = dynProg[i-1][j-1] + score[index(first.charAt(i-1))][index(second.charAt(j-1))];
                    iPredecessor[i][j] = i - 1;
                    jPredecessor[i][j] = j - 1;
                }
            }
        }
        
        for(int i = 0; i < first.length() + 1; i++){
            
            for(int j = 0; j < second.length() + 1; j++)
                System.out.printf("%d ", dynProg[i][j]);
            
            System.out.printf("\n");
        }
    }
    
    void backtrack(){
        
        int i = first.length();
        int j = second.length();
        StringBuilder firstAlignment = new StringBuilder();
        StringBuilder secondAlignment = new StringBuilder();
        StringBuilder alignment  = new StringBuilder();
        
        for(i = 0; i < first.length() + 1; i++){
            
            for(j = 0; j < second.length() + 1; j++){
                
                System.out.printf("(%d, %d) ", iPredecessor[i][j], jPredecessor[i][j]);
            }
            
            System.out.printf("\n");
        }
        
        i = first.length();
        j = second.length();
        
        while(i >= 0 || j >= 0){
            
            if(iPredecessor[i][j] == i - 1 && jPredecessor[i][j] == j){
                
                firstAlignment = firstAlignment.append("_");
                secondAlignment = secondAlignment.append(second.charAt(j - 1));
                alignment = alignment.append(" ");
                i--;
            }
            else if(iPredecessor[i][j] == i - 1 && jPredecessor[i][j] == j - 1){
                
                firstAlignment.append(first.charAt(i - 1));
                secondAlignment.append(second.charAt(j - 1));
                if(first.charAt(i - 1) == second.charAt(j - 1))
                    alignment.append("|");
                else
                    alignment.append(".");
                i--;
                j--;
                
            }
            else if(iPredecessor[i][j] == i && jPredecessor[i][j] == j - 1){
                
                firstAlignment.append(first.charAt(i - 1));
                secondAlignment.append("_");
                alignment.append(" ");
                j--;
            }
            else break;
        }
        
        System.out.printf("%s\n%s\n%s\n", firstAlignment.reverse(), alignment.reverse(), secondAlignment.reverse());
    }
}
