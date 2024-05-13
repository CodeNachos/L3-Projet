package SERVER;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import Controller.Player;
import Controller.SmartAI;
import GLOBAL.Configurations;
import Model.*;
import View.*;

public class Server {
    
    private ServerSocket serverSocket;
    private BufferedReader br;
    private BufferedWriter bw;
    private Engine eng;
    private InterfaceTextuelle it;
    private BlockingQueue<Turn> sendQueue;
    private BlockingQueue<Turn> receiveQueue;

     

    public Server(ServerSocket serverSocket, Engine eng, InterfaceTextuelle it, BlockingQueue<Turn> sendQueue, BlockingQueue<Turn> receiveQueue)
    {
        this.serverSocket = serverSocket;
        this.eng = eng;
        this.it = it;
        this.sendQueue = sendQueue;
        this.receiveQueue = receiveQueue;
    }
    
    @SuppressWarnings("resource")
    public void startServer() throws InterruptedException
    {
        try{
            Socket socket = serverSocket.accept();
            System.out.println("Player 2 has joined the game!");
            ServerReceiver serverReceiver = new ServerReceiver(socket, receiveQueue);
            ServerEmitter serverEmitter = new ServerEmitter(socket, sendQueue,eng);
            Thread sR = new Thread(serverReceiver);
            Thread sE = new Thread(serverEmitter);
            sR.start();
            sE.start();
            while(true)
            {
                it.display();
                eng.playTurn(it.getPiece(), it.getCard(), it.getMove());
                eng.changePlayer();
                Turn turn = new Turn(it.getCard(), it.getPiece(), it.getMove());
                sendQueue.put(turn);
                if (eng.isGameOver()) {
                    System.out.println("Bravo! You won!");
                    System.out.flush();
                    serverEmitter.waitSent();
                    break;
                }
                System.out.println("Waiting for other player to play...");
                Turn otherTurn = receiveQueue.take();
                eng.playTurn(otherTurn.getPiece(), otherTurn.getCard(), otherTurn.getMove());
                eng.changePlayer();
                if (eng.isGameOver()) {
                    System.out.println("Other player won!");
                    System.out.flush();
                    eng.getGameConfiguration().displayConfig();
                    break;
                }
            }
            //System.out.println("Successfully left loop!");
            serverReceiver.closeSocket();
            serverEmitter.closeSocket();
            sR.join();
            sE.join();
            /*
            Scanner scanner = new Scanner(System.in);
            while(socket.isConnected())
            {
                System.out.println("Send message to client!");
                System.out.flush();
                String messageToSend = scanner.nextLine();
                bw.write(messageToSend);
                bw.newLine();
                bw.flush();
                String response = br.readLine();
                if (response == null)
                {
                    System.out.println("Client disconnected!");
                    break;
                }
                System.out.println(response);
                System.out.flush();

            }
            //bw.write("Hello from server!");
            //bw.newLine();
            //bw.flush();
            //String response = br.readLine();
            //System.out.println(response);
            closeEverything(socket);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        */
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void closeEverything(Socket socket) throws IOException
    {
        if (br != null)
            br.close();
        if (bw != null)
            bw.close();
        if (socket != null)
            socket.close();
    }

    public void playTurn()
    {
        it.display();
        eng.playTurn(it.getPiece(), it.getCard(), it.getMove());
        eng.changePlayer();
    }

    public void closeServerSocket()
    {
        try{
            if(serverSocket!=null)
            {
                serverSocket.close();
            }
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(1224);
        Engine eng = new Engine();
        Configurations config = new Configurations();
        Player Ai1 = new SmartAI(eng, 3, GameConfiguration.RED);
        Player Ai2 = new SmartAI(eng, 3, GameConfiguration.BLUE);
        InterfaceTextuelle it = new InterfaceTextuelle(config, eng, Ai1, Ai2);
        System.out.println("Player 1 is ready, waiting for player 2...");
        System.out.flush();
        BlockingQueue<Turn> sendQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Turn> receiveQueue = new LinkedBlockingQueue<>();
        Server server = new Server(serverSocket, eng, it, sendQueue, receiveQueue);
        server.startServer();
        server.closeServerSocket();
        
    }
}
