package cat.itacademy.minddy.data.config;

public enum Priority {
    LOWER,
    LOW,
    NORMAL,
    HIGH,
    HIGHER;
    public static Priority parse(String str){
        try {
            return valueOf(str);
        }catch(Exception e){
            return parse(Integer.parseInt(str));
        }
    }
    static Priority parse(int num){
        return values()[num];
    }
}
