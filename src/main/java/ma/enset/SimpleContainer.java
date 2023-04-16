package ma.enset;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

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

        //        Dans cette partie on génère les clés publics et privés
        KeyPair keyPair = CryptographyUtils.generateRSAKeys();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        AgentController agentClient = agentContainer.createNewAgent("client", "ma.enset.AgentClient", new Object[]{publicKey});
        AgentController agentServer = agentContainer.createNewAgent("server", "ma.enset.AgentServer", new Object[]{privateKey});

        agentClient.start();
        agentServer.start();
    }
}
