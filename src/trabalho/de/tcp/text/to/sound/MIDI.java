package trabalho.de.tcp.text.to.sound;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ECO
 */

//Fiz essa classe para conter valores 
//que o MIDI precisa mas que eu não sabia onde colocar.
public class MIDI {
    
private static final int DAMPER_PEDAL = 64;
private static final int DAMPER_ON = 127;
private static final int DAMPER_OFF = 0;
private static final int END_OF_TRACK = 47;

int getDamperPedal(){return DAMPER_PEDAL;}
int getDamperOn(){return DAMPER_ON;}
int getDamperOFF(){return DAMPER_OFF;}
int getEndOfTrack(){return END_OF_TRACK;}

}
