package ma.enset.aes;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import ma.enset.CryptographyUtils;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class SimpleContainer {
    public static void main(String[] args) throws StaleProxyException, NoSuchAlgorithmException {
        Runtime runtime = Runtime.instance();
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(ProfileImpl.MAIN_HOST, "localhost");

        AgentContainer agentContainer = runtime.createAgentContainer(profile);
        System.out.println(agentContainer);

        //        Dans cette partie on génère la clé
        String secret = "123456123456123456123456";

        AgentController agentClient = agentContainer.createNewAgent("client", "ma.enset.aes.AgentClient", new Object[]{secret});
        AgentController agentServer = agentContainer.createNewAgent("server", "ma.enset.aes.AgentServer", new Object[]{secret});

        agentClient.start();
        agentServer.start();
    }
}
