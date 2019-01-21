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

//Classe abstrata para o arquivo a ser lido
public abstract class Arquivo {
    
protected char[] array_buffer;

abstract char[] getBuffer();

}
