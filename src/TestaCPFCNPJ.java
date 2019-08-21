import org.json.JSONObject;

public class TestaCPFCNPJ {

	public static void main(String[] args) {
		
		
		JSONObject retorno = ValidacaoCPF_CNPJ.execute("60.701.190/0067-22");
		System.out.println("Retorno = " + retorno);
		
		retorno = ValidacaoCPF_CNPJ.execute("00.781.190/0065-22");
		System.out.println("Retorno = " + retorno);
		
		retorno = ValidacaoCPF_CNPJ.execute("10.7.190/0067-22");
		System.out.println("Retorno = " + retorno);
		
		retorno = ValidacaoCPF_CNPJ.execute("123.123.123-43");
		System.out.println("Retorno = " + retorno);
		
		retorno = ValidacaoCPF_CNPJ.execute("22asd");
		System.out.println("Retorno = " + retorno);
		
		retorno = ValidacaoCPF_CNPJ.execute("227.969.912-53");
		System.out.println("Retorno = " + retorno);

	}

}
