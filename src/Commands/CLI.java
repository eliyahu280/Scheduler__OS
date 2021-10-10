package Commands;

import java.util.ArrayList;

public class CLI {
    allCommands allcmd;
    ArrayList<allCommands.Command> commands;


    public CLI() {
        allcmd = new allCommands();
        commands = new ArrayList<>();
        commands.add(allcmd.new CreateProcesses());
        commands.add(allcmd.new CreateCPU());
        commands.add(allcmd.new SelectALG());
        commands.add(allcmd.new Running());
      /*  commands.add(allcmd.new DisplayResult());
        commands.add(allcmd.new ChangeALG());
        commands.add(allcmd.new Exit());*/


    }

    public void displayMenu() {
        for (allCommands.Command c : commands)
            System.out.println(c.description);
    }

    public void start() {

        displayMenu();
        for(allCommands.Command c : commands)
            c.execute();
    }


}
