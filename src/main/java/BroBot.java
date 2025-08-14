import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Supplier;

public final class BroBot {
    private static final class Task implements Comparable<Task> {
        private final String taskName;
        private final int id;
        private final String logMessage;

        private static int nextFreeID = 1;

        private Task (final String taskName) {
            this.taskName = taskName;
            this.id = Task.nextFreeID;

            this.logMessage = String.format("%d. %s", this.id, this.taskName);

            Task.nextFreeID++;
        }

        @Override
        public boolean equals (final Object obj) {
            return this == obj;
        }

        @Override
        public int hashCode() {
            return this.id;
        }

        @Override
        public String toString() {
            return this.logMessage;
        }

        @Override
        public int compareTo (final Task otherTask) {
            return this.id - otherTask.id;
        }
    }

    public static final String chatBotName = "BroBot";
    private static final ArrayList<Task> taskList = new ArrayList<>();

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

        if (command.equals("list")) {
            BroBot.delimit();
            for (final Task task : taskList) {
                System.out.println(task);
            }
            BroBot.delimit();
            return true;
        }

        BroBot.delimit();
        BroBot.taskList.add(new Task(command));
        System.out.printf("added: %s\n", command);
        BroBot.delimit();
        return true;
    }

    public static void main (final String[] args) {
        BroBot.greet();
        while (BroBot.processCommand()) {

        }
    }
}
