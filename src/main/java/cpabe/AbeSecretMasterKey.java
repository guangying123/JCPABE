package cpabe;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import it.unisa.dia.gas.jpbc.Element;
/**
 * A master secret key
 */
public class AbeSecretMasterKey {

    private AbePublicKey pubKey;
    /** Zr **/
    public Element             beta;
    /** G2 **/
    public Element             g_alpha;

    public AbePublicKey getPublicKey() {
        return pubKey;
    }

    public AbeSecretMasterKey(AbePublicKey pubKey, Element beta, Element g_alpha) {
        this.pubKey = pubKey;
        this.beta = beta;
        this.g_alpha = g_alpha;
    }
    
    private static AbeSecretMasterKey readFromStream(AbeInputStream stream) throws IOException {
        AbePublicKey pubKey = AbePublicKey.readFromStream(stream);
        stream.setPublicKey(pubKey);
        Element betaIn = stream.readElement();
        Element g_alphaIn = stream.readElement();
        return new AbeSecretMasterKey(pubKey, betaIn, g_alphaIn);
    }

    public static AbeSecretMasterKey readFromFile(File file) throws IOException {
    	try (AbeInputStream stream = new AbeInputStream(new FileInputStream(file))) {
    		return readFromStream(stream);
    	}
    }
    
    public static AbeSecretMasterKey readFromByteArray(byte[] data) throws IOException {
        try (AbeInputStream stream = new AbeInputStream(new ByteArrayInputStream(data))) {
        	return readFromStream(stream);
        }
    }

    public void writeToFile(File file) throws IOException {
    	try (AbeOutputStream fileStream = new AbeOutputStream(new FileOutputStream(file), pubKey)) {
    		writeToStream(fileStream);
    	}
    }
    
    private void writeToStream(OutputStream stream) throws IOException {
    	writeToStream(new AbeOutputStream(stream, pubKey));
    }
    
    public void writeToStream(AbeOutputStream stream) throws IOException {
        pubKey.writeToStream(stream);
        stream.writeElement(beta);
        stream.writeElement(g_alpha);
    }
    
    public byte[] getAsByteArray() throws IOException {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	this.writeToStream(baos);
    	return baos.toByteArray();
    }
}