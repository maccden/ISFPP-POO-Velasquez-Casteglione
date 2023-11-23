package util;

/**
 * Clase de utilidad para operaciones con tiempo.
 */
public class Time {

    /**
     * Convierte una cadena de tiempo en formato "hh:mm" a minutos.
     *
     * @param s Cadena de tiempo en formato "hh:mm".
     * @return Valor en minutos.
     */
    public static int toMins(String s) {
        String[] hourMin = s.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        int hoursInMins = hour * 60;
        return hoursInMins + mins;
    }

    /**
     * Convierte un valor en minutos a una cadena de tiempo en formato "hh:mm".
     *
     * @param minutes Valor en minutos.
     * @return Cadena de tiempo en formato "hh:mm".
     */
    public static String toTime(int minutes) {
        int h = minutes / 60;
        int m = minutes % 60;
        return String.format("%d:%02d", h, m);
    }
}
