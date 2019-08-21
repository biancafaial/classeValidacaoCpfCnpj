import java.util.InputMismatchException;

import org.json.JSONObject;

public class ValidacaoCPF_CNPJ {

	private final static int CNPJ_SIZE = 14;
	private final static int CPF_SIZE = 11;

	public static JSONObject execute(String cnpj_cpf) {

		JSONObject retCodeError = new JSONObject();
		retCodeError.put("retcode", "ERROR");
		retCodeError.put("descricao", "");
		
		JSONObject retCodeSuccess = new JSONObject();
		retCodeSuccess.put("retcode", "SUCCESS");
		retCodeSuccess.put("tipo", "");
		retCodeSuccess.put("raiz", "");
		retCodeSuccess.put("filial", "");
		retCodeSuccess.put("dc", "");
		retCodeSuccess.put("valor", "");

		String string_cnpj_cpf = executeSplit(cnpj_cpf);

		boolean soNumeros = isOnlyNumbers(string_cnpj_cpf);

		if (!soNumeros) {
			retCodeError.put("descricao", "Não possui apenas números");
			return retCodeError;
		}

		String tmp = checkCnpjCpf(string_cnpj_cpf);
		String retCode = "";
		

		
		if(tmp.equals("CNPJ")){
			String raiz = string_cnpj_cpf.substring(0,8);
			String filial = string_cnpj_cpf.substring(8,12);
			String dc = string_cnpj_cpf.substring(12,14);
			
			retCode = checkIfValidCnpj(string_cnpj_cpf);
			
			if(retCode.equals("SUCCESS")) {
				retCodeSuccess.put("tipo", "CNPJ");
				retCodeSuccess.put("valor", string_cnpj_cpf);
				retCodeSuccess.put("raiz", raiz);
				retCodeSuccess.put("filial", filial);
				retCodeSuccess.put("dc", dc);

				return retCodeSuccess;	
			}
			else {
				retCodeError.put("descricao", "CNPJ inválido");
				return retCodeError;
				
			}

		}
		else if(tmp.equals("CPF")){
			String raiz = string_cnpj_cpf.substring(0,9);
			String dc = string_cnpj_cpf.substring(9,11);
			retCode = checkIfValidCpf(string_cnpj_cpf);
			
			if(retCode.equals("SUCCESS")) {
				retCodeSuccess.put("tipo", "CPF");
				retCodeSuccess.put("valor", string_cnpj_cpf);
				retCodeSuccess.put("raiz", raiz);
				retCodeSuccess.put("filial", "");
				retCodeSuccess.put("dc", dc);

				return retCodeSuccess;				
		} else {
			retCodeError.put("descricao", "CPF inválido");
			return retCodeError;		
			}
		}
		else {
			retCodeError.put("descricao", "Número de caracteres inválido");
			return retCodeError;	
		}
	}

	private static String checkIfValidCpf(String string_cnpj_cpf) {
		if (validaCpf(string_cnpj_cpf)) {
			return "SUCCESS";
		}
		return "ERRO";
	}

	private static boolean validaCpf(String string_cnpj_cpf) {

		if (string_cnpj_cpf.equals("00000000000") || string_cnpj_cpf.equals("11111111111")
				|| string_cnpj_cpf.equals("22222222222") || string_cnpj_cpf.equals("33333333333")
				|| string_cnpj_cpf.equals("44444444444") || string_cnpj_cpf.equals("55555555555")
				|| string_cnpj_cpf.equals("66666666666") || string_cnpj_cpf.equals("77777777777")
				|| string_cnpj_cpf.equals("88888888888") || string_cnpj_cpf.equals("99999999999"))
			return false;

		char dig10, dig11;
		int sm, i, r, num, peso;

		try {
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {

				num = (int) (string_cnpj_cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48);

			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (string_cnpj_cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			if ((dig10 == string_cnpj_cpf.charAt(9)) && (dig11 == string_cnpj_cpf.charAt(10)))
				return true;
			else
				return false;
		} catch (InputMismatchException erro) {
			return false;
		}
	}

	private static String checkIfValidCnpj(String string_cnpj_cpf) {

		if (validaCnpj(string_cnpj_cpf)) {

			return "SUCCESS";
			
		}
		return "ERRO";
	}

	private static boolean validaCnpj(String string_cnpj_cpf) {
		if (string_cnpj_cpf.equals("00000000000000") || string_cnpj_cpf.equals("11111111111111")
				|| string_cnpj_cpf.equals("22222222222222") || string_cnpj_cpf.equals("33333333333333")
				|| string_cnpj_cpf.equals("44444444444444") || string_cnpj_cpf.equals("55555555555555")
				|| string_cnpj_cpf.equals("66666666666666") || string_cnpj_cpf.equals("77777777777777")
				|| string_cnpj_cpf.equals("88888888888888") || string_cnpj_cpf.equals("99999999999999"))
			return false;

		char dig13, dig14;
		int sm, i, r, num, peso;

		try {
			sm = 0;
			peso = 2;
			for (i = 11; i >= 0; i--) {

				num = (int) (string_cnpj_cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig13 = '0';
			else
				dig13 = (char) ((11 - r) + 48);

			sm = 0;
			peso = 2;
			for (i = 12; i >= 0; i--) {
				num = (int) (string_cnpj_cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig14 = '0';
			else
				dig14 = (char) ((11 - r) + 48);

			if ((dig13 == string_cnpj_cpf.charAt(12)) && (dig14 == string_cnpj_cpf.charAt(13)))
				return true;
			else
				return false;
		} catch (InputMismatchException erro) {
			return false;
		}
		
		

	}
	

	private static String checkCnpjCpf(String string_cnpj_cpf) {

		if (string_cnpj_cpf.length() == CNPJ_SIZE)
			return "CNPJ";
		else if (string_cnpj_cpf.length() == CPF_SIZE)
			return "CPF";
		else
		return "INVALID";

	}

	private static boolean isOnlyNumbers(String string_cnpj_cpf) {
		
		return true;
	}

	private static String executeSplit(String cnpj_cpf) {

		String output_cnpj_cpf = cnpj_cpf.replaceAll("[^0-9]+", "");
	
		return output_cnpj_cpf;
	}
	

}
