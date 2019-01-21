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
public abstract class Instrument {
    
protected int instrument;
protected boolean sustain;
protected int tempo;


abstract void setInstrument(int i);
abstract int getInstrument();
abstract void setSustain(boolean v);
abstract void invSustain();
abstract boolean getSustain();
abstract void setTempo(int t);
abstract int getTempo();


}
