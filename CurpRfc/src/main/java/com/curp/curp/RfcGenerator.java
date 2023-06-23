package com.curp.curp;

public class RfcGenerator {
    public static String generateRfcFromCurp(String curp) {
        // Obtener los primeros 10 caracteres del CURP (hasta antes de la H o M)
        String curpPrimeraParte = curp.substring(0, curp.indexOf('H') + 1);

        // Calcular la homoclave
        String homoclave = calculateHomoclave(curpPrimeraParte);

        // Combinar la primera parte del RFC y la homoclave
        String rfc = curpPrimeraParte + homoclave;

        return rfc;
    }

    private static String calculateHomoclave(String curpPrimeraParte) {
        // Peso de cada dígito.
        int[] peso = {13, 12, 11, 10, 9, 8, 7, 6, 5, 4};

        int suma = 0;

        // Calcular la suma ponderada
        for (int i = 0; i < peso.length; i++) {
            char digito = curpPrimeraParte.charAt(i);
            int valor = Character.getNumericValue(digito);

            suma += valor * peso[i];
        }

        // Calcular el residuo
        int residuo = suma % 10;

        // Calcular el complemento a 10
        int complemento = 10 - residuo;

        // Convertir el complemento a 10 a un String
        String homoclave;
        if (complemento == 10) {
            homoclave = "A";
        } else {
            homoclave = String.valueOf(complemento);
        }

        return homoclave;
    }

}
