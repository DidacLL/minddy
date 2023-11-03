package cat.itacademy.minddy.data.config;

public enum TaskState {
    TODO,
    ON_PROGRESS,
    REVIEW,
    DEFERRED,
    DONE,
    DISCARDED;
    public static TaskState parse(String str){
        try {
            return valueOf(str);
        }catch(Exception e){
            return parse(Integer.parseInt(str));
        }
    }
    static TaskState parse(int num){
        return values()[num];
    }
}
