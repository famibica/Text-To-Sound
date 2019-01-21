/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho.de.tcp.text.to.sound;

/**
 *
 * @author ECO
 */
public class Piano extends Instrument{


        

Piano(){

    instrument = 0;
    sustain = false;
    tempo = 120;
}

void setInstrument(int i){

instrument = i;

}

int getInstrument(){

return instrument;

}

void setSustain(boolean state){

sustain = state;

}

void invSustain(){

sustain = !sustain;
}

boolean getSustain(){
return sustain;
}

void setTempo(int t){

tempo = t;

}

int getTempo(){

return tempo;
    
}



}
