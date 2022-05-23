package br.com.desafio.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import br.com.desafio.error.ResourceConflictException;

public class Util {
	private static final DateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");

    static {
    	ddMMyyyy.setLenient(false);
    }

    public static Date stringToDate(String data) {
        try {
            return ddMMyyyy.parse(data);
        } catch (ParseException e) {
            throw new ResourceConflictException("Data Inválida.");
        }
    }

    public static String dateToString(Date data) {
        return ddMMyyyy.format(data);
    }
    
    public static String removeMascaraCpf(String cpf) {
        return cpf.replaceAll("\\D", "");
    }

}
