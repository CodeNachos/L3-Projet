Game Engine Documentation
=========================

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