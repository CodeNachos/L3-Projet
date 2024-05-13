package SERVER;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import Model.Turn;

//THREAD FOR RECEIVING DATA FROM CLIENT

public class ServerReceiver implements Runnable {

    private ObjectInputStream objInput;
    private Socket socket;
    private BlockingQueue<Turn> receiveQueue;
    private Thread thread;
    private volatile boolean shouldClose = false;
    ServerReceiver(Socket socket,BlockingQueue<Turn> receiveQueue) throws IOException
    {
        this.socket = socket;
        this.objInput = new ObjectInputStream(socket.getInputStream());
        this.receiveQueue = receiveQueue;
    }


    @Override
    public void run() {
        thread = Thread.currentThread();
        while(socket.isConnected())
        {
            try {
                Turn receivedTurn = (Turn) objInput.readObject();
                if (shouldClose)
                    break;
                receiveQueue.put(receivedTurn);
                if (socket.isClosed())
                    break;

            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                break;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                break;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                break;
            }
            
        }
    }

    public void closeSocket()
    {
        shouldClose = true;
        if(thread!=null)
        {
            thread.interrupt();
        }
    }
    
}
