package CLIENT;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import Model.Engine;
import Model.Turn;

public class ClientReceiver implements Runnable {

    private ObjectInputStream objInput;
    private Socket socket;
    private BlockingQueue<Turn> receiveQueue;
    private Thread curThread;
    private volatile boolean shouldClose = false;
    private volatile Engine receivedEngine;
    private volatile boolean hasReceivedEngine = false;

    ClientReceiver(Socket socket, BlockingQueue<Turn> receiveQueue) throws IOException {
        this.socket = socket;
        this.objInput = new ObjectInputStream(socket.getInputStream());
        this.receiveQueue = receiveQueue;
    }

    @Override
    public void run() {
        curThread = Thread.currentThread();
        try {
            receivedEngine = (Engine) objInput.readObject();
            hasReceivedEngine = true;
            System.out.println("Successfully received engine!");
            System.out.flush();

        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while (!shouldClose) {
            try {
                Turn receiveTurn = (Turn) objInput.readObject();
                if (shouldClose)
                    break;
                receiveQueue.put(receiveTurn);
            } catch (ClassNotFoundException | IOException e) {
                // TODO Auto-generated catch block
                break;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                break;
            }
        }
        try {
            socket.close();
            objInput.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            
        }

    }

    public void closeSocket()
    {
        shouldClose = true;
        if(curThread!=null)
        {
            curThread.interrupt();
        }
    }

    public Engine getEngine() throws InterruptedException
    {

        while (!hasReceivedEngine) { Thread.sleep(100);
        }
        return receivedEngine;
    }
}


