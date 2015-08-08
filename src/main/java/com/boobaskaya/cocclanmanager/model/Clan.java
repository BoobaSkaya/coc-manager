/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boobaskaya.cocclanmanager.model;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@XmlRootElement
public class Clan {
    private String name;
    
    private final ObservableList<Player> members;
    
    public Clan(){
        this("NoName");
    }
            
    
    public Clan(String name){
        this.name = name;
        members = FXCollections.observableArrayList();
    }
    
    @XmlAttribute
    public String getName(){
        return name;
    }
    
    @XmlElement
    public ObservableList<Player> getMembers(){
        return members;
    }
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.members);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Clan other = (Clan) obj;
        return Objects.deepEquals(this.members, other.members);
    }
}
