package es.unex.cheapgamesv2.data.model;

public class VideojuegoGlobal {

    private static VideojuegoGlobal videojuegoGlobal = new VideojuegoGlobal();

    private static String gameID;

    private static String steamAppID;

    private static String cheapest;

    private static String cheapestDealID;

    private static String external;

    private static String thumb;

    private VideojuegoGlobal() {}

    public static VideojuegoGlobal getInstance() {
        return videojuegoGlobal;
    }

    public static String getGameID() {
        return gameID;
    }

    public static void setGameID(String gameID) {
        VideojuegoGlobal.gameID = gameID;
    }

    public static String getSteamAppID() {
        return steamAppID;
    }

    public static void setSteamAppID(String steamAppID) {
        VideojuegoGlobal.steamAppID = steamAppID;
    }

    public static String getCheapest() {
        return cheapest;
    }

    public static void setCheapest(String cheapest) {
        VideojuegoGlobal.cheapest = cheapest;
    }

    public static String getCheapestDealID() {
        return cheapestDealID;
    }

    public static void setCheapestDealID(String cheapestDealID) {
        VideojuegoGlobal.cheapestDealID = cheapestDealID;
    }

    public static String getExternal() {
        return external;
    }

    public static void setExternal(String external) {
        VideojuegoGlobal.external = external;
    }

    public static String getThumb() {
        return thumb;
    }

    public static void setThumb(String thumb) {
        VideojuegoGlobal.thumb = thumb;
    }


}
