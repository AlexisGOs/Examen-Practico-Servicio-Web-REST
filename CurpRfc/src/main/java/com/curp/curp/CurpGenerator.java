package com.curp.curp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CurpGenerator {

    private static final List<String> ESTADOS = Arrays.asList(
            "AS", "BC", "BS", "CC", "CL", "CM", "CS", "CH", "DF", "DG", "GT", "GR", "HG",
            "JC", "MC", "MN", "MS", "NT", "NL", "OC", "PL", "QT", "QR", "SP", "SL", "SR",
            "TC", "TS", "TL", "VZ", "YN", "ZS"
    );

    public String generateCurp(DataRequest request) throws Exception {
        validateInputs(request);

        StringBuilder curpBuilder = new StringBuilder();

        curpBuilder.append(getFirstLetter(request.getApellidoPaterno()));
        curpBuilder.append(getFirstInternalVowel(request.getApellidoPaterno()));
        curpBuilder.append(getFirstLetter(request.getApellidoMaterno()));
        curpBuilder.append(getFirstLetter(request.getNombre()));
        curpBuilder.append(getFechaNacimientoDigits(request.getFechaNacimiento()));
        curpBuilder.append(getGeneroLetter(request.getGenero()));
        curpBuilder.append(getEstadoClave(request.getEstado()));
        curpBuilder.append(getFirstConsonants(request.getApellidoPaterno()));
        curpBuilder.append(getFirstConsonants(request.getApellidoMaterno()));
        curpBuilder.append(getFirstConsonants(request.getNombre()));
        curpBuilder.append(getDiferenciadorHomonimiaSiglo(request.getFechaNacimiento()));
        curpBuilder.append(getDigitoVerificador(curpBuilder.toString()));

        return curpBuilder.toString().toUpperCase();
    }

    private void validateInputs(DataRequest request) throws Exception {
        if (request.getApellidoPaterno().isEmpty() ||
                request.getApellidoMaterno().isEmpty() ||
                request.getNombre().isEmpty()) {
            throw new Exception("El apellido paterno, apellido materno y nombre son obligatorios.");
        }

        if (request.getFechaNacimiento() == null) {
            throw new Exception("La fecha de nacimiento es obligatoria.");
        }
    }

    private char getFirstLetter(String word) {
        return Character.toUpperCase(word.charAt(0));
    }

    private char getFirstInternalVowel(String word) {
        return word
                .substring(1)
                .chars()
                .mapToObj(c -> (char) c)
                .filter(this::isVowel)
                .findFirst()
                .orElse('X');
    }

    private boolean isVowel(char c) {
        return "AEIOU".indexOf(Character.toUpperCase(c)) != -1;
    }

    private String getFechaNacimientoDigits(LocalDate fechaNacimiento) {
        return fechaNacimiento.format(DateTimeFormatter.ofPattern("yyMMdd"));
    }

    private char getGeneroLetter(String genero) {
        return Character.toUpperCase(genero.charAt(0));
    }

    private String getEstadoClave(String estado) {
        return ESTADOS.stream()
                .filter(e -> e.equalsIgnoreCase(estado))
                .findFirst()
                .orElse("NE");
    }

    private String getFirstConsonants(String word) {
        StringBuilder consonantsBuilder = new StringBuilder();
        boolean isFirstVowelFound = false;

        for (char c : word.toCharArray()) {
            if (!isVowel(c)) {
                consonantsBuilder.append(Character.toUpperCase(c));
                isFirstVowelFound = true;
            } else if (isFirstVowelFound) {
                break;
            }
        }

        return consonantsBuilder.toString().toUpperCase();
    }

    private char getDiferenciadorHomonimiaSiglo(LocalDate fechaNacimiento) {
        int year = fechaNacimiento.getYear() % 100;
        return Character.forDigit(year, 36);
    }

    private char getDigitoVerificador(String curp) {
        int sum = 0;
        int curpLength = curp.length();

        for (int i = 0; i < curpLength; i++) {
            char c = curp.charAt(i);
            int value = Character.isLetter(c) ? Character.getNumericValue(Character.toUpperCase(c)) : Character.getNumericValue(c);
            sum += value * (curpLength - i);
        }

        int remainder = sum % 10;
        return Character.forDigit((10 - remainder) % 10, 10);
    }

}
