/**
 * @date 5 Apr 2021
 * @author damianchng
 */
public class TennisPlayer implements Comparable<TennisPlayer>{

    private String name; //First name only
    private int level; //1-10
    private Location location; //WEST or EAST
    private Sex sex; //"M" or "F"
    private int age; // > 0
    
    public TennisPlayer(String name, Location location, Sex sex, int age){
        this.name = name;
        this.level = 1;
        this.location = location;
        this.sex = sex;
        this.age = age;
    }
    
    public TennisPlayer(String name, Location location, Sex sex, int age, int level){
        this.name = name;
        this.level = level;
        this.location = location;
        this.sex = sex;
        this.age = age;
    }
    
    @Override
    public int compareTo(TennisPlayer o) { //used for indexing in BST -> If level is equal use other factors
        if(level != o.getLevel()){
            return level - o.getLevel();
        }else if(location != o.getLocation()){
            return location.getValue() - o.getLocation().getValue();
        }else if(sex != o.getSex()){
            return sex.getValue() - o.getSex().getValue();
        }else{
            return age - o.getAge();
        }
    }
    
    public int compareLevel(TennisPlayer o){ //used for search by level
        return level - o.getLevel();
    }
    
    public boolean compareLocation(TennisPlayer o){ //used for search by location
        return location == o.getLocation();
    }
    
    public boolean compareSex(TennisPlayer o){ //used for search by sex
        return sex == o.getSex();
    }
    
    public int compareAge(TennisPlayer o){ //used for search by age
        return age - o.getAge();
    }
    
    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }
    
    private void setLevel(int level){
        this.level = level;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    //Update levels of opponent and user based on match
    public void updateRecord(boolean isWin, TennisPlayer opponent){
        if(isWin){
            if(opponent.getLevel() >= level){
                int oldLevel = level;
                level = opponent.getLevel();
                opponent.setLevel(oldLevel);
            }
        }else{
            if(opponent.getLevel() <= level){
                int oldLevel = level;
                level = opponent.getLevel();
                opponent.setLevel(oldLevel);
            }
        }
        if(level <= 0){
            level = 1;
        }
        if(opponent.getLevel() <= 0){
            opponent.setLevel(1);
        }
    }
    
    public String toString(){
        return name + ": level " + level + ", " + location + ", " + sex + ", " + age + " years old";
    }

}
