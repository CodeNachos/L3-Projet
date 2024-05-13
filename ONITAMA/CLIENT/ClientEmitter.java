package CLIENT;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import Model.Turn;

public class ClientEmitter implements Runnable {

    private Socket socket;
    private ObjectOutputStream objStream;
    private BlockingQueue<Turn> sendQueue;
    private volatile boolean shouldClose = false;
    private Thread thread;
    private volatile boolean sent = false;

    ClientEmitter(Socket socket, BlockingQueue<Turn> sendQueue) throws IOException
    {
        this.socket = socket;
        this.sendQueue = sendQueue;
        objStream = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        thread = Thread.currentThread();
        while (!shouldClose) {
            try {
                sent = false;
                Turn turn = sendQueue.take();
                if (shouldClose)
                    break;
                objStream.writeObject(turn);
                sent = true;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                break;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                break;
            }

        }
        try {
            socket.close();
            objStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
           
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

    public void waitSent() throws InterruptedException
    {
        while (!sent) {
        }
        return;
        
    }
    
    
}
