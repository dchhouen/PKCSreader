	import java.util.*;
	import java.security.cert.CertificateException;
	import java.security.KeyStoreException;
	import java.security.cert.X509Certificate;
	import java.security.KeyStore;
	import java.security.Provider;
	import java.security.Security;


	public class PKCSreader {
	    public static void main(String[] args) throws Exception {
	        try {
	            String configName = "pkcs11.properties";
	            Provider p = new sun.security.pkcs11.SunPKCS11(configName);
	            Security.addProvider(p);
//	            Console c = System.console();
//	            char[] pin = c.readPassword("Enter your PIN: ");
	            System.out.println("Please enter your PIN: ");
	            Scanner scan = new Scanner(System.in);
	            String input = scan.nextLine();
	            scan.close();
	            char[] pin = input.toCharArray();
	            KeyStore cac = null;
	            cac = KeyStore.getInstance("PKCS11");
	            cac.load(null, pin);
	            showInfoAboutCAC(cac);
	        }
	        catch(Exception ex) {
	            ex.printStackTrace();
	            System.exit(0);
	        }
	    }
	    public static void showInfoAboutCAC(KeyStore ks) throws KeyStoreException, CertificateException {
	        Enumeration<String> aliases = ks.aliases();
	        while(aliases.hasMoreElements()) {
	            String alias = aliases.nextElement();
	            X509Certificate[] cchain = (X509Certificate[]) ks.getCertificateChain(alias);
	            System.out.println("Certificate Chain for " + alias);
	            for(int i = 0; i < cchain.length; i++) {
	                System.out.println(" -SubjectDN: " + cchain[i].getSubjectDN());
	                System.out.println(" -IssuerDN: " + cchain[i].getIssuerDN());
	            }
	        }
	    }
	}
