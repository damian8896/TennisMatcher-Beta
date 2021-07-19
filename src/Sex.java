/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author damianchng
 */
public enum Sex{ //helpful in determining sex based on input -> more functional -> INTERESTING CONCEPT
    Female(0), Male(1);
    
    private int value;
    
    private Sex(int value){
        this.value = value;
    }
    
    public int getValue(){
        return value;
    }
    
    public static Sex getSex(String sex){
        if(sex.equals("Female")){
            return Female;
        }else{
            return Male;
        }
    }
}
