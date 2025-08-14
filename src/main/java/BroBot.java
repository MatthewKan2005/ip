import java.util.Scanner;
import java.util.function.Supplier;

public final class BroBot {
    public static final String chatBotName = "BroBot";
    private static void greet() {
        BroBot.delimit();
        System.out.printf("Hello, I'm %s! What can I do for you?\n", BroBot.chatBotName);
        BroBot.delimit();
    }

    private static void delimit() {
        System.out.println("____________________________________________________________");
    }

    private static boolean processCommand() {
        final Supplier<String> commandRetriever = () -> {
            final String command = (new Scanner(System.in)).nextLine();
            return command;
        };

        final String command = commandRetriever.get();
        if (command.equals("bye")) {
            BroBot.delimit();
            System.out.println("Bye. Hope to see you again soon!");
            BroBot.delimit();
            return false;
        }

        BroBot.delimit();
        System.out.println(command);
        BroBot.delimit();
        return true;
    }

    public static void main (final String[] args) {
        BroBot.greet();
        while (BroBot.processCommand()) {

        }
    }
}
