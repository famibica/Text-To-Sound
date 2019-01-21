/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho.de.tcp.text.to.sound;

import java.awt.List;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 *
 * @author Ottoni Bastos
 */
public class Player {
    
static final int[] Offsets= {-3, -1, 0, 1, 3, 5, 7 };
protected int velocity;
protected int basekey;
protected int time; // time in ticks for the composition

    

//construtor
Player(int v,int b,int t){


velocity = v;
basekey = b;
time = t;



}

//Dá um valor específico para o volume
void setVolume(int DefaultVolume){
velocity = DefaultVolume;
}

//pega o valor atual do volume
int getVolume(){

    return velocity;

}
//Aumenta o volume
void addVolume(int v){

velocity= velocity + v;

}
//Diminui o volume
void decVolume(int v){

velocity = velocity - v;

}

void setBasekey(int b){

    basekey = b;

}

int getBaseKey(){

    return basekey;

}

void addBaseKey(int o){

basekey = basekey + o;
    
}

void decBaseKey(int o){

basekey = basekey - o;    

}

void setTime(int t){

time = t;
    
}

int getTime(){

 return time;
    
}

void addTime(int t){

 time = time + t;   

}

void decTime(int t){

 time = time - t;   

}

public void addTrack(Track track,MIDI midi, Instrument instrument,
                                Note[  ] notes)
        throws InvalidMidiDataException
    {
       
        // Set the instrument on channel 0
        ShortMessage sm = new ShortMessage( );
        sm.setMessage(ShortMessage.PROGRAM_CHANGE,0, instrument.instrument, 0);
        track.add(new MidiEvent(sm, 0));

        int n = 0; // indice do array de notas
        int LastNote = 0x0;//last note played
        int LastNoteLength = 16;
        int LowerOrUpperCase= 0;//Flag
        // These values persist and apply to all notes 'till changed
        int MaxLength = 0;
        int notelength = 16; // default to quarter notes    // 60 is middle C. Adjusted up and down by octave
        int numnotes = 0;    // How many notes in current chord?
        int key=0;
        ArrayList<Integer> formerArranje = new ArrayList<>();
        ArrayList<Integer> formerLength = new ArrayList<>();
        
        while(n < notes.length) {
            
            char c = notes[n].getNote();
            //System.out.println(c);
            

            //Aumenta ou diminui oitavas
            if (c >= '0' && c <= '9'){       
               int e = (int)c - 48;
               if ((e % 2) == 0 ){addBaseKey(12);}//Se par,aumenta uma oitava //done
               else  {decBaseKey(12);}              //Se impar,diminui uma oitava //done
            }
            else if (c == '!') {addVolume(16);}
            else if (c == '?') {decVolume(16);}
            // se '/' verifica que numero vem após para 
            //saber o comprimento da nota
            else if (c == '/') { 
                char d = notes[++n].getNote();
                if (d == '2') {notelength = 32;
                    if(notelength>=MaxLength){
                    MaxLength = notelength;
                    }}  
                else if (d == '4') {notelength = 16;
                if(notelength>=MaxLength){
                    MaxLength = notelength;
                }
                }  // quarter note
                else if (d == '8') {notelength = 8;
                if(notelength>=MaxLength){
                    MaxLength = notelength;
                }
                }   // eighth note
                else if (d == '3' && notes[++n].getNote() == '2') {notelength = 2;
                if(notelength>=MaxLength){
                    MaxLength = notelength;
                }
                continue;
                }
                else if (d == '6' && notes[++n].getNote() == '4') {notelength = 1;
                if(notelength>=MaxLength){
                    MaxLength = notelength;
                }
                continue;
                }
                else if (d == '1') {
                    if (n < notes.length && notes[++n].getNote() == '6')
                    {notelength = 4;
                       if(notelength>=MaxLength){
                       MaxLength = notelength;
                       }
                    }    // 1/16th note
                    else if(n < notes.length)
                    {notelength = 64;
                       if(notelength>=MaxLength){
                       MaxLength = notelength;
                       }
                    }  // whole note
                continue;
                }
            continue;
            }
            else if (c == 's') {
                instrument.invSustain();
                // Change the sustain setting for channel 0
                ShortMessage m = new ShortMessage( );
                m.setMessage(ShortMessage.CONTROL_CHANGE, 0,
                             midi.getDamperPedal(), instrument.getSustain()?midi.getDamperOn():midi.getDamperOFF());
                track.add(new MidiEvent(m, getTime()));
            }
            //Se [A..G]: Converte os caracteres em notas
            else if (c >= 'A' && c <= 'G') {
                key = getBaseKey() + Offsets[c - 'A'];
               
                if (++n < notes.length) {
                    if ((notes[n].getNote() == 'J') || (notes[n].getNote() == 'j')) { // flat
                        key--; 
                       
                    }
                    else if (notes[n].getNote() == '#') { // sharp
                        key++;
                       
                    }
                }

                formerArranje.add(new Integer(key));
                formerLength.add(new Integer(notelength));
                addNote(track, getTime(), notelength, key, getVolume());
                if(notelength>=MaxLength){
                    MaxLength = notelength;
                }
                numnotes++;
                LastNote = c;
                LastNoteLength = notelength;
                LowerOrUpperCase = 2;
                continue;
            }
            //Se [a..g]: converte os caracteres em notas
            else if (c >= 'a' && c <= 'g') {
                
             
                key = getBaseKey() + Offsets[c - 'a'];
                
                if (++n < notes.length) {
                    if ((notes[n].getNote() == 'J') || (notes[n].getNote() == 'j')) { // flat
                        key--; 
                        
                    }
                    else if (notes[n].getNote() == '#') { // sharp
                        key++;
                       
                    }
                }
                
                formerArranje.add(new Integer(key));
                formerLength.add(new Integer(notelength));
                addNote(track, getTime(), notelength, key, getVolume());
                if(notelength>=MaxLength){
                    MaxLength = notelength;
                }
                numnotes++;
                LastNote = c;
                LastNoteLength = notelength;
                LowerOrUpperCase = 1;
                continue;
            }
            //Se I/O/U,tocar a última nota outra vez!
            else if((c == 'I') || (c == 'i') || (c == 'O') ||
                    (c == 'o') || (c == 'u') || (c == 'U')){
                
                addTime(notelength);
                for(int i = 0;i<formerArranje.size();i++){
               
                addNote(track, getTime(),formerLength.get(i).intValue(),
                        formerArranje.get(i).intValue(), getVolume());
                        }
                numnotes++;
                
                
                
                
               /* 
                if(LowerOrUpperCase == 1){          
                key = getBaseKey() + Offsets[LastNote - 'a'];
                }
                else if(LowerOrUpperCase == 2){
                key = getBaseKey() + Offsets[LastNote - 'A'];
                }
                addTime(notelength);
                addNote(track, getTime(),notelength, key, getVolume());
                numnotes++;
               */        
            }
            //Se ESPAÇO,todas as notas apartir daqui serão tocadas 
            //após as notas carregadas até agora e haverá um tempo de silêncio
            //entre esses dois períodos.
            else if(c == ' ' || c =='\n'){
            
                if(notes[n+1].getNote() != 'U' && notes[n+1].getNote() != 'u' 
                    && notes[n+1].getNote() != 'O' && notes[n+1].getNote() != 'o'
                    && notes[n+1].getNote() != 'I' && notes[n+1].getNote() != 'i'){
                  formerArranje.clear();
                  formerLength.clear();
            
             }
            
                
                    addTime(MaxLength + notelength);
                
            
            }
            //Se X,então todas as notas apartir daqui serão tocadas 
            //apos as notas carregadas até agora
            else if (c == 'X' || c == 'x') {
                if(notes[n+1].getNote() != 'U' && notes[n+1].getNote() != 'u' 
                    && notes[n+1].getNote() != 'O' && notes[n+1].getNote() != 'o'
                    && notes[n+1].getNote() != 'I' && notes[n+1].getNote() != 'i'){
                  formerArranje.clear();
                  formerLength.clear();
            
             }
               
            
            if (numnotes > 0) {
                    
                    addTime(notelength);
                    numnotes = 0;
                }
            MaxLength = 0;
            }
          //System.out.println("Hey"); 
          n++;
        }
    setTime(0);
    }

//Carrega a Nota "key" na trilha "track" indicando o momento inicial "startTick"
//o periodo da nota "tickLenght" e o volume "velocity".
    public static void addNote(Track track, int startTick,
                               int tickLength, int key, int velocity)
        throws InvalidMidiDataException
    {
        ShortMessage on = new ShortMessage( );
        on.setMessage(ShortMessage.NOTE_ON,0,key,velocity);
        ShortMessage off = new ShortMessage( );
        off.setMessage(ShortMessage.NOTE_OFF,0,key,velocity);
        track.add(new MidiEvent(on,startTick));
        track.add(new MidiEvent(off,startTick+tickLength));
        
    
    }
    
}
