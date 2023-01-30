package me.pvpb0t;

public class Bootstrap {

    private static App guiApp;

    static {
        guiApp = new App();
    }

    public static void main(String[] args){
        guiApp.show();
    }


}
