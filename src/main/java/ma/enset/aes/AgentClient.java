package ma.enset.aes;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.PublicKey;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;

public class AgentClient extends Agent {
    @Override
    protected void setup() {
        String secret = (String) getArguments()[0];
        String message = "Hello Serveur aesit ";
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
           cipher.init(Cipher.ENCRYPT_MODE, secretKey);

           byte[] cryptedMsg = cipher.doFinal(message.getBytes());
           String cryptedEncodeMsg = Base64.getEncoder().encodeToString(cryptedMsg);

            ACLMessage message1 = new ACLMessage(ACLMessage.INFORM);
            message1.addReceiver(new AID("server", AID.ISLOCALNAME));
            message1.setContent(cryptedEncodeMsg);

            send(message1);

            System.out.println("=============== Crypted Message ===========");
            System.out.println(Arrays.toString(cryptedMsg) );

            System.out.println("=============== Crypted Message String ===========");
            System.out.println(cryptedEncodeMsg);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
