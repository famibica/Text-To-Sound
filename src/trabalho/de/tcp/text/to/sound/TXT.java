/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho.de.tcp.text.to.sound;

/**
 *
 * @author Ottoni Bastos
 */

import java.io.*;
import java.util.Scanner;
import javax.swing.JTextArea;

//Classe que implementa FILE.
//Modela um aquivo do tipo .TXT
public class TXT extends Arquivo {

protected StringBuilder text;
   
//construtor
TXT(String path,String encode) throws IOException{
    int i =0;
    text = new StringBuilder();
    
    Scanner scanner = new Scanner(new FileInputStream(path),encode);
    try {
      while (scanner.hasNextLine()){
        text.append(scanner.nextLine());
      }
    }
    finally{
      scanner.close();}
array_buffer = new char[text.length()];
 
for(i=0;i<text.length();i++){
array_buffer[i] = text.charAt(i);
System.out.println(text.charAt(i));
}
}
TXT(JTextArea jTextArea){
int i=0;
text = new StringBuilder();
text.append(jTextArea.toString());

array_buffer= new char[text.length()];

for(i=0;i<text.length();i++){
array_buffer[i] = text.charAt(i);
System.out.println(text.charAt(i));
}


}
    
@Override
char[] getBuffer(){

return array_buffer;

}
}
