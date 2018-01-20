package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shell implements Runnable {
    private Thread thread;
    private String command;

    // Constructor
    public Shell(String command){
        this.command = command;
    }

    // Run the command and get the output
    @Override
    public void run(){

        Runtime runtime = Runtime.getRuntime();
        Process process = null;

        try {
            process = runtime.exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while (true) {

                // Read output
                while (br.ready()) {
                    String line = br.readLine();
                    System.out.println(">> " + line);
                }

                try {
                    // Execution finished
                    int exitCode = process.exitValue();
                    System.out.println("Execution finished!\nExit code: " + exitCode);
                    break;
                } catch (IllegalThreadStateException ex) {
                    // Ignore
                }

                // Wait 200ms
                Thread.sleep(200);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    // Start the command execution
    public void execute(){
        System.out.println("Executing command... : " + this.command);
        if (this.thread == null) {
            this.thread = new Thread (this, "sdf");
            this.thread.start ();
        }else {
            System.out.println(">>>>>");
            this.thread = new Thread (this, "sdf");
            this.thread.start ();
            System.out.println("<<<<<");
        }
    }

    // Set command
    public void setCommand(String command){
        this.command = command;
    }

}
