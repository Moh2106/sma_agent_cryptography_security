package ma.enset;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Base64;

public class AgentServer extends Agent {
    @Override
    protected void setup() {
        PrivateKey privateKey = (PrivateKey) getArguments()[0];

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage aclMessage = receive();

                if (aclMessage != null){
                    try {
                        System.out.println("acl Message " +aclMessage);
                        String cryptedMsg = aclMessage.getContent();

                        byte[] encodedCryptedMsg = Base64.getDecoder().decode(cryptedMsg);
                        Cipher cipher = Cipher.getInstance("RSA");
                        cipher.init(Cipher.DECRYPT_MODE, privateKey);
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
