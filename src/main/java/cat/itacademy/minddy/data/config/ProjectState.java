package cat.itacademy.minddy.data.config;

public enum ProjectState {
    ACTIVE(),
    DELAYED(),
    PAUSED(),
    SILENT(),
    DISCARDED(),
    COMPLETE();

    public static ProjectState parse(String str){
        try {
            return valueOf(str);
        }catch(Exception e){
            return parse(Integer.parseInt(str));
        }
    }
    static ProjectState parse(int num){
        return values()[num];
    }
}
