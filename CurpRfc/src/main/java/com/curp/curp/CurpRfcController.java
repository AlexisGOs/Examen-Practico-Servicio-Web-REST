package com.curp.curp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CurpRfcController {

    @Autowired
    private CurpGenerator curpGenerator;

    @PostMapping("/generarCurp")
    public ResponseEntity<Map<String, String>> generarCurpRfc(@RequestBody DataRequest data) throws Exception {
        Map<String, String> response = new HashMap<>();

        // Generar CURP
        String curp = curpGenerator.generateCurp(data);

        // Generar RFC
        String rfc = RfcGenerator.generateRfcFromCurp(curp);

        response.put("curp", curp);
        response.put("rfc", rfc);

        // Modificar el campo de salida de respuesta de "EXITO" a "OK"
        response.put("status", "OK");

        return ResponseEntity.ok(response);
    }
}
