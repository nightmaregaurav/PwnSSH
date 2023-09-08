public class RootChecker {
    public static void ensureRootUser() {
        String username = System.getProperty("user.name");
        if (username != "root") {
            System.err.println("This program must be run as root. " + username + " cannot execute this program.");
            System.exit(1);
        }
    }
}
