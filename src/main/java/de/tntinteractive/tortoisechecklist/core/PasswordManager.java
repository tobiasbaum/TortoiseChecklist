package de.tntinteractive.tortoisechecklist.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

public class PasswordManager {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private KeyStore keystore;

    public synchronized void registerNeededPassword(final String passwordKey) throws GeneralSecurityException, IOException {
        final KeyStore ks = this.loadKeyStore();
        final SecretKey e = (SecretKey) ks.getKey(passwordKey, this.getPw2());
        if (e == null) {
            final String password = JOptionPane.showInputDialog(passwordKey);
            if (password == null) {
                return;
            }
            ks.setKeyEntry(passwordKey, new SecretKeySpec(password.getBytes(UTF8), ""), this.getPw2(), null);
            this.storeKeyStore(ks);
        }
    }

    public synchronized String getPassword(final String passwordKey) throws GeneralSecurityException, IOException {
        final KeyStore ks = this.loadKeyStore();
        final SecretKey e = (SecretKey) ks.getKey(passwordKey, this.getPw2());
        if (e == null) {
            return null;
        }
        return new String(e.getEncoded(), UTF8);
    }

    private KeyStore loadKeyStore() throws GeneralSecurityException, IOException {
        if (this.keystore != null) {
            return this.keystore;
        }
        final KeyStore ks = KeyStore.getInstance("JCEKS");

        // get user password and file input stream
        final char[] password = this.getPw();

        final File file = this.getKeystoreFile();
        if (file.exists()) {
            final java.io.FileInputStream fis = new FileInputStream(file);
            try {
                ks.load(fis, password);
            } finally {
                fis.close();
            }
        } else {
            ks.load(null, password);
        }
        this.keystore = ks;
        return ks;
    }

    private void storeKeyStore(final KeyStore ks) throws GeneralSecurityException,  IOException {
        final FileOutputStream stream = new FileOutputStream(this.getKeystoreFile());
        try {
            ks.store(stream, this.getPw());
        } finally {
            stream.close();
        }
    }

    private char[] getPw() {
        return new char[] {'1', '%', '4', '#', '*', '6', '+', '-', 'm', '1', '4', 'w', '4', 'Q', '#', '7'};
    }

    private char[] getPw2() {
        return new char[] {'4', 'q', 'G', 't', 'r', 't', 'w', 'Y', '1', 'j'};
    }

    private File getKeystoreFile() {
        return new File(System.getProperty("user.home"), ".trtschklst");
    }

}
