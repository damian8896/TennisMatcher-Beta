/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author damianchng
 */
public enum Location{ //helpful in determining location based on input -> more functional -> INTERESTING CONCEPT
    WEST(0), EAST(1);
    
    private int value;
    
    private Location(int value){
        this.value = value;
    }
    
    public int getValue(){
        return value;
    }
    
    public static Location getLocation(String location){
        if(location.equals("EAST")){
            return EAST;
        }else{
            return WEST;
        }
    }
}
