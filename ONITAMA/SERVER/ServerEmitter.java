package SERVER;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import Model.Engine;
import Model.Turn;

//THREAD for emitting data to client
public class ServerEmitter implements Runnable {

    private Socket socket;
    private ObjectOutputStream objStream;
    private BlockingQueue<Turn> sendQueue;
    private Thread thread;
    private volatile boolean shouldClose = false;
    private volatile boolean sent = false;
    Engine eng;

    ServerEmitter(Socket socket, BlockingQueue<Turn> sendQueue, Engine eng) throws IOException
    {
        this.socket = socket;
        this.objStream = new ObjectOutputStream(socket.getOutputStream());
        this.sendQueue = sendQueue;
        this.eng = eng;
    }
    @Override
    public void run() {
        thread = Thread.currentThread();
        try {
            objStream.writeObject(eng);
            //System.out.println("Engine sent!");
            //System.out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block

        }
        while (socket.isConnected()) {
            try {
                sent = false;
                Turn turn = sendQueue.take();
                if (shouldClose)
                    break;
                objStream.writeObject(turn);
                sent = true;
                if (socket.isClosed())
                    break;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                break;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                break;
            }

        }
    }
    
    public void closeSocket()
    {
        shouldClose = true;
        if (thread != null) {
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
