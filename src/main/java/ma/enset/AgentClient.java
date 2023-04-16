package ma.enset;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Base64;

public class AgentClient extends Agent {
    @Override
    protected void setup() {
        PublicKey publicKey = (PublicKey) getArguments()[0];
        String message = "Hello Serveur";
        try {
            Cipher cipher = Cipher.getInstance("RSA");
           cipher.init(Cipher.ENCRYPT_MODE, publicKey);
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
