public final class BroBot {
    public static final String chatBotName = "BroBot";
    private static void greet() {
        System.out.printf("Hello, I'm %s! What can I do for you?", BroBot.chatBotName);
    }

    public static void main (final String[] args) {
        BroBot.greet();
    }
}
