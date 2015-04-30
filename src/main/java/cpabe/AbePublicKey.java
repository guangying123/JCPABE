package cpabe;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.parameters.PropertiesParameters;

public class AbePublicKey {
    /*
     * A public key
     */
    private String pairingDesc;
    private transient Pairing p;
    /** G_1 **/
    public Element  g;
    /** G_1 **/
    public Element  h;
    /** G_1 **/
    public Element  f;
    /** G_2 **/
    public Element  gp;
    /** G_T **/
    public Element  g_hat_alpha;

    /**
     * Creates a new AbePublicKey. This key should only be used after the elements have been set (setElements).
     * 
     * @param pairingDescription
     */
    public AbePublicKey(String pairingDescription) {
        this.pairingDesc = pairingDescription;
    }
    
    public String getPairingDescription() {
        return pairingDesc;
    }

    public Pairing getPairing() {
        if (p == null) {
            PairingParameters params = new PropertiesParameters().load(new ByteArrayInputStream(pairingDesc.getBytes()));
            p = PairingFactory.getPairing(params);
        }
        return p;
    }

    public void setElements(Element g, Element h, Element f, Element gp, Element g_hat_alpha) {
        this.g = g;
        this.h = h;
        this.f = f;
        this.gp = gp;
        this.g_hat_alpha = g_hat_alpha;
    }

    public static AbePublicKey readFromFile(File file) throws IOException {
        try (AbeInputStream stream = new AbeInputStream(new FileInputStream(file))) {
        	return readFromStream(stream);
        }
    }

    public static AbePublicKey readFromStream(AbeInputStream stream) throws IOException {
        String pairingDescription = stream.readString();
        AbePublicKey publicKey = new AbePublicKey(pairingDescription);
        stream.setPublicKey(publicKey);
        publicKey.g = stream.readElement();
        publicKey.h = stream.readElement();
        publicKey.f = stream.readElement();
        publicKey.gp = stream.readElement();
        publicKey.g_hat_alpha = stream.readElement();
        return publicKey;
    }

    public void writeToStream(AbeOutputStream stream) throws IOException {
        stream.writeString(pairingDesc);
        stream.writeElement(g);
        stream.writeElement(h);
        stream.writeElement(f);
        stream.writeElement(gp);
        stream.writeElement(g_hat_alpha);
    }

    public void writeToFile(File file) throws IOException {
        try (AbeOutputStream fos = new AbeOutputStream(new FileOutputStream(file), this)) {
        	writeToStream(fos);
        }
    }
}