package ma.enset.aes;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.PrivateKey;
import java.util.Base64;

public class AgentServer extends Agent {
    @Override
    protected void setup() {
        String secret = (String) getArguments()[0];

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage aclMessage = receive();

                if (aclMessage != null){
                    try {
                        SecretKey secretKey = new SecretKeySpec(secret.getBytes(), "AES");
                        String cryptedMsg = aclMessage.getContent();

                        byte[] encodedCryptedMsg = Base64.getDecoder().decode(cryptedMsg);
                        Cipher cipher = Cipher.getInstance("AES");
                        cipher.init(Cipher.DECRYPT_MODE, secretKey);
                        byte[] decryptMsg = cipher.doFinal(encodedCryptedMsg);

                        System.out.println(new String(decryptMsg));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else block();
            }
        });
    }
}
