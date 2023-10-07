package Exceptions;
public class NoSuchDynamicData extends RuntimeException{
    public NoSuchDynamicData(){
        super();
    }

    public NoSuchDynamicData(String s){
        super(s);
    }
}
