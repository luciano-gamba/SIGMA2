package org.tallerjava.moduloClientes.interfase.remota.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tallerjava.moduloClientes.dominio.*;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientesDTO {

    private String cedula;
    private String nombreCompleto;
    private String telefono;
    private String contrasenia;
    private Float porcentajeDescuento;
    private EnumTipoProfesional tipo;
    private List<MedioPagoDTO> mediosDePago = new ArrayList<>();

    public Cliente buildCliente(){

        List<MedioPago> medios = new ArrayList<>();

        for(MedioPagoDTO mpDTO : mediosDePago){
            medios.add(mpDTO.buildMedioPago());
        }

        if(porcentajeDescuento != null){

            Profesional p = new Profesional();
            p.setCedula(cedula);
            p.setNombreCompleto(nombreCompleto);
            p.setTelefono(telefono);
            p.setContrasenia(convertToHas(contrasenia));
            p.setMediosDePago(medios);
            p.setPorcentajeDescuento(porcentajeDescuento);
            p.setTipo(tipo);

            return p;
        }

        ClienteComun c = new ClienteComun();
        c.setCedula(cedula);
        c.setNombreCompleto(nombreCompleto);
        c.setTelefono(telefono);
        c.setContrasenia(convertToHas(contrasenia));
        c.setMediosDePago(medios);

        return c;
    }

    private static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 64)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }


    public static String convertToHas(String value) {
        try {
            return toHexString(getSHA(value));
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

}
