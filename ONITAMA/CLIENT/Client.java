package CLIENT;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import Controller.Player;
import Controller.SmartAI;
import GLOBAL.Configurations;
import Model.Engine;
import Model.GameConfiguration;
import Model.Turn;
import View.InterfaceTextuelle;

public class Client {
    private Socket socket;
    private BlockingQueue<Turn> sendQueue;
    private BlockingQueue<Turn> receiveQueue;

    boolean quit;

    public Client(Socket socket, BlockingQueue<Turn> sendQueue,
            BlockingQueue<Turn> receiveQueue) throws IOException {
        this.socket = socket;
        this.sendQueue = sendQueue;
        this.receiveQueue = receiveQueue;
        quit = false;
    }

    public void startClient() throws IOException, InterruptedException {

        ClientEmitter clientEmitter = new ClientEmitter(socket, sendQueue);
        ClientReceiver clientReceiver = new ClientReceiver(socket, receiveQueue);
        Thread cE = new Thread(clientEmitter);
        Thread cR = new Thread(clientReceiver);
        cE.start();
        cR.start();
        Engine eng = clientReceiver.getEngine();
        Configurations config = new Configurations();
        Player Ai1 = new SmartAI(eng, 3, GameConfiguration.RED);
        Player Ai2 = new SmartAI(eng, 3, GameConfiguration.BLUE);
        InterfaceTextuelle it = new InterfaceTextuelle(config, eng, Ai1, Ai2);
        while(true)
        {
            System.out.println("Waiting for other player to play...");
            Turn otherTurn = receiveQueue.take();
            eng.playTurn(otherTurn.getPiece(), otherTurn.getCard(), otherTurn.getMove());
            eng.changePlayer();
            if (eng.isGameOver()) {
                System.out.println("Other player won!");
                System.out.flush();
                eng.getGameConfiguration().displayConfig();
                //clientReceiver.closeSocket();
                //clientEmitter.closeSocket();
                break;
            }
            it.display();
            eng.playTurn(it.getPiece(), it.getCard(), it.getMove());
            eng.changePlayer();
            Turn turn = new Turn(it.getCard(), it.getPiece(), it.getMove());
            sendQueue.put(turn);
            if (eng.isGameOver()) {
                System.out.println("Bravo! You won!");
                System.out.flush();
                clientEmitter.waitSent();
                //eng.getGameConfiguration().displayConfig();
                //clientReceiver.closeSocket();
                //clientEmitter.closeSocket();
                break;
            }

        }
        //System.out.println("Left loop successfully!");
        socket.close();
        clientReceiver.closeSocket();
        clientEmitter.closeSocket();
        cR.join();
        //System.out.println("Receiver thread successfully end!");
        System.out.flush();
        cE.join();
    }

    public void sendMessage() throws IOException
    {

        /*
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Send message to server!");
            String message = scanner.nextLine();
            if (message.equals("Quit"))
            {
                quit = true;
                return;
            }
            //bw.write(message);
            //bw.newLine();
            //bw.flush();
                */
        //} catch (IOException e) {
            //closeEverything();
        //}
    }

public void listenForMessage()
    {
/* 
        try{
            //String msgReceived = br.readLine();
            //System.out.println(msgReceived);

        } catch (IOException e)
        {
            e.printStackTrace();
            
        }
        */
    }


    public void closeEverything() throws IOException
    {
        if (socket != null)
            socket.close();
    }

    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        Socket socket = new Socket("localhost", 1224);
        //Engine eng = new Engine();
        //Configurations config = new Configurations();
        //Player Ai1 = new SmartAI(eng, 3, GameConfiguration.RED);
        //Player Ai2 = new SmartAI(eng, 3, GameConfiguration.BLUE);
        //InterfaceTextuelle it = new InterfaceTextuelle(config, eng, Ai1, Ai2);
        BlockingQueue<Turn> sendQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Turn> receiveQueue = new LinkedBlockingQueue<>();
        Client client = new Client(socket, sendQueue, receiveQueue);
        client.startClient();
    }
    
}
