import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public final class BroBot {
    private BroBot() {

    }

    private static final class Task implements Comparable<Task> {
        private final String taskName;
        private final int id;
        private String logMessage = null;

        private static int nextFreeID = 1;

        private boolean isDone = false;

        private Task (final String taskName) {
            this.taskName = taskName;
            this.id = Task.nextFreeID;
            Task.nextFreeID++;
        }

        private void mark() {
            this.isDone = true;
            this.logMessage = null;
        }

        private void unmark() {
            this.isDone = false;
            this.logMessage = null;
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
            if (this.logMessage == null) {
                this.logMessage = String.format("%d. [%c] %s", this.id, (this.isDone ? 'X' : ' '), this.taskName);
            }

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

    private static final Pattern markCommandPattern = Pattern.compile("^mark [1-9]\\d*$");
    private static final Pattern unmarkCommandPattern = Pattern.compile("^unmark [1-9]\\d*$");
    private static boolean processCommand() {
        final Supplier<String> commandRetriever = () -> {
            final String command = (new Scanner(System.in)).nextLine();
            return command;
        };

        final String command = commandRetriever.get();
        final Runnable taskAdder = () -> {
            BroBot.delimit();
            BroBot.taskList.add(new Task(command));
            System.out.printf("added: %s\n", command);
            BroBot.delimit();
        };

        if (command.equals("bye")) {
            BroBot.delimit();
            System.out.println("Bye. Hope to see you again soon!");
            BroBot.delimit();
            return false;
        }

        if (markCommandPattern.matcher(command).matches()) {
            final String[] tokens = command.split(" ");
            final int taskIndex = Integer.parseInt(tokens[1]) - 1;
            if (0 <= taskIndex && taskIndex < BroBot.taskList.size()) {
                BroBot.delimit();

                BroBot.taskList.get(taskIndex).mark();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("\t" + BroBot.taskList.get(taskIndex));

                BroBot.delimit();

                return true;
            } else {
                taskAdder.run();
                return true;
            }
        } else if (unmarkCommandPattern.matcher(command).matches()) {
            final String[] tokens = command.split(" ");
            final int taskIndex = Integer.parseInt(tokens[1]) - 1;

            if (0 <= taskIndex && taskIndex < BroBot.taskList.size()) {
                BroBot.delimit();


                BroBot.taskList.get(taskIndex).unmark();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("\t" + BroBot.taskList.get(taskIndex));

                BroBot.delimit();

                return true;
            } else {
                taskAdder.run();
                return true;
            }
        }

        if (command.equals("list")) {
            BroBot.delimit();
            for (final Task task : taskList) {
                System.out.println(task);
            }
            BroBot.delimit();
            return true;
        }

        taskAdder.run();
        return true;
    }

    public static void main (final String[] args) {
        BroBot.greet();
        while (BroBot.processCommand()) {

        }
    }
}
