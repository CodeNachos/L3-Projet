Documentation
=========================

Contents
----------------
##### 1. Engine Presentation
1. Model
	- Game Engine
	- Collision Engine
2. View
	- Game Frame
	- Scene
3. Control
4. Entities
	- Game Object

##### 2. Wafflez: A simple game

##### 3. Creating your game

##### 4. Contributing on the project
2. Contributing on a game 
2. Contributing on the Engine

##### 5. Known problems

--- 
---

Engine Presentation
----------------

#### 1. Model

The _Model_ is composed as for now by the Game Engine alone. Plans for the future versions is to include also a Collision Engine.

###### Settings

	Engine/Global/Settings.java

This simple class holds static values used by the scene and that are available for the developed application.

| Name | Function |
|---|---|
| Dimension resolution | Defines the application window size. |
| boolean fullscreen | Whether the application is currently on fullscreen or not. |
| boolean resizable | Whether the window can be resized or not. |
| boolean stretch | Whether on resizing the window the contents should be resized. |
| int fixfps | Desired application FPS. |
| String applicationName | The name of the application. |
| int fullscreen_key | Key to be pressed to toggle fullscreen. |
| int undo_key | Key to be pressed to undo an action. |
| int redo_key | Key to be pressed to redo an action. |

This method is temporary and will probably be replaced in the future by a JSON.

---

###### Game Engine

	Engine/Core/Engines/GameEngine.java

The Game Engine runs on a independent thread and encapsulates the main loop of the application offering a friendly interface for the core of your program. \
It is responsible for managing the application window, updating components and refreshing the interface regularly using a frequency of your choosing (default 60 FPS). \
The updates, however, are updated on a frequency defined by the engine itself and its set to 60 UPS (updated per second). This is the frequency in which objects on the game are updated and its important to keep a fixed frequency across all devices. \
Here is quick explanation of the difference between FPS and UPS:
1. **FPS** (Frames Per Second):
	- FPS refers to the number of frames (images) displayed per second on your screen.
2. **UPS** (Updates Per Second):
	- UPS, on the other hand, represents the frequency at which the game's logic or simulation is updated per second.
	- It includes calculations related to player actions, AI behavior, physics simulations, and any other game mechanics.
	- Having a high UPS ensures that the game responds quickly to player inputs and maintains consistent gameplay mechanics.
	- In multiplayer games, UPS becomes even more critical to ensure synchronized gameplay among all players and prevent issues like desync or lag.

Here is a brief overview of the interface implemented:

| CONSTRUCTOR  | DESCRIPTION |
|---|---|
| public GameEngine() | Constructs a new GameEngine instance. |

| METHOD  | DESCRIPTION |
|---|---|
| public void start() | Starts the game engine and the game loop. To be run after properly configuring the Engine |
| public void setMainScene(Scene scn) | Sets the main scene of the game. |
| public void setCurrentScene(Scene scn) | Sets the current active scene of the game. |
| public Scene getCurrentScene() | Gets the current active scene of the game. |
| public void setResolution(Dimension res) | Sets the resolution of the game window. |
| public void stop() | Stops the game engine and exits the program. |
| public void pause() | Pauses the game loop. |
| public int getFPS() | return number of frames per second |
| public int getUPS() | return number of updates per second |
| public void setIcon(Image appicon) | Sets the icon for the application window. |

---

#### 2. View


#### 3. Control

#### 4. Entities


Using the Engine
----------------

To start using the Engine, you need to create a scene where your game objects will be displayed.

    
    import Engine.Core.Renderer.Scene;
    
    // Create a new scene
    Scene scene = new Scene();
        

Adding Objects to the Scene
---------------------------

You can add game objects, such as sprites, UI elements, or custom entities, to the scene.

    
    import Engine.Entities.GameObject;
    import Engine.Entities.UI.ColorArea;
    
    // Create a game object
    GameObject gameObject = new GameObject();
    
    // Add the game object to the scene
    scene.addComponent(gameObject);
    
    // Create a colored area UI object
    ColorArea colorArea = new ColorArea(Color.RED, new Dimension(100, 100));
    
    // Add the color area to the scene
    scene.addComponent(colorArea);
        

Setting Up the Game Engine
--------------------------

Before running your game, you need to set up the game engine.

    
    import Engine.Core.Engines.GameEngine;
    
    // Create a new game engine instance
    GameEngine engine = new GameEngine();
    
    // Set the current scene
    engine.setCurrentScene(scene);
        

Starting the Engine
-------------------

Once everything is set up, you can start the game engine to run your game.

    
    // Start the game engine
    engine.start();
        

Handling Input
--------------

You can handle user input, such as keyboard or mouse events, to interact with your game objects.
You just need to ovewrite those functions in any class inherited from a GameObject.

    
    import java.awt.event.KeyEvent;
    import java.awt.event.MouseEvent;
    
    // Implement input handling methods in your game objects
    public void input(KeyEvent e) {
        // Handle keyboard input
    }
    
    public void input(MouseEvent e) {
        // Handle mouse input
    }
        

Updating Game Logic
-------------------

Each GameObject in your scene can have a `process()` method, which is called at every frame update. You can use this method to update the game logic, perform calculations, and manage object behaviors.

    
    // Example implementation of the process method in a game object
    public void process() {
        // Update game logic here
        // Perform calculations, handle collisions, etc.
    }
        

Conclusion
----------

This documentation covers the basic usage of the Engine framework, including scene creation, object addition, engine setup, input handling, and game logic updating. You can explore additional features and functionalities provided by the Engine to create more complex and interactive applications. Refer to Rafael for more detailed information and advanced usage.

New UI features have been added, such as support for menus using the default Java Swing library, documentation will be added soon. Meanwhile you can also check the provided code for the WaffleGame menus implementation.