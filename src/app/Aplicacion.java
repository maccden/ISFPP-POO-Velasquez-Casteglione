package app;

import java.io.IOException;

import data.CargarParametros;

public class Aplicacion {
    public static void main(String[] args) {
        try {
            CargarParametros.parametros();
            System.out.println(CargarParametros.getArchivoColectivo());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
