package me.pvpb0t;

public class Bootstrap {

    private static App guiApp;

    public static App getGuiApp() {
        return guiApp;
    }

    static {
        guiApp = new App();
    }

    public static void main(String[] args){
        guiApp.show();
    }


}
