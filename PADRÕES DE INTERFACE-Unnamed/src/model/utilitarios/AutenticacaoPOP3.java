package model.utilitarios;

import java.util.Properties;

import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

public class AutenticacaoPOP3 {
	
	/*
	 * M�todo que ira autenticar um membro via protocolo de visualizacao de email POP3, caso autenticado
	 * retorna true, e isso significa que o email � realmente v�lido, e � possivel usa=lo no sistema.
	 * @params login, senha, provedor, porta*/
	
	public static boolean check(String login, String senha, String provedor, String porta) {

		Properties properties = new Properties();

		properties.put("mail.pop3s.host", provedor);
		properties.put("mail.pop3s.port", porta);
		properties.put("mail.pop3s.starttls.enable", "true");

		Session emailSession = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(login, senha);
			}
		});
		emailSession.setDebug(true);

		Store store;
		try {
			store = emailSession.getStore("pop3s");
			store.connect();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
